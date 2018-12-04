package myParser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.util.List;

import static myParser.ResponseStatus1.getStatus;


public class UrlParser1 {

    public static void main(String[] args) throws Exception {

        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.lapiec-pizza.com.ua");
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        FileWriter fwGoodLinks = new FileWriter("GoodLinks.txt");
        FileWriter fwErrorLinks = new FileWriter("ErrorLinks.txt");

        for (int i = 0; i<allLinks.size(); i++) {
            if (allLinks.get(i).getAttribute("href").startsWith("http") ){

                if(getStatus(allLinks.get(i).getAttribute("href")) == 200 ){
                    fwGoodLinks.write(allLinks.get(i).getAttribute("href").toString() +
                            " STATUS: " + getStatus(allLinks.get(i).getAttribute("href")) + "\r\n");

               }
              else {
                    fwErrorLinks.write(allLinks.get(i).getAttribute("href").toString() +
                        " STATUS: " + getStatus(allLinks.get(i).getAttribute("href")) + "\n");
                }
            }
        }
        fwGoodLinks.close();
        fwErrorLinks.close();
        driver.quit();
    }
}

