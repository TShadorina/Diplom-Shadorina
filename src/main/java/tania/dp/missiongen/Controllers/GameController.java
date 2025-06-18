package tania.dp.missiongen.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tania.dp.missiongen.Models.Mission;
import tania.dp.missiongen.Models.User;
import tania.dp.missiongen.Repos.MissionRepository;
import tania.dp.missiongen.Repos.UserRepository;
import tania.dp.missiongen.Services.UserService;

import java.util.List;
import java.util.Random;

@Controller
public class GameController {
    @Autowired
    MissionRepository missionRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserService userService;

    @GetMapping("/play")
    public String play(Model model) {
        List<Mission> missions = missionRepo.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Random random = new Random();
        model.addAttribute("mission", missions.get(random.nextInt(0, missions.size())));
        model.addAttribute("exp", user.getExp());
        model.addAttribute("lvl", user.getLvl());
        model.addAttribute("needexp", user.getXpToNextLevel(user.getLvl()));
        return "play";
    }
    @GetMapping("/play/comp")
    public String playComp(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setExp(user.getExp() + 25);
        userService.CheckLvl(user);
        userRepo.save(user);
        return play(model);
    }
    @GetMapping("/play/fav/{id}")
    public String playFav(Model model, @PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userService.likeMission(user.getId(), id);
        List<Mission> mis = user.getLikedMissions();
        return play(model);
    }
    @GetMapping("/play/favs")
    public String favorites(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("likedMissions", user.getLikedMissions());
        model.addAttribute("exp", user.getExp());
        model.addAttribute("lvl", user.getLvl());
        model.addAttribute("needexp", user.getXpToNextLevel(user.getLvl()));
        return "favorites";
    }
    @GetMapping("/play/fav/remove/{id}")
    public String favoriteDel(Model model, @PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Mission> liked = user.getLikedMissions();
        liked.removeIf(mission -> mission.getId() == id);
        user.setLikedMissions(liked);
        userRepo.save(user);
        model.addAttribute("likedMissions", user.getLikedMissions());
        model.addAttribute("exp", user.getExp());
        model.addAttribute("lvl", user.getLvl());
        model.addAttribute("needexp", user.getXpToNextLevel(user.getLvl()));
        return "favorites";
    }
}
