package by.a1qa.poznyak.support;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.mysqlTables.TestTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class TimeOfTests {
    public static ArrayList<Long> getListOfTime(ArrayList<TestTable> listNecessaryTests){
        ArrayList<Long> timeList = new ArrayList<>();

        for (TestTable test:listNecessaryTests) {
            Long finish = test.getEndTime().getTime();
            Long start = test.getStartTime().getTime();
            timeList.add(finish-start);
        }
        long maximumTime = Collections.max(timeList);
        long minimumTime = Collections.min(timeList);
        Logger.getInstance().info("MaximumTime is "+ maximumTime);
        Logger.getInstance().info("MinimumTime is "+ minimumTime);
        for (TestTable test:listNecessaryTests) {
            if ((test.getEndTime().getTime()-test.getStartTime().getTime())==maximumTime){
                Logger.getInstance().info("MaximumTimeTest data: "+ test);
            }
            if ((test.getEndTime().getTime()-test.getStartTime().getTime())==minimumTime){
                Logger.getInstance().info("MinimumTimeTest data: "+ test);
            }
        }
        return timeList;
    }

    public static void simulationOfTest(ArrayList<Long> timeList) throws InterruptedException {

        for (Long time : timeList) {
            Logger.getInstance().info(".....Test took about " + time + "ms");
            TimeUnit.MILLISECONDS.sleep(time);
            Logger.getInstance().info(".....Don't be afraid, all is working correctly");
        }
    }
}
