package n11;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
    }

}
