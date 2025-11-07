package AlexisKeesBahl.DWRandomizer_Swing.repository;

import AlexisKeesBahl.DWRandomizer_Swing.model.Danger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DangerRepository extends JpaRepository<Danger,Integer> {
}
