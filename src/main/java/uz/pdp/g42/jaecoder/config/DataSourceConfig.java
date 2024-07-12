package uz.pdp.g42.jaecoder.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceConfig {
    public static Connection connection;
    private static final String url = SettingConfig.get("datasource.url");
    private static final String username = SettingConfig.get("datasource.username");
    private static final String password = SettingConfig.get("datasource.password");

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }
}
