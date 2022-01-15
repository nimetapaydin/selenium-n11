package n11;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class AllStore {

    @Test

    public static void main(String[] args) throws IOException {
        //Driver tanımlama ve driver'ın lokasyonunu verme
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nyzk\\Desktop\\n11\\selenium-n11\\drivers\\chromedriver.exe");

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

        By mySelector = By.xpath("//*[@id=\"contentSellerList\"]/div/div[2]/div/div[2]/div[4]/div[2]/ul");
        List<WebElement> myElements = driver.findElements(mySelector);

        String storetext = null;
        for (WebElement e : myElements) {
            System.out.println(e.getText());
            storetext = e.getText();

            System.out.println(storetext);
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Store Info");


        Object storedata[][] = {{"A","B","C","D","E","F","G","H","I","İ","J","K","L","M","N","O","Ö","P","R","S","Ş","T","U","Ü","X","V","W","Y","Z","1","2","3","4","5","6","7","8","9","0"},
                                {textallstores}

        };

        //Using for loop
        /**/
        int rows = storedata.length;

        int cols = storedata[0].length;

        System.out.println(rows); //4
        System.out.println(cols); //3

        for (int r = 0; r < rows; r++)  //0
        {
            XSSFRow row = sheet.createRow(r);

            for (int c = 0; c < cols; c++) {
                XSSFCell cell = row.createCell(c); //0
                Object value = storedata[r][c];

                if (value instanceof String)
                    cell.setCellValue((String) value);
                if (value instanceof Integer)
                    cell.setCellValue((Integer) value);
                if (value instanceof Boolean)
                    cell.setCellValue((Boolean) value);

            }
        }
        //*/

        /// using for...each loop
        int rowCount = 0;

        for (Object str[] : storedata) {
            XSSFRow row = sheet.createRow(rowCount++);
            int columnCount = 0;
            for (Object value : str) {
                XSSFCell cell = row.createCell(columnCount++);

                if (value instanceof String)
                    cell.setCellValue((String) value);
                if (value instanceof Integer)
                    cell.setCellValue((Integer) value);
                if (value instanceof Boolean)
                    cell.setCellValue((Boolean) value);

            }
        }


        String filePath = ".\\datafiles\\store.xlsx";
        FileOutputStream outstream = new FileOutputStream(filePath);
        workbook.write(outstream);

        outstream.close();

        System.out.println("store.xls file written successfully...");
    }






}
