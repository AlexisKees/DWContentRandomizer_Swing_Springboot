package AlexisKeesBahl.DWRandomizer_Swing.repository;

import AlexisKeesBahl.DWRandomizer_Swing.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower,Integer> {
}
