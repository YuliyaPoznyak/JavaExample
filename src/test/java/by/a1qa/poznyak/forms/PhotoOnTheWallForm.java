package by.a1qa.poznyak.forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PhotoOnTheWallForm extends Form {
    private final ILabel photoOnTheWall = getElementFactory().getLabel(By.xpath("//div[contains(@id, 'photo')]//img[contains(@src, 'https')]"), "Photo from the wall");

    public PhotoOnTheWallForm() {
        super(By.xpath("//div[contains(@class, 'page_photo')]"), "Photo from the wall");
    }

    public String getLinkToDownload() {
        return photoOnTheWall.getAttribute("src");
    }

}
