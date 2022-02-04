package n11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.server.ExportException;
import java.time.Duration;
import java.util.*;

public class exportexcel {

    @Test
    public static void main(String[] args) throws IOException {
        //Driver tanımlama ve driver'ın lokasyonunu verme
        // WebDriverManager.chromedriver().setup();

        // WebDriver driver = WebDriverManager.chromedriver().remoteAddress("http://localhost:4444").create();
        // WebDriver driver = (WebDriver) new ChromeDriver();

//        // Testler parallel şekilde çalıştırılsın
//        new Thread(() -> {
//            try {
//                ChromeOptions chromeOptions = new ChromeOptions();
//                RemoteWebDriver chromeDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444"), chromeOptions);
//                runTest(chromeDriver);
//            } catch (Exception e) {
//                System.err.println("Chrome testi başarısız" + e.getMessage());
//            }
//        });
//
//        // Testler parallel şekilde çalıştırılsın
//        new Thread(() -> {
//            try {
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                RemoteWebDriver firefoxDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444"), firefoxOptions);
//                runTest(firefoxDriver);
//            } catch (Exception e) {
//                System.err.println("Firefox testi başarısız" + e.getMessage());
//            }
//        });

        // Testler parallel şekilde çalıştırılsın
//        new Thread(() -> {
            try {
                EdgeOptions edgeOptions = new EdgeOptions();
                // edgeOptions.setCapability(CapabilityType.PROXY, ZAP.getProxyConfiguration());
                edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--remote-debugging-port=0");
                RemoteWebDriver edgeDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4444"), edgeOptions);

                runTest(edgeDriver);
            } catch (Exception e) {
                System.err.println("Edge testi başarısız" + e.getMessage());
            }
//        });
    }

    public static void runTest(RemoteWebDriver driver) throws IOException {
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

        String[] alphabet = new String[]{"A", "B", "C", "Ç"};
        //String[] alphabet = new String[]{"A", "B", "C", "Ç", "D", "E", "F", "G", "H", "I", "İ", "J", "K", "L", "M", "N", "O", "Ö", "P", "R", "S", "Ş", "T", "U", "Ü", "X", "V", "W", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
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

            By mySelector = By.cssSelector(".allSellers .sellerListHolder > ul");
            WebElement brandUL = driver.findElement(mySelector);
            List<String> sdata = Arrays.asList(brandUL.getText().split("\n"));

            System.out.println(letter + " letter data size: " + sdata.size());

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

        By mySelector = By.cssSelector(".tabPanel .sellerListHolder > ul");
        WebElement brandUL = driver.findElement(mySelector);
        List<String> sdata = Arrays.asList(brandUL.getText().split("\n"));
        int randomStoreIndex = (int) (Math.random() * sdata.size());
        WebElement randomStore = driver.findElement(By.cssSelector("[title=\"" + sdata.get(randomStoreIndex) + "\"]"));
        randomStore.click();

        // burada yorum sayısına bak


        WebElement storeCommentButton = driver.findElement(By.cssSelector("[title=\"Mağaza Yorumları\"]"));
        storeCommentButton.click();

        try {
            WebElement storeCommentCount = driver.findElement(By.cssSelector(".feedbackContent .selectedReview"));
            String storeComment = storeCommentCount.getText();
            String[] comment = storeComment.split(" ");
            int feedback = Integer.parseInt(comment[0]);
            int commentCount = Integer.parseInt(comment[2]);

            System.out.println("Bu mağazaya " + feedback + " kadar değerlendirme ve " + commentCount + " kadar yorum yapılmıştır");

        }
        catch(NoSuchElementException e) {
            System.out.println("Bu mağazaya yorum yapılmamıştır");
        }

        driver.close();

        System.out.println("store.xls file written successfully...");
    }


}
