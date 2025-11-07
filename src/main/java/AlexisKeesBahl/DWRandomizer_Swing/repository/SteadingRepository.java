package AlexisKeesBahl.DWRandomizer_Swing.repository;

import AlexisKeesBahl.DWRandomizer_Swing.model.Steading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SteadingRepository extends JpaRepository<Steading,Integer> {
}
