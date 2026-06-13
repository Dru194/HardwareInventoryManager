import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel{
    int controlPanelWidth = 300;
    int controlPanelHeight = 900;

    //buttons need to be initialized here so they can be linked to an actionListener in the setter function
    JButton addProductButton;
    JButton replenishButton;
    JButton orderButton;
    JButton archiveButton;

    public ControlPanel(){
        this.setBounds(0, 0, controlPanelWidth, controlPanelHeight);
        this.setLayout(null);
        this.setBackground(new Color(88,88,88));

        JLabel controlPanelLabel = new JLabel("Hardware Inventory Manager");
        controlPanelLabel.setOpaque(true);
        controlPanelLabel.setBackground(new Color(188,188,188));
        controlPanelLabel.setHorizontalAlignment(JLabel.CENTER);
        controlPanelLabel.setBounds(0,0, controlPanelWidth, 60);
        controlPanelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(controlPanelLabel);

        orderButton = new JButton("Customer Order");
        orderButton.setBounds(15, 75, controlPanelWidth - 30, 60);
        orderButton.setFocusable(false);
        this.add(orderButton);

        archiveButton = new JButton("Archived Orders");
        archiveButton.setBounds(15, 150, controlPanelWidth - 30, 60);
        archiveButton.setFocusable(false);
        this.add(archiveButton);

        replenishButton = new JButton("Stock Replenishment");
        replenishButton.setBounds(15, 225, controlPanelWidth - 30, 60);
        replenishButton.setFocusable(false);
        this.add(replenishButton);

        addProductButton = new JButton("Add A New Product");
        addProductButton.setBounds(15, 300, controlPanelWidth - 30, 60);
        addProductButton.setFocusable(false);
        this.add(addProductButton);
    }


    //This function lets the console listen to our button presses
    public void setActionListeners(ConsoleTextPane console){
        orderButton.addActionListener(console);
        archiveButton.addActionListener(console);
        replenishButton.addActionListener(console);
        addProductButton.addActionListener(console);
    }
}
