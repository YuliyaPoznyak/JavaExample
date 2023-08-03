package by.a1qa.poznyak.support;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.TestDao;
import by.a1qa.poznyak.dao.mysqlTables.TestTable;

import java.sql.SQLException;
import java.util.ArrayList;

public class WorkingWithTestGroup {
    public static ArrayList<TestTable> addGroupOfTestsInMySQL(ArrayList<TestTable> updatedListNecessaryTests) throws SQLException {
        ArrayList<TestTable> newTestTableList = new ArrayList<>();
        for (TestTable test: updatedListNecessaryTests) {
           TestTable newTestEntity = TestDao.create(test);
           long newId = newTestEntity.getId();
           Logger.getInstance().info("Id of new entity is " + newId);
           test.setId(newId);
           newTestTableList.add(newTestEntity);
        }
        return  newTestTableList;
    }

    public static void deleteGroupOfTestsInMySQL(ArrayList<TestTable> updatedListNecessaryTests) throws SQLException {
        for (TestTable test: updatedListNecessaryTests) {
            int result = TestDao.delete(test.getId());
            if (result==1) {
                Logger.getInstance().info("Test with id " + test.getId() + "was deleted");
            }
        }
    }
}
