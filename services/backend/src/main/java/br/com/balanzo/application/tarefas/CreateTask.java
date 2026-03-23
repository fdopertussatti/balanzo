package br.com.balanzo.application.tarefas;

import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.common.exception.ResourceNotFoundException;
import br.com.balanzo.domain.tarefas.entity.Task;
import br.com.balanzo.domain.tarefas.entity.TaskPriority;
import br.com.balanzo.infrastructure.persistence.familia.FamilyRepository;
import br.com.balanzo.infrastructure.persistence.tarefas.TaskRepository;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTask {

    private final TaskRepository taskRepository;
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    public CreateTask(TaskRepository tr, FamilyRepository fr, UserRepository ur) {
        this.taskRepository = tr;
        this.familyRepository = fr;
        this.userRepository = ur;
    }

    /**
     * @param userId caller (must be authorized for family scope by the API layer before invocation)
     */
    @Transactional
    public Task run(UUID userId, UUID familyId, String title, String description,
                    UUID assignedToId, TaskPriority priority, LocalDate dueDate) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFoundException("Family", familyId));

        Task task = new Task(family, title);
        if (description != null) task.setDescription(description);
        if (priority != null) task.setPriority(priority);
        if (dueDate != null) task.setDueDate(dueDate);
        if (assignedToId != null) {
            userRepository.findById(assignedToId).ifPresent(task::setAssignedTo);
        }

        return taskRepository.save(task);
    }
}
