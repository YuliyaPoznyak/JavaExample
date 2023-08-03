package by.a1qa.poznyak.interfaceDrivers;

public enum APIMethod {

    PHOTO_INFO("photos.get"),
    WALL("wall.post"),
    WALL_EDIT("wall.edit"),
    WALL_ADD_COMMENT("wall.createComment"),
    USER_ID("users.get"),
    IS_LIKES("likes.isLiked"),
    WALL_DELETE("wall.delete"),
    WALL_PHOTO("photos.saveWallPhoto"),
    ADDRESS_OF_SERVER_TO_UPLOAD_PHOTO("photos.getWallUploadServer");

    private String apiMethod;

    APIMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getAPIMethod() {
        return apiMethod;
    }
}


