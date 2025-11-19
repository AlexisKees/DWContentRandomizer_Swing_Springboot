package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.NPC;
import AlexisKeesBahl.DWRandomizer_Swing.model.Quest;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.NPCService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class QuestNPCMenuForm extends JFrame {


    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final NPCService npcService;
    private final GenericFunctions genericFunctions;
    private NPC npc;
    private Quest quest;
    private JButton goBackButton;
    private JButton generateButton;
    private JButton rerollButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton exportButton;
    private JFormattedTextField npcField;
    private JFormattedTextField genderField;
    private JFormattedTextField ageField;
    private JFormattedTextField appearanceField;
    private JFormattedTextField personalityField;
    private JFormattedTextField quirkField;


    public QuestNPCMenuForm(ApplicationContext context,
                       SessionManager sessionManager,
                       NPCService npcService,
                       GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.npcService=npcService;
        this.genericFunctions=genericFunctions;
        buildUI();
        if (sessionManager.getSelected(Quest.class)==null) {
            this.quest = new Quest();
            this.npc=new NPC();
        }
        else {
            this.quest = sessionManager.getSelected(Quest.class);
            this.npc = quest.getQuestGiver();
            updateFields();
        }

        initializeForm(context);

    }

    private void buildUI() {

        leftButton = new JButton("←");
        rightButton = new JButton("→");
        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        JLabel title = new JLabel("Random NPC generator");
        title.setFont(titleFont);
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        root.add(title);
        root.add(Box.createVerticalStrut(20));

        npcField = new JFormattedTextField();
        genderField = new JFormattedTextField();
        ageField = new JFormattedTextField();
        appearanceField = new JFormattedTextField();
        personalityField = new JFormattedTextField();
        quirkField = new JFormattedTextField();

        JComponent[] fields = {
                labeledField("NPC:", npcField, labelFont, fieldFont),
                labeledField("Gender:", genderField, labelFont, fieldFont),
                labeledField("Age:", ageField, labelFont, fieldFont),
                labeledField("Appearance:", appearanceField, labelFont, fieldFont),
                labeledField("Personality:", personalityField, labelFont, fieldFont),
                labeledField("Quirk:", quirkField, labelFont, fieldFont)
        };

        for (JComponent c : fields) {
            root.add(c);
            root.add(Box.createVerticalStrut(10));
        }

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new GridLayout(1, 3, 10, 0));

        generateButton = new JButton("Generate");
        rerollButton = new JButton("Reroll");
        exportButton = new JButton("Export");

        generateButton.setFont(buttonFont);
        rerollButton.setFont(buttonFont);
        exportButton.setFont(buttonFont);

        buttonRow.add(generateButton);
        buttonRow.add(rerollButton);
        buttonRow.add(exportButton);

        root.add(Box.createVerticalStrut(10));
        root.add(buttonRow);
        root.add(Box.createVerticalStrut(20));

        JPanel arrowsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rightButton.setFont(new Font("Arial", Font.PLAIN, 14));

        arrowsPanel.add(leftButton);
        arrowsPanel.add(rightButton);

        root.add(arrowsPanel);
        root.add(Box.createVerticalStrut(8));


        goBackButton = new JButton("Go back");
        goBackButton.setFont(buttonFont);
        goBackButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        goBackButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        root.add(goBackButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
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


    private void initializeForm(ApplicationContext context){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e->{
            npc=npc.clone();
            npcService.rollNPC(npc);
            sessionManager.add(NPC.class,npc);
            quest.setQuestGiver(npc);
            updateFields();
        });

        rerollButton.addActionListener(e->{
            npc=npc.clone();
            if(sessionManager.getSelected(NPC.class)==null)
                npcService.rollNPC(npc);
            else
                npcService.rollDetails(npc);

            sessionManager.add(NPC.class,npc);
            updateFields();
        });

        exportButton.addActionListener(e->{
            try{
                genericFunctions.exportPW(npc);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Couldn't export npc...");
            }
        });

        leftButton.addActionListener(e->{
            if(sessionManager.getList(NPC.class).indexOf(npc)>0){
                int currentIndex = sessionManager.getList(NPC.class).indexOf(npc);
                int newIndex=currentIndex-1;
                npc = sessionManager.getList(NPC.class).get(newIndex);
                updateFields();}
        });

        rightButton.addActionListener(e->{
            if(sessionManager.getList(NPC.class).indexOf(npc)<sessionManager.getList(NPC.class).size()-1) {
                int currentIndex = sessionManager.getList(NPC.class).indexOf(npc);
                int newIndex = currentIndex + 1;
                this.npc = sessionManager.getList(NPC.class).get(newIndex);
                updateFields();
            }
        });

        goBackButton.addActionListener(e -> {
            QuestMenuForm questMenuForm = context.getBean(QuestMenuForm.class);
            questMenuForm.setVisible(true);
            questMenuForm.updateFields();
            dispose();
        });
    }

    public void updateFields() {
        npcField.setText(npc.getOneLiner());
        genderField.setText(npc.getGender());
        ageField.setText(npc.getAge());
        appearanceField.setText(npc.getAppearance());
        personalityField.setText(npc.getPersonality());
        quirkField.setText(npc.getQuirk());
    }
}
