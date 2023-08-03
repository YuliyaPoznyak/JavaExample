package by.a1qa.poznyak.dao;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.mysqlTables.Author;
import by.a1qa.poznyak.readPropertiesFiles.ReadFromConfProperties;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;

import java.sql.*;

public class AuthorDao {
    private static final String connectionUrl = ReadFromConfProperties.getUrlSQL();
    private static final String username = ReadFromConfProperties.getUsernameSQL();
    private static final String password = ReadFromConfProperties.getPasswordSQL();
    private static final String insertAuthor = "INSERT INTO author(" + "name," + " login," + "email)" + " VALUES(?,?,?)";
    private static final String selectByLogin = "SELECT * FROM author WHERE login=?";
    private static final String updateNameById = "UPDATE author SET name = ? WHERE id = ?";
    private static final String deleteById = "DELETE FROM author WHERE id = ?";

    public static long create(Author authorId) {
        long id = ReadTestProperties.getDefaultId();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(insertAuthor, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, authorId.getName());
            statement.setString(2, authorId.getLogin());
            statement.setString(3, authorId.getEmail());
            long row =statement.executeUpdate();
            if (row > ReadTestProperties.getDefaultId()) {
                try(ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    }
                }
            }
        }
        catch(SQLException ex){
            Logger.getInstance().warn("There are some problems with closing Connection or PreparedStatement :"
                    + ex + " or the author already exists");
        }
        return id;
    }

    public static Author readOne(String loginAuthor) {
        Author author = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
            PreparedStatement statement = connection.prepareStatement(selectByLogin)) {
            statement.setString(1, loginAuthor);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    Long authorId = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String login = resultSet.getString("login");
                    String email = resultSet.getString("email");
                    author = new Author(authorId, name, login, email);

                    Logger.getInstance().info("AuthorData is : id - " + authorId + ", name - " + name +
                            ", login - " + login +", email - " + email);
                }
            } catch (SQLException ex) {
                Logger.getInstance().warn("There are some problems with closing ResultSet");
            }} catch(SQLException ex){
            Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement");
        }
        return author;
    }

    public static int update(Author author, long id) {
        int count = ReadTestProperties.getDefaultCount();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     updateNameById)) {

            statement.setString(1, author.getName());
            statement.setLong(2, id);

            count =  statement.executeUpdate();
            if (count!=ReadTestProperties.getDefaultCount()) {
                Logger.getInstance().info("Author data was updated");
            }
        }
        catch(Exception ex){
            Logger.getInstance().warn("Exception " + ex);
        }
        return count;
    }

    public static int delete(Long id) throws SQLException {
        int number = ReadTestProperties.getDefaultCount();
        try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteById)) {
            statement.setLong(1, id);

            number = statement.executeUpdate();
            Logger.getInstance().info("Number of deleted rows in 'author' table is " + number + ".");
        }
        catch(Exception ex){
            Logger.getInstance().warn("This entity can't be deleted" + ex);
            throw new SQLException();
        }
        return number;
    }
}
