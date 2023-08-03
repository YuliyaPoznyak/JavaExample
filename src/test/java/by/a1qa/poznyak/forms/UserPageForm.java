package by.a1qa.poznyak.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import by.a1qa.poznyak.interfaceDrivers.BtnMenuText;
import org.openqa.selenium.By;

public class UserPageForm extends Form {
    private IButton btnLeftMenu;
    LeftMenuForm leftMenuForm = new LeftMenuForm();

    public UserPageForm() {
        super(By.xpath("//div[contains(@class, 'ui_rmenu' )]/a[@id='ui_rmenu_news']"), "Right column news");
    }

    public void getLeftMenuAndClickButton(BtnMenuText titleToClick) {
        leftMenuForm.clickBtnMenu(titleToClick);
    }
}
