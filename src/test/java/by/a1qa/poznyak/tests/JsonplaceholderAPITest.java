package by.a1qa.poznyak.tests;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dao.TestDao;
import by.a1qa.poznyak.dataProcessors.Post;
import by.a1qa.poznyak.dataProcessors.User;
import by.a1qa.poznyak.dao.mysqlTables.TestTable;
import by.a1qa.poznyak.readPropertiesFiles.ReadTestProperties;
import by.a1qa.poznyak.support.*;
import by.a1qa.poznyak.utils.*;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import by.a1qa.poznyak.readPropertiesFiles.ReadFromConfProperties;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


public class JsonplaceholderAPITest {
    private static String urlToEnter;
    TestTable test = new TestTable();
    private long startTime;
    private static long authorId;
    private static long projectId;
    private static long sessionId;
    private Timestamp start;

    @BeforeSuite
    public void enterUrl() {
        ReadFromConfProperties.prepareConfigData();
        ReadTestProperties.prepareTestData();
        urlToEnter = ReadFromConfProperties.getURL();
        authorId = AuthorAndProjectData.getAuthorId();
        projectId = AuthorAndProjectData.getProjectID();
        sessionId = AuthorAndProjectData.getSessionId();
    }

    @BeforeMethod
    public void setFeauresOfTests(){
        startTime = System.currentTimeMillis();
        start = new Timestamp(startTime);
        Logger.getInstance().info("Start time for method is " + start);
    }

    @Test
    public void getRequestGetAllPosts() throws Exception {
        List<Post> listOfPosts = JsonplaceholderAPI.getSeveralPosts(urlToEnter);
        Assert.assertTrue(CheckingTheSorting.isListSorted(listOfPosts));
    }

    @Parameters({"id"})
    @Test
    public void getRequestGetOnePost(String id) throws Exception {
        Post post = JsonplaceholderAPI.getOnePost(urlToEnter, id);
        Gson g = new Gson();
        String str = g.toJson(post);
        Assert.assertNotNull(post.getBody());
        Assert.assertNotNull(post.getTitle());
        Assert.assertEquals(str, ReadTestProperties.getOnePostShouldBe());
    }

    @Parameters({"id"})
    @Test
    public void getRequestGetThereIsNoSuchPost(String id) throws Exception {
        Post post = JsonplaceholderAPI.getNoOnePost(urlToEnter, id);
        Assert.assertNull(post.getBody());
        Assert.assertNull(post.getTitle());
        Assert.assertEquals(post.getId(), 0);
        Assert.assertEquals(post.getUserId(), 0);
    }

    @Parameters({"userId", "maxTextLength"})
    @Test
    public void getRequestPost(String userId, String maxTextLength) throws Exception {
        int maxLength = Integer.parseInt(maxTextLength);
        String textFromTitle = RandomText.getRandomText(maxLength);
        String textFromBody = RandomText.getRandomText(maxLength);
        Post post = JsonplaceholderAPI.postMethod(urlToEnter, userId, textFromTitle, textFromBody);

        Assert.assertEquals(post.getBody(), textFromBody);
        Assert.assertEquals(post.getTitle(), textFromTitle);
        Assert.assertEquals(String.valueOf(post.getUserId()), userId);
        Assert.assertNotEquals(post.getId(), 0);
    }

    @Test
    public void getRequestGetAllUsers() throws Exception {
        List<User> listOfUsers = JsonplaceholderAPI.severalUsersGetMethod(urlToEnter);
        int sizeOflist = listOfUsers.size();
        Assert.assertEquals(sizeOflist, ReadTestProperties.getArraySize());
    }

    @Parameters({"id"})
    @Test
    public void getRequestGetOneUser(String id) throws Exception {
        User user = JsonplaceholderAPI.oneUserGetMethod(urlToEnter, id);

        Gson g = new Gson();
        String str = g.toJson(user);
        Assert.assertEquals(str, ReadTestProperties.getOneUserShouldBe());
    }
    
    @SneakyThrows
    @AfterMethod
    public void setOtherDifferentFeatures(ITestResult result, ITestContext contextResult) throws SQLException {
        test.setAuthorId(authorId);
        test.setProjectId(projectId);
        test.setSessionId(sessionId);
        test.setStartTime(start);
        long endTime = System.currentTimeMillis();
        Timestamp timeStampEnd = new Timestamp(endTime);
        Logger.getInstance().info("Test has finished, took around " + (endTime - startTime) + "ms");
        test.setEndTime(timeStampEnd);
        int statusId= StatusResult.getResult(result);
        test.setStatusId(statusId);
        test.setEnv(ReadFromConfProperties.getEnv());
        test.setBrowser(ReadFromConfProperties.getBrowserName());
        Logger.getInstance().info("testcase is " +contextResult.getName());
        test.setName(contextResult.getName());
        test.setMethodName(result.getMethod().getMethodName());
        Logger.getInstance().info("Test method is " + result.getMethod().getMethodName());

        Logger.getInstance().info(test.toString());

        TestTable uploadedTest = TestDao.create(test);
        long id = uploadedTest.getId();
        Logger.getInstance().info("Id of new entry(record) in test table (mysql) is: " + id);
        Assert.assertNotEquals(id, 0, "New entry (record) in table about test wasn't added");
    }

}


