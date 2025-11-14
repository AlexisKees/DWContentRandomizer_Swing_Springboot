package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Quest;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.QuestService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope("prototype")
public class QuestMenuForm extends JFrame{
    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final QuestService questService;
    private final GenericFunctions genericFunctions;
    private Quest quest;
    private JButton goBackButton;
    private JPanel panel1;
    private JButton generateButton;
    private JButton viewQuestGiverButton;
    private JButton viewBiomeButton;
    private JButton exportButton;
    private JButton viewDungeonButton;
    private JFormattedTextField oneLinerField;
    private JFormattedTextField taskFormattedTextField;
    private JFormattedTextField relevanceFormattedTextField;
    private JFormattedTextField rewardFormattedTextField;
    private JFormattedTextField givenByFormattedTextField;
    private JFormattedTextField biomeFormattedTextField;
    private JFormattedTextField dungeonFormattedTextField;


    public QuestMenuForm(
            ApplicationContext context,
            SessionManager sessionManager,
            QuestService questService,
            GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.questService=questService;
        this.genericFunctions=genericFunctions;
        if (sessionManager.getSelected(Quest.class)==null)
            this.quest = new Quest();
        else {
            this.quest = sessionManager.getSelected(Quest.class);
            updateFields();
        }

        iniciarForma(context);

    }
    private void iniciarForma(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e->{
            questService.rollQuest(quest);
            sessionManager.add(Quest.class,quest);
            updateFields();
        });

        viewQuestGiverButton.addActionListener(e->{
            QuestNPCMenuForm questNPCMenuForm = context.getBean(QuestNPCMenuForm.class);
            questNPCMenuForm.setVisible(true);
            questNPCMenuForm.updateFields();
            dispose();
        });

        viewBiomeButton.addActionListener(e->{
            QuestBiomeMenuForm questBiomeMenuForm = context.getBean(QuestBiomeMenuForm.class);
            questBiomeMenuForm.setVisible(true);
            questBiomeMenuForm.updateFields();
            dispose();
        });
        viewDungeonButton.addActionListener(e->{
            QuestDungeonMenuForm questDungeonMenuForm = context.getBean(QuestDungeonMenuForm.class);
            questDungeonMenuForm.setVisible(true);
            questDungeonMenuForm.updateFields();
            dispose();
        });
        exportButton.addActionListener(e->{
            try {
                genericFunctions.exportPW(quest);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Couldn't export quest...");
            }

        });

        goBackButton.addActionListener(e-> {
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
        });
    }

    public void updateFields(){
        oneLinerField.setText(quest.getOneLiner());
        taskFormattedTextField.setText(quest.getTask());
        relevanceFormattedTextField.setText(quest.getRelevance());
        rewardFormattedTextField.setText(quest.getReward());
        givenByFormattedTextField.setText(quest.getQuestGiver().getOneLiner());
        biomeFormattedTextField.setText(quest.getBiome().getOneLiner());
        dungeonFormattedTextField.setText(quest.getDungeon().getOneLiner());
    }
}
