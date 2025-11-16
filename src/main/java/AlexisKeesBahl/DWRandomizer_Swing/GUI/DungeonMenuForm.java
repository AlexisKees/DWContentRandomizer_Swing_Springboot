package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Dungeon;
import AlexisKeesBahl.DWRandomizer_Swing.service.DungeonService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Scope("prototype")
public class DungeonMenuForm extends JFrame{
    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final DungeonService dungeonService;
    private final GenericFunctions genericFunctions;
    private Dungeon dungeon;
    private JButton goBackButton;
    private JPanel panel1; 
    private JFormattedTextField nameTextField;
    private JFormattedTextField sizeFormattedTextField;
    private JFormattedTextField roomsFormattedTextField;
    private JFormattedTextField exitsFormattedTextField;
    private JFormattedTextField themesFormattedTextField;
    private JButton generateButton;
    private JButton addAreasButton;
    private JButton exportButton;
    private JFormattedTextField formFormattedTextField;
    private JFormattedTextField situationFormattedTextField;
    private JFormattedTextField accessibilityFormattedTextField;
    private JFormattedTextField builderFormattedTextField;
    private JFormattedTextField functionFormattedTextField;
    private JFormattedTextField causeOfRuinFormattedTextField;
    private JLabel themesLabel;
    private JLabel areasLabel;
    private JTextPane areasTextPane;

    public DungeonMenuForm(ApplicationContext context,
    SessionManager sessionManager,
    DungeonService dungeonService,
    GenericFunctions genericFunctions) {
        this.context=context;
        this.sessionManager=sessionManager;
        this.dungeonService=dungeonService;
        this.genericFunctions=genericFunctions;
        if (sessionManager.getSelected(Dungeon.class)==null)
            this.dungeon = new Dungeon();
        else {
            this.dungeon = sessionManager.getSelected(Dungeon.class);
            updateFields();
        }

        initializeForm();

    }
    private void initializeForm(){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,700);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            dungeonService.rollDungeon(dungeon);
            sessionManager.add(Dungeon.class,dungeon);
            updateFields();
        });

        addAreasButton.addActionListener(e -> {
            if (sessionManager.getSelected(Dungeon.class)==null){
                dungeonService.rollDungeon(dungeon);
                sessionManager.add(Dungeon.class,dungeon);
                updateFields();
            }
            DungeonAreaForm dungeonAreaForm = context.getBean(DungeonAreaForm.class);
            dungeonAreaForm.setVisible(true);
            this.setVisible(false);
        });

        exportButton.addActionListener(e->{
            try {
                genericFunctions.exportPW(dungeon);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Couldn't export dungeon");
            }
        });


        goBackButton.addActionListener(e -> {
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
        });
    }

    public void updateFields() {
        nameTextField.setText(dungeon.getName().toUpperCase());
        sizeFormattedTextField.setText(dungeon.getSize());
        roomsFormattedTextField.setText(String.valueOf(dungeon.getRooms()));
        exitsFormattedTextField.setText(String.valueOf(dungeon.getExits()));
        themesFormattedTextField.setText(dungeon.getThemes());
        themesLabel.setText(String.format("Themes: " + dungeon.getThemesAmount()));
        formFormattedTextField.setText(dungeon.getForm());
        situationFormattedTextField.setText(dungeon.getSituation());
        accessibilityFormattedTextField.setText(dungeon.getAccessibility());
        builderFormattedTextField.setText(dungeon.getBuilder());
        functionFormattedTextField.setText(dungeon.getPurpose());
        causeOfRuinFormattedTextField.setText(dungeon.getCauseOfRuin());
        areasLabel.setText(String.format("Areas: " + dungeon.getRooms()));


        if (!dungeon.getAreas().isEmpty()) {
            String str = "";
            int areaNumber = 1;
            for (Area a : dungeon.getAreas()) {
                str += String.format("\nAREA %d:\n%s", areaNumber, a);
                areaNumber++;
            }
            areasTextPane.setText(str);
        } else areasTextPane.setText("");
    }
}
