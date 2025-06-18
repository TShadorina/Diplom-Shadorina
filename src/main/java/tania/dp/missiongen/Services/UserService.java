package tania.dp.missiongen.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tania.dp.missiongen.Models.Mission;
import tania.dp.missiongen.Repos.MissionRepository;
import tania.dp.missiongen.Repos.UserRepository;

import java.util.Optional;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MissionRepository missionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<tania.dp.missiongen.Models.User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .build();
        }else{
            throw new UsernameNotFoundException("Usera ne znaideno!");
        }
    }

    public void CheckLvl(tania.dp.missiongen.Models.User user){
        if(user.getXpToNextLevel(user.getLvl()) <= user.getExp()){
            user.setExp(user.getExp() - user.getXpToNextLevel(user.getLvl()));
            user.setLvl(user.getLvl()+1);
            userRepository.save(user);
        }
    }
    public void likeMission(int userId, int missionId) {
        tania.dp.missiongen.Models.User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new RuntimeException("Mission not found"));

        if (!user.getLikedMissions().contains(mission)) {
            user.getLikedMissions().add(mission);
            userRepository.save(user);
        }
    }
}

