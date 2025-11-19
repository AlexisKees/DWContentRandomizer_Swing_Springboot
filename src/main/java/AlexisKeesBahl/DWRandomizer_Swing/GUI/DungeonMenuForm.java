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
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.function.BiFunction;

@Component
@Scope("prototype")
public class DungeonMenuForm extends JFrame {

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final DungeonService dungeonService;
    private final GenericFunctions genericFunctions;

    private Dungeon dungeon;

    private JButton generateButton;
    private JButton addAreasButton;
    private JButton exportButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton goBackButton;

    private JFormattedTextField nameTextField;
    private JFormattedTextField sizeFormattedTextField;
    private JFormattedTextField roomsFormattedTextField;
    private JFormattedTextField exitsFormattedTextField;
    private JFormattedTextField themesFormattedTextField;
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
        this.context = context;
        this.sessionManager = sessionManager;
        this.dungeonService = dungeonService;
        this.genericFunctions = genericFunctions;

        Dungeon selected = sessionManager.getSelected(Dungeon.class);
        if (selected == null) {
            this.dungeon = new Dungeon();
        } else {
            this.dungeon = selected;
        }

        buildUI();

        if (selected != null) {
            updateFields();
        }

        initializeForm();
    }

    private void initializeForm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            dungeon=dungeon.clone();
            dungeonService.rollDungeon(dungeon);
            sessionManager.add(Dungeon.class, dungeon);
            updateFields();
        });

        addAreasButton.addActionListener(e -> {
            dungeon=dungeon.clone();
            if (sessionManager.getSelected(Dungeon.class) == null) {
                dungeonService.rollDungeon(dungeon);
                sessionManager.add(Dungeon.class, dungeon);
                updateFields();
            }
            DungeonAreaForm dungeonAreaForm = context.getBean(DungeonAreaForm.class);
            dungeonAreaForm.setVisible(true);
            this.setVisible(false);
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(dungeon);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export dungeon");
            }
        });

        leftButton.addActionListener(e->{
            if(sessionManager.getList(Dungeon.class).indexOf(dungeon)>0){
                int currentIndex = sessionManager.getList(Dungeon.class).indexOf(dungeon);
                int newIndex=currentIndex-1;
                dungeon = sessionManager.getList(Dungeon.class).get(newIndex);
                updateFields();}
        });

        rightButton.addActionListener(e->{
            if(sessionManager.getList(Dungeon.class).indexOf(dungeon)<sessionManager.getList(Dungeon.class).size()-1) {
                int currentIndex = sessionManager.getList(Dungeon.class).indexOf(dungeon);
                int newIndex = currentIndex + 1;
                this.dungeon = sessionManager.getList(Dungeon.class).get(newIndex);
                updateFields();
            }
        });

        goBackButton.addActionListener(e -> {
            MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
            mainMenuForm.setVisible(true);
            dispose();
        });
    }

    public void updateFields() {
        if (dungeon == null) return;

        if (dungeon.getName() != null)
            nameTextField.setText(dungeon.getName().toUpperCase());
        if (dungeon.getSize() != null)
            sizeFormattedTextField.setText(dungeon.getSize());
        roomsFormattedTextField.setText(String.valueOf(dungeon.getRooms()));
        exitsFormattedTextField.setText(String.valueOf(dungeon.getExits()));

        if (dungeon.getThemes() != null)
            themesFormattedTextField.setText(dungeon.getThemes());
        themesLabel.setText("Themes: " + dungeon.getThemesAmount());

        if (dungeon.getForm() != null)
            formFormattedTextField.setText(dungeon.getForm());
        if (dungeon.getSituation() != null)
            situationFormattedTextField.setText(dungeon.getSituation());
        if (dungeon.getAccessibility() != null)
            accessibilityFormattedTextField.setText(dungeon.getAccessibility());
        if (dungeon.getBuilder() != null)
            builderFormattedTextField.setText(dungeon.getBuilder());
        if (dungeon.getPurpose() != null)
            functionFormattedTextField.setText(dungeon.getPurpose());
        if (dungeon.getCauseOfRuin() != null)
            causeOfRuinFormattedTextField.setText(dungeon.getCauseOfRuin());

        areasLabel.setText("Areas: " + dungeon.getRooms());

        if (dungeon.getAreas() != null && !dungeon.getAreas().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int areaNumber = 1;
            for (Area a : dungeon.getAreas()) {
                sb.append(String.format("\nAREA %d:\n%s", areaNumber, a));
                areaNumber++;
            }
            areasTextPane.setText(sb.toString());
        } else {
            areasTextPane.setText("");
        }
    }

    private void buildUI() {

        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);


        nameTextField = new JFormattedTextField();
        sizeFormattedTextField = new JFormattedTextField();
        roomsFormattedTextField = new JFormattedTextField();
        exitsFormattedTextField = new JFormattedTextField();
        themesFormattedTextField = new JFormattedTextField();
        formFormattedTextField = new JFormattedTextField();
        situationFormattedTextField = new JFormattedTextField();
        accessibilityFormattedTextField = new JFormattedTextField();
        builderFormattedTextField = new JFormattedTextField();
        functionFormattedTextField = new JFormattedTextField();
        causeOfRuinFormattedTextField = new JFormattedTextField();
        leftButton = new JButton("←");
        rightButton = new JButton("→");

        themesLabel = new JLabel("Themes:");
        areasLabel = new JLabel("Areas:");

        areasTextPane = new JTextPane();

        generateButton = new JButton("Generate dungeon");
        addAreasButton = new JButton("Add / edit areas");
        exportButton = new JButton("Export");
        goBackButton = new JButton("Back");


        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JLabel title = new JLabel("Random dungeon generator");
        title.setFont(titleFont);
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        root.add(title);
        root.add(Box.createRigidArea(new Dimension(0, 15)));


        BiFunction<JLabel, JComponent, JPanel> lf = (lbl, field) -> {
            JPanel p = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(2, 2, 2, 2);

            lbl.setFont(labelFont);
            if (field instanceof JTextComponent textComp) {
                textComp.setFont(fieldFont);
            } else {
                field.setFont(fieldFont);
            }

            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.WEST;
            p.add(lbl, c);

            c.gridx = 1;
            c.weightx = 1;
            c.fill = GridBagConstraints.HORIZONTAL;
            p.add(field, c);

            return p;
        };


        root.add(lf.apply(new JLabel("Name:"), nameTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));


        JPanel sizeRow = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 4, 2, 4);

        JLabel sizeLbl = new JLabel("Size:");
        sizeLbl.setFont(labelFont);
        sizeFormattedTextField.setFont(fieldFont);

        c.gridx = 0;
        c.gridy = 0;
        sizeRow.add(sizeLbl, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        sizeRow.add(sizeFormattedTextField, c);

        JLabel roomsLbl = new JLabel("Rooms:");
        roomsLbl.setFont(labelFont);
        roomsFormattedTextField.setFont(fieldFont);

        c.gridx = 2;
        c.fill = 0;
        sizeRow.add(roomsLbl, c);
        c.gridx = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        sizeRow.add(roomsFormattedTextField, c);

        JLabel exitsLbl = new JLabel("Exits:");
        exitsLbl.setFont(labelFont);
        exitsFormattedTextField.setFont(fieldFont);

        c.gridx = 4;
        c.fill = 0;
        sizeRow.add(exitsLbl, c);
        c.gridx = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        sizeRow.add(exitsFormattedTextField, c);

        root.add(sizeRow);
        root.add(Box.createRigidArea(new Dimension(0, 5)));


        root.add(lf.apply(themesLabel, themesFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));

        root.add(lf.apply(new JLabel("Form:"), formFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));

        root.add(lf.apply(new JLabel("Situation:"), situationFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));

        root.add(lf.apply(new JLabel("Accessibility:"), accessibilityFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));

        root.add(lf.apply(new JLabel("Builder:"), builderFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));

        root.add(lf.apply(new JLabel("Function:"), functionFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 5)));

        root.add(lf.apply(new JLabel("Cause of ruin:"), causeOfRuinFormattedTextField));
        root.add(Box.createRigidArea(new Dimension(0, 10)));


        areasLabel.setFont(labelFont);
        areasLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        areasTextPane.setFont(fieldFont);
        areasTextPane.setEditable(false);
        JScrollPane areasScroll = new JScrollPane(areasTextPane);
        areasScroll.setPreferredSize(new Dimension(350, 150));
        areasScroll.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        root.add(areasLabel);
        root.add(Box.createRigidArea(new Dimension(0, 3)));
        root.add(areasScroll);
        root.add(Box.createRigidArea(new Dimension(0, 15)));


        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton[] btns = {generateButton, addAreasButton, exportButton};
        for (JButton b : btns) {
            b.setFont(buttonFont);
            b.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            buttons.add(b);
            buttons.add(Box.createRigidArea(new Dimension(10, 0)));
        }

        root.add(buttons);
        root.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel arrowsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rightButton.setFont(new Font("Arial", Font.PLAIN, 14));

        arrowsPanel.add(leftButton);
        arrowsPanel.add(rightButton);

        root.add(arrowsPanel);
        root.add(Box.createVerticalStrut(8));


        goBackButton.setFont(buttonFont);
        goBackButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        root.add(goBackButton);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }
}
