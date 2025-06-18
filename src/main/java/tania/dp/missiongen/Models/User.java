package tania.dp.missiongen.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private int lvl;
    private int exp;

    @ManyToMany
    @JoinTable(
            name = "liked_missions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id")
    )
    private List<Mission> likedMissions = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.lvl = 1;
        this.exp = 0;
    }
    public int getXpToNextLevel(int level) {
        int base = 100;
        double exponent = 1.15;
        return (int) Math.floor(base * Math.pow(level, exponent));
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public List<Mission> getLikedMissions() {
        return likedMissions;
    }

    public void setLikedMissions(List<Mission> likedMissions) {
        this.likedMissions = likedMissions;
    }
}
