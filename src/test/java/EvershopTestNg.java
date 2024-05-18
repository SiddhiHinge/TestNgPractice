

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class EvershopTestNg {
    ChromeOptions chrome = new ChromeOptions();
    WebDriver webDriver;
    WebDriverWait wait;

    @BeforeClass
    public void basicSetup() throws MalformedURLException {
        chrome = new ChromeOptions();
        webDriver = new RemoteWebDriver(new URL("http://localhost:4444"),chrome);
        wait = new WebDriverWait(webDriver,Duration.ofSeconds(5));
    }

    @Parameters({"username","password"})
    @Test
    public void login(String username, String password) {
        webDriver.get("https://demo.evershop.io/account/login");
        webDriver.findElement(By.name("email")).sendKeys(username);
        webDriver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        webDriver.findElement(By.xpath("//button[@type='submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'button')]/span[text()='Shop women']")));
    }

    @Parameters({"productName"})
    @Test
    public void findProductPage(String productName){
        String productXpath = "//a/span[text()='"+productName+"']";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productXpath)));
        webDriver.findElement(By.xpath(productXpath)).click();
    }

    @Parameters({"size","colour","qty"})
    @Test
    public void addItemFromPDP(String size, String colour, String qty) throws InterruptedException {
        String qtyXpath = "//input[@name='qty']";
        String sizeXpath = "//ul[contains(@class,'variant-option-list')]//a[text()='"+size+"']";
        String colourXpath = "//ul[contains(@class,'variant-option-list')]//a[text()='"+colour+"']";
        webDriver.findElement(By.xpath(qtyXpath)).clear();
        webDriver.findElement(By.xpath(qtyXpath)).sendKeys(qty);
        Thread.sleep(3000);
        webDriver.findElement(By.xpath(sizeXpath)).click();
        Thread.sleep(3000);
        webDriver.findElement(By.xpath(colourXpath)).click();
        Thread.sleep(3000);
        webDriver.findElement(By.xpath("//button/span[text()='ADD TO CART']")).click();
    }

    @Parameters({"button"})
    @Test
    public void addItemFromShop(String button){
        webDriver.findElement(By.xpath("//a/span[text()='"+button+"']")).click();
    }

    @Test
    public void logout(){
        webDriver.findElement(By.xpath("//a[@href='/account']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Logout']")));
        webDriver.findElement(By.xpath("//a[text()='Logout']")).click();
    }
}
