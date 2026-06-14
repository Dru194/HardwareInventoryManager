import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hardware_store_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Password123!";

    List<String[]> queryData = new ArrayList<>();
    DatabaseConnection(){
        //Connection is created in the constructor for now... will probably find a different way to do this
        if (getInventory()){
            String msg = "Inventory has been updated";
            Main.logger.info(msg);
        }
        else{
            String msg = "Inventory has failed to update";
            Main.logger.warning(msg);
        }
    }

    public boolean getInventory(){
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {
            Main.logger.info("Database connection is successful");
            String initQuery = "SELECT productName, stock, price FROM inventory";
            this.queryData = new ArrayList<>();
            try(ResultSet resultSet = statement.executeQuery(initQuery)){
                while(resultSet.next()){
                    String partName = resultSet.getString("productName");
                    int stock = resultSet.getInt("stock");
                    float price = resultSet.getFloat("price");
                    this.queryData.add(new String[]{partName, String.valueOf(stock), String.valueOf(price)});
                }

                return true;
            }


        } catch (SQLException e) {
            //this should be a logging situation "WARNING: DB is not connected"
            Main.logger.warning("Failed to connect to database -> " + e.getMessage());
            return false;
        }
    }

    public boolean completeOrder(List<String[]> orderData, int transactionType){

        if(orderData == null || orderData.isEmpty()){
            return false;
        }
        String updateQuery;
        if(transactionType == 0){
            updateQuery = "UPDATE inventory SET stock = stock - ? WHERE productName = ?";
        }
        else{
            updateQuery = "UPDATE inventory SET stock = stock + ? WHERE productName = ?";
        }

        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){
            connection.setAutoCommit(false);
            try{
                for(String[] item : orderData){
                    String productName = item[0];
                    int orderAmount = Integer.parseInt(item[1]);
                    preparedStatement.setInt(1, orderAmount);
                    preparedStatement.setString(2, productName);
                    preparedStatement.addBatch();
                }

                int[] updateCounts = preparedStatement.executeBatch();
                for(int count: updateCounts){
                    if(count == Statement.EXECUTE_FAILED || count == 0){
                        Main.logger.warning("Order failed: A part was not found in the inventory.");
                        connection.rollback();
                        return false;
                    }
                }

                connection.commit();
                Main.logger.info("Order completed successfully");
                return true;
            } catch (Exception e) {
                Main.logger.warning("Order failed: A part was not found in the inventory.");
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addProduct(Object [] newProductData){

        String insertQuery = "INSERT INTO inventory (productName, stock, price) VALUES (?,?,?)";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            Main.logger.info("Database connection is successful");

            preparedStatement.setString(1, (String) newProductData[0]);
            preparedStatement.setInt(2, (Integer) newProductData[1]);
            preparedStatement.setDouble(3, (Double) newProductData[2]);

            int rowsInserted = preparedStatement.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            Main.logger.warning("Database error: " + e.getMessage());
            return false;
        }
    }

    public void archiveOrder(List<String[]> orderData, int transactionType){
        String insertQuery = "INSERT INTO orders (orderItems, orderType) VALUES (?, ?)";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
            Main.logger.info("Database connection is successful");
            preparedStatement.setString(1, orderDataToString(orderData));
            if(transactionType == 0){
                preparedStatement.setString(2, "CUSTOMER");
            }
            else{
                preparedStatement.setString(2, "STOCK");
            }
        } catch (SQLException e) {
            Main.logger.warning("Database error: " + e.getMessage());
        }
    }

    //this method is so that the rest of the project components can get data as needed.
    public Object[][] getQueryDataObjectArray(){
        if(queryData == null || queryData.isEmpty()){
            return new Object[0][0];
        }

        Object[][] result = new Object[queryData.size()][];

        for(int i = 0; i < queryData.size(); i++){
            result[i] = queryData.get(i);
        }
        return result;
    }

    public String orderDataToString(List<String[]> orderData){
        if(orderData == null || orderData.isEmpty()){
            return "";
        }

        StringBuilder result = new StringBuilder();
        String[] stringArray;
        for(int i = 0; i < orderData.size(); i++){
            stringArray = orderData.get(i);
            result.append(stringArray[0]).append(",").append(stringArray[1]).append(";");
        }
        return result.toString();
    }
}
