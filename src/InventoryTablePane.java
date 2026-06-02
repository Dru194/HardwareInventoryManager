import javax.swing.*;

public class InventoryTablePane extends JScrollPane {
    public String[] columnHeaders = {"Part Name", "Stock", "Price"};
    public Object[][] tableData;
    JTable inventoryTable;

    InventoryTablePane(DatabaseConnection databaseConnection){
        this.tableData = databaseConnection.getQueryDataObjectArray();
        this.setBounds(300, 0, 800, 600);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        inventoryTable = new JTable(this.tableData, this.columnHeaders);
        this.setViewportView(inventoryTable);
    }
}
