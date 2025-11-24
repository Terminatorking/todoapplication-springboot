package ghaimoradi.soheil.todoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SuppressWarnings("all")
@RequestMapping("api/v1")
public class ViewController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String signup(){
        return "register";
    }

    @GetMapping("/tasks")
    public String home(){
        return "tasks";
    }
}