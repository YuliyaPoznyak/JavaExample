package by.a1qa.poznyak.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import by.a1qa.poznyak.interfaceDrivers.BtnMenuText;
import org.openqa.selenium.By;

public class LeftMenuForm extends Form {
    private IButton btnMenu;

    public LeftMenuForm() {
        super(By.xpath("//div[@class = 'side_bar_inner']//nav"), "Left Menu");
    }

    public void clickBtnMenu(BtnMenuText btnMenuText) {
        String textToInput = btnMenuText.getBtnMenu();
        btnMenu = getElementFactory().getButton(By.
                xpath("//div[@id = 'side_bar_inner']//li[@id = '"+textToInput+"']//a"), "My page");
        btnMenu.click();
    }


}
