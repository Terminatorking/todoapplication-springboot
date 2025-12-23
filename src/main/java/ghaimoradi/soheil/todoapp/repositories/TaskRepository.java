package ghaimoradi.soheil.todoapp.repositories;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByReminderDate(LocalDateTime dateTime);
    List<Task> findByUserAndCompleted(User user, boolean completed);
    List<Task> findByUserAndHasReminder(User user, boolean hasReminder);
    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String query);
}
