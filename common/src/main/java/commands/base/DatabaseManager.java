package commands.base;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseManager {
    private static BasicDataSource basicDS;

    static {
        try {
            //todo gitignore
            basicDS = new BasicDataSource();
            Properties properties = new Properties();
            InputStream inputStream = DatabaseManager.class.getResourceAsStream("/db.properties");
            properties.load(inputStream);
            basicDS.setUrl(properties.getProperty("DB_CONNECTION_URL"));
            basicDS.setUsername(properties.getProperty("DB_USER"));
            basicDS.setPassword(properties.getProperty("DB_PWD"));
            basicDS.setInitialSize(5);
            basicDS.setMaxTotal(10);
            basicDS.setMaxOpenPreparedStatements(100);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close() throws SQLException {
        basicDS.close();
    }

    public static synchronized Connection getConnection() throws SQLException {
        return basicDS.getConnection();
    }

}