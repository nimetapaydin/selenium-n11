package n11;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class HomePage {

    @Test

    public static void main(String[] args){
        //Driver tanımlama ve driver'ın lokasyonunu verme
        System.setProperty("webdriver.chrome.driver","C:\\Users\\nyzk\\Desktop\\n11\\selenium-n11\\drivers\\chromedriver.exe");

        WebDriver driver = (WebDriver) new ChromeDriver();

        driver.get("https://www.n11.com/");

        //driver'ın tam pencere açılmasını sağlar
        driver.manage().window().maximize();

        //class kullanarak elementi bulma
        WebElement search = driver.findElement(By.className("desktopOldAutosuggestTheme-input"));
       //bulunan elemente tıklama
        search.click();
        //bulunan alana text girmek
        search.sendKeys("Buzdolabı");
        search.click();
        //arama yapma
        WebElement ara = driver.findElement(By.xpath("//*[@class='SearchBoxOld-buttonContainer']"));

    }

}
