package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Biome;
import AlexisKeesBahl.DWRandomizer_Swing.service.BiomeService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class BiomeMenuForm extends JFrame {

    private final BiomeService biomeService;
    private final GenericFunctions genericFunctions;
    private final SessionManager sessionManager;
    private final ApplicationContext context;

    private Biome biome;

    private JFormattedTextField biomeFormattedTextField;
    private JFormattedTextField weatherFormattedTextField;
    private JFormattedTextField weatherIntensityFormattedTextField;
    private JFormattedTextField wildlifeFormattedTextField;
    private JFormattedTextField populationFormattedTextField;
    private JFormattedTextField roadsFormattedTextField;
    private JFormattedTextField alignmentFormattedTextField;
    private JFormattedTextField distanceFormattedTextField;

    private JButton generateButton;
    private JButton rerollButton;
    private JButton exportButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton backButton;

    public BiomeMenuForm(BiomeService biomeService,
                         GenericFunctions genericFunctions,
                         SessionManager sessionManager,
                         ApplicationContext context) {

        this.biomeService = biomeService;
        this.genericFunctions = genericFunctions;
        this.sessionManager = sessionManager;
        this.context = context;

        if (sessionManager.getSelected(Biome.class) == null) {
            this.biome = new Biome();
        } else {
            this.biome = sessionManager.getSelected(Biome.class);
        }

        buildUI();
        updateFields();
        initializeForm();
    }


    private void buildUI() {

        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Random biome generator");
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
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;

        biomeFormattedTextField = addField(center, gbc, "Biome:", labelFont, fieldFont);
        weatherFormattedTextField = addField(center, gbc, "Weather:", labelFont, fieldFont);
        weatherIntensityFormattedTextField = addField(center, gbc, "Weather intensity:", labelFont, fieldFont);
        wildlifeFormattedTextField = addField(center, gbc, "Wildlife:", labelFont, fieldFont);
        populationFormattedTextField = addField(center, gbc, "Population:", labelFont, fieldFont);
        roadsFormattedTextField = addField(center, gbc, "Roads:", labelFont, fieldFont);
        alignmentFormattedTextField = addField(center, gbc, "Alignment:", labelFont, fieldFont);
        distanceFormattedTextField = addField(center, gbc, "Distance:", labelFont, fieldFont);

        gbc.gridy++;
        gbc.weighty = 1.0;
        center.add(Box.createVerticalGlue(), gbc);

        main.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel row = new JPanel(new GridLayout(1, 3, 10, 0));
        generateButton = new JButton("Generate");
        rerollButton = new JButton("Reroll");
        exportButton = new JButton("Export");
        leftButton = new JButton("←");
        rightButton = new JButton("→");

        generateButton.setFont(buttonFont);
        rerollButton.setFont(buttonFont);
        exportButton.setFont(buttonFont);

        row.add(generateButton);
        row.add(rerollButton);
        row.add(exportButton);

        bottom.add(row);
        bottom.add(Box.createVerticalStrut(8));
        JPanel arrowsPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        leftButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rightButton.setFont(new Font("Arial", Font.PLAIN, 14));

        arrowsPanel.add(leftButton);
        arrowsPanel.add(rightButton);

        bottom.add(arrowsPanel);
        bottom.add(Box.createVerticalStrut(8));


        backButton = new JButton("Go back");
        backButton.setFont(buttonFont);

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.CENTER);
        bottom.add(backPanel);


        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);
        pack();
        setLocationRelativeTo(null);
    }


    private JFormattedTextField addField(JPanel parent, GridBagConstraints gbc, String title, Font labelFont, Font fieldFont) {

        JLabel label = new JLabel(title);
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

    private void initializeForm() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateButton.addActionListener(e -> {
            biome=biome.clone();
            biomeService.rollBiome(biome);
            sessionManager.add(Biome.class, biome);
            updateFields();
        });

        rerollButton.addActionListener(e -> {
            biome=biome.clone();
            biomeService.reRollDetails(biome);
            sessionManager.add(Biome.class, biome);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(biome);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export file...");
            }
        });

        leftButton.addActionListener(e->{
            if(sessionManager.getList(Biome.class).indexOf(biome)>0){
                int currentIndex = sessionManager.getList(Biome.class).indexOf(biome);
                int newIndex=currentIndex-1;
                biome = sessionManager.getList(Biome.class).get(newIndex);
                updateFields();}
        });

        rightButton.addActionListener(e->{
            if(sessionManager.getList(Biome.class).indexOf(biome)<sessionManager.getList(Biome.class).size()-1) {
                int currentIndex = sessionManager.getList(Biome.class).indexOf(biome);
                int newIndex = currentIndex + 1;
                this.biome = sessionManager.getList(Biome.class).get(newIndex);
                updateFields();
            }
        });

        backButton.addActionListener(e -> {
            MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
            mainMenuForm.setVisible(true);
            dispose();
        });
    }

    private void updateFields() {
        biomeFormattedTextField.setText(biome.getBiome());
        weatherFormattedTextField.setText(biome.getWeather());
        weatherIntensityFormattedTextField.setText(biome.getWeatherIntensity());
        wildlifeFormattedTextField.setText(biome.getWildlife());
        populationFormattedTextField.setText(biome.getPopulation());
        roadsFormattedTextField.setText(biome.getRoads());
        alignmentFormattedTextField.setText(biome.getAlignment());
        distanceFormattedTextField.setText(biome.getDistance());
    }
}
