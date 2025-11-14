package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.BiomeArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.DetailsArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.Biome;
import AlexisKeesBahl.DWRandomizer_Swing.repository.BiomeRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.*;

@Service
public class BiomeService implements IGenericService<Biome>, IGenericCRUDService<Biome> {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private BiomeRepository biomeRepository;

    public void rollBiome(Biome biome){
//        private String biome;
        biome.setBiome(PickFrom(BiomeArrays.BIOME));
//        private String weather;
        biome.setWeather(PickFrom(BiomeArrays.WEATHER));
//        private String weatherIntensity;
        biome.setWeatherIntensity(PickFrom(BiomeArrays.WEATHER_INTENSITY));
//        private String wildlife;
        biome.setWildlife(PickFrom(BiomeArrays.WILDLIFE));
//        private String population;
        biome.setPopulation(PickFrom(BiomeArrays.POPULATION));
//        private String roads;
        biome.setRoads(PickFrom(BiomeArrays.ROADS));
//        private String alignment;
        biome.setAlignment(PickFrom(DetailsArrays.ALIGNMENT));
//        private String distance;
        biome.setDistance(PickFrom(BiomeArrays.DISTANCE));
        biome.setOneLiner(String.format("%s %s",biome.getPopulation(),biome.getBiome()));
    }

    public void reRollDetails(Biome biome){
        //        private String weather;
        biome.setWeather(PickFrom(BiomeArrays.WEATHER));
//        private String weatherIntensity;
        biome.setWeatherIntensity(PickFrom(BiomeArrays.WEATHER_INTENSITY));
//        private String wildlife;
        biome.setWildlife(PickFrom(BiomeArrays.WILDLIFE));
//        private String population;
        biome.setPopulation(PickFrom(BiomeArrays.POPULATION));
//        private String roads;
        biome.setRoads(PickFrom(BiomeArrays.ROADS));
//        private String alignment;
        biome.setAlignment(PickFrom(DetailsArrays.ALIGNMENT));
//        private String distance;
        biome.setDistance(PickFrom(BiomeArrays.DISTANCE));
        biome.setOneLiner(String.format("%s %s",biome.getPopulation(),biome.getBiome()));

    }

    @Override
    public List<Biome> list() {
        return biomeRepository.findAll();
    }

    @Override
    public Biome searchById(Integer id) {
        return biomeRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Biome biome) {
        biomeRepository.save(biome);
    }

    @Override
    public void delete(Biome biome) {
        biomeRepository.delete(biome);
    }



}
