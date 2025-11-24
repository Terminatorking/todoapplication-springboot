package ghaimoradi.soheil.todoapp.controllers;

import ghaimoradi.soheil.todoapp.models.User;
import ghaimoradi.soheil.todoapp.services.UserService;
import ghaimoradi.soheil.todoapp.utils.encryption.AES;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SuppressWarnings("all")
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username);
        if (user != null && AES.decryptAES(user.getPassword()).equals(password)) {
            return "redirect:/api/v1/tasks";
        } else {
            return "redirect:/api/v1/login?error";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return "redirect:/api/v1/register?error";
        }
        userService.register(username, password);
        return "redirect:/api/v1/tasks";
    }
}