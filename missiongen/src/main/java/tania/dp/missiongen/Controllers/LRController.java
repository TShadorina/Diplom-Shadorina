package tania.dp.missiongen.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tania.dp.missiongen.Models.User;
import tania.dp.missiongen.Repos.UserRepository;

@Controller
public class LRController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        User user = new User(username, Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(password));
        userRepository.save(user);
        return "redirect:/login";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/play";
    }
}
