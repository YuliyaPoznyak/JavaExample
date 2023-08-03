package by.a1qa.poznyak.interfaceDrivers;

public enum BtnMenuText {
    MY_PAGE("l_pr"),
    NEWS("l_nwsf");

    private String btnMenu;

    BtnMenuText(String btnMenu) {
        this.btnMenu = btnMenu;
    }

    public String getBtnMenu() {
        return btnMenu;
    }
}
