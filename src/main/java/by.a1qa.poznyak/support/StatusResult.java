package by.a1qa.poznyak.support;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.interfaceDrivers.TestStatus;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;
import org.testng.ITestResult;

public class StatusResult {
    public static int getResult(ITestResult result) throws Exception {
        int statusId = ReadTestProperties.getDefaultCount();
        try
        {
            if(result.getStatus() == ITestResult.SUCCESS)
            {
                statusId = TestStatus.PASSED.getStatus();
                Logger.getInstance().info("Status passed is __ " + statusId);
            }

            else if(result.getStatus() == ITestResult.FAILURE)
            {
                statusId =TestStatus.FAILED.getStatus();
                Logger.getInstance().info("Status failed __ " +statusId);
            }

            else if(result.getStatus() == ITestResult.SKIP ){
                statusId = TestStatus.SKIPPED.getStatus();
                Logger.getInstance().info("Skiped status is __ " + statusId);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("There is no any status");
        }
        return statusId;
    }
}
