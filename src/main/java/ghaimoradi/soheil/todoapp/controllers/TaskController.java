package ghaimoradi.soheil.todoapp.controllers;

import ghaimoradi.soheil.todoapp.models.Task;
import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.services.TaskService;
import ghaimoradi.soheil.todoapp.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@SuppressWarnings("all")
@RequestMapping("api/v1")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(
            TaskService taskService,
            UserService userService
    ) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public String getTasks(
            Model model,
            HttpSession session,
            @RequestParam(required = false, defaultValue = "all")
            String filter
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        List<Task> tasks = taskService.getTasksByUser(user, filter);
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        model.addAttribute("currentFilter", filter);
        return "tasks";
    }

    @GetMapping("/search")
    public String searchTasks(
            @RequestParam(value = "query", required = false)
            String query,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        if (query != null && !query.trim().isEmpty()) {
            List<Task> tasks = taskService.searchTasks(user, query);
            model.addAttribute("tasks", tasks);
        }
        model.addAttribute("query", query);
        model.addAttribute("user", user);
        return "search";
    }

    @PostMapping("/tasks")
    public String createTask(
            @RequestParam String title,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime reminderDate,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.createTask(title, user, reminderDate);
        return "redirect:/api/v1/tasks";
    }

    @GetMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.deleteTask(id, user);
        return "redirect:/api/v1/tasks";
    }

    @GetMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.toggleTask(id, user);
        return "redirect:/api/v1/tasks";
    }

    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        Task task = taskService.getTaskById(id);

        if (task == null || !task.getUser().equals(user)) {
            return "redirect:/api/v1/tasks";
        }

        model.addAttribute("task", task);
        model.addAttribute("user", user);
        return "editTask";
    }

    @PostMapping("/tasks/{id}/edit")
    public String updateTask(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime reminderDate,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/api/v1/login";
        }
        taskService.updateTask(id, title, reminderDate, user);
        return "redirect:/api/v1/tasks";
    }
}