package AlexisKeesBahl.DWRandomizer_Swing.repository;

import AlexisKeesBahl.DWRandomizer_Swing.model.AreaDiscovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaDiscoveryRepository extends JpaRepository<AreaDiscovery,Integer> {
}
