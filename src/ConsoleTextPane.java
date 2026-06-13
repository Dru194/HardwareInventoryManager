import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Here, I am creating a custom console component by using existing classes and interfaces within the Swing framework
public class ConsoleTextPane extends JScrollPane implements ActionListener { // ConsoleTextPane is now an extended JScroll Pane instead of JTextPane

    ControlPanel controlPanel; //I initialize our ControlPanel so it can be set later on
    JTextPane console; //console text pane is created here instead of in main method
    InventoryTablePane inventoryTablePane;
    public ConsoleTextPane(){
        this.setBounds(300,600,800,300);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        console = new JTextPane();
        this.setViewportView(console);
        console.setEditable(false);
        console.setFont(new Font("Arial", Font.PLAIN, 18));
        console.setText("Welcome to your Hardware Inventory Manager!\n\nLet's get to work!\n\n");

    }

    //Implement our actionPerformed function so that our console can be aware of button presses from our ControlPanel
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(controlPanel.orderButton)){
            logOrder();
            new CustomerOrderForm(this, inventoryTablePane);
        }

        if(e.getSource().equals(controlPanel.archiveButton)){
            logArchive();
        }

        if(e.getSource().equals(controlPanel.replenishButton)){
            logReplenish();
            new StockOrderForm(this, inventoryTablePane);
        }

        if(e.getSource().equals(controlPanel.addProductButton)){
            logAddParts();
            new AddProductForm(this, inventoryTablePane);
        }
    }

    //ControlPanel Setter
    public void setControlPanel(ControlPanel controlPanel){
        this.controlPanel = controlPanel;
    }


    //Test logging functions for the buttons.
    //Output is printed to both the IDE console and our custom, scrollable console
    private void logOrder(){
        String message = "Starting new order application...\n";
        System.out.print(message);
        appendStringToConsole(message);
    }

    private void logArchive(){
        String message = "Opening Archive...\n";
        System.out.print(message);
        appendStringToConsole(message);
    }

    private void logReplenish(){
        String message = "Starting stock replenishment application...\n";
        System.out.print(message);
        appendStringToConsole(message);
    }

    private void logAddParts(){
        String message = "Adding a new part to the inventory...\n";
        System.out.print(message);
        appendStringToConsole(message);
    }

    //this function appends strings to the console
    public void appendStringToConsole(String message){
        Document doc = console.getDocument();
        try {
            doc.insertString(doc.getLength(), message, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        console.setCaretPosition(doc.getLength());
    }

    public void setInventoryTablePane(InventoryTablePane inventoryTablePane){
        this.inventoryTablePane = inventoryTablePane;
    }
}
