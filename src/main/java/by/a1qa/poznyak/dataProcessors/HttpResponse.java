package by.a1qa.poznyak.dataProcessors;

import lombok.Getter;

@Getter
public class HttpResponse {
    private int statusCode;
    private String responseBody;

    public HttpResponse(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

}
