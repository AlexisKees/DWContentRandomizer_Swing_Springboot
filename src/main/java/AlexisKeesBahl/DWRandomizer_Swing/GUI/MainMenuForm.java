package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.presentation.DBMenu;
import AlexisKeesBahl.DWRandomizer_Swing.presentation.SubMenu;
import AlexisKeesBahl.DWRandomizer_Swing.service.*;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.ClassIdentifier;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainMenuForm extends JFrame{


    private JPanel MainFrame;
    private JLabel DWTitle;
    private JLabel Subtitle;
    private JButton AreaButton;
    private JButton BiomeButton;
    private JButton CreatureButton;
    private JButton DangerButton;
    private JButton discoveryButton;
    private JButton dungeonButton;
    private JButton followerButton;
    private JButton NPCButton;
    private JButton questButton;
    private JButton steadingButton;
    private JButton quitButton;

    DBMenu dbMenu;
    AreaService areaService;
    BiomeService biomeService;
    CreatureService creatureService;
    DangerService dangerService;
    DiscoveryService discoveryService;
    DungeonService dungeonService;
    FollowerService followerService;
    NPCService npcService;
    QuestService questService;
    SteadingService steadingService;
    ClassIdentifier classIdentifier;
    SessionManager sessionManager;
    SubMenu subMenu;

    @Autowired
    public MainMenuForm(ApplicationContext context){
        this.dbMenu=dbMenu;
        this.areaService = areaService;
        this.biomeService = biomeService;
        this.creatureService = creatureService;
        this.dangerService = dangerService;
        this.discoveryService = discoveryService;
        this.dungeonService = dungeonService;
        this.followerService = followerService;
        this.npcService = npcService;
        this.questService = questService;
        this.steadingService = steadingService;
        this.classIdentifier=classIdentifier;
        this.sessionManager =sessionManager;
        this.subMenu=subMenu;

        iniciarForma();

        AreaButton.addActionListener(e -> {
            AreaMenuForm areaMenuForm = context.getBean(AreaMenuForm.class);
            areaMenuForm.setVisible(true);
            dispose();
        });
        BiomeButton.addActionListener(e -> {
            BiomeMenuForm biomeMenuForm = context.getBean(BiomeMenuForm.class);
            biomeMenuForm.setVisible(true);
            dispose();
        });
        CreatureButton.addActionListener(e -> {
            CreatureMenuForm creatureMenuForm = context.getBean(CreatureMenuForm.class);
            creatureMenuForm.setVisible(true);
            dispose();
        });
        DangerButton.addActionListener(e -> {
            DangerMenuForm dangerMenuForm = context.getBean(DangerMenuForm.class);
            dangerMenuForm.setVisible(true);
            dispose();
        });
        discoveryButton.addActionListener(e -> {
            DiscoveryMenuForm discoveryMenuForm = context.getBean(DiscoveryMenuForm.class);
            discoveryMenuForm.setVisible(true);
            dispose();
        });
        dungeonButton.addActionListener( e -> {
            DungeonMenuForm dungeonMenuForm = context.getBean(DungeonMenuForm.class);
            dungeonMenuForm.setVisible(true);
            dispose();
        });
        followerButton.addActionListener(e -> {

            FollowerMenuForm followerMenuForm = context.getBean(FollowerMenuForm.class);
            followerMenuForm.setVisible(true);
            dispose();

        });
        NPCButton.addActionListener(e -> {
            NPCMenuForm npcMenuForm = context.getBean(NPCMenuForm.class);
            npcMenuForm.setVisible(true);
            dispose();
        });
        questButton.addActionListener(e->{
            QuestMenuForm questMenuForm = context.getBean(QuestMenuForm.class);
            questMenuForm.setVisible(true);
            dispose();
        });
        steadingButton.addActionListener(e ->{
            SteadingMenuForm steadingMenuForm = context.getBean(SteadingMenuForm.class);
            steadingMenuForm.setVisible(true);
            dispose();
        });
        quitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void iniciarForma(){
        setContentPane(MainFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

    }
}
