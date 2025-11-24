package ghaimoradi.soheil.todoapp.controllers;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.services.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SuppressWarnings("all")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/v1/tasks")
    public String getTasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        List<Task> tasks = taskService.getTasksByUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping("/api/v1/tasks")
    public String createTask(@RequestParam String title, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.createTask(title, user);
        return "redirect:/api/v1/tasks";
    }

    @GetMapping("/api/v1/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.deleteTask(id, user);
        return "redirect:/api/v1/tasks";
    }

    @GetMapping("/api/v1/tasks/{id}/toggle")
    public String toggleTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.toggleTask(id, user);
        return "redirect:/api/v1/tasks";
    }
}