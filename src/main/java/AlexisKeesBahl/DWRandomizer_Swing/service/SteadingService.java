package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.DetailsArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.SteadingArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.Steading;
import AlexisKeesBahl.DWRandomizer_Swing.repository.SteadingRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.PickFrom;

@Service
public class SteadingService implements IGenericService<Steading>, IGenericCRUDService<Steading> {


    @Autowired
    CreatureService creatureService;

    @Autowired
    SteadingRepository steadingRepository;

    @Override
    public List<Steading> list() {
        return steadingRepository.findAll();
    }

    @Override
    public Steading searchById(Integer id) {
        return steadingRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Steading steading) {
        steadingRepository.save(steading);
    }

    @Override
    public void delete(Steading steading) {
        steadingRepository.delete(steading);
    }




    public void rollSteading(Steading steading){
        steading.setSize(PickFrom(SteadingArrays.SETTLEMENT_SIZE));
        steading.setName(PickFrom(SteadingArrays.STEADING_NAMES));
        steading.setRaceOfBuilders(creatureService.rollHumanoid());
        rollDetails(steading);
    }

    public void rollSteading(Steading steading, String size){
        steading.setSize(size);
        steading.setName(PickFrom(SteadingArrays.STEADING_NAMES));
        steading.setRaceOfBuilders(creatureService.rollHumanoid());
        rollDetails(steading);
    }

    public void rollDetails(Steading steading){

        switch (steading.getSize()){
            case "VILLAGE" ->{
                steading.setTags("Poor, steady, militia, resource (GM's choice)");
                steading.setFeature(PickFrom(SteadingArrays.VILLAGE_FEATURES));
                steading.setProblem(PickFrom(SteadingArrays.VILLAGE_PROBLEMS));
            }
            case "TOWN" -> {
                steading.setTags("Moderate, steady, watch, and trade (with 2 places of GM’s choice).");
                steading.setFeature(PickFrom(SteadingArrays.TOWN_FEATURES));
                steading.setProblem(PickFrom(SteadingArrays.TOWN_PROBLEMS));
            }
            case "KEEP" -> {
                steading.setTags("Poor, steady, militia, resource (GM decides or rolls)");
                steading.setFeature(PickFrom(SteadingArrays.KEEP_FEATURES));
                steading.setProblem(PickFrom(SteadingArrays.KEEP_PROBLEMS));
            }
            case "CITY"-> {
                steading.setTags("Poor, steady, militia, resource (GM decides or rolls)");
                steading.setFeature(PickFrom(SteadingArrays.CITY_FEATURES));
                steading.setProblem(PickFrom(SteadingArrays.CITY_PROBLEMS));
            }
        }


        steading.setAlignment(PickFrom(DetailsArrays.ALIGNMENT));
        steading.setDangerLevel(PickFrom(SteadingArrays.DANGER_LEVEL));
        steading.setOneLiner(steading.getName()+", "+steading.getRaceOfBuilders()+" "+steading.getSize().toLowerCase());
    }

}
