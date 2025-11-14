package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.QuestArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.*;
import AlexisKeesBahl.DWRandomizer_Swing.repository.QuestRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.PickFrom;

@Service
public class QuestService implements IGenericService<Quest>, IGenericCRUDService<Quest> {

    @Autowired
    private DungeonService dungeonService;
    @Autowired
    private BiomeService biomeService;
    @Autowired
    private NPCService npcService;

    @Autowired
    QuestRepository questRepository;

    @Override
    public List<Quest> list() {
        return questRepository.findAll();
    }

    @Override
    public Quest searchById(Integer id) {
        return questRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Quest quest) {
        questRepository.save(quest);
    }

    @Override
    public void delete(Quest quest) {
        questRepository.delete(quest);
    }



    public void rollQuest(Quest quest){
        quest.setTask(PickFrom(QuestArrays.TASK));
        quest.setRelevance(PickFrom(QuestArrays.RELEVANCE));
        quest.setReward(PickFrom(QuestArrays.REWARD));

        quest.setQuestGiver(new NPC());
        quest.setDungeon(new Dungeon());
        quest.setBiome(new Biome());
        dungeonService.rollDungeon(quest.getDungeon());
        npcService.rollNPC(quest.getQuestGiver());
        biomeService.rollBiome(quest.getBiome());
        quest.setOneLiner(String.format("%s's %s quest",quest.getQuestGiver().getName(),quest.getTask()));

        quest.setBrief(String.format("""
                \nQUEST
                Task: %s
                Relevance: %s
                Reward: %s
                Tasked by: %s
                To be carried out at: %s, in some %s
                """, quest.getTask(),quest.getRelevance(),quest.getReward(),quest.getQuestGiver().getOneLiner(),quest.getDungeon().getName(), quest.getBiome().getOneLiner()));
    }


}
