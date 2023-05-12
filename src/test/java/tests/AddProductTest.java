package tests;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;


public class AddProductTest {

    WebDriver driver;



    @BeforeTest
    public void createDriver(){
        System.setProperty("webdriver.chrome.driver", "src//test//resources//drivers//chrome//" + getChromeDriverForCurrentOS() + "//chromedriver");

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--window-size=1920x1080");
        driver = new ChromeDriver(options);

    }




    private static String getChromeDriverForCurrentOS(){
        return System.getProperty("os.name").contains("Mac") ? "arm64" : "linux64";
    }


    @Test
    public void addYodaToBasket(){


        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        driver.manage().deleteAllCookies();

        driver.get("https://www.lego.com/en-us");

        driver.findElement(By.xpath("//button[text()='Continue']")).click();
        driver.findElement(By.xpath("//button[@data-test='cookie-accept-all']")).click();
        driver.findElement(By.xpath("//button[@data-test='search-input-button']")).click();
        driver.findElement(By.xpath("//input[@data-test='search-input']")).sendKeys("Yoda"+ Keys.ENTER);
        driver.findElement(By.xpath("(//button[@data-test='add-to-cart-skroll-cta'])[1]")).click();
        String productName = driver.findElement(By.xpath("(//button[@data-test='add-to-cart-skroll-cta'])[1]//..//..//a[@data-test='product-leaf-title-link']/span")).getText();
        driver.findElement(By.xpath("//a[text()='View My Bag']")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//h1")).getText().contains("My Bag"));
        Assert.assertEquals(driver.findElement(By.xpath("//h3/span")).getText(), productName);

    }

    @AfterMethod(alwaysRun = true)
    public void teardown(){
        driver.quit();
    }
}
