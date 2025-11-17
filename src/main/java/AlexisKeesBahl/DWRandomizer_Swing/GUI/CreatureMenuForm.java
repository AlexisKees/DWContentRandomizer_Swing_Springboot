package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Creature;
import AlexisKeesBahl.DWRandomizer_Swing.service.CreatureService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class CreatureMenuForm extends JFrame {

    private final CreatureService creatureService;
    private final GenericFunctions genericFunctions;
    private final SessionManager sessionManager;
    private final ApplicationContext context;

    private Creature creature;

    private JFormattedTextField categoryField;
    private JFormattedTextField subcategoryField;
    private JFormattedTextField speciesField;
    private JFormattedTextField groupSizeField;
    private JFormattedTextField individualSizeField;
    private JFormattedTextField hpField;
    private JFormattedTextField armorField;
    private JFormattedTextField damageField;
    private JFormattedTextField tagsField;
    private JFormattedTextField alignmentField;
    private JFormattedTextField dispositionField;

    private JButton generateButton;
    private JButton rerollSubButton;
    private JButton rerollSpeciesButton;
    private JButton rerollStatsButton;
    private JButton exportButton;
    private JButton backButton;

    public CreatureMenuForm(CreatureService creatureService,
                            GenericFunctions genericFunctions,
                            SessionManager sessionManager,
                            ApplicationContext context) {

        this.creatureService = creatureService;
        this.genericFunctions = genericFunctions;
        this.sessionManager = sessionManager;
        this.context = context;

        this.creature = sessionManager.getSelected(Creature.class) != null
                ? sessionManager.getSelected(Creature.class)
                : new Creature();

        buildUI();
        updateFields();
        initializeListeners();
    }

    private void buildUI() {

        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Random creature generator");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        top.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        main.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;

        categoryField = addField(center, gbc, "Category:", labelFont, fieldFont);
        subcategoryField = addField(center, gbc, "Subcategory:", labelFont, fieldFont);
        speciesField = addField(center, gbc, "Species:", labelFont, fieldFont);
        groupSizeField = addField(center, gbc, "Group size:", labelFont, fieldFont);
        individualSizeField = addField(center, gbc, "Individual size:", labelFont, fieldFont);

        addHPArmor(center, gbc, labelFont, fieldFont);

        damageField = addField(center, gbc, "Damage:", labelFont, fieldFont);
        tagsField = addField(center, gbc, "Tags:", labelFont, fieldFont);
        alignmentField = addField(center, gbc, "Alignment:", labelFont, fieldFont);
        dispositionField = addField(center, gbc, "Disposition:", labelFont, fieldFont);

        gbc.gridy++;
        gbc.weighty = 1;
        center.add(Box.createVerticalGlue(), gbc);

        JScrollPane scroll = new JScrollPane(center);
        scroll.setBorder(null);
        main.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new GridLayout(1, 3, 10, 0));
        generateButton = styledButton("Generate", buttonFont);
        rerollSubButton = styledButton("Reroll subcategory", buttonFont);
        rerollSpeciesButton = styledButton("Reroll species", buttonFont);
        row1.add(generateButton);
        row1.add(rerollSubButton);
        row1.add(rerollSpeciesButton);

        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));
        rerollStatsButton = styledButton("Reroll stats", buttonFont);
        exportButton = styledButton("Export", buttonFont);
        row2.add(rerollStatsButton);
        row2.add(exportButton);

        bottom.add(row1);
        bottom.add(Box.createVerticalStrut(8));
        bottom.add(row2);
        bottom.add(Box.createVerticalStrut(10));

        backButton = styledButton("Go back", buttonFont);

        JPanel backRow = new JPanel(new BorderLayout());
        backRow.add(backButton, BorderLayout.CENTER);
        bottom.add(backRow);

        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);
        pack();
        setLocationRelativeTo(null);
    }

    private JFormattedTextField addField(JPanel parent, GridBagConstraints gbc,
                                         String txt, Font labelFont, Font fieldFont) {

        JLabel label = new JLabel(txt);
        label.setFont(labelFont);
        parent.add(label, gbc);

        gbc.gridy++;
        JFormattedTextField field = new JFormattedTextField();
        field.setFont(fieldFont);
        field.setEditable(false);
        parent.add(field, gbc);

        gbc.gridy++;
        return field;
    }

    private void addHPArmor(JPanel parent, GridBagConstraints gbc,
                            Font labelFont, Font fieldFont) {

        JPanel row = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.WEST;

        JLabel hpLbl = new JLabel("HP:");
        hpLbl.setFont(labelFont);

        hpField = new JFormattedTextField();
        hpField.setFont(fieldFont);
        hpField.setColumns(4);
        hpField.setEditable(false);

        JLabel armorLbl = new JLabel("Armor:");
        armorLbl.setFont(labelFont);

        armorField = new JFormattedTextField();
        armorField.setFont(fieldFont);
        armorField.setColumns(4);
        armorField.setEditable(false);

        c.gridx = 0; row.add(hpLbl, c);
        c.gridx = 1; row.add(hpField, c);
        c.gridx = 2; row.add(armorLbl, c);
        c.gridx = 3; row.add(armorField, c);

        parent.add(row, gbc);
        gbc.gridy++;
    }

    private JButton styledButton(String text, Font font) {
        JButton b = new JButton(text);
        b.setFont(font);
        return b;
    }

    private void initializeListeners() {

        generateButton.addActionListener(e -> {
            creatureService.rollAttributes(creature);
            sessionManager.add(Creature.class, creature);
            updateFields();
        });

        rerollSubButton.addActionListener(e -> {
            creatureService.reRollSubcategory(creature);
            updateFields();
        });

        rerollSpeciesButton.addActionListener(e -> {
            creatureService.reRollPrompt(creature);
            updateFields();
        });

        rerollStatsButton.addActionListener(e -> {
            creatureService.rollStats(creature);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(creature);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export file...");
            }
        });

        backButton.addActionListener(e -> {
            MainMenuForm form = context.getBean(MainMenuForm.class);
            form.setVisible(true);
            dispose();
        });
    }

    private void updateFields() {
        categoryField.setText(creature.getCategory());
        subcategoryField.setText(creature.getSubcategory());
        speciesField.setText(creature.getPrompt());
        groupSizeField.setText(creature.getGroupSize());
        individualSizeField.setText(creature.getSize());
        hpField.setText(String.valueOf(creature.getHitPoints()));
        armorField.setText(String.valueOf(creature.getArmor()));
        damageField.setText(creature.getDamage());
        tagsField.setText(creature.getTags());
        alignmentField.setText(creature.getAlignment());
        dispositionField.setText(creature.getDisposition());
    }
}
