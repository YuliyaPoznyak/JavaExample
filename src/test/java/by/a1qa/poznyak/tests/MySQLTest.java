package by.a1qa.poznyak.tests;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.TestDao;
import by.a1qa.poznyak.dao.mysqlTables.TestTable;
import by.a1qa.poznyak.support.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class MySQLTest {
    ArrayList<TestTable> updatedListNecessaryTests;
    @Test
    public void workWithEntityInMySQL() throws SQLException, InterruptedException {

        ArrayList<TestTable> listNecessaryTests = TestDao.readSeveralTestsId();
        long authorId = AuthorAndProjectData.getAuthorId();
        long projectId = AuthorAndProjectData.getProjectID();

        updatedListNecessaryTests = ChangingAuthorAndProject.changeAuthorAndProject
                (listNecessaryTests, authorId, projectId);
        ArrayList<TestTable> newListFromDataBase = WorkingWithTestGroup.addGroupOfTestsInMySQL(updatedListNecessaryTests);
        Logger.getInstance().info(String.valueOf(newListFromDataBase));

        ArrayList<Long> timeList = TimeOfTests.getListOfTime(listNecessaryTests);
        TimeOfTests.simulationOfTest(timeList);

    }

    @AfterMethod
    public void deleteGroupOfTests() throws SQLException {
        WorkingWithTestGroup.deleteGroupOfTestsInMySQL(updatedListNecessaryTests);
    }
}
