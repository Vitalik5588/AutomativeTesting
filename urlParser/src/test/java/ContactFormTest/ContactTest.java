package ContactFormTest;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class ContactTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private FunctionsForContactForm functionsCF;

    @BeforeMethod
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.get("http://ortholine.redstone.org.ua/kontakty/");
        functionsCF = PageFactory.initElements(driver, FunctionsForContactForm.class);
    }

    @AfterMethod
    public void finishTest(){
        driver.quit();
    }

    @Test()
    public void positiveTest(){

        functionsCF.fillFirstName("Моє імя");
        functionsCF.fillSurname("Моє прізвище");
        functionsCF.fillPhoneNumber("123456789");
        functionsCF.fillEmail("test@mail.com");
        functionsCF.fillMessage("Моє повідомленя");
        functionsCF.clickSubmitButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("thank")));
    }

    @Test(priority = 1)
    public void requiredFieldsTest(){
        functionsCF.clickSubmitButton();
        wait.until(ExpectedConditions.attributeToBe(functionsCF.getFirstNameField(),"aria-invalid","true"));
        wait.until(ExpectedConditions.attributeToBe(functionsCF.getSurnameField(),"aria-invalid","true"));
        wait.until(ExpectedConditions.attributeToBe(functionsCF.getPhoneNumberField(),"aria-invalid","true"));
        wait.until(ExpectedConditions.attributeToBe(functionsCF.getEmailField(),"aria-invalid","true"));
        wait.until(ExpectedConditions.attributeToBe(functionsCF.getMessageField(),"aria-invalid","false"));
    }

    @Test(priority = 2)
    public void phoneNumberFieldTest() {
        String[] wrongPhoneNumber = {"QWERTY","asdsdx","!@#$%^&*(","n13435","1234v","123+","+123+","+123-456+"};
        String[] goodPhoneNumber = {"12345","+4321","+4321-2321","+321-2321-","-123-45-67-"};


        for (String number:wrongPhoneNumber) {
            functionsCF.fillPhoneNumber(number);
            functionsCF.clickSubmitButton();
            wait.until(ExpectedConditions.attributeToBe(functionsCF.getPhoneNumberField(), "aria-invalid", "true"));
            functionsCF.getPhoneNumberField().clear();
        }

        for (String number:goodPhoneNumber) {
            functionsCF.fillPhoneNumber(number);
            functionsCF.clickSubmitButton();
            wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("span[class=\"ajax-loader is-active\"]"))));
            wait.until(ExpectedConditions.attributeToBe(functionsCF.getPhoneNumberField(), "aria-invalid", "false"));
            functionsCF.getPhoneNumberField().clear();
        }

    }


    @Test(priority = 3)
    public void emailFieldTest() {
        String[] wrongEmail = {"12345","qwerty","qwewr.","aert.qw","asdf.asdf.com","12rfvdx","43yhnd@gmail.","qaz3222@gmail.com.","@gmail.com","asdas#sd.sd"};
        String[] goodEmail = {"poiuy@rfv.cde","123@12.345","123qwer@vcxcvcxxvbvcx.xccxcxxc"};


        for (String email:wrongEmail) {
            functionsCF.fillEmail(email);
            functionsCF.clickSubmitButton();
            wait.until(ExpectedConditions.attributeToBe(functionsCF.getEmailField(), "aria-invalid", "true"));
            functionsCF.getEmailField().clear();
        }

        for (String email:goodEmail) {
            functionsCF.fillEmail(email);
            functionsCF.clickSubmitButton();
            wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("span[class=\"ajax-loader is-active\"]"))));
            wait.until(ExpectedConditions.attributeToBe(functionsCF.getEmailField(), "aria-invalid", "false"));
            functionsCF.getEmailField().clear();
        }

    }




}
