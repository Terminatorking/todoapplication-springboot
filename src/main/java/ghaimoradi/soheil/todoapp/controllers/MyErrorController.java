package ghaimoradi.soheil.todoapp.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SuppressWarnings("all")
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "pageNotFound";
    }
}