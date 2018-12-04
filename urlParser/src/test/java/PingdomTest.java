import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class PingdomTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 60);
        driver.manage().window().maximize();
        driver.get("https://tools.pingdom.com/");
    }

    @AfterMethod
    public void finishTest(){
        driver.quit();
    }

    @Test
    public void getPingdomTest() throws InterruptedException {

        WebElement inputField = driver.findElement(By.id("urlInput"));
        inputField.sendKeys("http://tag-stg.itwdev.info");
        WebElement dropDown = driver.findElement(By.tagName("app-select"));
        dropDown.click();
        dropDown.findElement(By.xpath("//*[contains(text(), 'North America - USA - San Francisco')]")).click();
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Load time")));

        Thread.sleep(8000);

        WebElement loadTimeBlock = driver.findElement(By.name("Load time"));
        StringBuilder sbLoadTime = new StringBuilder(loadTimeBlock.findElement(By.cssSelector("div:nth-child(2)")).getText());

//        System.out.println(sbLoadTime);

        SoftAssert softAsert = new SoftAssert();

        if (sbLoadTime.toString().endsWith("ms")) {
            System.out.println("GOOD LOAD SPEED " + sbLoadTime.toString());
        } else if (sbLoadTime.toString().endsWith("s")){
            Double loadTime = Double.valueOf(sbLoadTime.deleteCharAt(sbLoadTime.length() - 1).toString());
            softAsert.assertTrue( loadTime <= 1.5, "LOAD TIME /// Expected <= 1.5  but found " + loadTime);
        }
        softAsert.assertAll();
    }
}
