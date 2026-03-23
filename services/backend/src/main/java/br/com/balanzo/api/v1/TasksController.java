package br.com.balanzo.api.v1;

import br.com.balanzo.application.tarefas.CreateTask;
import br.com.balanzo.domain.tarefas.entity.Task;
import br.com.balanzo.domain.tarefas.entity.TaskPriority;
import br.com.balanzo.infrastructure.persistence.tarefas.TaskRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.balanzo.common.security.CurrentUserResolver;
import br.com.balanzo.security.authorization.FamilyScopeAccess;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/families/{familyId}/tasks")
public class TasksController {

    private final TaskRepository taskRepository;
    private final CreateTask createTask;
    private final FamilyScopeAccess familyScopeAccess;
    private final CurrentUserResolver currentUser;

    public TasksController(TaskRepository taskRepository, CreateTask createTask,
                           FamilyScopeAccess familyScopeAccess,
                           CurrentUserResolver currentUser) {
        this.taskRepository = taskRepository;
        this.createTask = createTask;
        this.familyScopeAccess = familyScopeAccess;
        this.currentUser = currentUser;
    }

    @GetMapping
    public ResponseEntity<List<TaskSummary>> list(Principal principal, @PathVariable UUID familyId) {
        UUID userId = currentUser.require(principal);
        familyScopeAccess.requireMemberCanView(userId, familyId);
        var tasks = taskRepository.findByFamilyIdOrderByDueDateAsc(familyId);
        return ResponseEntity.ok(tasks.stream().map(this::toSummary).toList());
    }

    @PostMapping
    public ResponseEntity<TaskSummary> create(Principal principal,
                                              @PathVariable UUID familyId,
                                              @Valid @RequestBody CreateTaskRequest req) {
        UUID userId = currentUser.require(principal);
        familyScopeAccess.requireMemberCanEdit(userId, familyId);
        Task t = createTask.run(userId, familyId, req.title(), req.description(),
                req.assignedToId(), req.priority(), req.dueDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(t));
    }

    private TaskSummary toSummary(Task t) {
        return new TaskSummary(t.getId(), t.getTitle(), t.getStatus().name(), t.getPriority().name(),
                t.getDueDate(), t.getAssignedTo() != null ? t.getAssignedTo().getId() : null);
    }

    public record TaskSummary(UUID id, String title, String status, String priority,
                              LocalDate dueDate, UUID assignedToId) {}

    public record CreateTaskRequest(
            @NotBlank String title,
            String description,
            UUID assignedToId,
            TaskPriority priority,
            LocalDate dueDate
    ) {}
}
