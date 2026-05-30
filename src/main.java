import javax.swing.*;
void main(){
    SwingUtilities.invokeLater(new Runnable(){
        public void run(){ // everything within this function acts as a main function within the Swing framework
            JFrame mainWindow = new JFrame(); //root JFrame is my main GUI window
            mainWindow.setTitle("Hardware Inventory Manager");
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setResizable(false);
            mainWindow.setLayout(null);
            mainWindow.setSize(1100,925);

            ConsoleTextPane console = new ConsoleTextPane(); //This custom object will be my text output
            JScrollPane consoleScrollPane = new JScrollPane(console); //I put the console inside a scrollable panel
            consoleScrollPane.setBounds(300,600,800,300);
            consoleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            mainWindow.add(consoleScrollPane); //add the scrollable console to our root JFrame

            ControlPanel controlPanel = new ControlPanel(); //create the custom panel for user input
            mainWindow.add(controlPanel); //add this object to the root JFrame

            controlPanel.setActionListeners(console); //this makes the console aware of the controlPanel buttons
            console.setControlPanel(controlPanel);//I use a setter instead of a constructor to resolve circular dependency

            mainWindow.setVisible(true);
            mainWindow.requestFocus();
        }
    });
}
