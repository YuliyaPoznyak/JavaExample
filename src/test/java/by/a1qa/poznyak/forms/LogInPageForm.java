package by.a1qa.poznyak.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LogInPageForm extends Form {
    private final ITextBox txbEmail = getElementFactory().getTextBox(By.xpath("//input[@name='email' and @id = 'index_email']"), "enter email or phone number");
    private final ITextBox txbPassword = getElementFactory().getTextBox(By.xpath("//input[@name='pass' and @id = 'index_pass']"), "enter password");
    private final IButton btnSignIn = getElementFactory().getButton(By.xpath("//button[@id = 'index_login_button']"), "Sign in");

    public LogInPageForm() {
        super(By.xpath("//div[@class = 'JoinForm']"), "Registration field");
    }

    public void setEmail(String email){
        txbEmail.clearAndType(email);
    }

    public void setPassword(String password){
        txbPassword.clearAndType(password);
    }

    public UserPageForm clickBtnSignIn() {
        btnSignIn.click();
        return new UserPageForm();
    }



}
