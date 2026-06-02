import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hardware_store_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Password123!";

    List<String[]> queryData = new ArrayList<>();
    DatabaseConnection(Logger logger){
        //Connection is created in the constructor for now... will probably find a different way to do this
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement()) {
            logger.info("Database connection is successful");
            String initQuery = "SELECT partName, stock, price FROM inventory";
            try(ResultSet resultSet = statement.executeQuery(initQuery)){
                while(resultSet.next()){
                    String partName = resultSet.getString("partName");
                    int stock = resultSet.getInt("stock");
                    float price = resultSet.getFloat("price");
                    queryData.add(new String[]{partName, String.valueOf(stock), String.valueOf(price)});
                }

            }


        } catch (SQLException e) {
            //this should be a logging situation "WARNING: DB is not connected"
            logger.warning("Failed to connect to database -> " + e.getMessage());
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
}
