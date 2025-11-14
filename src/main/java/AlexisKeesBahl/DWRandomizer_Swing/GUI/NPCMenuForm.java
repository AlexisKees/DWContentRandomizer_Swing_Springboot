package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.NPC;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.NPCService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Scope("prototype")
public class NPCMenuForm extends JFrame{

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final NPCService npcService;
    private final GenericFunctions genericFunctions;
    private NPC npc;
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

    public NPCMenuForm(ApplicationContext context,
    SessionManager sessionManager,
    NPCService npcService,
    GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.npcService=npcService;
        this.genericFunctions=genericFunctions;
        if(sessionManager.getSelected(NPC.class)==null)
            this.npc=new NPC();
        else {
            this.npc = sessionManager.getSelected(NPC.class);
            updateFields();
        }

        initializeForm(context);

    }


    private void initializeForm(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e->{
            npcService.rollNPC(npc);
            sessionManager.add(NPC.class,npc);
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
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
        });
    }

    private void updateFields() {
        NPCFormattedTextField.setText(npc.getOneLiner());
        genderFormattedTextField.setText(npc.getGender());
        ageFormattedTextField.setText(npc.getAge());
        appearanceFormattedTextField.setText(npc.getAppearance());
        personalityFormattedTextField.setText(npc.getPersonality());
        quirkFormattedTextField.setText(npc.getQuirk());
    }
}
