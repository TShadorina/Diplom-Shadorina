package tania.dp.missiongen.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String missiontext;

    @ManyToMany(mappedBy = "likedMissions")
    private final List<User> likedByUsers = new ArrayList<>();

    public Mission() {
    }
    public Mission(String mission) {
        this.missiontext = mission;
    }

    public String getMission() {
        return missiontext;
    }

    public void setMission(String mission) {
        this.missiontext = mission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
