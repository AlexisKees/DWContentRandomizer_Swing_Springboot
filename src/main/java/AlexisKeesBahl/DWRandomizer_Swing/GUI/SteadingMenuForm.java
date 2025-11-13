package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Steading;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.SteadingService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class SteadingMenuForm extends JFrame{
    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final SteadingService steadingService;
    private final GenericFunctions genericFunctions;
    private Steading steading;
    private JButton goBackButton;
    private JPanel panel1;
    private JButton generateButton;
    private JButton rerollButton;
    private JButton exportButton;
    private JFormattedTextField steadingFormattedTextField;
    private JFormattedTextField alignmentFormattedTextField;
    private JFormattedTextField dangerLevelFormattedTextField;
    private JFormattedTextField tagsFormattedTextField;
    private JFormattedTextField featureFormattedTextField;
    private JFormattedTextField problemFormattedTextField;

    public SteadingMenuForm(ApplicationContext context,
    SessionManager sessionManager,
    SteadingService steadingService,
    GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.steadingService=steadingService;
        this.genericFunctions=genericFunctions;
        if(sessionManager.getSelected(Steading.class)==null)
            this.steading= new Steading();
        else
            this.steading=sessionManager.getSelected(Steading.class);

        iniciarForma(context);

    }
    private void iniciarForma(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            steadingService.rollSteading(steading);
            sessionManager.add(Steading.class,steading);
            updateFields();
        });

        rerollButton.addActionListener(e -> {
            if (sessionManager.getSelected(Steading.class)==null)
                steadingService.rollSteading(steading);
            else
                steadingService.rollDetails(steading);

            sessionManager.add(Steading.class,steading);
            updateFields();
        });

        exportButton.addActionListener(e ->{
            try {
                genericFunctions.exportPW(steading);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Couldn't export steading...");
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
        steadingFormattedTextField.setText(String.format("%s, %s %s", steading.getName(), steading.getRaceOfBuilders(), steading.getSize()));
        alignmentFormattedTextField.setText(steading.getAlignment());
        dangerLevelFormattedTextField.setText(steading.getDangerLevel());
        tagsFormattedTextField.setText(steading.getTags());
        featureFormattedTextField.setText(steading.getFeature());
        problemFormattedTextField.setText(steading.getProblem());
    }
}
