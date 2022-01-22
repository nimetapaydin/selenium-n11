package n11;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class Search {

    @Test

    public static void main(String[] args){
        //Driver tanımlama ve driver'ın lokasyonunu verme
        System.setProperty("webdriver.chrome.driver","C:\\Users\\nyzk\\Desktop\\n11\\selenium-n11\\drivers\\chromedriver.exe");

        WebDriver driver = (WebDriver) new ChromeDriver();

        driver.get("https://www.n11.com/");

        //driver'ın tam pencere açılmasını sağlar
        driver.manage().window().maximize();

        //class kullanarak elementi bulma
        WebElement searchinput = driver.findElement(By.id("searchData"));
       //bulunan elemente tıklama
        searchinput.click();
        //bulunan alana text girmek
        searchinput.sendKeys("Buzdolabı");

        //arama yapma
        WebElement search = driver.findElement(By.xpath("//*[@class='searchBtn']"));
        search.click();

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,450)");

        //WebElement third = driver.findElement(By.cssSelector(".view > ul > li:nth-child(3)"));
        //third.click();

        Actions action = new Actions(driver);

        WebElement third = driver.findElement(By.cssSelector("#view > ul > li:nth-child(3) #p-492340375 > div.pro > a"));
        third.click();
        action.doubleClick(third).perform();

        WebElement plus = driver.findElement(By.cssSelector("#unf-p-id > div > div.unf-p-cvr > div.unf-p-left.proDetailArea > div.unf-p-lBox > div.unf-p-detail > div.unf-buy-button-group.button-group-cover > div.button-group > div.pDetail-spinner > div > div > span.spinnerUp.spinnerArrow"));
        plus.click();
        action.doubleClick(plus).perform();

        WebElement addbasket = driver.findElement(By.xpath("//*[@class=\"btn btnGrey addBasketUnify\" and text()=\"Sepete Ekle\"]"));
        addbasket.click();

    }


}
