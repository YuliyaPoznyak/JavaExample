package by.a1qa.poznyak.support;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.mysqlTables.TestTable;

import java.util.ArrayList;

public class ChangingAuthorAndProject {
    public static ArrayList<TestTable> changeAuthorAndProject (ArrayList<TestTable> listNecessaryTests,
                                                                Long authorId, Long projectId) {
        for (TestTable test : listNecessaryTests) {
            test.setAuthorId(authorId);
            test.setProjectId(projectId);
            Logger.getInstance().info("New info for test is"+test);
        }
        return listNecessaryTests;
    }
}
