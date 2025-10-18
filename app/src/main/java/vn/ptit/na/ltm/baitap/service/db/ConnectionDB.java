package vn.ptit.na.ltm.baitap.service.db;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Windows
 */
public class ConnectionDB {
    private static String url  =  "jdbc:postgresql://192.168.100.228:6432/ltm";
//        private static String url  =  "jdbc:postgresql://localhost:5432/ltm";
    private static String user = "repuser";
    private static String pass = "reppass";
    
    public static Connection getConnection() throws SQLException {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(url);
        cfg.setUsername(user);
        cfg.setPassword(pass);
        cfg.setMaximumPoolSize(5);
        cfg.setMinimumIdle(1);
        cfg.setConnectionInitSql("SET TIME ZONE 'Asia/Ho_Chi_Minh'");
        cfg.setDriverClassName("org.postgresql.Driver");
        
        return new HikariDataSource(cfg).getConnection();
      
    }
}
