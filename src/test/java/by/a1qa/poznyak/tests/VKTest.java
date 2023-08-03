package by.a1qa.poznyak.tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.data.ReadFromConfProperties;
import by.a1qa.poznyak.dataProcessors.Comment;
import by.a1qa.poznyak.dataProcessors.PostData;
import by.a1qa.poznyak.dataProcessors.LikesInfo;
import by.a1qa.poznyak.dataProcessors.UserData;
import by.a1qa.poznyak.forms.LeftMenuForm;
import by.a1qa.poznyak.forms.LogInPageForm;
import by.a1qa.poznyak.forms.UserPageForm;
import by.a1qa.poznyak.forms.WallUserPageForm;
import by.a1qa.poznyak.interfaceDrivers.BtnMenuText;
import by.a1qa.poznyak.interfaceDrivers.Flags;
import by.a1qa.poznyak.utils.ComparisonOfPhoto;
import by.a1qa.poznyak.utils.DownloadingFile;
import by.a1qa.poznyak.utils.RandomText;
import by.a1qa.poznyak.utils.VkApiUtils;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class VKTest {
    static Browser browser = AqualityServices.getBrowser();

    @BeforeMethod
    public void openWebsite() {
        browser.maximize();
        ReadFromConfProperties.prepareConfigData();
        browser.goTo(ReadFromConfProperties.getURL());
        browser.waitForPageToLoad();
    }

    @Parameters({"photoNameToDownload", "maximumLengthOfText"})
    @Test
    public void testVkWallPost(String photoNameAddress, String maximumLengthOfText) throws Exception{
        LogInPageForm logInPageForm = new LogInPageForm();
        Assert.assertTrue(logInPageForm.state().waitForDisplayed());
        Logger.getInstance().info(ReadFromConfProperties.getURL() + " was opened");

        logInPageForm.setEmail(ReadFromConfProperties.getEmail());
        logInPageForm.setPassword(ReadFromConfProperties.getPassword());

        PostData postDataFromAPI = new PostData();

        UserPageForm userPageForm = logInPageForm.clickBtnSignIn();
        LeftMenuForm leftMenuForm = new LeftMenuForm();
        Assert.assertTrue(userPageForm.state().waitForDisplayed());
        Logger.getInstance().info("User has been logged in. ");

        userPageForm.getLeftMenuAndClickButton(BtnMenuText.MY_PAGE);
        WallUserPageForm wallUserPageForm = new WallUserPageForm();
        Assert.assertTrue(wallUserPageForm.state().waitForDisplayed());
        Logger.getInstance().info("There is user page");

        UserData userData = VkApiUtils.getUserData();
        int userId = userData.getId();
        postDataFromAPI.setUserId(userId);
        Logger.getInstance().info("UserId is " + userId);
        String textForPost = RandomText.getRandomText(Integer.parseInt(maximumLengthOfText));
        postDataFromAPI.setTextFromPost(textForPost);

        int postId = VkApiUtils.postRequestPostOnTheWall(userId, textForPost);
        postDataFromAPI.setPostId(postId);

        PostData postDataFromUI = wallUserPageForm.getDataFromPost(userId, postId);
        postDataFromAPI.setTextFromPost(textForPost);
        Assert.assertEquals(postDataFromUI, postDataFromAPI);
        Logger.getInstance().info("Post appeared on the wall with the desired text from correct user using api request.");

        String newTextForPost = RandomText.getRandomText(Integer.parseInt(maximumLengthOfText));
        int idOfPhotoToUpload = VkApiUtils.postEditPostOnTheWall(userId, newTextForPost, postId, photoNameAddress);
        postDataFromAPI.setPhotoId(idOfPhotoToUpload);
        postDataFromAPI.setTextFromPost(newTextForPost);
        Logger.getInstance().info("Id of photo is " + idOfPhotoToUpload);

        postDataFromUI = wallUserPageForm.waitForChangedTextInPost(newTextForPost, userId, postId, postDataFromUI);
        Assert.assertEquals(postDataFromUI, postDataFromAPI);
        Logger.getInstance().info("Text on the last post was changed (" +newTextForPost +
                "), image was uploaded throw api request, id of image is " + idOfPhotoToUpload);

        String linkToDownloadPhoto = VkApiUtils.getLinkToDownloadPhoto(userId, idOfPhotoToUpload);
        File fileToSave = DownloadingFile.downloadPhoto(linkToDownloadPhoto);

        ImageComparisonResult imageComparisonResult = ComparisonOfPhoto.compare(fileToSave.getName(),
                photoNameAddress);
        Assert.assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState(),
                "images are not equal");
        Logger.getInstance().info("Images are the same");
        String textForPostComment = RandomText.getRandomText(Integer.parseInt(maximumLengthOfText));
        int commentId = VkApiUtils.postCreateCommentOnTheWall(userId, textForPostComment, postId);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(new Comment(textForPostComment, userId));
        postDataFromAPI.setCommentList(commentList);

        postDataFromUI = wallUserPageForm.getCommentData(userId, postId, commentId, postDataFromUI);
        Assert.assertEquals(postDataFromUI, postDataFromAPI);
        Logger.getInstance().info("Comment was added to necessary post throw api request, comment text is '"
                +textForPostComment +"', id of comment is " +commentId);

        wallUserPageForm.clickLike(userId, postId, postDataFromUI);
        LikesInfo likesInfo = VkApiUtils.getInfoAboutLikes(userId, postId);
        Assert.assertEquals(Flags.FLAG1.getFlag(), likesInfo.getLiked());

        int resultOfDelete = VkApiUtils.getRequestDeletePost(userId, postId);
        Assert.assertEquals(Flags.FLAG1.getFlag(), resultOfDelete);

        Logger.getInstance().info("The post with id " + postId + " was deleted throw api request.");

        if(fileToSave.delete()) {
            Logger.getInstance().info(fileToSave.getName() + " was deleted");
        }
        else {
            Logger.getInstance().info("There are some troubles with deleting photo");
        }
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            AqualityServices.getBrowser().quit();
        }
    }


}
