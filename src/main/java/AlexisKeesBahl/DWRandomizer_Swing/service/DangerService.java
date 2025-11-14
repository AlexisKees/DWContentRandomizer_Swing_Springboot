package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.DangerArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.DetailsArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.*;
import AlexisKeesBahl.DWRandomizer_Swing.repository.DangerRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.PickFrom;

@Service
public class DangerService implements IGenericService<Danger>, IGenericCRUDService<Danger> {


    @Autowired
    private CreatureService creatureService;

    @Autowired
    private DangerRepository dangerRepository;

    @Override
    public List<Danger> list() {
        return dangerRepository.findAll();
    }

    @Override
    public Danger searchById(Integer id) {
        return dangerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Danger danger) {
        dangerRepository.save(danger);
    }

    @Override
    public void delete(Danger danger) {
        dangerRepository.delete(danger);
    }



    public void rollDanger(Danger danger){
        danger.setCategory(PickFrom(DangerArrays.DANGER_CATEGORIES));

        switch (danger.getCategory()){
            case "UNNATURAL ENTITY" ->  danger.setSubcategoriesTable(DangerArrays.UNNATURAL_ENTITY_SUBCATEGORIES);
            case "HAZARD" -> danger.setSubcategoriesTable(DangerArrays.HAZARD_SUBCATEGORIES);
            case "CREATURE" -> danger.setSubcategoriesTable(DangerArrays.CREATURE_SUBCATEGORIES);
        }

        rollSubcategory(danger);
        rollPrompt(danger);
    }

    public void rollPrompt(Danger danger) {
        danger.setPrompt(PickFrom(danger.getPromptTable()));

        switch (danger.getPrompt()) {
            case "lesser elemental" -> {
                String element = creatureService.rollElement();
                danger.setFinalResult("lesser "+element+" elemental");
            }
            case "elemental" ->{
                String element = creatureService.rollElement();
                danger.setFinalResult(element+" elemental");
            }
            case "greater elemental" -> {
                String element = creatureService.rollElement();
                danger.setFinalResult("greater "+element+" elemental");
            }
            case "elemental lord" -> {
                String element = creatureService.rollElement();
                danger.setFinalResult(element+" elemental lord");
            }
            case "magical: natural + MAGIC TYPE" -> {
                String magicType = creatureService.rollMagicType();
                danger.setFinalResult("Magical natural phenomenon: "+magicType);
                danger.setOneLiner(danger.getFinalResult());
            }
            case "planar: natural + ELEMENT" -> {
                String magicType = creatureService.rollMagicType();
                danger.setFinalResult("Planar natural phenomenon: "+magicType);
                danger.setOneLiner(danger.getFinalResult());
            }
            case "oddity-based" -> {
                String oddity = creatureService.rollOddity();
                danger.setFinalResult("Natural "+oddity.toLowerCase());
                danger.setOneLiner(danger.getFinalResult());
            }
            case "Creature"->{
                Creature c = new Creature();
                creatureService.rollAttributes(c);
                c.setDisposition(DetailsArrays.DISPOSITION[0]); //SET DISPOSITION TO "ATTACKING"
                danger.setFinalResult(c.toString());
                danger.setOneLiner(c.getOneLiner());
            }
            default -> {
                danger.setFinalResult(danger.getPrompt());
                danger.setOneLiner(danger.getFinalResult());
            }
        }
    }

    public void rollSubcategory(Danger danger) {
        danger.setSubcategory(PickFrom(danger.getSubcategoriesTable()));

        switch (danger.getSubcategory()){
            case "Divine" -> danger.setPromptTable(DangerArrays.DIVINE_PROMPTS);
            case "Planar" -> danger.setPromptTable(DangerArrays.PLANAR_PROMPTS);
            case "Undead" -> danger.setPromptTable(DangerArrays.UNDEAD_PROMPTS);
            case "Unnatural" -> danger.setPromptTable(DangerArrays.UNNATURAL_PROMPTS);
            case "Natural" -> danger.setPromptTable(DangerArrays.NATURAL_PROMPTS);
            case "Creature" -> danger.setPromptTable(DangerArrays.CREATURE_SUBCATEGORIES);
        }
        rollPrompt(danger);

    }

}
