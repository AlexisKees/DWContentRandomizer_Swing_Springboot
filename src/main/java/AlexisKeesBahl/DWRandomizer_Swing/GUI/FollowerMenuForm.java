package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import AlexisKeesBahl.DWRandomizer_Swing.model.Follower;
import AlexisKeesBahl.DWRandomizer_Swing.service.FollowerService;
import AlexisKeesBahl.DWRandomizer_Swing.service.GenericFunctions;
import AlexisKeesBahl.DWRandomizer_Swing.service.util.SessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
@Scope("prototype")
public class FollowerMenuForm extends JFrame{
    private final ApplicationContext context;
    private final SessionManager sessionManager;
    private final FollowerService followerService;
    private final GenericFunctions genericFunctions;
    private Follower follower;
    private JButton goBackButton;
    private JPanel panel1;
    private JButton generateButton;
    private JButton rerollButton;
    private JButton exportButton;
    private JFormattedTextField nameField;
    private JFormattedTextField ageFormattedTextField;
    private JFormattedTextField instinctFormattedTextField;
    private JFormattedTextField costFormattedTextField;
    private JFormattedTextField tagsFormattedTextField;
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
        this.context=context;
        this.sessionManager=sessionManager;
        this.followerService=followerService;
        this.genericFunctions=genericFunctions;
        if(sessionManager.getSelected(Follower.class)==null)
            this.follower=new Follower();
        else {
            this.follower = sessionManager.getSelected(Follower.class);
            updateFields();
        }

        iniciarForma();

    }
    private void iniciarForma(){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);

        generateButton.addActionListener(e -> {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    followerService.rollFollower(follower);
                    sessionManager.add(Follower.class,follower);
                    return null;
                }
                @Override
                protected void done() {
                    updateFields();
                }
            }.execute();
        });

        rerollButton.addActionListener(e -> {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    if (sessionManager.getSelected(Follower.class)==null)
                        followerService.rollFollower(follower);
                    else
                        followerService.rollFollowerDetails(follower);

                    sessionManager.add(Follower.class,follower);
                    return null;
                }

                @Override
                protected void done() {
                    updateFields();
                }
            }.execute();
        });


        exportButton.addActionListener(e->{
            try{
                genericFunctions.exportPW(follower);
                JOptionPane.showMessageDialog(this,"Check your files!");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Couldn't export follower...");
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm mainMenuForm =  context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
            }
        });
    }

    private void updateFields(){
        nameField.setText(follower.getOneLiner().toUpperCase());
        ageFormattedTextField.setText(follower.getAge());
        instinctFormattedTextField.setText(follower.getInstinct());
        costFormattedTextField.setText(follower.getCost());
        hpLabel.setText("HP: "+follower.getHP());
        armorLabel.setText("Armor: "+follower.getArmor()+" ("+follower.getArmorType()+")");
        damageLabel.setText("Damage: "+follower.getDamage());
        qualityLabel.setText("Quality: "+follower.getQuality());
        loyaltyLabel.setText("Loyalty: "+follower.getLoyalty());
        tagsFormattedTextField.setText(follower.getTags());
    }
}
