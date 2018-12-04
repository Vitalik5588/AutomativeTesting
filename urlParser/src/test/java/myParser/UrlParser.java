package myParser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static myParser.ResponseStatus1.getStatus;


public class UrlParser {

    private WebDriver driver;
    private FileWriter fwGoodLinks;
    private FileWriter fwErrorLinks;
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

        fwGoodLinks = new FileWriter("GoodLinks" + curentDate + ".txt");
        fwErrorLinks = new FileWriter("ErrorLinks" + curentDate + ".txt");

        softAsert = new SoftAssert();
    }

    @AfterMethod
    public void finishTest() throws IOException {
        fwGoodLinks.close();
        fwErrorLinks.close();
        driver.quit();
    }

    @Test
    public void urlParser() throws Exception {

        List<WebElement> allPageLinks = driver.findElements(By.cssSelector(".row-entry li a"));
        List<String> linksOnPage = new ArrayList<String>();

        Set<String> setAllLinks = new LinkedHashSet<String>();

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
                                fwGoodLinks.write(linkFromAttribute +
                                        " STATUS: " + getStatus(linkFromAttribute) + "\r\n");
                                softAsert.assertEquals(getStatus(linkFromAttribute), 200);
                            } else {
                                fwErrorLinks.write(linkFromAttribute +
                                        " STATUS: " + getStatus(linkFromAttribute) + "\n");
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

