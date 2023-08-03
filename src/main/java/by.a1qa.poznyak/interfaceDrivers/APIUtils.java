package by.a1qa.poznyak.interfaceDrivers;

import aquality.selenium.core.logging.Logger;
import by.a1qa.poznyak.dataProcessors.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
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
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest httpGet = new HttpGet(url);
            Logger.getInstance().debug(url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            final HttpEntity entity1 = response1.getEntity();
            StatusLine statusLine = response1.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            String result = EntityUtils.toString(entity1);
            //System.out.println(result);
            Logger.getInstance().debug(result);
            Logger.getInstance().debug("Statuscode is " + String.valueOf(statusCode));
            HttpResponse httpResponse = new HttpResponse(statusCode, result);
            return httpResponse;
        }
        }

    public static HttpResponse postRequest(String urlToEnter, List<NameValuePair> params) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(urlToEnter);
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            final HttpEntity entity2 = response2.getEntity();
            int statusCode = response2.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(entity2);
            Logger.getInstance().debug(result);
            Logger.getInstance().debug("Statuscode is " + String.valueOf(statusCode));
            HttpResponse httpResponse = new HttpResponse(statusCode, result);
            return httpResponse;
        }
    }
}
