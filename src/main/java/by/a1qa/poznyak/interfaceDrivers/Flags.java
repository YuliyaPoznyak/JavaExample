package by.a1qa.poznyak.interfaceDrivers;

public enum Flags {
    FLAG1(1),
    ZERO_FLAG(0);

    private int flag;

    Flags(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }
}
