package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.DetailsArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.DungeonArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.AreaDanger;
import AlexisKeesBahl.DWRandomizer_Swing.model.Creature;
import AlexisKeesBahl.DWRandomizer_Swing.repository.AreaDangerRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.PickFrom;

@Service
public class AreaDangerService implements IGenericService<AreaDanger>, IGenericCRUDService<AreaDanger> {

    @Autowired
    private CreatureService creatureService;
    @Autowired
    private AreaDangerRepository areaDangerRepository;
    @Override
    public List<AreaDanger> list() {
        List<AreaDanger> areaDangerList = areaDangerRepository.findAll();
        return areaDangerList;
    }

    @Override
    public AreaDanger searchById(Integer id) {
        AreaDanger areaDanger = areaDangerRepository.findById(id).orElse(null);
        return areaDanger;
    }

    @Override
    public void save(AreaDanger areaDanger) {
        areaDangerRepository.save(areaDanger);

    }

    @Override
    public void delete(AreaDanger areaDanger) {
        areaDangerRepository.delete(areaDanger);

    }



    public void rollAreaDanger(AreaDanger danger){
        danger.setCategory(PickFrom(DungeonArrays.DUNGEON_DANGER_CATEGORIES));

        switch (danger.getCategory()){
            case "Trap" ->  danger.setPromptTable(DungeonArrays.DUNGEON_DANGER_TRAP_PROMPTS);
            case "Creature" -> danger.setPromptTable(DungeonArrays.DUNGEON_DANGER_CREATURE_PROMPTS);
        }

        danger.setPrompt(PickFrom(danger.getPromptTable()));
        switch (danger.getPrompt()) {
            case "based on Element" -> {
                String element = creatureService.rollElement();
                danger.setFinalResult(element+"trap");
                danger.setOneLiner(danger.getFinalResult());
            }
            case "based on Magic Type" -> {
                String magicType = creatureService.rollMagicType();
                danger.setFinalResult("Magic "+magicType+" trap");
                danger.setOneLiner(danger.getFinalResult());
            }
            case "based on Oddity" -> {
                String oddity = creatureService.rollOddity();
                danger.setFinalResult(oddity+" trap");
                danger.setOneLiner(danger.getFinalResult());
            }
            case "Creature leader (with minions)" -> {
                Creature c = new Creature();
                creatureService.rollAttributes(c);
                creatureService.setGroupSize(c,"solitary (1)");
                c.setDisposition(DetailsArrays.DISPOSITION[0]); //SET DISPOSITION TO "ATTACKING"
                danger.setFinalResult("CREATURE LEADER:\n"+c);
                danger.setOneLiner(c.getOneLiner()+" leader");
            }
            case "Creature lord (with minions)" ->{
                Creature c = new Creature();
                creatureService.rollAttributes(c);
                c.setDisposition(DetailsArrays.DISPOSITION[0]); //SET DISPOSITION TO "ATTACKING"
                creatureService.setGroupSize(c,"solitary (1)");
                danger.setFinalResult("CREATURE LORD:\n"+c);
                danger.setFinalResult(c.toString());
                danger.setOneLiner(c.getOneLiner()+" lord");
            }
            case "Creature" -> {
                Creature c = new Creature();
                creatureService.rollAttributes(c);
                c.setDisposition(DetailsArrays.DISPOSITION[0]); //SET DISPOSITION TO "ATTACKING"
                danger.setFinalResult("CREATURE:\n"+c);
                danger.setOneLiner(c.getOneLiner());
            }
            default -> {
                if (danger.getCategory().equals("Trap")) danger.setFinalResult(danger.getPrompt()+" trap.");
                else danger.setFinalResult(danger.getPrompt());

                danger.setOneLiner(danger.getFinalResult());
            }
        }



    }

}
