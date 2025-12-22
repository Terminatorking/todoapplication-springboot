package ghaimoradi.soheil.todoapp.controllers;

import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.services.UserService;
import ghaimoradi.soheil.todoapp.utils.encryption.SHA256Hasher;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SuppressWarnings("all")
@RequestMapping("api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session
    ) {
        User user = userService.getUserByUserName(username);
        if (user != null && user.getPassword().equals(SHA256Hasher.hash(password))) {
            session.setAttribute("user", user);
            return "redirect:/api/v1/tasks";
        } else {
            return "redirect:/api/v1/login?error";
        }
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password
    ) {
        if (userService.getUserByUserName(username) != null) {
            return "redirect:/api/v1/register?error";
        }
        userService.register(username, password);
        return "redirect:/api/v1/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/api/v1/login";
    }
}
