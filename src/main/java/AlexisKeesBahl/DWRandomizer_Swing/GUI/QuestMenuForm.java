package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Biome;
import AlexisKeesBahl.DWRandomizer_Swing.model.Dungeon;
import AlexisKeesBahl.DWRandomizer_Swing.model.NPC;
import AlexisKeesBahl.DWRandomizer_Swing.model.Quest;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.QuestService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class QuestMenuForm extends JFrame {

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final QuestService questService;
    private final GenericFunctions genericFunctions;

    private Quest quest;

    private JFormattedTextField oneLinerField;
    private JFormattedTextField taskField;
    private JFormattedTextField relevanceField;
    private JFormattedTextField rewardField;
    private JFormattedTextField givenByField;
    private JFormattedTextField biomeField;
    private JFormattedTextField dungeonField;

    private JButton generateButton;
    private JButton viewBiomeButton;
    private JButton viewDungeonButton;
    private JButton viewQuestGiverButton;
    private JButton exportButton;
    private JButton goBackButton;

    public QuestMenuForm(
            ApplicationContext context,
            SessionManager sessionManager,
            QuestService questService,
            GenericFunctions genericFunctions
    ) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.questService = questService;
        this.genericFunctions = genericFunctions;

        if (sessionManager.getSelected(Quest.class) == null){
            this.quest = new Quest();
            this.quest.setBiome(new Biome());
            this.quest.setDungeon(new Dungeon());
            this.quest.setQuestGiver(new NPC());
        }
        else
            this.quest = sessionManager.getSelected(Quest.class);



        buildUI();
        initializeLogic();
        updateFields();
    }


    private void buildUI() {
        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        JLabel title = new JLabel("Random quest generator");
        title.setFont(titleFont);
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        root.add(title);
        root.add(Box.createVerticalStrut(20));

        oneLinerField = new JFormattedTextField();
        taskField = new JFormattedTextField();
        relevanceField = new JFormattedTextField();
        rewardField = new JFormattedTextField();
        givenByField = new JFormattedTextField();
        biomeField = new JFormattedTextField();
        dungeonField = new JFormattedTextField();

        JComponent[] fields = {
                fieldBlock(oneLinerField, fieldFont),
                labeledField("Task:", taskField, labelFont, fieldFont),
                labeledField("Relevance:", relevanceField, labelFont, fieldFont),
                labeledField("Reward:", rewardField, labelFont, fieldFont),
                labeledField("Given by:", givenByField, labelFont, fieldFont),
                labeledField("Biome:", biomeField, labelFont, fieldFont),
                labeledField("Dungeon:", dungeonField, labelFont, fieldFont)
        };

        for (JComponent c : fields) {
            root.add(c);
            root.add(Box.createVerticalStrut(10));
        }

        JPanel row1 = new JPanel(new GridLayout(1, 3, 10, 0));
        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));

        generateButton = new JButton("Generate");
        viewBiomeButton = new JButton("View biome");
        viewDungeonButton = new JButton("View dungeon");
        viewQuestGiverButton = new JButton("View quest giver");
        exportButton = new JButton("Export");

        JButton[] row1Buttons = { generateButton, viewBiomeButton, viewDungeonButton };
        JButton[] row2Buttons = { viewQuestGiverButton, exportButton };

        for (JButton b : row1Buttons) {
            b.setFont(buttonFont);
            row1.add(b);
        }

        for (JButton b : row2Buttons) {
            b.setFont(buttonFont);
            row2.add(b);
        }

        root.add(Box.createVerticalStrut(10));
        root.add(row1);
        root.add(Box.createVerticalStrut(10));
        root.add(row2);
        root.add(Box.createVerticalStrut(20));

        goBackButton = new JButton("Go back");
        goBackButton.setFont(buttonFont);
        goBackButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        goBackButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        root.add(goBackButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
    }


    private JPanel fieldBlock(JFormattedTextField field, Font fieldFont) {
        JPanel panel = new JPanel(new BorderLayout());
        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(200, 28));
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel labeledField(String text, JFormattedTextField field, Font labelFont, Font fieldFont) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(200, 28));
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }


    private void initializeLogic() {

        generateButton.addActionListener(e -> {
            questService.rollQuest(quest);
            sessionManager.add(Quest.class, quest);
            updateFields();
        });

        viewQuestGiverButton.addActionListener(e -> {
            QuestNPCMenuForm f = context.getBean(QuestNPCMenuForm.class);
            f.setVisible(true);
            f.updateFields();
            dispose();
        });

        viewBiomeButton.addActionListener(e -> {
            QuestBiomeMenuForm f = context.getBean(QuestBiomeMenuForm.class);
            f.setVisible(true);
            f.updateFields();
            dispose();
        });

        viewDungeonButton.addActionListener(e -> {
            QuestDungeonMenuForm f = context.getBean(QuestDungeonMenuForm.class);
            f.setVisible(true);
            f.updateFields();
            dispose();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(quest);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export quest...");
            }
        });

        goBackButton.addActionListener(e -> {
            MainMenuForm m = context.getBean(MainMenuForm.class);
            m.setVisible(true);
            dispose();
        });
    }


    public void updateFields() {
        oneLinerField.setText(quest.getOneLiner());
        taskField.setText(quest.getTask());
        relevanceField.setText(quest.getRelevance());
        rewardField.setText(quest.getReward());
        givenByField.setText(quest.getQuestGiver().getOneLiner());
        biomeField.setText(quest.getBiome().getOneLiner());
        dungeonField.setText(quest.getDungeon().getOneLiner());
    }
}
