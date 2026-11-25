package listeners;

import base.DriverManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/** Logs test start/pass (INFO) and failure (ERROR) and attaches a screenshot to the Allure report on failure. */
public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("===== START: {} {}", result.getName(), paramsOf(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("===== PASSED: {} {}", result.getName(), paramsOf(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("===== FAILED: {} {} - {}", result.getName(), paramsOf(result), result.getThrowable().toString());
        WebDriver driver = DriverManager.getDriver(); // same thread, browser is still open here
        if (driver instanceof TakesScreenshot shot) {
            try {
                byte[] png = shot.getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on failure", "image/png", new ByteArrayInputStream(png), "png");
            } catch (Exception e) {
                LOG.error("Could not take screenshot: {}", e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TestNG reports a test that is about to be retried as "skipped"
        LOG.warn("===== SKIPPED/RETRY: {} {}", result.getName(), paramsOf(result));
    }

    private static String paramsOf(ITestResult result) {
        return result.getParameters().length == 0 ? "" : java.util.Arrays.toString(result.getParameters());
    }
}
