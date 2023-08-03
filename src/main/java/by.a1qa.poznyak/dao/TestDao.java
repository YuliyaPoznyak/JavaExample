package by.a1qa.poznyak.dao;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.mysqlTables.TestTable;
import by.a1qa.poznyak.readPropertiesFiles.ReadFromConfProperties;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;

import java.sql.*;
import java.util.ArrayList;

public class TestDao {

    private static final String connectionUrl = ReadFromConfProperties.getUrlSQL();
    private static final String username = ReadFromConfProperties.getUsernameSQL();
    private static final String password = ReadFromConfProperties.getPasswordSQL();
    private static int commonNumberOfDeletedRawsInTest = 0;

        public static ArrayList<TestTable> readAll() {
            ArrayList<TestTable> tests = new ArrayList<TestTable>();
            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM test");
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TestTable test = readResultSet(resultSet);

                    tests.add(test);
                }
            }
            catch(SQLException ex){
                Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement or ResultSet");
            }
        return tests;
        }

    public static ArrayList<TestTable> readSeveralTests(ArrayList<Long> numbersOfRaws) throws SQLException {
        ArrayList<TestTable> testList = new ArrayList<>();

        for (Long numbersOfRaw : numbersOfRaws) {
            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM test WHERE id=?")) {
                statement.setInt(1, Math.toIntExact(numbersOfRaw));
                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        TestTable test = readResultSet(resultSet);
                        testList.add(test);
                    }
                } catch (SQLException ex) {
                    Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement or ResultSet");
                }
            }
        }
        return testList;
    }

        public static TestTable readOneTest(int id) {

            TestTable test = null;
            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM test WHERE id=?")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        test = readResultSet(resultSet);

                    }
                } catch (SQLException ex) {
                    Logger.getInstance().warn("There are some problems with closing ResultSet");
                }
            } catch(SQLException ex){
             Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement");
            }
        return test;
        }

        public static TestTable create(TestTable test) throws SQLException {
            final String sqlRequest = "INSERT INTO test(" + "name," + " status_id," + " method_name," + " project_id," +
                    " session_id," + " start_time," + " end_time," + " env," + " browser," + " author_id)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?)";
            long id = ReadTestProperties.getDefaultId();

            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                 PreparedStatement statement = connection.prepareStatement(sqlRequest, Statement.RETURN_GENERATED_KEYS)) {

                        statement.setString(1, test.getName());
                        statement.setInt(2, test.getStatusId());
                        statement.setString(3, test.getMethodName());
                        statement.setLong(4, test.getProjectId());
                        statement.setLong(5, test.getSessionId());
                        statement.setTimestamp(6, test.getStartTime());
                        statement.setTimestamp(7, test.getEndTime());
                        statement.setString(8, test.getEnv());
                        statement.setString(9, test.getBrowser());
                        statement.setLong(10, test.getAuthorId());

                        long row =statement.executeUpdate();

                 if (row > ReadTestProperties.getDefaultId()) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {
                        id = resultSet.getLong(1);
                    }
                }
            }
            catch(SQLException ex){
                Logger.getInstance().warn("There are some problems with closing Connection or PreparedStatement :"
                        + ex);
                throw new SQLException();
            }
            test.setId(id);
            return test;
        }

        public static int update(TestTable test, long id) {
            int count = ReadTestProperties.getDefaultCount();
            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE test SET env = ? WHERE id = ?")) {

                        statement.setString(1, test.getEnv());
                        statement.setLong(2, id);

                        count =  statement.executeUpdate();
            }
            catch(Exception ex){
                Logger.getInstance().warn("" + ex);
            }
            return count;
        }

        public static int delete(Long id) {
            int number = ReadTestProperties.getDefaultCount();
            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM test WHERE id = ?")) {
                    statement.setLong(1, id);

                    number = statement.executeUpdate();
                    Logger.getInstance().info("Number of deleted rows in 'test' table is " + number + ", id was " + id);
                    commonNumberOfDeletedRawsInTest ++;
                    Logger.getInstance().info("Common number of deleted rows in 'test' table during all testcases is "
                            + commonNumberOfDeletedRawsInTest);
            }
            catch(Exception ex){
                Logger.getInstance().error(""+ex);
            }
            return number;
        }

        public static TestTable readResultSet(ResultSet resultSet) throws SQLException {
            long testId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int statusId = resultSet.getInt("status_id");
            String methodName = resultSet.getString("method_name");
            long projectId = resultSet.getInt("project_id");
            long sessionId = resultSet.getInt("session_id");
            Timestamp startTime = resultSet.getTimestamp("start_time");
            Timestamp endTime = resultSet.getTimestamp("end_time");
            String env = resultSet.getString("env");
            String browser = resultSet.getString("browser");
            long authorId = resultSet.getLong("author_id");
            TestTable test = new TestTable(testId, name, statusId, methodName, projectId, sessionId,
                    startTime, endTime, env, browser, authorId);

            Logger.getInstance().info(testId + " - " + name + " - " + statusId + " - " + methodName + " - " +
                    projectId + " - " + sessionId + " - " +
                    env + " - " + browser + " - " + authorId);

            return test;
        }

        public static ArrayList<TestTable> readSeveralTestsId() throws SQLException {
        ArrayList<TestTable> testList = new ArrayList<>();
        String numberToRepeat = ReadTestProperties.getRepeatNumber();
            try (Connection connection = DriverManager.getConnection(connectionUrl, username, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM test WHERE id LIKE '%"
                         + numberToRepeat+numberToRepeat + "%'")) {

                try (ResultSet resultSet = statement.executeQuery()) {
                    for (int i=0; i<ReadTestProperties.getArraySize(); i++){
                    if (resultSet.next()) {
                        TestTable test = readResultSet(resultSet);
                        testList.add(test);
                    }
                } }
                    catch (SQLException ex) {
                    Logger.getInstance().warn("There are some problems with closing connection, PreparedStatement or ResultSet");
                }
            }
        return testList;
    }
}
