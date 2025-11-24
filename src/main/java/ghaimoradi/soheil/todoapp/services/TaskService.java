package ghaimoradi.soheil.todoapp.services;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public void createTask(String title, User user) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        task.setUser(user);
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
}