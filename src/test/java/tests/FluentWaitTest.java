package tests;

import base.BaseTest;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.FluentWaitPage;

public class FluentWaitTest extends BaseTest {

    private FluentWaitPage page;

    @BeforeMethod
    public void openFluentWaitPage() {
        openLab("lab5");
        page = new FluentWaitPage(driver);
    }

    @DataProvider(name = "counterTargets")
    public Object[][] counterTargets() {
        return new Object[][]{{2}, {4}};
    }

    @Test
    public void downloadReaches100Percent() {
        page.clickStartDownload();
        page.waitForDownloadComplete();

        Assert.assertEquals(page.getProgressText(), "Progress: 100%", "Final progress text");
    }

    @Test
    public void laterMessageAppears() {
        page.clickShowLater();

        Assert.assertEquals(page.waitForLaterMessage(), "I am here at last!", "Text of the late message");
    }

    @Test(dataProvider = "counterTargets")
    public void counterReachesTheTarget(int target) {
        page.clickStartCounter();
        page.waitForCounterAtLeast(target);

        Assert.assertTrue(page.getCounter() >= target,
                "Counter should be at least " + target + " but was " + page.getCounter());
    }

    @Test
    public void submitButtonCanBeClickedOnceEnabled() {
        page.clickPrepareButton();
        page.clickSubmitWhenReady();

        Assert.assertEquals(page.getLateResult(), "Submitted!", "Result after clicking Submit");
    }

    @Test
    public void timeoutShowsOurMessage() {
        page.clickNeverButton();
        try {
            page.waitForNeverElement();
            Assert.fail("The wait should have ended with a TimeoutException");
        } catch (TimeoutException e) {
            Assert.assertTrue(e.getMessage().contains("The element with id 'neverAppears' was not visible"),
                    "The error should show our own message, but it was: " + e.getMessage());
        }
    }
}