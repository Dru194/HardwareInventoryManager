import javax.swing.*;

public class InventoryTablePane extends JScrollPane {
    public String[] columnHeaders = {"Part Name", "Stock", "Price"};
    public Object[][] tableData = {
            {"Bolt 10mm", "832", "0.18"},
            {"Bolt 8mm", "201", "0.17"},
            {"Nut 10mm", "1091", "0.11"},
            {"Nut 8mm", "644", "0.10"}};
    JTable inventoryTable;

    InventoryTablePane(){
        this.setBounds(300, 0, 800, 600);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inventoryTable = new JTable(this.tableData, this.columnHeaders);
        this.setViewportView(inventoryTable);
    }
}
