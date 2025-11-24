package ghaimoradi.soheil.todoapp.repositories;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
