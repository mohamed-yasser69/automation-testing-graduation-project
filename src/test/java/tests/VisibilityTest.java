package tests;

import base.BaseTest;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.VisibilityPage;

public class VisibilityTest extends BaseTest {

    private VisibilityPage page;

    @BeforeMethod
    public void openVisibilityPage() {
        openLab("lab4");
        page = new VisibilityPage(driver);
    }

    @Test
    public void messageIsHiddenAtStart() {
        Assert.assertFalse(page.isMessageDisplayed(), "The message should be hidden when the page opens");
    }

    @Test
    public void toggleShowsAndHidesTheMessage() {
        page.clickToggle();
        page.waitForMessageVisible();
        Assert.assertTrue(page.isMessageDisplayed(), "The message should be visible after the first click");

        page.clickToggle();
        page.waitForMessageHidden();
        Assert.assertFalse(page.isMessageDisplayed(), "The message should be hidden after the second click");
    }

    @Test
    public void secretFieldIsInThePageButNotDisplayed() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(page.isSecretFieldInPage(), "The secret field should exist in the HTML");
        soft.assertFalse(page.isSecretFieldDisplayed(), "The secret field should not be visible");
        soft.assertAll();
    }

    @Test
    public void bannerIsNotInThePageUntilItIsLoaded() {
        Assert.assertFalse(page.isBannerInPage(), "The banner should not exist before we load it");

        page.clickLoadBanner();
        page.waitForBanner();

        Assert.assertTrue(page.isBannerDisplayed(), "The banner should be visible after loading");
        Assert.assertEquals(page.getBannerText(), "Special offer: 20% off today!", "Banner text");
    }

    @Test
    public void removedBannerIsNoLongerInThePage() {
        page.clickLoadBanner();
        page.waitForBanner();

        page.clickRemoveBanner();

        Assert.assertFalse(page.isBannerInPage(), "The banner should be gone after Remove");
    }

    // isDisplayed() needs the element to exist first. If it does not, findElement throws.
    @Test(expectedExceptions = NoSuchElementException.class)
    public void isDisplayedOnAMissingElementThrows() {
        page.isBannerDisplayed();
    }
}