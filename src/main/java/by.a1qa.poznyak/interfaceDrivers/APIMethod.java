package by.a1qa.poznyak.interfaceDrivers;

public enum APIMethod {
    POSTS("/posts"),
    USERS("/users");

    private String middleGetRequest;

    APIMethod(String middleGetRequest) {
        this.middleGetRequest = middleGetRequest;
    }

    public String getRequestMiddle() {
        return middleGetRequest;
    }
}
