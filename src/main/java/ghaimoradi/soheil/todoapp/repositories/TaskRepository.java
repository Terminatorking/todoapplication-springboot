package ghaimoradi.soheil.todoapp.repositories;

import ghaimoradi.soheil.todoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
