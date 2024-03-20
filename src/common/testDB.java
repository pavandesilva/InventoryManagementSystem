
package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL
 */
public class testDB {

    private static Connection connection;
    
    private static void createConnection() throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/test", "root", "abcd123");
    }
    
    public static void addData(String sql)throws Exception{
        if(connection == null){
            createConnection();
        }else{
            connection.createStatement().executeUpdate(sql);
        }
    }

}
