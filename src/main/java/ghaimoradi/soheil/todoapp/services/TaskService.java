package ghaimoradi.soheil.todoapp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.repositories.TaskRepository;

@Service
@SuppressWarnings("all")
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUser(User user, String filter) {
        if (Objects.equals(filter, "completed")) {
            return taskRepository.findByUserAndCompleted(user, true);
        } else if (Objects.equals(filter, "incomplete")) {
            return taskRepository.findByUserAndCompleted(user, false);
        }
        return taskRepository.findByUser(user);
    }

    public void createTask(String title, User user, LocalDateTime reminderDate) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setUser(user);
        task.setReminderDate(reminderDate);
        task.setReminderSent(reminderDate == null);
        taskRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Task Id!")
        );
        if (task.getUser().equals(user)) {
            taskRepository.deleteById(id);
        }
    }

    public void toggleTask(Long id, User user) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Task Id!")
        );
        if (task.getUser().equals(user)) {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void updateTask(Long id, String title, LocalDateTime reminderDate, User user) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Task Id!")
        );
        if (task.getUser().equals(user)) {
            task.setTitle(title);
            task.setReminderDate(reminderDate);
            task.setReminderSent(reminderDate == null);
            taskRepository.save(task);
        }
    }
}