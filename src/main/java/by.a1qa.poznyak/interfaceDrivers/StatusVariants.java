package by.a1qa.poznyak.interfaceDrivers;

public enum StatusVariants {
    OK(200),
    NOT_FOUND(404),
    RESULT_OF_POSITIVE_POST(201);

    private int statusOfRequest;

    StatusVariants(int statusOfRequest) {
        this.statusOfRequest = statusOfRequest;
    }

    public int getStatusOfRequest() {
        return statusOfRequest;
    }
}
