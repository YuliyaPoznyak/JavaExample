package by.a1qa.poznyak.interfaceDrivers;

public enum HttpStatusCode {
    OK(200),
    NOT_FOUND(404);

    private int statusOfRequest;

    HttpStatusCode(int statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

    public int getStatusOfRequest() {
        return statusOfRequest;
    }
}
