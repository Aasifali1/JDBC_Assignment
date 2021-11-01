package com.knoldus.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    final static String className = "com.mysql.cj.jdbc.Driver";
    final static String dbUrl = "jdbc:mysql://localhost:3306/shopping";
    final static String dbUser = "root";
    final static String dbPassword = "root";

    public static Connection getDbConnection(){
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            Class.forName(className);
            con = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
