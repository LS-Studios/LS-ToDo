package de.check24.dualesstudium.stubbe.database.service;

import de.check24.dualesstudium.stubbe.Constants;
import de.check24.dualesstudium.stubbe.TodoProgram;
import de.check24.dualesstudium.stubbe.todo.view.TodoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private final TodoView view = TodoProgram.getGlobalTodoView();

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        if (instance.connection == null) {
            instance.openConnection();
        }

        return instance;
    }

    public void openConnection() {
        File dbConfig = new File(Constants.CONFIG_PROPERTIES_FILE_NAME);

        try (FileInputStream inputStream = new FileInputStream(dbConfig)) {

            Properties properties = new Properties();

            properties.load(inputStream);

            String url = properties.getProperty("DB_URL");

            String[] split = url.split("/");
            File dir = new File(split[split.length-2]);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            connection = DriverManager.getConnection(url);
        } catch (SQLException | IOException e) {
            view.write(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            view.write(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
