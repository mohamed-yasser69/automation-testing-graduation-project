package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

/**
 * One WebDriver per thread. Because testng.xml runs each <test> on its own thread, every
 * thread stores its own browser in this ThreadLocal and never touches another thread's.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void initDriver() {
        boolean headless = ConfigReader.getBoolean("headless");
        WebDriver driver;
        switch (ConfigReader.get("browser").toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions o = new FirefoxOptions();
                if (headless) o.addArguments("-headless");
                driver = new FirefoxDriver(o);
            }
            case "edge" -> {
                EdgeOptions o = new EdgeOptions();
                if (headless) o.addArguments("--headless=new");
                o.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(o);
            }
            default -> {
                ChromeOptions o = new ChromeOptions();
                if (headless) o.addArguments("--headless=new");
                o.addArguments("--window-size=1920,1080", "--disable-notifications", "--remote-allow-origins=*");
                driver = new ChromeDriver(o);
            }
        }
        if (!headless) {
            driver.manage().window().maximize();
        }
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove(); // avoid leaking the reference on pooled threads
        }
    }
}
