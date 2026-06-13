import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
public class AddProductForm extends JFrame implements ActionListener {
    JButton addProductButton;
    JTextField productNameField;
    JFormattedTextField productAmountField;
    JFormattedTextField productPriceField;
    ConsoleTextPane consoleTextPane;
    InventoryTablePane inventoryTablePane;
    Object [] newProductData = new Object [3];
    AddProductForm(ConsoleTextPane consoleTextPane, InventoryTablePane inventoryTablePane){
        this.setTitle("Add New Product Form");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(800, 325);
        this.setVisible(true);
        this.requestFocus();

        this.consoleTextPane = consoleTextPane;
        this.inventoryTablePane = inventoryTablePane;

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setBounds(100, 100, 600, 40);
        textFieldPanel.setBackground(new Color(88,88,88));
        textFieldPanel.setLayout(new GridLayout(1,8));
        JLabel productNameFieldLabel = new JLabel("Part Name");
        productNameFieldLabel.setHorizontalAlignment(JLabel.CENTER);
        productNameField = new JTextField();
        JLabel productAmountFieldLabel = new JLabel("Amount");
        productAmountFieldLabel.setHorizontalAlignment(JLabel.CENTER);
        productAmountField = setupIntegerField();
        JLabel productPriceFieldLabel = new JLabel("Price");
        productPriceFieldLabel.setHorizontalAlignment(JLabel.CENTER);
        productPriceField = setupDecimalField();
        textFieldPanel.add(productNameFieldLabel);
        textFieldPanel.add(productNameField);
        textFieldPanel.add(productAmountFieldLabel);
        textFieldPanel.add(productAmountField);
        textFieldPanel.add(productPriceFieldLabel);
        textFieldPanel.add(productPriceField);

        addProductButton = new JButton("Add Product");
        addProductButton.setBounds(100, 170, 150, 30);
        addProductButton.addActionListener(this);

        this.add(textFieldPanel);
        this.add(addProductButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(this.addProductButton)){
            System.out.println("Adding Product...");
            this.newProductData[0] = productNameField.getText();
            this.newProductData[1] = Integer.parseInt(productAmountField.getText());
            this.newProductData[2] = Double.parseDouble(productPriceField.getText());
            if(Main.databaseConnection.addProduct(newProductData)){
                inventoryTablePane.updateTable();
                String msg = "New product as successfully been added to the inventory!";
                JOptionPane.showMessageDialog(this, msg);
                consoleTextPane.appendStringToConsole(msg + "\n");
                dispose();
            }
            else{
                String msg = "New product has failed to process. This form will be closed";
                JOptionPane.showMessageDialog(this, msg);
                consoleTextPane.appendStringToConsole(msg + "\n");
                dispose();
            }
        }
    }

    private JFormattedTextField setupIntegerField(){
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);

        return new JFormattedTextField(formatter);
    }

    private JFormattedTextField setupDecimalField() {

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setGroupingUsed(false);


        format.setMaximumIntegerDigits(4);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);

        NumberFormatter formatter = new NumberFormatter(format);


        formatter.setValueClass(Double.class);

        formatter.setAllowsInvalid(false);

        return new JFormattedTextField(formatter);
    }
}
