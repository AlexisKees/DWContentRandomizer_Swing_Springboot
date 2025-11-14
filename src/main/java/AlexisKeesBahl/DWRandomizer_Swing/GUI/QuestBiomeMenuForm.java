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

@Component
@Scope("prototype")
public class QuestBiomeMenuForm extends JFrame {

    private final BiomeService biomeService;
    private final GenericFunctions genericFunctions;
    private final SessionManager sessionManager;
    private final ApplicationContext context;
    private Biome biome;
    private Quest quest;
    private JButton button1;
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
        if (sessionManager.getSelected(Quest.class)==null) {
            this.quest = new Quest();
            this.biome=new Biome();
        }
        else {
            this.quest = sessionManager.getSelected(Quest.class);
            this.biome = quest.getBiome();
            updateFields();
        }

        iniciarForma(context);

    }

    private void iniciarForma(ApplicationContext context){

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
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


        button1.addActionListener(e -> {
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
