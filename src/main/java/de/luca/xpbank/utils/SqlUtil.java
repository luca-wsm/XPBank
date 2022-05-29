package de.luca.xpbank.utils;

import de.luca.xpbank.Main;

import java.io.File;
import java.sql.*;

public class SqlUtil {

    private static final String host = Main.getDatabaseConfig().getString("host");
    private static final String port = Main.getDatabaseConfig().getString("port");
    private static final String database = Main.getDatabaseConfig().getString("database");
    private static final String username = Main.getDatabaseConfig().getString("username");
    private static final String password = Main.getDatabaseConfig().getString("password");
    public static Connection conn;

    public static void connect() throws SQLException {
        if (!isConnected()) {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                conn.close();
                System.out.println("Die MySQL Verbindung wurde geschlossen.");
            } catch (SQLException e) {
                System.out.println("SQL Disconnect Fehler: " + e.getMessage());
            }
        }
    }

    public static boolean isConnected() {
        return (conn != null);
    }

    public static void update(String query) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Update Fehler: " + e.getMessage());
        }
    }

    public static ResultSet getResult(String query) {
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQL Query Fehler: " + e.getMessage());
        }

        return null;
    }

    public static boolean recordExists(String record, String table, String key, String value) {
        try {
            return getResult("SELECT " + record + " FROM " + table + " WHERE " + key + " ='" + value + "'").isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}