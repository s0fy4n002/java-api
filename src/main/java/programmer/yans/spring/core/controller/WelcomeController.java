package programmer.yans.spring.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeController {
    
    @GetMapping
    public String welcome() {
        return "Welcome to Spring Framework!";
    }

    @PostMapping
    public String createWelcomeMessage() {
        return "Welcome message created!";
    }
}
