package br.com.balanzo.application.tarefas;

import br.com.balanzo.domain.familia.entity.Family;
import br.com.balanzo.domain.familia.entity.FamilyMember;
import br.com.balanzo.domain.familia.entity.FamilyMemberRole;
import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import br.com.balanzo.domain.tarefas.entity.Task;
import br.com.balanzo.domain.tarefas.entity.TaskPriority;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.familia.FamilyMemberRepository;
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
    private final FamilyMemberRepository familyMemberRepository;
    private final UserRepository userRepository;

    public CreateTask(TaskRepository tr, FamilyRepository fr, FamilyMemberRepository fmr,
                      UserRepository ur) {
        this.taskRepository = tr;
        this.familyRepository = fr;
        this.familyMemberRepository = fmr;
        this.userRepository = ur;
    }

    @Transactional
    public Task run(UUID userId, UUID familyId, String title, String description,
                    UUID assignedToId, TaskPriority priority, LocalDate dueDate) {
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new IllegalArgumentException("Family not found: " + familyId));

        FamilyMember member = familyMemberRepository.findByFamilyIdAndUserId(familyId, userId)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this family"));
        if (member.getStatus() != FamilyMemberStatus.active) {
            throw new IllegalArgumentException("User is not an active member");
        }

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
