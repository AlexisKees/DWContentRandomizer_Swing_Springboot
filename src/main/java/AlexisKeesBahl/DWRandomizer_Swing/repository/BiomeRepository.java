package AlexisKeesBahl.DWRandomizer_Swing.repository;

import AlexisKeesBahl.DWRandomizer_Swing.model.Biome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiomeRepository extends JpaRepository<Biome,Integer> {

}
