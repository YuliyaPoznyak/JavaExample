package by.a1qa.poznyak.utils;

import by.a1qa.poznyak.dataProcessors.HttpResponse;
import by.a1qa.poznyak.dataProcessors.Post;
import by.a1qa.poznyak.dataProcessors.User;
import by.a1qa.poznyak.interfaceDrivers.APIUtils;
import by.a1qa.poznyak.interfaceDrivers.StatusVariants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import by.a1qa.poznyak.interfaceDrivers.APIMethod;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonplaceholderAPI {
    static String url;

    public static Post getOnePost(String urlToEnter, String id) throws Exception {
         url = urlToEnter + APIMethod.POSTS.getRequestMiddle() + "/" + id;
         HttpResponse response = APIUtils.getRequest(url);
         assert response.getStatusCode() == StatusVariants.OK.getStatusOfRequest();

         String responseBody = response.getResponseBody();

         return new GsonBuilder().create().fromJson(responseBody, Post.class);
    }
    public static Post getNoOnePost(String urlToEnter, String id) throws Exception {
        url = urlToEnter + APIMethod.POSTS.getRequestMiddle() + "/" +id;
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == StatusVariants.NOT_FOUND.getStatusOfRequest();

        String responseBody = response.getResponseBody();
        return new GsonBuilder().create().fromJson(responseBody, Post.class);}

    public static List<Post> getSeveralPosts(String urlToEnter) throws Exception {
        url = urlToEnter + APIMethod.POSTS.getRequestMiddle();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == StatusVariants.OK.getStatusOfRequest();

        String responseBody = response.getResponseBody();
        Type postListType = new TypeToken<List<Post>>() {}.getType();
        return new Gson().fromJson(responseBody,postListType);
    }

    public static User oneUserGetMethod(String urlToEnter, String id) throws Exception {
        url = urlToEnter + APIMethod.USERS.getRequestMiddle() + "/" + id;
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == StatusVariants.OK.getStatusOfRequest();

        String responseBody = response.getResponseBody();
        return new GsonBuilder().create().fromJson(responseBody, User.class);
    }

    public static List<User> severalUsersGetMethod(String urlToEnter) throws Exception {
        url = urlToEnter + APIMethod.USERS.getRequestMiddle();
        HttpResponse response = APIUtils.getRequest(url);
        assert response.getStatusCode() == StatusVariants.OK.getStatusOfRequest();

        String responseBody = response.getResponseBody();
        Type userListType = new TypeToken<List<User>>() {}.getType();
        return new Gson().fromJson(responseBody,userListType);
    }

    public static Post postMethod(String urlYoEnter, String userId, String textFromTitle, String textFromBody) throws Exception {
        url = urlYoEnter + APIMethod.POSTS.getRequestMiddle();
        final List<NameValuePair> params = new ArrayList<>();

        params.add(new BasicNameValuePair("title", textFromTitle));
        params.add(new BasicNameValuePair("body", textFromBody));
        params.add(new BasicNameValuePair("userId", userId));

        HttpResponse response = APIUtils.postRequest(url, params);

        assert response.getStatusCode() == StatusVariants.RESULT_OF_POSITIVE_POST.getStatusOfRequest();
        String responseBody = response.getResponseBody();
        Post responseObject = new GsonBuilder().create().fromJson(responseBody, Post.class);

        //PostRequestData postRequestData = new PostRequestData(responseObject, userId, textFromBody, textFromTitle);
        return responseObject;
    }
}
