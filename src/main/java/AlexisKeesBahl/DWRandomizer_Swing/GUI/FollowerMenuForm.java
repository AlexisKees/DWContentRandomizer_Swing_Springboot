package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.Follower;
import AlexisKeesBahl.DWRandomizer_Swing.service.FollowerService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class FollowerMenuForm extends JFrame {

    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final FollowerService followerService;
    private final GenericFunctions genericFunctions;

    private Follower follower;

    private JButton leftButton;
    private JButton rightButton;
    private JButton goBackButton;
    private JButton generateButton;
    private JButton rerollButton;
    private JButton exportButton;

    private JFormattedTextField nameField;
    private JFormattedTextField ageField;
    private JFormattedTextField instinctField;
    private JFormattedTextField costField;
    private JFormattedTextField tagsField;

    private JLabel hpLabel;
    private JLabel armorLabel;
    private JLabel damageLabel;
    private JLabel qualityLabel;
    private JLabel loyaltyLabel;

    public FollowerMenuForm(
            ApplicationContext context,
            SessionManager sessionManager,
            FollowerService followerService,
            GenericFunctions genericFunctions) {

        this.context = context;
        this.sessionManager = sessionManager;
        this.followerService = followerService;
        this.genericFunctions = genericFunctions;

        if (sessionManager.getSelected(Follower.class) == null)
            this.follower = new Follower();
        else
            this.follower = sessionManager.getSelected(Follower.class);

        buildUI();
        initializeActions();
        updateFields();
    }

    private void buildUI() {

        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font labelFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font fieldFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        leftButton = new JButton("←");
        rightButton = new JButton("→");

        setTitle("Random follower generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1;

        JLabel title = new JLabel("Random follower generator", SwingConstants.CENTER);
        title.setFont(titleFont);
        c.gridy = 0;
        main.add(title, c);

        JPanel inner = new JPanel(new GridBagLayout());
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        GridBagConstraints i = new GridBagConstraints();
        i.insets = new Insets(5,5,5,5);
        i.fill = GridBagConstraints.HORIZONTAL;
        i.gridx = 0;
        i.weightx = 1;

        int row = 0;

        nameField = new JFormattedTextField();
        nameField.setFont(fieldFont);
        i.gridy = row++;
        inner.add(nameField, i);

        JPanel ageRow = new JPanel(new GridLayout(1,2,5,5));
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(labelFont);
        ageRow.add(ageLabel);
        ageField = new JFormattedTextField();
        ageField.setFont(fieldFont);
        ageRow.add(ageField);
        i.gridy = row++;
        inner.add(ageRow, i);

        JPanel statsRow = new JPanel(new GridLayout(1,3,5,5));
        hpLabel = new JLabel("HP:");
        hpLabel.setFont(labelFont);
        armorLabel = new JLabel("Armor:");
        armorLabel.setFont(labelFont);
        damageLabel = new JLabel("Damage:");
        damageLabel.setFont(labelFont);
        statsRow.add(hpLabel);
        statsRow.add(armorLabel);
        statsRow.add(damageLabel);
        i.gridy = row++;
        inner.add(statsRow, i);

        JPanel qlRow = new JPanel(new GridLayout(1,2,5,5));
        qualityLabel = new JLabel("Quality:");
        qualityLabel.setFont(labelFont);
        loyaltyLabel = new JLabel("Loyalty:");
        loyaltyLabel.setFont(labelFont);
        qlRow.add(qualityLabel);
        qlRow.add(loyaltyLabel);
        i.gridy = row++;
        inner.add(qlRow, i);

        JPanel tagsRow = new JPanel(new GridLayout(1,2,5,5));
        JLabel tagsLabel = new JLabel("Tags:");
        tagsLabel.setFont(labelFont);
        tagsRow.add(tagsLabel);
        tagsField = new JFormattedTextField();
        tagsField.setFont(fieldFont);
        tagsRow.add(tagsField);
        i.gridy = row++;
        inner.add(tagsRow, i);

        JPanel instinctRow = new JPanel(new GridLayout(1,2,5,5));
        JLabel instLabel = new JLabel("Instinct:");
        instLabel.setFont(labelFont);
        instinctRow.add(instLabel);
        instinctField = new JFormattedTextField();
        instinctField.setFont(fieldFont);
        instinctRow.add(instinctField);
        i.gridy = row++;
        inner.add(instinctRow, i);

        JPanel costRow = new JPanel(new GridLayout(1,2,5,5));
        JLabel costLabel = new JLabel("Cost:");
        costLabel.setFont(labelFont);
        costRow.add(costLabel);
        costField = new JFormattedTextField();
        costField.setFont(fieldFont);
        costRow.add(costField);
        i.gridy = row++;
        inner.add(costRow, i);

        c.gridy = 1;
        main.add(inner, c);

        JPanel buttonsRow = new JPanel(new GridLayout(1,3,10,5));
        generateButton = new JButton("Generate");
        generateButton.setFont(buttonFont);
        rerollButton = new JButton("Reroll");
        rerollButton.setFont(buttonFont);
        exportButton = new JButton("Export");
        exportButton.setFont(buttonFont);
        buttonsRow.add(generateButton);
        buttonsRow.add(rerollButton);
        buttonsRow.add(exportButton);
        c.gridy = 2;
        main.add(buttonsRow, c);

        JPanel arrowsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        leftButton.setFont(new Font("Arial", Font.PLAIN, 14));
        rightButton.setFont(new Font("Arial", Font.PLAIN, 14));
        arrowsPanel.add(leftButton);
        arrowsPanel.add(rightButton);
        c.gridy = 3;
        main.add(arrowsPanel, c);

        c.gridy = 4;
        main.add(Box.createVerticalStrut(8), c);

        goBackButton = new JButton("Go back");
        goBackButton.setFont(buttonFont);
        c.gridy = 5;
        main.add(goBackButton, c);

        setContentPane(main);
    }


    private void initializeActions() {
        generateButton.addActionListener(e -> {
            follower=follower.clone();
            followerService.rollFollower(follower);
            sessionManager.add(Follower.class, follower);
            updateFields();
        });

        rerollButton.addActionListener(e -> {
            follower=follower.clone();
            if (sessionManager.getSelected(Follower.class) == null)
                followerService.rollFollower(follower);
            else
                followerService.rollFollowerDetails(follower);

            sessionManager.add(Follower.class, follower);
            updateFields();
        });

        exportButton.addActionListener(e -> {
            try {
                genericFunctions.exportPW(follower);
                JOptionPane.showMessageDialog(this, "Check your files!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Couldn't export follower...");
            }
        });
        leftButton.addActionListener(e->{
            if(sessionManager.getList(Follower.class).indexOf(follower)>0){
                int currentIndex = sessionManager.getList(Follower.class).indexOf(follower);
                int newIndex=currentIndex-1;
                follower = sessionManager.getList(Follower.class).get(newIndex);
                updateFields();}
        });

        rightButton.addActionListener(e->{
            if(sessionManager.getList(Follower.class).indexOf(follower)<sessionManager.getList(Follower.class).size()-1) {
                int currentIndex = sessionManager.getList(Follower.class).indexOf(follower);
                int newIndex = currentIndex + 1;
                this.follower = sessionManager.getList(Follower.class).get(newIndex);
                updateFields();
            }
        });

        goBackButton.addActionListener(e -> {
            MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
            mainMenuForm.setVisible(true);
            dispose();
        });
    }

    private void updateFields() {
        nameField.setText(follower.getOneLiner().toUpperCase());
        ageField.setText(follower.getAge());
        instinctField.setText(follower.getInstinct());
        costField.setText(follower.getCost());
        tagsField.setText(follower.getTags());
        hpLabel.setText("HP: " + follower.getHP());
        armorLabel.setText("Armor: " + follower.getArmor() + " (" + follower.getArmorType() + ")");
        damageLabel.setText("Damage: " + follower.getDamage());
        qualityLabel.setText("Quality: " + follower.getQuality());
        loyaltyLabel.setText("Loyalty: " + follower.getLoyalty());
    }
}
