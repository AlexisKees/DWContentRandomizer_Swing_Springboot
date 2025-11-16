package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Creature;
import AlexisKeesBahl.DWRandomizer_Swing.service.CreatureService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope("prototype")
public class CreatureMenuForm extends JFrame {
    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final CreatureService creatureService;
    private final GenericFunctions genericFunctions;
    private Creature creature;
    private JButton goBackButton;
     private JPanel panel1;
    private JButton generateButton;
    private JButton rerollSubcategoryButton;
    private JButton rerollSpeciesButton;
    private JButton rerollStatsButton;
    private JButton exportButton;
    private JFormattedTextField categoryFormattedTextField;
    private JFormattedTextField subcategoryFormattedTextField;
    private JFormattedTextField speciesFormattedTextField;
    private JFormattedTextField groupSizeFormattedTextField;
    private JFormattedTextField individualSizeFormattedTextField;
    private JFormattedTextField HPFormattedTextField;
    private JFormattedTextField armorFormattedTextField;
    private JFormattedTextField damageFormattedTextField;
    private JFormattedTextField tagsFormattedTextField;
    private JFormattedTextField alignmentFormattedTextField;
    private JFormattedTextField dispositionFormattedTextField;

    public CreatureMenuForm(ApplicationContext context,
    SessionManager sessionManager,
    CreatureService creatureService,
    GenericFunctions genericFunctions) {
         this.context=context;
        this.sessionManager=sessionManager;
        this.creatureService=creatureService;
        this.genericFunctions=genericFunctions;
        if(sessionManager.getSelected(Creature.class)==null)
            this.creature = new Creature();
        else {
            this.creature = sessionManager.getSelected(Creature.class);
            updateFields();
        }

         initializeForm(context);
    }

    private void initializeForm(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            creatureService.rollAttributes(creature);
            sessionManager.add(Creature.class,creature);
            updateFields();
        });

        rerollSubcategoryButton.addActionListener(e ->{
            if(sessionManager.getSelected(Creature.class)==null){
                creatureService.rollAttributes(creature);
            } else {
                creatureService.reRollSubcategory(creature);
            }
            sessionManager.add(Creature.class,creature);
            updateFields();
        });

        rerollSpeciesButton.addActionListener(e ->{
            if(sessionManager.getSelected(Creature.class)==null){
                creatureService.rollAttributes(creature);
            } else {
                creatureService.reRollPrompt(creature);
            }
            sessionManager.add(Creature.class,creature);
            updateFields();
        });

        rerollStatsButton.addActionListener(e -> {
            if(sessionManager.getSelected(Creature.class)==null){
                creatureService.rollAttributes(creature);
            } else {
                creatureService.rollStats(creature);
            }
            sessionManager.add(Creature.class,creature);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(creature);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Couldn't export creature...");
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
            }
        });
    }

    private void updateFields() {
        categoryFormattedTextField.setText(creature.getCategory());
        subcategoryFormattedTextField.setText(creature.getSubcategory());
        speciesFormattedTextField.setText(creature.getPrompt());
        groupSizeFormattedTextField.setText(creature.getGroupSize());
        individualSizeFormattedTextField.setText(creature.getSize());
        HPFormattedTextField.setText(String.format("%d",creature.getHitPoints()));
        armorFormattedTextField.setText(String.format("%d (%s)",creature.getArmor(),creature.getArmorType()));
        damageFormattedTextField.setText(String.format("%s (%s)", creature.getDamage(),creature.getDamageType()));
        tagsFormattedTextField.setText(creature.getTags());
        alignmentFormattedTextField.setText(creature.getAlignment());
        dispositionFormattedTextField.setText(creature.getDisposition());
    }
}
