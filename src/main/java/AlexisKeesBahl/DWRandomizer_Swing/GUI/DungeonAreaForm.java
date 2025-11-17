package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Dungeon;
import AlexisKeesBahl.DWRandomizer_Swing.service.AreaService;
import AlexisKeesBahl.DWRandomizer_Swing.service.DungeonService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class DungeonAreaForm extends JFrame{

    private final SessionManager sessionManager;
    private final AreaService areaService;
    private final ApplicationContext context;
    private final GenericFunctions genericFunctions;
    private JButton generateButton;
    private JButton rerollButton;
    private JButton addButton;
    private JButton goBackButton;
    private JFormattedTextField areaFormattedTextField;
    private JTextPane dressingTextPane;
    private JTextPane rarityTextPane;
    private JTextPane discoveriesTextPane;
    private JTextPane dangersTextPane;
    private JLabel discoveriesLabel;
    private JLabel dangersLabel;
    private Area area;
    private Dungeon dungeon;
    private final DungeonService dungeonService;

    @Autowired
    public DungeonAreaForm(ApplicationContext context,
                        SessionManager sessionManager,
                        AreaService areaService,
                        GenericFunctions genericFunctions,
                           DungeonService dungeonService){

        this.areaService=areaService;
        this.sessionManager=sessionManager;
        this.context = context;
        this.genericFunctions = genericFunctions;
        this.dungeonService=dungeonService;
        buildUI();

        if(sessionManager.getSelected(Area.class)==null) {
            this.area = new Area();
        } else {
            this.area = sessionManager.getSelected(Area.class);
        }

        if(sessionManager.getSelected(Dungeon.class)==null)
            this.dungeon = new Dungeon();
        else {
            this.dungeon = sessionManager.getSelected(Dungeon.class);
            updateFields();
        }

        initializeForm(context);
    }

    private void initializeForm(ApplicationContext context){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e ->{
            areaService.rollArea(area);
            sessionManager.add(Area.class,area.clone());
            updateFields();
        });


        rerollButton.addActionListener(e -> {
            areaService.rollAreaDetails(area);
            sessionManager.add(Area.class,area.clone());
            updateFields();
        });

        addButton.addActionListener(e -> {

            if(sessionManager.getSelected(Dungeon.class)==null){
                dungeonService.rollDungeon(dungeon);
                sessionManager.add(Dungeon.class,dungeon);
            }
            if(dungeon.getAreas().size()<dungeon.getRooms()-1)
                dungeon.addArea(area);
            else JOptionPane.showMessageDialog(this, "All areas set.");
        });


        goBackButton.addActionListener(e->{
            DungeonMenuForm dungeonMenuForm = context.getBean(DungeonMenuForm.class);
            dungeonMenuForm.setVisible(true);
            dispose();
            dungeonMenuForm.updateFields();
        });
    }

    private JFormattedTextField createField() {
        JFormattedTextField f = new JFormattedTextField();
        f.setPreferredSize(new Dimension(200, 30));   // ancho y alto estándar
        f.setMinimumSize(new Dimension(200, 30));
        return f;
    }

    private JScrollPane createScroll(JTextPane pane, int height) {
        pane.setEditable(false);
        JScrollPane sp = new JScrollPane(pane);
        sp.setPreferredSize(new Dimension(200, height));
        sp.setMinimumSize(new Dimension(200, height));
        return sp;
    }

    private void buildUI() {
        // Fuentes
        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);
        Font textFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);

        // Panel principal con padding
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ----- Título -----
        JLabel titleLabel = new JLabel("Random area generator");
        titleLabel.setFont(titleFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        main.add(titlePanel, BorderLayout.NORTH);

        // ----- Panel central con campos -----
        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 0, 4, 0);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Area
        JLabel areaLabel = new JLabel("Area:");
        areaLabel.setFont(labelFont);
        center.add(areaLabel, gbc);

        gbc.gridy++;
        areaFormattedTextField = createField();
        areaFormattedTextField.setFont(textFont);
        areaFormattedTextField.setEditable(false);
        center.add(areaFormattedTextField, gbc);

        // Dressing
        gbc.gridy++;
        JLabel dressingLabel = new JLabel("Dressing:");
        dressingLabel.setFont(labelFont);
        center.add(dressingLabel, gbc);

        gbc.gridy++;
        dressingTextPane = new JTextPane();
        dressingTextPane.setFont(textFont);
        dressingTextPane.setEditable(false);
        JScrollPane dressingScroll = createScroll(dressingTextPane,30);
        dressingScroll.setPreferredSize(new Dimension(0, 40));
        center.add(dressingScroll, gbc);

        // Rarity
        gbc.gridy++;
        JLabel rarityLabel = new JLabel("Rarity:");
        rarityLabel.setFont(labelFont);
        center.add(rarityLabel, gbc);

        gbc.gridy++;
        rarityTextPane = new JTextPane();
        rarityTextPane.setFont(textFont);
        rarityTextPane.setEditable(false);
        JScrollPane rarityScroll = createScroll(rarityTextPane,30);
        rarityScroll.setPreferredSize(new Dimension(0, 40));
        center.add(rarityScroll, gbc);

        // Discoveries
        gbc.gridy++;
        discoveriesLabel = new JLabel("Discoveries:");
        discoveriesLabel.setFont(labelFont);
        center.add(discoveriesLabel, gbc);

        gbc.gridy++;
        discoveriesTextPane = new JTextPane();
        discoveriesTextPane.setFont(textFont);
        discoveriesTextPane.setEditable(false);
        JScrollPane discoveriesScroll = createScroll(discoveriesTextPane,70);
        discoveriesScroll.setPreferredSize(new Dimension(0, 100));
        center.add(discoveriesScroll, gbc);

        // Dangers
        gbc.gridy++;
        dangersLabel = new JLabel("Dangers:");
        dangersLabel.setFont(labelFont);
        center.add(dangersLabel, gbc);

        gbc.gridy++;
        dangersTextPane = new JTextPane();
        dangersTextPane.setFont(textFont);
        dangersTextPane.setEditable(false);
        JScrollPane dangersScroll = createScroll(dangersTextPane,70);
        dangersScroll.setPreferredSize(new Dimension(0, 100));
        center.add(dangersScroll, gbc);

        // un poco de espacio al final
        gbc.gridy++;
        gbc.weighty = 1.0;
        center.add(Box.createVerticalGlue(), gbc);

        main.add(center, BorderLayout.CENTER);

        // ----- Panel inferior con botones -----
        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel buttonsRow = new JPanel(new GridLayout(1, 3, 10, 0));

        generateButton = new JButton("Generate");
        rerollButton = new JButton("Reroll");
        addButton = new JButton("Add to dungeon");
        goBackButton = new JButton("Go back");

        generateButton.setFont(buttonFont);
        rerollButton.setFont(buttonFont);
        addButton.setFont(buttonFont);
        goBackButton.setFont(buttonFont);

        // “Horizontal fill” para todos los botones
        buttonsRow.add(generateButton);
        buttonsRow.add(rerollButton);
        buttonsRow.add(addButton);

        bottom.add(buttonsRow);
        bottom.add(Box.createVerticalStrut(8));

        // Back ocupa todo el ancho
        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.add(goBackButton, BorderLayout.CENTER);
        bottom.add(backPanel);

        main.add(bottom, BorderLayout.SOUTH);

        // Configurar frame
        setContentPane(main);
        pack();                    // se adapta al contenido + padding
        setLocationRelativeTo(null);
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
}
