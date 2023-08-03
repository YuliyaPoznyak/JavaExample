package by.a1qa.poznyak.forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import by.a1qa.poznyak.dataProcessors.Comment;
import by.a1qa.poznyak.dataProcessors.PostData;
import by.a1qa.poznyak.interfaceDrivers.Flags;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class WallUserPageForm extends Form {

    private ILabel textInPost;
    private ILabel forIdOfNewPost;
    private ILabel photoOnTheWall;
    private IButton btnForCommentQuantity;
    private IButton btnLookAtComments;
    private IButton btnAuthorOfComment;
    private ILabel textInComment;
    private IButton btnLike;
    private ILabel post;

    public WallUserPageForm() {
        super(By.xpath("//div[contains(@class, 'page_photo')]"), "User photo");
    }

    public PostData getDataFromPost(int userId, int postId) {
        PostData postDataFromUI = new PostData();
        textInPost = getElementFactory().getLabel(By.xpath("//div[@id = 'wpt"+ userId+ "_" + postId+ "']//div[contains(@class,'wall_post_text')]"), "Text from post on the wall");
        forIdOfNewPost = getElementFactory().getLabel(By.xpath("//a[contains(@class, 'post_image')]//img"), "To get id of new post");
        AqualityServices.getConditionalWait().waitFor(() -> forIdOfNewPost.getAttribute("data-post-id").
                equals("" + userId + "_" + postId));
        String textFromPost = textInPost.getText();
        postDataFromUI.setTextFromPost(textFromPost);
        String postNumberData =forIdOfNewPost.getAttribute("data-post-id");
        int numberOfSymbol = postNumberData.indexOf("_");
        postDataFromUI.setUserId(Integer.parseInt(postNumberData.substring(0, numberOfSymbol)));
        postDataFromUI.setPostId(Integer.parseInt(postNumberData.substring(numberOfSymbol+1)));
        return postDataFromUI;
    }

    public PostData waitForChangedTextInPost(String newTextForPost, int userId, int postId, PostData postDataFromUI) {
        textInPost = getElementFactory().getLabel(By.xpath("//div[@id = 'wpt"+ userId+ "_" + postId+ "']//div[contains(@class,'wall_post_text')]"), "Text from post on the wall");
        photoOnTheWall = getElementFactory().getLabel(By.xpath("//div[@class= 'post_info']//a[contains(@class, 'image')]"), "Photo on the post wall");

        String textFromPost = textInPost.getText();
        AqualityServices.getConditionalWait().waitFor(() -> (textFromPost.equals(newTextForPost)));
        postDataFromUI.setTextFromPost(textFromPost);
        String photoData = photoOnTheWall.getAttribute("data-photo-id");
        int numberOfSymbol1 = photoData.indexOf("_");
        postDataFromUI.setPhotoId(Integer.parseInt(photoData.substring(numberOfSymbol1+1)));
        return postDataFromUI;
    }

    public PostData getCommentData(int userId, int postId, int commentId, PostData postDataFromUI) {
        btnForCommentQuantity = getElementFactory().getButton(By.xpath("//div[contains(@class, 'like_wall') and contains(@class, '"+postId+ "')]//a[contains(@class, 'comment')]//div[@class = 'like_button_count']"), "Comment quantity");
        btnLookAtComments = getElementFactory().getButton(By.xpath("//a[contains(@class, 'replies_next') and contains(@href, '" + postId+ "')] "), "Look at comments");
        btnAuthorOfComment = getElementFactory().getButton(By.xpath("//div[contains(@id, '"+ postId +"')]//div[@class = 'reply_content']//*[contains(@class, 'reply_author')]//a[@class = 'author' and contains(@href, '"+ userId +"')]"), "Author of the comment");
        textInComment = getElementFactory().getLabel(By.xpath("//div[contains(@id, '"+postId+"')]//div[@class = 'reply_content']//div[contains(@class, 'reply_text')]//div[contains(@id, '"+commentId+"')]//div[@class = 'wall_reply_text']"), "Text from last comment");

        int commentQuantity = Integer.parseInt(btnForCommentQuantity.getText());
        AqualityServices.getConditionalWait().waitFor(() ->commentQuantity== Flags.FLAG1.getFlag());

        btnLookAtComments.click();
        int authorId = Integer.parseInt(btnAuthorOfComment.getAttribute("data-from-id"));
        String commentText = textInComment.getText();
        Comment comment1 = new Comment(commentText, authorId);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        postDataFromUI.setCommentList(commentList);
        return postDataFromUI;
    }

    public  void clickLike(int userId, int postId, PostData postDataFromUI) {
        btnLike = getElementFactory().getButton(By.xpath("//div[@id = 'post" +userId+ "_"+postId+"']//div[contains(@class, 'like')]//a[contains(@class, 'like_btn')]"), "Like");

        btnLike.click();
    }


    public void waitForPostWasDeleted(int userId, int postId) {
        post = getElementFactory().getLabel(By.xpath("//div[@id = 'post"+ userId+ "_"+postId+"']"), "Post on the wall");
        AqualityServices.getConditionalWait().waitFor(() ->(!post.state().isDisplayed()));
    }
}
