package AlexisKeesBahl.DWRandomizer_Swing;

import AlexisKeesBahl.DWRandomizer_Swing.GUI.MainMenuForm;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class RandomizerSwing {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        ConfigurableApplicationContext springContext =
                new SpringApplicationBuilder(RandomizerSwing.class).headless(false).web(WebApplicationType.NONE).run(args);
        SwingUtilities.invokeLater(()->{
            MainMenuForm mainMenuForm = springContext.getBean(MainMenuForm.class);
            mainMenuForm.setVisible(true);
        });
    }
}
