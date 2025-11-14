package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Dungeon;
import AlexisKeesBahl.DWRandomizer_Swing.model.Quest;
import AlexisKeesBahl.DWRandomizer_Swing.service.AreaService;
import AlexisKeesBahl.DWRandomizer_Swing.service.DungeonService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Scope("prototype")
public class QuestDungeonAreaMenuForm extends JFrame {


    private final SessionManager sessionManager;
    private final AreaService areaService;
    private final ApplicationContext context;
    private final GenericFunctions genericFunctions;
    private JPanel panel1;
    private JButton newAreaButton;
    private JButton rerollButton;
    private JButton addButton;
    private JButton goBackButton;
    private JFormattedTextField areaFormattedTextField;
    private JTextPane dressingTextPane;
    private JTextPane rarityTextPane;
    private JTextPane discoveriesTextPane;
    private JTextPane dangersTextPane;
    private JLabel discoveriesLabel;
    private JLabel dangersLabel;
    private Area area;
    private Quest quest;
    private Dungeon dungeon;
    private final DungeonService dungeonService;

    @Autowired
    public QuestDungeonAreaMenuForm(ApplicationContext context,
                           SessionManager sessionManager,
                           AreaService areaService,
                           GenericFunctions genericFunctions,
                           DungeonService dungeonService){

        this.areaService=areaService;
        this.sessionManager=sessionManager;
        this.context = context;
        this.genericFunctions = genericFunctions;
        this.dungeonService=dungeonService;
        this.area=new Area();
        if (sessionManager.getSelected(Quest.class)==null) {
            this.quest = new Quest();
            this.dungeon=new Dungeon();
        }
        else {
            this.quest = sessionManager.getSelected(Quest.class);
            this.dungeon = quest.getDungeon();
            updateFields();
        }

        iniciarForma(context);

    }

    private void iniciarForma(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        newAreaButton.addActionListener(e ->{
            areaService.rollArea(area);
            updateFields();
        });


        rerollButton.addActionListener(e -> {
            areaService.rollAreaDetails(area);
            updateFields();
        });

        addButton.addActionListener(e -> {
            dungeon.addArea(area);
        });


        goBackButton.addActionListener(e->{
            QuestDungeonMenuForm questDungeonMenuForm = context.getBean(QuestDungeonMenuForm.class);
            questDungeonMenuForm.setVisible(true);
            dispose();
            questDungeonMenuForm.updateFields();
        });
    }

    private void updateFields() {
        areaFormattedTextField.setText(area.getAreaType());
        dressingTextPane.setText(area.getAreaDressing());
        rarityTextPane.setText(area.getRarity());
        discoveriesLabel.setText(String.format("Discoveries: %d",area.getDiscoveriesAmount()));
        discoveriesTextPane.setText(area.getDiscoveries());
        dangersLabel.setText(String.format("Dangers: %d",area.getDangersAmount()));
        dangersTextPane.setText(area.getDangers());
    }
}
