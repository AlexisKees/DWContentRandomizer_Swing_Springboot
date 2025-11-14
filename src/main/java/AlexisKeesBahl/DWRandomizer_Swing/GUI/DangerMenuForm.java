package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Danger;
import AlexisKeesBahl.DWRandomizer_Swing.service.DangerService;
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
public class DangerMenuForm extends JFrame {
    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final DangerService dangerService;
    private final GenericFunctions genericFunctions;
    private Danger danger;
    private JButton goBackButton;
    private JPanel panel1;
    private JButton generateButton;
    private JButton rerollscButton;
    private JTextField categoryTextField;
    private JTextField subcategoryTextField;
    private JTextPane dangerTextPane;
    private JButton rerollDangerButton;
    private JButton exportButton;

    public DangerMenuForm(SessionManager sessionManager,
    DangerService dangerService,
    GenericFunctions genericFunctions,
    ApplicationContext context) {
        this.sessionManager=sessionManager;
        this.context=context;
        this.dangerService = dangerService;
        this.genericFunctions = genericFunctions;
        if (sessionManager.getSelected(Danger.class)==null)
            this.danger = new Danger();
        else {
            this.danger = sessionManager.getSelected(Danger.class);
            updateFields();
        }
        iniciarForma(context);
        }

    private void iniciarForma(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            dangerService.rollDanger(danger);
            sessionManager.add(Danger.class,danger);
            updateFields();
        });

        rerollscButton.addActionListener(e ->{
            if(sessionManager.getSelected(Danger.class)==null)
                dangerService.rollDanger(danger);
            else dangerService.rollSubcategory(danger);

            sessionManager.add(Danger.class,danger);
            updateFields();
        });

        rerollDangerButton.addActionListener(e ->{
            if(sessionManager.getSelected(Danger.class)==null)
                dangerService.rollDanger(danger);
            else dangerService.rollPrompt(danger);

            sessionManager.add(Danger.class,danger);
            updateFields();
        });

        exportButton.addActionListener(e->{
            try{
                genericFunctions.exportPW(danger);
                JOptionPane.showMessageDialog(this,"Check your files!");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Couldn't export danger...");
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm mainMenuForm =context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
            }
        });
    }

    private void updateFields() {
        categoryTextField.setText(danger.getCategory());
        subcategoryTextField.setText(danger.getSubcategory());
        dangerTextPane.setText(danger.getFinalResult());
    }
}
