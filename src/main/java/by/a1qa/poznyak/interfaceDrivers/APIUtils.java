package by.a1qa.poznyak.interfaceDrivers;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dataProcessors.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

public class APIUtils {

    public static HttpResponse getRequest(String url) throws Exception {
        HttpUriRequest httpGet = new HttpGet(url);
        Logger.getInstance().debug(url);
        return responseProcessing(httpGet);
        }

    public static HttpResponse postRequest(String urlToEnter, List<NameValuePair> params) throws Exception {
        final HttpPost httpPost = new HttpPost(urlToEnter);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        return responseProcessing(httpPost);
    }

    public static HttpResponse responseProcessing(HttpUriRequest httpUriRequest) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpclient.execute(httpUriRequest);
            final HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(entity);
            Logger.getInstance().debug(result);
            Logger.getInstance().debug("Statuscode is " + statusCode);
            HttpResponse httpResponse = new HttpResponse(statusCode, result);
            return httpResponse;
        }
    }
}
