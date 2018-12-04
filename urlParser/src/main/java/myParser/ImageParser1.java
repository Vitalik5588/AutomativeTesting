package myParser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.util.List;

import static myParser.ResponseStatus1.getStatus;

public class ImageParser1 {

    public static void main(String[] args) throws Exception {

        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.lapiec-pizza.com.ua");
        List<WebElement> allLinks = driver.findElements(By.tagName("img"));

        FileWriter fwGoodImagesLinks = new FileWriter("GoodImagesLinks.txt");
        FileWriter fwErrorImagesLinks = new FileWriter("ErrorImagesLinks.txt");

        for (int i = 0; i<allLinks.size(); i++) {
            if (allLinks.get(i).getAttribute("src").startsWith("http") ){

                if(getStatus(allLinks.get(i).getAttribute("src")) == 200 ){
                    fwGoodImagesLinks.write(allLinks.get(i).getAttribute("src") +
                            " STATUS: " + getStatus(allLinks.get(i).getAttribute("src")) + "\r\n");

                }
              else {
                    fwErrorImagesLinks.write(allLinks.get(i).getAttribute("src") +
                        " STATUS: " + getStatus(allLinks.get(i).getAttribute("src")) + "\n");
                }
            }
        }
        fwGoodImagesLinks.close();
        fwErrorImagesLinks.close();
        driver.quit();
    }
}
