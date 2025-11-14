package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.NPC;
import AlexisKeesBahl.DWRandomizer_Swing.model.Quest;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.NPCService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;

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
    private JPanel panel1;
    private JButton generateButton;
    private JButton rerollButton;
    private JButton exportButton;
    private JFormattedTextField NPCFormattedTextField;
    private JFormattedTextField genderFormattedTextField;
    private JFormattedTextField ageFormattedTextField;
    private JFormattedTextField appearanceFormattedTextField;
    private JFormattedTextField personalityFormattedTextField;
    private JFormattedTextField quirkFormattedTextField;

    public QuestNPCMenuForm(ApplicationContext context,
                       SessionManager sessionManager,
                       NPCService npcService,
                       GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.npcService=npcService;
        this.genericFunctions=genericFunctions;
        if (sessionManager.getSelected(Quest.class)==null) {
            this.quest = new Quest();
            this.npc=new NPC();
        }
        else {
            this.quest = sessionManager.getSelected(Quest.class);
            this.npc = quest.getQuestGiver();
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
            npcService.rollNPC(npc);
            quest.setQuestGiver(npc);
            updateFields();
        });

        rerollButton.addActionListener(e->{
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

        goBackButton.addActionListener(e -> {
            QuestMenuForm questMenuForm = context.getBean(QuestMenuForm.class);
            questMenuForm.setVisible(true);
            questMenuForm.updateFields();
            dispose();
        });
    }

    public void updateFields() {
        NPCFormattedTextField.setText(npc.getOneLiner());
        genderFormattedTextField.setText(npc.getGender());
        ageFormattedTextField.setText(npc.getAge());
        appearanceFormattedTextField.setText(npc.getAppearance());
        personalityFormattedTextField.setText(npc.getPersonality());
        quirkFormattedTextField.setText(npc.getQuirk());
    }
}
