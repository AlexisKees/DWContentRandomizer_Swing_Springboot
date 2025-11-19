package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.CreatureArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.DetailsArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.NPCArrays;
import AlexisKeesBahl.DWRandomizer_Swing.data.NPCNamesArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.Follower;
import AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls;
import AlexisKeesBahl.DWRandomizer_Swing.repository.FollowerRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.PickFrom;

@Service
public class FollowerService implements IGenericService<Follower>, IGenericCRUDService<Follower> {


    @Autowired
    FollowerRepository followerRepository;

    @Override
    public List<Follower> list() {
        return followerRepository.findAll();
    }

    @Override
    public Follower searchById(Integer id) {
        return followerRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Follower follower) {
        followerRepository.save(follower);
    }

    @Override
    public void delete(Follower follower) {
        followerRepository.delete(follower);
    }



    public void rollFollower(Follower follower){
        int i;
        for (i=0;i<NPCArrays.FOLLOWER_TAGS.length;i++){
            follower.getAvailableTags().add(NPCArrays.FOLLOWER_TAGS[i]);
        }
        follower.setTags("");
        follower.setQuality(0);
        follower.setLoyalty(1);

        String rarity = PickFrom(CreatureArrays.SUBCATEGORIES_HUMANOID);
        switch (rarity) {
            case "Uncommon" -> follower.setRaceTable(CreatureArrays.PROMPTS_HUMANOID_UNCOMMON);
            case "Rare" -> follower.setRaceTable(CreatureArrays.PROMPTS_HUMANOID_RARE);
            default -> follower.setRaceTable(CreatureArrays.PROMPTS_HUMANOID_COMMON);
        }

        follower.setRace(PickFrom(follower.getRaceTable()));
        // set gender, ethnics and name
        follower.setGender(PickFrom(NPCNamesArrays.GENDER));
        follower.setEthnics(PickFrom(NPCNamesArrays.ETHNICS));
        switch (follower.getEthnics()){
            case "Yoruba" -> follower.setNamesTable(NPCNamesArrays.NAMES_YORUBA_BASED);
            case "Finnish" -> follower.setNamesTable(NPCNamesArrays.NAMES_FINNISH_BASED);
            case "Indonesian" -> follower.setNamesTable(NPCNamesArrays.NAMES_INDONESIAN_BASED);
            default -> follower.setNamesTable(NPCNamesArrays.NAMES_HUNGARIAN_BASED);
        }
        //Male names use the first half of each 50 elements array, while female names use the second half
        switch (follower.getGender()){
            case "Male" ->{
                int roll = (int)(Math.random() * 24);
                follower.setName(follower.getNamesTable()[roll]);
            }
            case "Female" -> {
                int roll = (int)(Math.random() * 24 + 25);
                follower.setName(follower.getNamesTable()[roll]);
            }
            default -> follower.setName(PickFrom(follower.getNamesTable()));
        }

        //Set age using DetailArrays
        int ageRoll = (int)(Math.random()*7+3);
        follower.setAge(DetailsArrays.AGE[ageRoll]);
        follower.setOneLiner(follower.getName()+", "+follower.getGender()+" "+follower.getRace());
        rollFollowerDetails(follower);
    }

