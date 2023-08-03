package by.a1qa.poznyak.interfaceDrivers;

public enum TypeToCheck {
    POST("post"),
    PHOTO("photo");

    private String type;

    TypeToCheck(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
