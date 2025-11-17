package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Discovery;
import AlexisKeesBahl.DWRandomizer_Swing.service.DiscoveryService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class DiscoveryMenuForm extends JFrame {

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final DiscoveryService discoveryService;
    private final GenericFunctions genericFunctions;

    private Discovery discovery;

    private JTextField categoryField;
    private JTextField subcategoryField;
    private JTextPane discoveryPane;

    private JButton generateButton;
    private JButton rerollSCButton;
    private JButton rerollDiscoveryButton;
    private JButton exportButton;
    private JButton backButton;

    public DiscoveryMenuForm(ApplicationContext context,
                             SessionManager sessionManager,
                             DiscoveryService discoveryService,
                             GenericFunctions genericFunctions) {

        this.context = context;
        this.sessionManager = sessionManager;
        this.discoveryService = discoveryService;
        this.genericFunctions = genericFunctions;

        this.discovery = sessionManager.getSelected(Discovery.class) != null
                ? sessionManager.getSelected(Discovery.class)
                : new Discovery();

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

        JLabel title = new JLabel("Random discovery generator");
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

        JLabel lblDiscovery = new JLabel("Discovery:");
        lblDiscovery.setFont(labelFont);
        center.add(lblDiscovery, gbc);

        gbc.gridy++;

        discoveryPane = new JTextPane();
        discoveryPane.setFont(fieldFont);
        discoveryPane.setEditable(false);

        JScrollPane scroll = new JScrollPane(discoveryPane);
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
        rerollDiscoveryButton = styledButton("Reroll discovery", buttonFont);
        exportButton = styledButton("Export", buttonFont);
        row2.add(rerollDiscoveryButton);
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
            discoveryService.rollDiscovery(discovery);
            sessionManager.add(Discovery.class, discovery);
            updateFields();
        });

        rerollSCButton.addActionListener(e -> {
            discoveryService.rollSubcategory(discovery);
            sessionManager.add(Discovery.class, discovery);
            updateFields();
        });

        rerollDiscoveryButton.addActionListener(e -> {
            discoveryService.rollPrompt(discovery);
            sessionManager.add(Discovery.class, discovery);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(discovery);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export discovery...");
            }
        });

        backButton.addActionListener(e -> {
            MainMenuForm mainMenu = context.getBean(MainMenuForm.class);
            mainMenu.setVisible(true);
            dispose();
        });
    }

    private void updateFields() {
        categoryField.setText(discovery.getCategory());
        subcategoryField.setText(discovery.getSubcategory());
        discoveryPane.setText(discovery.getFinalResult());
    }
}
