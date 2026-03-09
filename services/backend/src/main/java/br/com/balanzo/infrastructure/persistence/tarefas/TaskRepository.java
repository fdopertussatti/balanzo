package br.com.balanzo.infrastructure.persistence.tarefas;

import br.com.balanzo.domain.tarefas.entity.Task;
import br.com.balanzo.domain.tarefas.entity.TaskStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByFamilyIdOrderByDueDateAsc(UUID familyId);

    List<Task> findByFamilyIdAndStatus(UUID familyId, TaskStatus status);
}
