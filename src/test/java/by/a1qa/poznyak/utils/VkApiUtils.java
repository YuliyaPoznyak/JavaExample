package by.a1qa.poznyak.utils;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.data.ReadFromConfProperties;
import by.a1qa.poznyak.dataProcessors.*;
import by.a1qa.poznyak.interfaceDrivers.APIMethod;
import by.a1qa.poznyak.interfaceDrivers.APIUtils;
import by.a1qa.poznyak.interfaceDrivers.HttpStatusCode;
import by.a1qa.poznyak.interfaceDrivers.TypeToCheck;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VkApiUtils {

    public static UserData getUserData() throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.USER_ID.getAPIMethod()+ "?uid" + "&access_token=" +
                ReadFromConfProperties.getToken() +"&v=" + ReadFromConfProperties.getV();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        JSONObject userData = jsonArray.getJSONObject(0);
        return new GsonBuilder().create().fromJson(String.valueOf(userData), UserData.class);
    }

    public static int postRequestPostOnTheWall(int userId,String textForPost) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() +APIMethod.WALL.getAPIMethod()+ "?uid="
                + userId +"&access_token=" + ReadFromConfProperties.getToken() + "&v="
                + ReadFromConfProperties.getV();
        final List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("owner_id", String.valueOf(userId)));
        params.add(new BasicNameValuePair("message", textForPost));

        HttpResponse response = APIUtils.postRequest(url, params);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject result = jsonObject.getJSONObject("response");
        return result.getInt("post_id");
    }

    public static int postEditPostOnTheWall(int userId, String newTextForPost, int postId, String photoName) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.WALL_EDIT.getAPIMethod()+ "?uid=" + userId +"&access_token="
                + ReadFromConfProperties.getToken() + "&v=" + ReadFromConfProperties.getV();
        final List<NameValuePair> params = new ArrayList<>();

        int photoId = postToUploadPhoto(userId, photoName);
        String idOfPhoto = userId +"_"+photoId;
        params.add(new BasicNameValuePair("owner_id", String.valueOf(userId)));
        params.add(new BasicNameValuePair("message", newTextForPost));
        params.add(new BasicNameValuePair("post_id", String.valueOf(postId)));
        params.add(new BasicNameValuePair("attachments", "photo"+ idOfPhoto));

        HttpResponse response = APIUtils.postRequest(url, params);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        Logger.getInstance().debug("The body of response is " + response.getResponseBody());

        return photoId;
    }

    public static int postCreateCommentOnTheWall(int userId, String textForPostComment, int postId) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.WALL_ADD_COMMENT.getAPIMethod()+ "?uid=" + userId
                +"&access_token=" + ReadFromConfProperties.getToken() + "&v=" + ReadFromConfProperties.getV();
        final List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("owner_id", String.valueOf(userId)));
        params.add(new BasicNameValuePair("message", textForPostComment));
        params.add(new BasicNameValuePair("post_id", String.valueOf(postId)));

        HttpResponse response = APIUtils.postRequest(url, params);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject objectAnswer = jsonObject.getJSONObject("response");
        return objectAnswer.getInt("comment_id");
    }

    public static LikesInfo getInfoAboutLikes(int userId, int postId) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.IS_LIKES.getAPIMethod()+ "?user_id=" + userId + "&item_id="+ postId
                +"&type="+ TypeToCheck.POST.getType() +"&access_token=" + ReadFromConfProperties.getToken()
                +"&v=" + ReadFromConfProperties.getV();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject result = jsonObject.getJSONObject("response");
        return new GsonBuilder().create().fromJson(String.valueOf(result), LikesInfo.class);
    }

    public static int getRequestDeletePost(int userId, int postId) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.WALL_DELETE.getAPIMethod()+ "?user_id=" + userId + "&post_id="+ postId
                +"&access_token=" + ReadFromConfProperties.getToken() +"&v=" + ReadFromConfProperties.getV();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getInt("response");
    }

    public static int postToUploadPhoto(int userId, String photoName) throws Exception {
        String addressOfServerToUploadPhoto = VkApiUtils.getAddressOfServerToUploadPhoto(userId);

        ResultOfSavingPhoto resultOfSavingPhoto = savePhotoOnServer(photoName, addressOfServerToUploadPhoto);

        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.WALL_PHOTO.getAPIMethod()+ "?uid=" + userId +"&access_token=" +
                ReadFromConfProperties.getToken() + "&v=" + ReadFromConfProperties.getV();

        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("server", String.valueOf(resultOfSavingPhoto.getServer())));
        params.add(new BasicNameValuePair("hash", resultOfSavingPhoto.getHash()));
        params.add(new BasicNameValuePair("photo", resultOfSavingPhoto.getPhoto()));

        HttpResponse response = APIUtils.postRequest(url, params);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody2 = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody2);

        JSONObject jsonObject2 = new JSONObject(responseBody2);
        JSONArray jsonArray = jsonObject2.getJSONArray("response");
        JSONObject result2 = jsonArray.getJSONObject(0);
        int photoId = result2.getInt("id");
        return photoId;
    }

    public static String getAddressOfServerToUploadPhoto (int userId) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.ADDRESS_OF_SERVER_TO_UPLOAD_PHOTO.getAPIMethod()+ "?user_id="
                + userId + "&access_token=" + ReadFromConfProperties.getToken() +"&v=" + ReadFromConfProperties.getV();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject result = jsonObject.getJSONObject("response");
        Logger.getInstance().debug("Upload_url is " + result.getString("upload_url"));

        return result.getString("upload_url");
        }

    public static String getLinkToDownloadPhoto(int userId, int photoId) throws Exception {
        String url = ReadFromConfProperties.getUrlForRequest() + APIMethod.PHOTO_INFO.getAPIMethod()+ "?owner_id=" + userId + "&photo_ids="
                + photoId +"&album_id=wall" +"&access_token=" + ReadFromConfProperties.getToken()
                +"&v=" + ReadFromConfProperties.getV();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == HttpStatusCode.OK.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Logger.getInstance().debug("The body of response is " + responseBody);
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject result = jsonObject.getJSONObject("response");
        JSONArray jsonArray = result.getJSONArray("items");
        JSONObject photoInfo = jsonArray.getJSONObject(0);
        PhotoData photoData = new GsonBuilder().create().fromJson(String.valueOf(photoInfo), PhotoData.class);
        return photoData.getPhoto_604();
    }

    public static ResultOfSavingPhoto savePhotoOnServer(String photoName, String addressOfServerToUploadPhoto) {
        final String dir = System.getProperty("user.dir") + photoName;
        File f = new File(dir);
        kong.unirest.HttpResponse<JsonNode> result = Unirest.post(addressOfServerToUploadPhoto)
                .multiPartContent()
                .field("photo", new File(dir))
                .asJson();

        String responseBody = String.valueOf(result.getBody());
        Logger.getInstance().debug("The body of response is " + responseBody);
        ResultOfSavingPhoto resultOfSavingPhoto = new GsonBuilder().create().fromJson(responseBody, ResultOfSavingPhoto.class);
        return resultOfSavingPhoto;
    }
}
