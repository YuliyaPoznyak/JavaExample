package by.a1qa.poznyak.interfaceDrivers;

public enum TestFields {
    NAME(" name,"),
    STATUS_ID(" status_id,"),
    METHOD_NAME(" method_name,"),
    PROJECT_ID(" project_id,"),
    SESSION_ID(" session_id,"),
    START_TIME(" start_time,"),
    END_TIME(" end_time,"),
    ENV(" env,"),
    BROWSER(" browser,"),
    AUTHOR_ID(" author_id");

    private String fieldFromTest;

    TestFields(String fieldFromTest) {
        this.fieldFromTest = fieldFromTest;
    }

    public String getTestField() {
        return fieldFromTest;
    }

}
