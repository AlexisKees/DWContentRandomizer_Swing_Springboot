package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class QuestMenuForm extends JFrame{
    private JButton goBackButton;
    private JPanel panel1;

    public QuestMenuForm(ApplicationContext context) {
        iniciarForma();
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
            }
        });
    }
    private void iniciarForma(){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,600);
        setLocationRelativeTo(null);
    }
}
