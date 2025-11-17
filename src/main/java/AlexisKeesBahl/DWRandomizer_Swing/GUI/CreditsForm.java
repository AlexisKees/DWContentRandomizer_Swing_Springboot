package AlexisKeesBahl.DWRandomizer_Swing.GUI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
@Scope("prototype")
public class CreditsForm extends JFrame {

    private final ApplicationContext context;

    private JEditorPane creditsEditorPane;
    private JButton goBackButton;

    public CreditsForm(ApplicationContext context) {
        this.context = context;

        buildUI();
        initializeListeners();
    }

    private void buildUI() {
        Font titleFont = new Font("Adobe Jenson Pro", Font.BOLD, 24);
        Font textFont = new Font("Adobe Jenson Pro Lt", Font.PLAIN, 16);
        Font buttonFont = new Font("Adobe Jenson Pro Lt", Font.ITALIC, 16);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Credits");
        title.setFont(titleFont);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        top.add(title, BorderLayout.CENTER);
        top.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        creditsEditorPane = new JEditorPane();
        creditsEditorPane.setEditable(false);
        creditsEditorPane.setFont(textFont);
        creditsEditorPane.setText(loadCreditsText());

        JScrollPane scroll = new JScrollPane(creditsEditorPane);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel center = new JPanel(new BorderLayout());
        center.add(scroll, BorderLayout.CENTER);

        goBackButton = new JButton("Go back");
        goBackButton.setFont(buttonFont);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(goBackButton, BorderLayout.CENTER);
        bottom.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        main.add(top, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        setContentPane(main);
        setSize(600, 500);
        setLocationRelativeTo(null);
    }

    private String loadCreditsText() {
        return """
                DW Randomizer — Swing Edition

                Developed by: AlexisKeesBahl
                Based on Dungeon World and The Perilous Wilds
                All rights reserved by their respective creators.

                Special thanks:
                - The Dungeon World community
                - Playtesters and friends
                - Inspiration from PbtA ecosystem
                
                DISCLAIMER:
                This app is a personal project. It was made
                partly because I loved "The Perilous Wilds",
                partly because I wanted to test my programming skills.
                It is, in many ways, a work in progress and is open to contributions.
                It is in no way meant to replace the book, nor could it do so.
                
                Also, the content of this app is not exactly
                the same as the content in the book,
                since I took the liberty to make many modifications
                that made sense in context and add a few things I simply thought of.
                Again, this started as a personal project
                and became a working app later.
                
                If this got to you and you choose to use it,
                may help you improvise while playing
                or spark your imagination while planning your next session.
                
                Be free to make your contributions to my public repository at:
                https://github.com/AlexisKees/DWContentRandomizer_Swing_Springboot.git

                """;
    }

    private void initializeListeners() {
        goBackButton.addActionListener(e -> {
            MainMenuForm main = context.getBean(MainMenuForm.class);
            main.setVisible(true);
            dispose();
        });
    }
}

