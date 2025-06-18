package tania.dp.missiongen.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tania.dp.missiongen.Models.Mission;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Integer> {

}
