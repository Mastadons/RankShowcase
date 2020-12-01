package me.mastadons.rankshowcase.database;

import me.mastadons.flag.DefinedFlag;
import me.mastadons.flag.FlagManager;
import me.mastadons.rankshowcase.configuration.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@FlagManager.FlaggedClass
public class DatabaseManager {

    public static DatabaseManager INSTANCE;

    @FlagManager.FlaggedMethod(flag = DefinedFlag.PLUGIN_LOAD, priority = 10)
    public static void load() {
        INSTANCE = new DatabaseManager();
        try {
            INSTANCE.establishConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Connection connection;

    public void establishConnection() throws SQLException {
        DatabaseConfiguration configuration = Configuration.INSTANCE.databaseConfiguration;
        connection = DriverManager.getConnection("jdbc:mysql://" + configuration.hostname + "/" + configuration.database, configuration.username, configuration.password);
    }

    public List<UUID> hasPermission(String permission) throws SQLException {
        List<UUID> persons = new ArrayList<>();

        String statement = "select * from `" + Configuration.INSTANCE.databaseConfiguration.table + "` where `permission` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, permission);

        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) persons.add(UUID.fromString(result.getString("uuid")));

        return persons;
    }
}
