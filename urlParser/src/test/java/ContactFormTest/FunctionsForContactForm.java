package ContactFormTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FunctionsForContactForm {

    private WebDriver driver;

    public FunctionsForContactForm(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }

    public WebElement getSurnameField() {
        return surnameField;
    }

    public WebElement getPhoneNumberField() {
        return phoneNumberField;
    }

    public WebElement getEmailField() {
        return emailField;
    }

    public WebElement getMessageField() {
        return messageField;
    }

    @FindBy(css = "input[name=\"your-name\"]")
    private WebElement firstNameField;

    @FindBy(css = "input[name=\"your-surname\"]")
    private WebElement surnameField;

    @FindBy(css = "input[name=\"your-tel\"]")
    private WebElement phoneNumberField;

    @FindBy(css = "input[name=\"your-email\"]")
    private WebElement emailField;

    @FindBy(css = "textarea[name=\"message\"]")
    private WebElement messageField;

    @FindBy(css = "input[type=\"submit\"]")
    private WebElement submitButton;





    public void fillFirstName(String firstName){
        firstNameField.sendKeys(firstName);
    }

    public void fillSurname(String surname){
        surnameField.sendKeys(surname);
    }

    public void fillPhoneNumber(String phone){
        phoneNumberField.sendKeys(phone);
    }

    public void fillEmail(String email){
        emailField.sendKeys(email);
    }

    public void fillMessage(String message){
        messageField.sendKeys(message);
    }

    public void clickSubmitButton(){
        submitButton.click();
    }



}
