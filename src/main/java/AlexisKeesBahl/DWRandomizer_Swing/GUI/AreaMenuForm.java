package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.service.AreaService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class AreaMenuForm extends JFrame {

    private final SessionManager sessionManager;
    private final AreaService areaService;
    private final ApplicationContext context;
    private final GenericFunctions genericFunctions;
    private JPanel panel1;
    private JButton newAreaButton;
    private JButton rerollButton;
    private JButton exportButton;
    private JButton goBackButton;
    private JFormattedTextField areaFormattedTextField;
    private JTextPane dressingTextPane;
    private JTextPane rarityTextPane;
    private JTextPane discoveriesTextPane;
    private JTextPane dangersTextPane;
    private JLabel discoveriesLabel;
    private JLabel dangersLabel;
    private Area area;

    @Autowired
    public AreaMenuForm(ApplicationContext context,
                        SessionManager sessionManager,
                        AreaService areaService,
                        GenericFunctions genericFunctions){

        this.areaService=areaService;
        this.sessionManager=sessionManager;
        this.context = context;
        this.genericFunctions = genericFunctions;
        if(sessionManager.getSelected(Area.class)==null) {
            this.area = new Area();
        } else {
            this.area = sessionManager.getSelected(Area.class);
        }

        iniciarForma(context);
    }

    private void iniciarForma(ApplicationContext context){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        newAreaButton.addActionListener(e ->{
            areaService.rollArea(area);
            sessionManager.add(Area.class,area.clone());
            updateFields();
        });


        rerollButton.addActionListener(e -> {
            areaService.rollAreaDetails(area);
            sessionManager.add(Area.class,area.clone());
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(this.area);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex){
                dispose();
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
        areaFormattedTextField.setText(area.getAreaType());
        dressingTextPane.setText(area.getAreaDressing());
        rarityTextPane.setText(area.getRarity());
        discoveriesLabel.setText(String.format("Discoveries: %d",area.getDiscoveriesAmount()));
        discoveriesTextPane.setText(area.getDiscoveries());
        dangersLabel.setText(String.format("Dangers: %d",area.getDangersAmount()));
        dangersTextPane.setText(area.getDangers());
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
