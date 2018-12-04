import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImgAltTest {

    private WebDriver driver;
    private FileWriter imgAltEmpty;
    private SoftAssert softAsert;

    @BeforeMethod
    public void setDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://tag-stg.itwdev.info/sitemap/");
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy__HH.mm.ss");
        String curentDate = dateFormat.format(date);
        imgAltEmpty = new FileWriter("EmptyAlt" + curentDate + ".txt");
        softAsert = new SoftAssert();
    }

    @AfterMethod
    public void finishTest() throws IOException {
        imgAltEmpty.close();
        driver.quit();
    }

    @Test
    public void imgAlt() throws IOException {

//        FAIND ALL LINKS WITH SITE MAP

        List<WebElement> allPageLinks = driver.findElements(By.cssSelector(".row-entry li a"));
        List<WebElement> allImgTags;
        List<String> linksOnPage = new ArrayList<String>();

//        GO TO EVERY PAGE AND CHECK ALL IMG ALT TAGS

        for (WebElement pageLink : allPageLinks) {
            linksOnPage.add(pageLink.getAttribute("href"));
        }
        for (String pageLink : linksOnPage) {
            driver.get(pageLink);
            allImgTags = driver.findElements(By.tagName("img"));
            for (WebElement imgTeg : allImgTags) {
                String alt = imgTeg.getAttribute("alt");
                if (StringUtils.isBlank(alt)) {
                    imgAltEmpty.write(String.format("PAGE: %s  --- --- IMAGE LINK: %s %n %n",pageLink,imgTeg.getAttribute("src") ));
                }
                softAsert.assertNotEquals(StringUtils.isBlank(alt), true);
            }
        }softAsert.assertAll();
    }
}
