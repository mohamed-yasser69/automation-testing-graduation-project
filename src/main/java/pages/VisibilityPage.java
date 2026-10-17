package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class VisibilityPage extends BasePage {

    private final By toggleButton = By.id("toggleMessage");
    private final By message = By.id("message");
    private final By secretField = By.id("secretField");
    private final By loadBannerButton = By.id("loadBanner");
    private final By removeBannerButton = By.id("removeBanner");
    private final By banner = By.id("promoBanner");

    public VisibilityPage(WebDriver driver) {
        super(driver);
    }

    // ---------- 1. A message that can be shown and hidden ----------

    public void clickToggle() {
        driver.findElement(toggleButton).click();
    }

    public boolean isMessageDisplayed() {
        // TODO 1: return true if the message is visible on the screen.
        return true;
    }

    public void waitForMessageVisible() {
        // TODO 2: wait until the message is visible.
    }

    public void waitForMessageHidden() {
        // TODO 3: wait until the message is hidden.
    }

    // ---------- 2. A field that is in the HTML but never shown ----------

    public boolean isSecretFieldInPage() {
        // TODO 4: return true if the element exists in the HTML (visible or not).
        return false;
    }

    public boolean isSecretFieldDisplayed() {
        // TODO 5: return true if the element is visible on the screen.
        return true;
    }

    // ---------- 3. A banner that is added to the page later ----------

    public void clickLoadBanner() {
        driver.findElement(loadBannerButton).click();
    }

    public void clickRemoveBanner() {
        driver.findElement(removeBannerButton).click();
    }

    public boolean isBannerInPage() {
        // TODO 6: return true if the banner exists in the HTML.
        //         It must NOT throw an exception when the banner is missing.
        return true;
    }

    public boolean isBannerDisplayed() {
        // TODO 7: return true if the banner is visible.
        return false;
    }

    public void waitForBanner() {
        // TODO 8: wait until the banner is visible.
    }

    public String getBannerText() {
        // TODO 9: return the text of the banner.
        return "";
    }
}