package by.a1qa.poznyak.dao;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.mysqlTables.Session;
import by.a1qa.poznyak.readPropertiesFiles.ReadFromConfProperties;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;

import java.sql.*;
import java.util.ArrayList;

public class SessionDao {
    private static final String connectionUrl = ReadFromConfProperties.getUrlSQL();
    private static final String username = ReadFromConfProperties.getUsernameSQL();
    private static final String password = ReadFromConfProperties.getPasswordSQL();
    private static final String selectAll = "SELECT * FROM session";
    private static final String updateNameById = "UPDATE session SET session_key = ? WHERE id = ?";
    private static final String deleteById = "DELETE FROM session WHERE id = ?";

    public static long create(Session session) {
        final String sqlRequest = "INSERT INTO session(" + "session_key," + " created_time," + " build_number)" +
                " VALUES(?,?,?)";
        long id = ReadTestProperties.getDefaultId();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, session.getSessionKey());
            statement.setTimestamp(2, session.getCreatedTime());
            statement.setLong(3, session.getBuildNumber());

            long row =statement.executeUpdate();
            if (row > ReadTestProperties.getDefaultId()) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getLong(1);
                }
                resultSet.close();
            }
        }
        catch(SQLException ex){
            Logger.getInstance().warn("There are some problems with closing Connection or PreparedStatement :"
                    + ex + " or the session already exists");
        }
        return id;
    }

    public static ArrayList<Session> readAll() throws SQLException {
        ArrayList<Session> sessionList = new ArrayList<Session>();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(selectAll);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long sessionId = resultSet.getLong("id");
                String sessionKey = resultSet.getString("session_key");
                Timestamp createdTime = resultSet.getTimestamp("created_time");
                Long buildNumber = resultSet.getLong("build_number");

                Logger.getInstance().info(""+ sessionId + "-" + sessionKey + "-" + createdTime + "-" +
                        buildNumber);

                Session session = new Session(sessionId, sessionKey, createdTime, buildNumber);
                sessionList.add(session);
            }
        }
        catch(SQLException ex){
            Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement or ResultSet");
            throw new SQLException();
        }
        return sessionList;
    }

    public static int update(Session session, long id) {
        int count = ReadTestProperties.getDefaultCount();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(updateNameById)) {

            statement.setString(1, session.getSessionKey());
            statement.setLong(2, id);

            count =  statement.executeUpdate();
        }
        catch(Exception ex){
            Logger.getInstance().warn("" + ex);
        }
        return count;
    }

    public static int delete(Long id) throws SQLException {
        int number = ReadTestProperties.getDefaultCount();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(deleteById)) {
            statement.setLong(1, id);

            number = statement.executeUpdate();
            Logger.getInstance().info("Number of deleted rows in 'session' table is " + number + ", id was " + id);
        }
        catch(Exception ex){
            Logger.getInstance().warn("This entity can't be deleted" + ex);
            throw new SQLException();
        }
        return number;
    }
}



