package n11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class AllStore {

    @Test

    public static void main(String[] args) throws IOException {
        //Driver tanımlama ve driver'ın lokasyonunu verme



        WebDriverManager.chromedriver().setup();

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

        String textallstores = driver.findElement(By.xpath("//h3[contains (text(),'Tüm Mağazalar')]")).getText();
        System.out.println(textallstores);

        //String[] alphabet = new String[] {"A", "B", "C", "Ç", "D", "E", "F", "G", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "X", "V", "W", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String[] alphabet = new String[] {"A"};

        Map<String, List<String>> dataMap = new HashMap<>();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Store Info");

        for (String letter : alphabet) {
            WebElement letterElement = driver.findElement(By.cssSelector("[data-has-seller=\"" + letter + "\"]"));
            letterElement.click();

            // verilerin yüklenmesi için.
            // sonraki harfin değerlerini getirmeden önce yüklenmiş mi onu kontrol ediyor
            // .infoText > i yüklenince değişen yer
            FluentWait wait = new FluentWait(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);

            WebElement letterResElem = driver.findElement(By.cssSelector(".infoText > i"));
            wait.until(ExpectedConditions.textToBePresentInElement(letterResElem, "\"" + letter + "\""));

            System.out.println("find :" + letter + " element");

            By mySelector = By.cssSelector(".allSellers .sellerListHolder > ul > li > a");
            List<WebElement> myElements = driver.findElements(mySelector);

            System.out.println(letter + " letter data size: " + myElements.size());


            Random r = new Random();
            int randomValue = r.nextInt(myElements.size()); //Getting a random value that is between 0 and (list's size)-1
            myElements.get(randomValue).click(); //Clicking on the random item in the list.
            System.out.println(randomValue + " randomValue: ");





            List<String> sdata = new ArrayList<>();

            String storetext;
            int count = 0;
            for (WebElement e : myElements) {
                storetext = e.getText();

                sdata.add(storetext);

                // hızlıca test etmek için yapıldı
                // onar onar getiriyor
                // if (count++ > 1) {
                //   break;
                // }
            }

            dataMap.put(letter, sdata);

            System.out.println(letter + " find ended");
        }

        List<XSSFRow> rows = new ArrayList<>();

        for (int i = 0; i < alphabet.length; i++) {
            List<String> letterData = dataMap.get(alphabet[i]);

            for (int j = 0; j < letterData.size(); j++) {
                if (rows.size() <= j)
                    rows.add(sheet.createRow(j));
                XSSFRow row = rows.get(j);

                if (row.getCell(i) == null)
                    row.createCell(i);
                XSSFCell cell = row.getCell(i);

                cell.setCellValue(letterData.get(j));
            }
        }

        String filePath = ".\\datafiles\\store.xlsx";
        FileOutputStream outstream = new FileOutputStream(filePath);
        workbook.write(outstream);

        outstream.close();

        System.out.println("store.xls file written successfully...");




    }






}
