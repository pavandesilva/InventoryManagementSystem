package common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pavan De Silva
 */
public class DB {

    private static Connection connection;
    private static StatLogging log;

    public static void makeCon() throws SQLException, ClassNotFoundException {
        createConnection();
    }

    private static void createConnection() {

//        String className = "com.mysql.jdbc.Driver";
//        String host = "";
//        String uName = "";
//        String pswd = "";

        try {
//            InputStream classInput = new java.io.FileInputStream("D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\info\\className.txt");
//            InputStream hostInput = new java.io.FileInputStream("info\\host.txt");
//            InputStream portInput = new java.io.FileInputStream("info\\port.txt");
//            InputStream dbNameInput = new java.io.FileInputStream("info\\dbname.txt");
//            InputStream usernameInput = new java.io.FileInputStream("D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\info\\username.txt");
//            InputStream hostInput = new java.io.FileInputStream("D:\\projects\\Java\\1stYear\\FinalProjects\\1stYearProject\\info\\host.txt");
//            InputStream passwordInput = new java.io.FileInputStream("info\\pwd.txt");

//            int DBClassName = classInput.read();
//            while (DBClassName != -1) {
//                className += ((char) DBClassName);
//                DBClassName = classInput.read();
//            }
//            classInput.close();

//            int username = usernameInput.read();
//            while (username != -1) {
//                uName += ((char) username);
//                username = usernameInput.read();
//            }
//            usernameInput.close();
//
//            int hostName = hostInput.read();
//            while (hostName != -1) {
//                host += ((char) hostName);
//                hostName = hostInput.read();
//            }
//            hostInput.close();

//            int pass = passwordInput.read();
//            while (pass != -1) {
//                pswd += ((char) pass);
//
//                pass = classInput.read();
//            }
//            passwordInput.close();
//            Class.forName(className);
//            connection = null;
//            connection = DriverManager.getConnection(host, uName, "abcd123");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/project", "root", "abcd123");

        } catch (Exception e) {
            log.errorLog(e.getMessage());
        }

//        Class.forName("com.mysql.jdbc.Driver");
    }

    public static void addData(String sql) throws Exception {
        if (connection == null) {
            createConnection();
        } else {
            connection.createStatement().executeUpdate(sql);
        }
    }

    public static ResultSet search(String sql) throws Exception {
        if (connection == null) {
            createConnection();
        }
        return connection.createStatement().executeQuery(sql);
    }

    public static Connection getNewConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }
}
