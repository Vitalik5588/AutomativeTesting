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

import java.util.List;


public class PageSpeedTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 60);
        driver.manage().window().maximize();
        driver.get("https://developers.google.com/speed/pagespeed/insights/");
    }

    @AfterMethod
    public void finishTest(){
        driver.quit();
    }

    @Test
    public void getPageSpeed(){

        WebElement inputField = driver.findElement(By.cssSelector("input[name=\"url\"]"));
        inputField.sendKeys("http://tag-stg.itwdev.info");
        driver.findElement(By.cssSelector(".analyze-cell")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".report-summary")));

        List<WebElement> result = driver.findElements(By.cssSelector(".lh-gauge__percentage"));
        List<WebElement> buttons = driver.findElements(By.cssSelector(".goog-tab"));
        buttons.get(1).click();

        int mobileSpeed = Integer.valueOf(result.get(0).getText());
        int desktopSpeed = Integer.valueOf(result.get(1).getText());

//        System.out.println(mobileSpeed);
//        System.out.println(desktopSpeed);

        SoftAssert softAsert = new SoftAssert();
        softAsert.assertTrue(mobileSpeed >= 90, "MOBILE SPEED /// Expected 90 but found " + mobileSpeed);
        softAsert.assertTrue(desktopSpeed >= 90, "DESKTOP SPEED /// Expected 90 but found " + desktopSpeed);
        softAsert.assertAll();
    }
}
