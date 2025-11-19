package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Steading;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.SteadingService;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class SteadingMenuForm extends JFrame {

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final SteadingService steadingService;
    private final GenericFunctions genericFunctions;

    private Steading steading;

    private JFormattedTextField steadingField;
    private JFormattedTextField tagsField;
    private JFormattedTextField alignmentField;
    private JFormattedTextField dangerField;
    private JFormattedTextField featureField;
    private JFormattedTextField problemField;

    private JButton generateButton;
    private JButton rerollButton;
    private JButton exportButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton goBackButton;

    public SteadingMenuForm(
            ApplicationContext context,
            SessionManager sessionManager,
            SteadingService steadingService,
            GenericFunctions genericFunctions
    ) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.steadingService = steadingService;
        this.genericFunctions = genericFunctions;

        if (sessionManager.getSelected(Steading.class) == null)
            this.steading = new Steading();
        else
            this.steading = sessionManager.getSelected(Steading.class);

        buildUI();
        initializeLogic();
        updateFields();
    }


    private void buildUI() {

        leftButton = new JButton("←");
        rightButton = new JButton("→");

        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(root);

        JLabel title = new JLabel("Random steading generator");
        title.setFont(titleFont);
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        root.add(title);
        root.add(Box.createVerticalStrut(20));

        steadingField = new JFormattedTextField();
        tagsField = new JFormattedTextField();
        alignmentField = new JFormattedTextField();
        dangerField = new JFormattedTextField();
        featureField = new JFormattedTextField();
        problemField = new JFormattedTextField();

        JComponent[] fields = {
                labeledField("Steading:", steadingField, labelFont, fieldFont),
                labeledField("Tags:", tagsField, labelFont, fieldFont),
                labeledField("Alignment:", alignmentField, labelFont, fieldFont),
                labeledField("Danger level:", dangerField, labelFont, fieldFont),
                labeledField("Feature:", featureField, labelFont, fieldFont),
                labeledField("Problem:", problemField, labelFont, fieldFont)
        };

        for (JComponent c : fields) {
            root.add(c);
            root.add(Box.createVerticalStrut(10));
        }

        JPanel buttonRow = new JPanel(new GridLayout(1, 3, 10, 0));

        generateButton = new JButton("Generate");
        rerollButton = new JButton("Reroll");
        exportButton = new JButton("Export");

        JButton[] br = { generateButton, rerollButton, exportButton };

        for (JButton b : br) {
            b.setFont(buttonFont);
            buttonRow.add(b);
        }

        root.add(Box.createVerticalStrut(10));
        root.add(buttonRow);
        root.add(Box.createVerticalStrut(20));

        JPanel arrowsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rightButton.setFont(new Font("Arial", Font.PLAIN, 14));

        arrowsPanel.add(leftButton);
        arrowsPanel.add(rightButton);

        root.add(arrowsPanel);
        root.add(Box.createVerticalStrut(8));


        goBackButton = new JButton("Go back");
        goBackButton.setFont(buttonFont);
        goBackButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        goBackButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        root.add(goBackButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
    }


    private JPanel labeledField(String text, JFormattedTextField field, Font labelFont, Font fieldFont) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(200, 28));
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }


    private void initializeLogic() {
        generateButton.addActionListener(e -> {
            steading=steading.clone();
            steadingService.rollSteading(steading);
            sessionManager.add(Steading.class, steading);
            updateFields();
        });

        rerollButton.addActionListener(e -> {
            steading=steading.clone();
            if (sessionManager.getSelected(Steading.class) == null)
                steadingService.rollSteading(steading);
            else
                steadingService.rollDetails(steading);

            sessionManager.add(Steading.class, steading);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(steading);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export steading...");
            }
        });

        leftButton.addActionListener(e->{
            if(sessionManager.getList(Steading.class).indexOf(steading)>0){
                int currentIndex = sessionManager.getList(Steading.class).indexOf(steading);
                int newIndex=currentIndex-1;
                steading = sessionManager.getList(Steading.class).get(newIndex);
                updateFields();}
        });

        rightButton.addActionListener(e->{
            if(sessionManager.getList(Steading.class).indexOf(steading)<sessionManager.getList(Steading.class).size()-1) {
                int currentIndex = sessionManager.getList(Steading.class).indexOf(steading);
                int newIndex = currentIndex + 1;
                this.steading = sessionManager.getList(Steading.class).get(newIndex);
                updateFields();
            }
        });

        goBackButton.addActionListener(e -> {
            MainMenuForm m = context.getBean(MainMenuForm.class);
            m.setVisible(true);
            dispose();
        });
    }


    private void updateFields() {
        steadingField.setText(String.format("%s, %s %s", steading.getName(), steading.getRaceOfBuilders(), steading.getSize()));
        tagsField.setText(steading.getTags());
        alignmentField.setText(steading.getAlignment());
        dangerField.setText(steading.getDangerLevel());
        featureField.setText(steading.getFeature());
        problemField.setText(steading.getProblem());
    }
}
