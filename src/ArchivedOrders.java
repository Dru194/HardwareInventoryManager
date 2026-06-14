import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ArchivedOrders extends JFrame {
    public String[] columnHeaders = {"Date", "Type", "Receipt"};
    public Object[][] tableData = {{"N/A","N/A","<html>N/A<br>lol</html>"}};
    JTable archiveTable;
    DefaultTableModel model;
    ArchivedOrders(){

        this.setTitle("Archived Orders");
        this.setSize(800, 625);

        if(Main.databaseConnection.getOrders()){
            tableData = Main.databaseConnection.getQueryDataObjectArray();
        }


        model = new DefaultTableModel(tableData, columnHeaders){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        archiveTable = new JTable(model);
        autoResizeRowHeights(archiveTable);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(archiveTable);

        this.add(scrollPane);
        this.setVisible(true);
        this.requestFocus(true);
    }

    public void autoResizeRowHeights(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight(); // Default height

            for (int column = 0; column < table.getColumnCount(); column++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);

                // Find the maximum preferred height for this row
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            // Apply the new height to the row
            table.setRowHeight(row, rowHeight);
        }
    }
}
