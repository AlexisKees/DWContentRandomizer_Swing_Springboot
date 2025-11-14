package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Dungeon;
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
public class DungeonAreaForm extends JFrame{

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
    private Dungeon dungeon;
    private final DungeonService dungeonService;

    @Autowired
    public DungeonAreaForm(ApplicationContext context,
                        SessionManager sessionManager,
                        AreaService areaService,
                        GenericFunctions genericFunctions,
                           DungeonService dungeonService){

        this.areaService=areaService;
        this.sessionManager=sessionManager;
        this.context = context;
        this.genericFunctions = genericFunctions;
        this.dungeonService=dungeonService;
        if(sessionManager.getSelected(Area.class)==null) {
            this.area = new Area();
        } else {
            this.area = sessionManager.getSelected(Area.class);
        }

        if(sessionManager.getSelected(Dungeon.class)==null)
            this.dungeon = new Dungeon();
        else {
            this.dungeon = sessionManager.getSelected(Dungeon.class);
            updateFields();
        }

        initializeForm(context);

    }

    private void initializeForm(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        newAreaButton.addActionListener(e ->{
            areaService.rollArea(area);
            sessionManager.add(Area.class,area.clone());
            updateFields();
        });


        rerollButton.addActionListener(e -> {
            areaService.rollAreaDetails(area);
            sessionManager.add(Area.class,area.clone());
            updateFields();
        });

        addButton.addActionListener(e -> {
            if(sessionManager.getSelected(Dungeon.class)==null){
                dungeonService.rollDungeon(dungeon);
                sessionManager.add(Dungeon.class,dungeon);
            }
            dungeon.addArea(area);
        });


        goBackButton.addActionListener(e->{
            DungeonMenuForm dungeonMenuForm = context.getBean(DungeonMenuForm.class);
            dungeonMenuForm.setVisible(true);
            dispose();
            dungeonMenuForm.updateFields();
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
