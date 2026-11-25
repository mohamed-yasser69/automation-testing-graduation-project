package pages.orangehrm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parent of every OrangeHRM page object (extends the course BasePage). Holds the driver + explicit wait and the small set of
 * wait-wrapped actions all pages share. No Thread.sleep anywhere - only explicit waits.
 */
public abstract class HrmBasePage extends BasePage {

    private static final By LOADER = By.cssSelector(".oxd-loading-spinner, .oxd-form-loader");

    protected final WebDriverWait wait;
    protected final Logger log = LogManager.getLogger(getClass());

    protected HrmBasePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    // ---------- actions ----------

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    protected String textOf(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    protected String valueOf(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("value");
    }

    protected void scrollIntoView(By locator) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    // ---------- state checks (never throw - return false on timeout) ----------

    public boolean isDisplayed(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean urlContains(String fragment) {
        try {
            return wait.until(ExpectedConditions.urlContains(fragment));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    protected List<String> textsOf(By locator) {
        return driver.findElements(locator).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    /**
     * OrangeHRM shows a spinner while lists/forms load. Wait for it to appear (briefly, it may
     * already be gone) and then to disappear, so we never read stale data.
     */
    protected void waitForLoader() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.presenceOfElementLocated(LOADER));
        } catch (TimeoutException ignored) {
            // spinner was too quick to catch - fine
        }
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADER));
    }

    /** Top tab bar inside a module, e.g. PIM > "Employee List" / "Add Employee". */
    protected void clickTopNavTab(String tabName) {
        click(By.xpath("//nav[@aria-label='Topbar Menu']//a[normalize-space()='" + tabName + "']"));
    }
}
