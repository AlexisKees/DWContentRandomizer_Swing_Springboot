package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Discovery;
import AlexisKeesBahl.DWRandomizer_Swing.service.DiscoveryService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope("prototype")
public class  DiscoveryMenuForm extends JFrame{

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final DiscoveryService discoveryService;
    private final GenericFunctions genericFunctions;
    private Discovery discovery;
    private JButton goBackButton;
    private JPanel panel1;
    private JTextField categoryTextField;
    private JTextField subcategoryTextField;
    private JButton generateButton;
    private JButton rerollSCButton;
    private JButton rerollDiscoveryButton;
    private JButton exportButton;
    private JTextPane discoveryTextPane;

    public DiscoveryMenuForm(ApplicationContext context,
    SessionManager sessionManager,
    DiscoveryService discoveryService,
    GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.discoveryService=discoveryService;
        this.genericFunctions=genericFunctions;
        if(sessionManager.getSelected(Discovery.class)==null)
            this.discovery = new Discovery();
        else {
            this.discovery = sessionManager.getSelected(Discovery.class);
            updateFields();
        }


        initializeForm(context);

        }

    private void initializeForm(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e ->{
            discoveryService.rollDiscovery(discovery);
            sessionManager.add(Discovery.class,discovery);
            updateFields();
        });

        rerollSCButton.addActionListener(e->{
            if(sessionManager.getSelected(Discovery.class)==null){
                discoveryService.rollDiscovery(discovery);
            } else {
                discoveryService.rollSubcategory(discovery);
            }
            sessionManager.add(Discovery.class,discovery);
            updateFields();
        });

        rerollDiscoveryButton.addActionListener(e->{
            if(sessionManager.getSelected(Discovery.class)==null)
                discoveryService.rollDiscovery(discovery);
            else
                discoveryService.rollPrompt(discovery);

            sessionManager.add(Discovery.class,discovery);
            updateFields();
        });

        exportButton.addActionListener(e ->{
            try {
                genericFunctions.exportPW(discovery);
                JOptionPane.showMessageDialog(this,"Check your files!");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Couldn't export discovery...");
            }
        });

        goBackButton.addActionListener(e ->{
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
        });
    }

    private void updateFields() {
        categoryTextField.setText(discovery.getCategory());
        subcategoryTextField.setText(discovery.getSubcategory());
        discoveryTextPane.setText(discovery.getFinalResult());
    }
}
