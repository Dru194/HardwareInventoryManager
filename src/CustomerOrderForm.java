import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
public class CustomerOrderForm extends JFrame implements ActionListener {
    JButton submitButton;
    JButton addToOrderButton;
    JTextField productNameField;
    JFormattedTextField productAmountField;
    JTable orderTable;
    String[] tableHeaders = {"Part Name", "Amount"};
    Object[][] tableData = {{"Add an order to begin", "N/A"}};
    List<String[]> orderData = new ArrayList<>();
    DefaultTableModel model;
    CustomerOrderForm(ConsoleTextPane consoleTextPane){
        this.setTitle("Customer Order Form");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(800, 625);
        this.setVisible(true);
        this.requestFocus();

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setBounds(100, 100, 600, 40);
        textFieldPanel.setBackground(new Color(88,88,88));
        textFieldPanel.setLayout(new GridLayout(1,5));
        JLabel partNameFieldLabel = new JLabel("Part Name");
        partNameFieldLabel.setHorizontalAlignment(JLabel.CENTER);
        productNameField = new JTextField();
        JLabel partAmountFieldLabel = new JLabel("Amount");
        partAmountFieldLabel.setHorizontalAlignment(JLabel.CENTER);
        productAmountField = setupIntegerField();
        addToOrderButton = new JButton("Add Item");
        addToOrderButton.addActionListener(this);
        textFieldPanel.add(partNameFieldLabel);
        textFieldPanel.add(productNameField);
        textFieldPanel.add(partAmountFieldLabel);
        textFieldPanel.add(productAmountField);
        textFieldPanel.add(addToOrderButton);

        JScrollPane currentOrderPanel = new JScrollPane();
        currentOrderPanel.setBounds(100,140, 600, 300);
        currentOrderPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        model = new DefaultTableModel(tableData, tableHeaders);
        orderTable = new JTable(model);
        currentOrderPanel.add(orderTable);
        currentOrderPanel.setViewportView(orderTable);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 460, 80, 30);
        submitButton.addActionListener(this);

        this.add(textFieldPanel);
        this.add(currentOrderPanel);
        this.add(submitButton);
    }

    public Object[][] getOrderDataObjectArray(){
        if(orderData == null || orderData.isEmpty()){
            return new Object[0][0];
        }

        Object[][] result = new Object[orderData.size()][];

        for(int i = 0; i < orderData.size(); i++){
            result[i] = orderData.get(i);
        }
        return result;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.addToOrderButton)){
            if(orderData.isEmpty()){
                model.removeRow(0);
            }
            System.out.println("Adding item to order...");
            String inputPartName = productNameField.getText();
            String inputPartAmount = productAmountField.getText();
            String[] orderRow = {inputPartName, inputPartAmount};
            orderData.add(orderRow);
            model.addRow(orderRow);
        }

        if(e.getSource().equals(this.submitButton)){
            System.out.println("Submitting Order...");
            if(Main.databaseConnection.completeOrder(orderData)){
                System.out.println("Order has been submitted");
            }
            else{
                System.out.println("This order has failed to be processed.");
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
}
