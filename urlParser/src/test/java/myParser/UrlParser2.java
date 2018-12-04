package myParser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static myParser.ResponseStatus1.getStatus;


public class UrlParser2 {

    private WebDriver driver;
    private SoftAssert softAsert;

    private ExcelFile excelFile = new ExcelFile();

    @BeforeMethod
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://tag-stg.itwdev.info/sitemap/");

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - HH.mm");
        String curentDate = dateFormat.format(date);

        excelFile.createExcelFile("D:/GoodLinks "+curentDate+".xls","FirstSheet");
        softAsert = new SoftAssert();
    }

    @AfterMethod
    public void finishTest() throws IOException {
        excelFile.closeExcelFile();
        driver.quit();
    }

    @Test
    public void urlParser() {

//        FAIND ALL LINKS WITH SITE MAP

        List<WebElement> allPageLinks = driver.findElements(By.cssSelector(".row-entry li a"));
        List<String> linksOnPage = new ArrayList<String>();
        Set<String> setAllLinks = new LinkedHashSet<String>();

        int rowNum = 1;

//        GO TO EVERY PAGE AND CHECK ALL URL STATUS

        for (WebElement pageLink : allPageLinks) {
            linksOnPage.add(pageLink.getAttribute("href"));
        }

        for (String pageLink : linksOnPage) {
            driver.get(pageLink);
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));

            for (int i = 0; i < allLinks.size(); i++) {
                System.out.println(i);

                String linkFromAttribute = allLinks.get(i).getAttribute("href");

                if (!setAllLinks.contains(linkFromAttribute)){

                    setAllLinks.add(linkFromAttribute);

                    try {
                        if (linkFromAttribute.startsWith("http")) {
                            if (getStatus(linkFromAttribute) == 200) {
                                excelFile.writeExcel(rowNum,linkFromAttribute,getStatus(linkFromAttribute));
                                rowNum++;
                            } else {
                                softAsert.assertEquals(getStatus(linkFromAttribute), 200);
                            }
                        }
                    } catch (NullPointerException e) {
                        continue;
                    }
                    softAsert.assertAll();
                }
            }
        }
    }
}

