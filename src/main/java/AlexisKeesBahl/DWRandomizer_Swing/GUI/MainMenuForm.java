package AlexisKeesBahl.DWRandomizer_Swing.GUI;


import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class MainMenuForm extends JFrame{

    private JLabel DWTitle;
    private JLabel Subtitle;
    private JPanel MainFrame;
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
    private JButton creditsButton;


    @Autowired
    public MainMenuForm(ApplicationContext context){


        buildUI();
        initializeForm();

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

        creditsButton.addActionListener(e->{
            CreditsForm creditsForm = context.getBean(CreditsForm.class);
            creditsForm.setVisible(true);
            dispose();
        }) ;

        quitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    // buildUI implementation for MainMenuForm with fill-aligned buttons and padding spacers

    private void buildUI() {
        MainFrame = new JPanel();
        MainFrame.setLayout(new BorderLayout());

        // Outer padding panel
        JPanel padded = new JPanel();
        padded.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        padded.setLayout(new BorderLayout());
        MainFrame.add(padded, BorderLayout.CENTER);

        // Top title section
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        DWTitle = new JLabel("Dungeon World");
        DWTitle.setFont(new Font("Adobe Jenson Pro", Font.BOLD, 36));
        DWTitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        Subtitle = new JLabel("Random content generator");
        Subtitle.setFont(new Font("Adobe Jenson Pro", Font.PLAIN,24));
        Subtitle.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        topPanel.add(DWTitle);
        topPanel.add(Box.createRigidArea(new Dimension(0,10)));
        topPanel.add(Subtitle);
        topPanel.add(Box.createRigidArea(new Dimension(0,20)));
        padded.add(topPanel, BorderLayout.NORTH);

        // Center buttons grid
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,0,5,0);

        AreaButton = new JButton("Random area");
        AreaButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(AreaButton, gbc);

        gbc.gridy++;
        BiomeButton = new JButton("Random biome");
        BiomeButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(BiomeButton, gbc);

        gbc.gridy++;
        CreatureButton = new JButton("Random creature");
        CreatureButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(CreatureButton, gbc);

        gbc.gridy++;
        DangerButton = new JButton("Random danger");
        DangerButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(DangerButton, gbc);

        gbc.gridy++;
        discoveryButton = new JButton("Random discovery");
        discoveryButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(discoveryButton, gbc);

        gbc.gridy++;
        dungeonButton = new JButton("Random dungeon");
        dungeonButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(dungeonButton, gbc);

        gbc.gridy++;
        followerButton = new JButton("Random follower");
        followerButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(followerButton, gbc);

        gbc.gridy++;
        NPCButton = new JButton("Random NPC");
        NPCButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(NPCButton, gbc);

        gbc.gridy++;
        questButton = new JButton("Random quest");
        questButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(questButton, gbc);

        gbc.gridy++;
        steadingButton = new JButton("Random steading");
        steadingButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(steadingButton, gbc);

        gbc.gridy++;
        creditsButton = new JButton("Credits");
        creditsButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(creditsButton, gbc);

        gbc.gridy++;
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Adobe Jenson Pro Lt",Font.ITALIC,16));
        centerPanel.add(quitButton, gbc);

        padded.add(centerPanel, BorderLayout.CENTER);

        setContentPane(MainFrame);
        pack();
        setLocationRelativeTo(null);
    }


    private void initializeForm(){
        setContentPane(MainFrame);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,650);
        setLocationRelativeTo(null);
    }
}
