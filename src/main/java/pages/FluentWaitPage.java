package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class FluentWaitPage extends BasePage {

    private final By startDownloadButton = By.id("startDownload");
    private final By progressText = By.id("progressText");
    private final By showLaterButton = By.id("showLater");
    private final By laterMessage = By.id("laterMessage");
    private final By startCounterButton = By.id("startCounter");
    private final By counter = By.id("counter");
    private final By enableLateButton = By.id("enableLate");
    private final By lateButton = By.id("lateButton");
    private final By lateResult = By.id("lateResult");
    private final By neverButton = By.id("neverButton");
    private final By neverElement = By.id("neverAppears");

    public FluentWaitPage(WebDriver driver) {
        super(driver);
    }

    // One FluentWait recipe that every method below reuses
    private FluentWait<WebDriver> fluentWait(int timeoutSeconds, int pollMillis, String message) {
        // TODO 1: build and return a FluentWait<WebDriver> with:
        //         - the timeout (seconds)       - the polling interval (milliseconds)
        //         - ignoring NoSuchElementException   - the custom message
        return null;
    }

    // ---------- 1. Download progress ----------

    public void clickStartDownload() {
        driver.findElement(startDownloadButton).click();
    }

    public void waitForDownloadComplete() {
        // TODO 2: wait (10 s, every 500 ms) until the progress text is exactly "Progress: 100%".
        //         The condition is a lambda that returns true or false.
    }

    public String getProgressText() {
        return driver.findElement(progressText).getText();
    }

    // ---------- 2. A message that appears later ----------

    public void clickShowLater() {
        driver.findElement(showLaterButton).click();
    }

    public String waitForLaterMessage() {
        // TODO 3: wait until the element exists, then return its text.
        //         The lambda returns the WebElement. NoSuchElementException is ignored, so it retries.
        return "";
    }

    // ---------- 3. Counter ----------

    public void clickStartCounter() {
        driver.findElement(startCounterButton).click();
    }

    public void waitForCounterAtLeast(int number) {
        // TODO 4: wait until the number in the counter is greater than or equal to "number".
        //         Hint: Integer.parseInt(text) turns the text into a number.
    }

    public int getCounter() {
        return Integer.parseInt(driver.findElement(counter).getText());
    }

    // ---------- 4. Button that is disabled at first ----------

    public void clickPrepareButton() {
        driver.findElement(enableLateButton).click();
    }

    public void clickSubmitWhenReady() {
        // TODO 5: wait until the Submit button is enabled, then click it.
        //         The lambda returns the element when it is enabled, and null when it is not.
    }

    public String getLateResult() {
        return driver.findElement(lateResult).getText();
    }

    // ---------- 5. Something that never becomes visible ----------

    public void clickNeverButton() {
        driver.findElement(neverButton).click();
    }

    public void waitForNeverElement() {
        // TODO 6: wait only 3 seconds until #neverAppears is visible.
        //         It never will be, so this method must end with a TimeoutException.
        //         Use the message "The element with id 'neverAppears' was not visible".
    }
}