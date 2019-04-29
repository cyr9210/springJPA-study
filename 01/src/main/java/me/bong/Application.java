package me.bong;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/springboot";
        String username = "bong";
        String password = "pass";

        try (Connection connection = DriverManager.getConnection(url, username, password);){
            System.out.println("Connection crate : " + connection);
            String sql = "CREATE TABLE ACCOUNT (id int, username varchar(50), password varchar(50));";
            try (PreparedStatement statement = connection.prepareStatement(sql);) {
                statement.execute();
            }
        }
    }
}
