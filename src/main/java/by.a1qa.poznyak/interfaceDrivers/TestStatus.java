package by.a1qa.poznyak.interfaceDrivers;

public enum TestStatus {
    PASSED(1),
    FAILED(2),
    SKIPPED(3);

    private int status;

    TestStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
