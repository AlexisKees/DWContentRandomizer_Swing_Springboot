package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Danger;
import AlexisKeesBahl.DWRandomizer_Swing.service.DangerService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class DangerMenuForm extends JFrame {

    private final DangerService dangerService;
    private final GenericFunctions genericFunctions;
    private final SessionManager sessionManager;
    private final ApplicationContext context;

    private Danger danger;

    private JTextField categoryField;
    private JTextField subcategoryField;
    private JTextPane dangerPane;

    private JButton generateButton;
    private JButton rerollSCButton;
    private JButton rerollDangerButton;
    private JButton exportButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton backButton;

    public DangerMenuForm(SessionManager sessionManager,
                          DangerService dangerService,
                          GenericFunctions genericFunctions,
                          ApplicationContext context) {

        this.dangerService = dangerService;
        this.genericFunctions = genericFunctions;
        this.sessionManager = sessionManager;
        this.context = context;

        this.danger = sessionManager.getSelected(Danger.class) != null
                ? sessionManager.getSelected(Danger.class)
                : new Danger();

        buildUI();
        updateFields();
        initializeListeners();
    }

    private void buildUI() {

        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);
        leftButton = new JButton("←");
        rightButton = new JButton("→");

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JLabel title = new JLabel("Random danger generator");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        top.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        main.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;

        categoryField = addField(center, gbc, "Category:", labelFont, fieldFont);
        subcategoryField = addField(center, gbc, "Subcategory:", labelFont, fieldFont);

        JLabel lblDanger = new JLabel("Danger:");
        lblDanger.setFont(labelFont);
        center.add(lblDanger, gbc);

        gbc.gridy++;

        dangerPane = new JTextPane();
        dangerPane.setFont(fieldFont);
        dangerPane.setEditable(false);

        JScrollPane scroll = new JScrollPane(dangerPane);
        scroll.setPreferredSize(new Dimension(300, 200));
        center.add(scroll, gbc);

        gbc.gridy++;

        gbc.weighty = 1;
        center.add(Box.createVerticalGlue(), gbc);

        main.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        generateButton = styledButton("Generate", buttonFont);
        rerollSCButton = styledButton("Reroll SC", buttonFont);
        row1.add(generateButton);
        row1.add(rerollSCButton);

        JPanel row2 = new JPanel(new GridLayout(1, 2, 10, 0));
        rerollDangerButton = styledButton("Reroll danger", buttonFont);
        exportButton = styledButton("Export", buttonFont);
        row2.add(rerollDangerButton);
        row2.add(exportButton);

        bottom.add(row1);
        bottom.add(Box.createVerticalStrut(8));
        bottom.add(row2);
        bottom.add(Box.createVerticalStrut(10));
        JPanel arrowsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rightButton.setFont(new Font("Arial", Font.PLAIN, 14));

        arrowsPanel.add(leftButton);
        arrowsPanel.add(rightButton);

        bottom.add(arrowsPanel);
        bottom.add(Box.createVerticalStrut(8));

        backButton = styledButton("Go back", buttonFont);
        JPanel backRow = new JPanel(new BorderLayout());
        backRow.add(backButton, BorderLayout.CENTER);

        bottom.add(backRow);

        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);
        pack();
        setLocationRelativeTo(null);
    }

    private JTextField addField(JPanel parent, GridBagConstraints gbc,
                                String title, Font labelFont, Font fieldFont) {

        JLabel lbl = new JLabel(title);
        lbl.setFont(labelFont);
        parent.add(lbl, gbc);

        gbc.gridy++;

        JTextField txt = new JTextField();
        txt.setFont(fieldFont);
        txt.setEditable(false);

        parent.add(txt, gbc);

        gbc.gridy++;

        return txt;
    }

    private JButton styledButton(String text, Font font) {
        JButton b = new JButton(text);
        b.setFont(font);
        return b;
    }

    private void initializeListeners() {

        generateButton.addActionListener(e -> {
            danger=danger.clone();
            dangerService.rollDanger(danger);
            sessionManager.add(Danger.class, danger);
            updateFields();
        });

        rerollSCButton.addActionListener(e -> {
            danger=danger.clone();
            if(sessionManager.getSelected(Danger.class)==null)
                dangerService.rollDanger(danger);
            else
                dangerService.rollSubcategory(danger);
            sessionManager.add(Danger.class, danger);
            updateFields();
        });

        rerollDangerButton.addActionListener(e -> {
            danger=danger.clone();
            if(sessionManager.getSelected(Danger.class)==null)
                dangerService.rollDanger(danger);
            else
                dangerService.rollPrompt(danger);
            sessionManager.add(Danger.class, danger);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(danger);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export...");
            }
        });

        leftButton.addActionListener(e->{
            if(sessionManager.getList(Danger.class).indexOf(danger)>0){
                int currentIndex = sessionManager.getList(Danger.class).indexOf(danger);
                int newIndex=currentIndex-1;
                danger = sessionManager.getList(Danger.class).get(newIndex);
                updateFields();}
        });

        rightButton.addActionListener(e->{
            if(sessionManager.getList(Danger.class).indexOf(danger)<sessionManager.getList(Danger.class).size()-1) {
                int currentIndex = sessionManager.getList(Danger.class).indexOf(danger);
                int newIndex = currentIndex + 1;
                this.danger = sessionManager.getList(Danger.class).get(newIndex);
                updateFields();
            }
        });

        backButton.addActionListener(e -> {
            MainMenuForm mainMenu = context.getBean(MainMenuForm.class);
            mainMenu.setVisible(true);
            dispose();
        });
    }

    private void updateFields() {
        categoryField.setText(danger.getCategory());
        subcategoryField.setText(danger.getSubcategory());
        dangerPane.setText(danger.getFinalResult());
    }
}
