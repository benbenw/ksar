
package net.atomique.ksar;

import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.UI.Desktop;
import net.atomique.ksar.UI.SplashScreen;

/**
 *
 * @author Max
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    private static ResourceBundle resource = ResourceBundle.getBundle("net/atomique/ksar/Language/Message");

    public static void usage() {
        showVersion();
    }

    public static void showVersion() {
        System.err.println("ksar Version : " + VersionNumber.getVersionNumber());
    }

    private static void setLookAndFeel() {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if (Config.getLandf().equals(laf.getName())) {
                try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (ClassNotFoundException ex) {
                    LOGGER.error("", ex);
                } catch (InstantiationException ex) {
                    LOGGER.error("", ex);
                } catch (IllegalAccessException ex) {
                    LOGGER.error("", ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    LOGGER.error("", ex);
                }
            }
        }
    }

    public static void makeUi() {
        SplashScreen mysplash = new SplashScreen(null, 3000);
        while (mysplash.isVisible()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        setLookAndFeel();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                GlobalOptions.setUI(new Desktop());
                SwingUtilities.updateComponentTreeUI(GlobalOptions.getUI());
                GlobalOptions.getUI().add_window();
                GlobalOptions.getUI().maxall();
            }
        });

    }

    public static void main(String[] args) {
       
        /// load default
        String mrjVersion = System.getProperty("mrj.version");
        if (mrjVersion != null) {
            System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "kSar");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

      
        int i = 0;
        String arg;
        if (args.length > 0) {
            while (i < args.length && args[i].startsWith("-")) {
                arg = args[i++];
                if ("-version".equals(arg)) {
                    showVersion();
                    System.exit(0);
                }
                if ("-help".equals(arg)) {
                    usage();
                    continue;
                }
                
                if ("-input".equals(arg)) {
                    if (i < args.length) {
                        GlobalOptions.setCLfilename(args[i++]);
                    } else {
                        exitError(resource.getString("INPUT_REQUIRE_ARG"));
                    }
                    continue;
                }
            }
        }

        makeUi();
    }

    public static void exitError(final String message) {
        System.err.println(message);
        System.exit(1);
    }
}
