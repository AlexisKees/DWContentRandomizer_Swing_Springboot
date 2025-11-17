package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Biome;
import AlexisKeesBahl.DWRandomizer_Swing.model.Quest;
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
public class QuestBiomeMenuForm extends JFrame {

    private final BiomeService biomeService;
    private final GenericFunctions genericFunctions;
    private final SessionManager sessionManager;
    private final ApplicationContext context;
    private Biome biome;
    private Quest quest;
    private JButton backButton;
    private JPanel panel1;
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

    public QuestBiomeMenuForm( BiomeService biomeService,
                          GenericFunctions genericFunctions,
                          SessionManager sessionManager,
                          ApplicationContext context) {
        this.biomeService = biomeService;
        this.genericFunctions = genericFunctions;
        this. sessionManager = sessionManager;
        this.context = context;
        buildUI();
        if (sessionManager.getSelected(Quest.class)==null) {
            this.quest = new Quest();
            this.biome=new Biome();
        }
        else {
            this.quest = sessionManager.getSelected(Quest.class);
            this.biome = quest.getBiome();
            updateFields();
        }

        initializeForm(context);

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

    private void buildUI() {

        // Fuentes
        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        // Panel principal con padding
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ---------- TÍTULO ----------
        JLabel title = new JLabel("Random biome generator");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        top.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        main.add(top, BorderLayout.NORTH);

        // ---------- PANEL CENTRAL ----------
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;

        // Helper para crear cada fila
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

        // ---------- PANEL INFERIOR (BOTONES) ----------
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel row = new JPanel(new GridLayout(1, 3, 10, 0));
        generateButton = new JButton("Generate");
        rerollButton = new JButton("Reroll");
        exportButton = new JButton("Export");

        generateButton.setFont(buttonFont);
        rerollButton.setFont(buttonFont);
        exportButton.setFont(buttonFont);

        row.add(generateButton);
        row.add(rerollButton);
        row.add(exportButton);

        bottom.add(row);
        bottom.add(Box.createVerticalStrut(8));

        // Back button ocupa toda la fila
        backButton = new JButton("Go back");
        backButton.setFont(buttonFont);

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(backButton, BorderLayout.CENTER);
        bottom.add(backPanel);

        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);
        pack(); // Ajusta la ventana al contenido real
        setLocationRelativeTo(null);
    }

    private void initializeForm(ApplicationContext context){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            biomeService.rollBiome(biome);
            quest.setBiome(biome);
            updateFields();
        });

        rerollButton.addActionListener(e -> {
            if(sessionManager.getSelected(Biome.class)==null){
                biomeService.rollBiome(biome);
                quest.setBiome(biome);
                updateFields();
            } else {
                biomeService.reRollDetails(biome);
                quest.setBiome(biome);
                updateFields();
            }
        });

        exportButton.addActionListener(e -> {
            try{
                genericFunctions.exportPW(biome);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Couldn't export file...");
            }
        });


        backButton.addActionListener(e -> {
                QuestMenuForm questMenuForm = context.getBean(QuestMenuForm.class);
                questMenuForm.setVisible(true);
                questMenuForm.updateFields();
                dispose();
        });
    }

    public void updateFields() {
        biomeFormattedTextField.setText(biome.getBiome());
        weatherFormattedTextField.setText(biome.getWeather());
        weatherIntensityFormattedTextField.setText(biome.getWeatherIntensity());
        wildlifeFormattedTextField.setText(biome.getWildlife());
        populationFormattedTextField.setText(biome.getPopulation());
        roadsFormattedTextField.setText(biome.getRoads());
        distanceFormattedTextField.setText(biome.getDistance());
        alignmentFormattedTextField.setText(biome.getAlignment());
    }
}
