package br.com.balanzo.api.v1;

import br.com.balanzo.application.tarefas.CreateTask;
import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import br.com.balanzo.domain.tarefas.entity.Task;
import br.com.balanzo.domain.tarefas.entity.TaskPriority;
import br.com.balanzo.infrastructure.persistence.familia.FamilyMemberRepository;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/families/{familyId}/tasks")
public class TasksController {

    private final TaskRepository taskRepository;
    private final CreateTask createTask;
    private final FamilyMemberRepository familyMemberRepository;

    public TasksController(TaskRepository taskRepository, CreateTask createTask,
                           FamilyMemberRepository familyMemberRepository) {
        this.taskRepository = taskRepository;
        this.createTask = createTask;
        this.familyMemberRepository = familyMemberRepository;
    }

    @GetMapping
    public ResponseEntity<List<TaskSummary>> list(Principal principal, @PathVariable UUID familyId) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        var member = familyMemberRepository.findByFamilyIdAndUserId(familyId, userId);
        if (member.isEmpty() || member.get().getStatus() != FamilyMemberStatus.active) {
            return ResponseEntity.status(403).build();
        }
        var tasks = taskRepository.findByFamilyIdOrderByDueDateAsc(familyId);
        return ResponseEntity.ok(tasks.stream().map(this::toSummary).toList());
    }

    @PostMapping
    public ResponseEntity<TaskSummary> create(Principal principal,
                                              @PathVariable UUID familyId,
                                              @Valid @RequestBody CreateTaskRequest req) {
        UUID userId = requireUserId(principal);
        Task t = createTask.run(userId, familyId, req.title(), req.description(),
                req.assignedToId(), req.priority(), req.dueDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(toSummary(t));
    }

    private UUID userIdFrom(Principal principal) {
        if (principal instanceof Jwt jwt && jwt.getSubject() != null) {
            try {
                return UUID.fromString(jwt.getSubject());
            } catch (IllegalArgumentException ignored) {}
        }
        return null;
    }

    private UUID requireUserId(Principal principal) {
        UUID userId = userIdFrom(principal);
        if (userId == null) throw new IllegalArgumentException("Authentication required");
        return userId;
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
