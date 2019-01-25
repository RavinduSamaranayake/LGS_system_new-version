package lgsapp.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    private DbConnect() {

    }

    public static DbConnect getInstance() {

        return new DbConnect();
    }

    public static Connection getConnection() {

        String connect_string = "jdbc:sqlite:lgsdb.sqlite";


        Connection connection = null;
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(connect_string);
            System.out.println("Connection Sucesss......................");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("not connected.....");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("not connected");
        }

        return connection;
    }


}
