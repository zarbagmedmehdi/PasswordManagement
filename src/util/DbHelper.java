package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {



        public static Connection GetDatabaseConnection() {
            Connection connection = null;

            String dbUrl = "jdbc:mysql://localhost:3306/passwordmanagement?zeroDateTimeBehavior=convertToNull";
            String user = "root";
            String pass = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(dbUrl, user, pass);
                System.out.println("connection a abouti");
            } catch (ClassNotFoundException e) {
                e.getLocalizedMessage();
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
                e.getLocalizedMessage();
            }

            return connection;
        }
}
