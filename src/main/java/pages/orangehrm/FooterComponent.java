package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Set;

/** The "OrangeHRM, Inc" branding footer (shown on the login page). */
public class FooterComponent extends HrmBasePage {

    private final By brandLink = By.xpath("//a[normalize-space()='OrangeHRM, Inc']");
    private final By footerText = By.xpath("//a[normalize-space()='OrangeHRM, Inc']/parent::*");

    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    @Step("Scroll to the footer")
    public FooterComponent scrollToFooter() {
        scrollIntoView(brandLink);
        return this;
    }

    @Step("Read the footer text")
    public String getFooterText() {
        return textOf(footerText);
    }

    /** Clicks the link, switches to the tab it opens, and returns that tab's URL. */
    @Step("Click the 'OrangeHRM, Inc' link and read the new tab's URL")
    public String clickBrandLinkAndGetNewTabUrl() {
        String original = driver.getWindowHandle();
        click(brandLink);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> handles = driver.getWindowHandles();
        handles.remove(original);
        driver.switchTo().window(handles.iterator().next());
        // the new tab starts as about:blank - wait until it has really navigated
        wait.until(ExpectedConditions.urlContains("orangehrm"));
        String url = driver.getCurrentUrl();

        driver.close();
        driver.switchTo().window(original);
        return url;
    }
}
