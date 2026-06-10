import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class InventoryTablePane extends JScrollPane {
    public String[] columnHeaders = {"Part Name", "Stock", "Price"};
    public Object[][] tableData;
    JTable inventoryTable;
    DefaultTableModel model;

    InventoryTablePane(){
        this.tableData = Main.databaseConnection.getQueryDataObjectArray();
        this.setBounds(300, 0, 800, 600);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        model = new DefaultTableModel(tableData, columnHeaders);
        inventoryTable = new JTable(model);
        this.setViewportView(inventoryTable);
    }

    public void updateTable(){
        Main.databaseConnection.getInventory();
        this.tableData = Main.databaseConnection.getQueryDataObjectArray();
        System.out.println(Arrays.deepToString(this.tableData));
        model.setDataVector(tableData, columnHeaders);
    }

}
