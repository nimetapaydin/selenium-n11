package n11;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class Store {

    @Test

    public static void main(String[] args){
        //Driver tanımlama ve driver'ın lokasyonunu verme
        System.setProperty("webdriver.chrome.driver","C:\\Users\\nyzk\\Desktop\\n11\\selenium-n11\\drivers\\chromedriver.exe");

        WebDriver driver = (WebDriver) new ChromeDriver();

        driver.get("https://www.n11.com/");

        //driver'ın tam pencere açılmasını sağlar
        driver.manage().window().maximize();

        WebElement popup = driver.findElement(By.id("myLocation-close-info"));
        //bulunan elemente tıklama
        popup.click();

        //class kullanarak elementi bulma
        WebElement store = driver.findElement(By.xpath("//*[contains(@title, 'Mağazalar')]"));
       //bulunan elemente tıklama
        store.click();

        WebElement stores = driver.findElement(By.xpath("//*[contains(@class, 'hOpenMenuContent')]//*[contains(@title, 'Mağazaları Gör')]"));
        stores.click();

        WebElement allstores = driver.findElement(By.xpath("//h3[contains (text(), 'Tüm Mağazalar')]"));
        allstores.click();

        //String textallstores = driver.findElement(By.xpath ("//h3[contains (text(),'Tüm Mağazalar')]")).getText ();
       // System.out.println(textallstores);

    }

}
