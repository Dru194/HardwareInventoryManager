import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    void main() {

        //this code sets up the logger manager.
        // The handler and the formatter are created by reading the config file
        try(InputStream is = Main.class.getClassLoader().getResourceAsStream("logging.properties")){

            if(is != null){
                LogManager.getLogManager().readConfiguration(is);
                System.out.println("Logging config file has been found");
            } else{
                System.err.println("Could not find logging.properties file!");
            }
        } catch (IOException e) {
            System.err.println("failed to load logging config: " + e.getMessage());
        }

        logger.info("logger initialized");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() { // everything within this function acts as a main function within the Swing framework
                DatabaseConnection databaseConnection = new DatabaseConnection(logger); // this class is meant to manage database connections

                JFrame mainWindow = new JFrame(); //root JFrame is my main GUI window
                mainWindow.setTitle("Hardware Inventory Manager");
                mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainWindow.setResizable(false);
                mainWindow.setLayout(null);
                mainWindow.setSize(1100, 925);

                ConsoleTextPane consoleTextPane = new ConsoleTextPane(); //This custom object will be my text output
                mainWindow.add(consoleTextPane); //add the console to our root JFrame

                InventoryTablePane inventoryTablePane = new InventoryTablePane(databaseConnection); //this is my table class
                mainWindow.add(inventoryTablePane);

                ControlPanel controlPanel = new ControlPanel(); //create the custom panel for user input
                mainWindow.add(controlPanel); //add this object to the root JFrame

                controlPanel.setActionListeners(consoleTextPane); //this makes the console aware of the controlPanel buttons
                consoleTextPane.setControlPanel(controlPanel);//I use a setter instead of a constructor to resolve circular dependency

                mainWindow.setVisible(true);
                mainWindow.requestFocus();


            }
        });
    }
}
