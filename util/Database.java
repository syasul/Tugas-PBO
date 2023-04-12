package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static Connection getConnection()
    {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/pbo",
                        "root",
                        "root"
                );
            }
            return connection;
        } catch (SQLException exception) {
            System.out.println("Error : " + exception.getMessage());
            return null;
        }
    }
}
