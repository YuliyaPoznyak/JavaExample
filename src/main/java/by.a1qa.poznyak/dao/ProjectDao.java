package by.a1qa.poznyak.dao;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.mysqlTables.Project;
import by.a1qa.poznyak.readPropertiesFiles.ReadFromConfProperties;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;

import java.sql.*;

public class ProjectDao {
    private static final String connectionUrl = ReadFromConfProperties.getUrlSQL();
    private static final String username = ReadFromConfProperties.getUsernameSQL();
    private static final String password = ReadFromConfProperties.getPasswordSQL();
    private static final String insertProject = "INSERT INTO project(" + "name)" + " VALUES(?)";
    private static final String selectByName = "SELECT * FROM project WHERE name=?";
    private static final String updateNameById = "UPDATE test SET name = ? WHERE id = ?";
    private static final String deleteById = "DELETE FROM project WHERE id = ?";

    public static long create(Project project) {
        long id = ReadTestProperties.getDefaultId();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(insertProject, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, project.getName());

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
                    + ex + " or the project already exists");
        }
        return id;
    }

    public static Project readOne(String name) {
        Project project = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(selectByName)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    Long projectId = resultSet.getLong("id");
                    String projectName = resultSet.getString("name");

                    project = new Project(projectId, projectName);

                    Logger.getInstance().info("ProjectData is : id - " + projectId + ", name - " + projectName);
                }
            } catch (SQLException ex) {
                Logger.getInstance().warn("There are some problems with closing ResultSet");
            }} catch(SQLException ex){
            Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement");
        }
        return project;
    }
    public static int update(Project project, long id) {
        int count = ReadTestProperties.getDefaultCount();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(updateNameById)) {

            statement.setString(1, project.getName());
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
            Logger.getInstance().info("Number of deleted rows in 'project' table is " + number + ", id was " + id);
        }
        catch(Exception ex){
            Logger.getInstance().warn("This entity can't be deleted" + ex);
            throw new SQLException();
        }
        return number;
    }
}
