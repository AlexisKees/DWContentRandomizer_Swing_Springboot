package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class CreditsForm extends JFrame {
    private final ApplicationContext context;
    private JButton goBackButton;
    private JEditorPane creditsEditorPane;
    private JPanel contentPane;

    public CreditsForm(ApplicationContext context){
        this.context=context;
        initializeForm(context);
    }

    private void initializeForm(ApplicationContext context){
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);

        creditsEditorPane.setText("""
                Created by Alexis Kees Bahl.
                Based on "The perilous wilds, revised edition".
                
                DISCLAIMER:
                This app is a personal project. It was made
                partly because I loved "The perilous wilds",
                partly because I wanted to test my programming skills.
                It is in no way meant to replace the book
                and it the content of this app is not exactly
                the same as the content in the book.
                
                May this app help you improvise while playing
                or sparkle your imagination while planning your next session.
                                               
                Be free to make your contributions to my public repository at:
                https://github.com/AlexisKees/DWContentRandomizer_Swing_Springboot.git
                
                Thanks to all who make de DW community.
                """);

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuForm mainMenuForm = context.getBean(MainMenuForm.class);
                mainMenuForm.setVisible(true);
                dispose();
            }
        });
    }

}