    public void rollFollowerDetails(Follower follower){
        follower.setQuality(0);
        follower.setLoyalty(1);
        follower.setTags("");
        follower.setQualityString(PickFrom(NPCArrays.FOLLOWER_QUALITY));
        switch (follower.getQualityString()){
            case "A liability: Quality -1, +0 tags" -> follower.setQuality(follower.getQuality()-1);
            case "Competent: Quality +0, +1 tags" -> addTag(follower);
            case "Fully capable: Quality +1, +2 tags" -> {
                follower.setQuality(follower.getQuality()+1);
                int i;
                for (i=1;i<=2;i++){
                    addTag(follower);
                }
            }
            case "Exceptional: Quality +2, +4 tags" -> {
                follower.setQuality(follower.getQuality()+2);
                int i;
                for (i=1;i<=4;i++){
                    addTag(follower);
                }
            }
        }

        follower.setInstinct(PickFrom(NPCArrays.FOLLOWER_INSTINCT));

        follower.setLoyaltyString(PickFrom(NPCArrays.FOLLOWER_LOYALTY));
        switch (follower.getLoyaltyString()){
            case "Dutiful" -> follower.setLoyalty(follower.getLoyalty()+1);
            case "Devoted to the cause" ->follower.setLoyalty(follower.getLoyalty()+2);
        }

        follower.setCost(PickFrom(NPCArrays.FOLLOWER_COST));

        follower.setHpAndDamage(PickFrom(NPCArrays.FOLLOWER_HP_DAMAGE));
        follower.setHP(Integer.parseInt(follower.getHpAndDamage().substring(0,1)));
        follower.setDamage("1"+follower.getHpAndDamage().substring(6,8));

        follower.setBackground(PickFrom(NPCArrays.FOLLOWER_BACKGROUND));
        reviseBackground(follower);

        follower.setArmorString(PickFrom(NPCArrays.FOLLOWER_ARMOR));
        switch (follower.getArmorString()){
                case "None: 0 Armor" -> {
                    follower.setArmor(0);
                    follower.setArmorType(follower.getArmorString().substring(0, follower.getArmorString().indexOf(":")));
                }
                case "Hides or leather: 1 Armor" -> {
                    follower.setArmor(1);
                    follower.setArmorType(follower.getArmorString().substring(0, follower.getArmorString().indexOf(":")));
                }
                case "Scale or chain: 2 Armor" -> {
                    follower.setArmor(2);
                    follower.setArmorType(follower.getArmorString().substring(0, follower.getArmorString().indexOf(":")));
                }
                case "Plate: 3 Armor" -> {
                    follower.setArmor(3);
                    follower.setArmorType(follower.getArmorString().substring(0, follower.getArmorString().indexOf(":")));
                }
        }
        follower.setShield(Rolls.rollBoolean());
        if(follower.getShield())
        {
            follower.setArmor(follower.getArmor()+1);
            follower.setArmorType(follower.getArmorType()+" and a shield");
        }

    }

    public void addTag(Follower f){
        String tag = PickFrom(f.getAvailableTags());

        if (f.getTags().isEmpty()) f.setTags(tag);
        else f.setTags(f.getTags()+", "+tag.toLowerCase());

        f.getAvailableTags().remove(tag);
    }

    public void addTag(Follower f, String tag){
        boolean tagAlreadyExists = ((f.getTags().contains(tag))||(f.getTags().contains(tag.toLowerCase())));

        if (f.getTags().equals("")) f.setTags(tag);
        else if (!tagAlreadyExists) f.setTags(f.getTags()+", "+tag.toLowerCase());
    }

    public void reviseBackground(Follower follower){
        switch (follower.getBackground()){
            case "Life of servitude/oppression: +meek" -> addTag(follower, "Meek");
            case "Past prime: -1 to Quality, +1 wise" ->{
                follower.setQuality(follower.getQuality()-1);
                addTag(follower,"____-wise");
            }
            case "Has lived a life of danger: +2 tags" ->{
                int i;
                for (i=1;i<=2;i++){
                    addTag(follower);
                }
            }
            case "Has lived a life of privilege: +1 tag" ->
                addTag(follower);
            case "Specialist: +1 to Quality, -2 tags" ->{
                follower.setQuality(follower.getQuality()+1);
                removeTag(follower);
                removeTag(follower);
            }
        }
    }

    public void removeTag(Follower follower){
        String str;
        if (!follower.getTags().contains(",")) follower.setTags("");
        else {
            str = follower.getTags().substring(0,follower.getTags().indexOf(", "));
            follower.setTags(follower.getTags().replace(str,""));
        }

    }

}
