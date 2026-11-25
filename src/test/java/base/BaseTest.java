package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {

    protected WebDriver driver;

    // The browser now comes from DriverManager, which keeps one WebDriver per thread
    // (ThreadLocal<WebDriver>) so tests can run in parallel. Browser / headless are read
    // from config.properties.
    @BeforeMethod
    public void startBrowser() {
        DriverManager.initDriver();
        driver = DriverManager.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser() {
        DriverManager.quitDriver();
        driver = null;
    }

    // Opens one lab of the practice site, for example openLab("employees")
    protected void openLab(String lab) {
        Path site = Paths.get("src/test/resources/practice-site.html").toAbsolutePath();
        driver.get(site.toUri() + "?lab=" + lab);
    }
}
