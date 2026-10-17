package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void startBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Opens one lab of the practice site, for example openLab("employees")
    protected void openLab(String lab) {
        Path site = Paths.get("src/test/resources/practice-site.html").toAbsolutePath();
        driver.get(site.toUri() + "?lab=" + lab);
    }
}