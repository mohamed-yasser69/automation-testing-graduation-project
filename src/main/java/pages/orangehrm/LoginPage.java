package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ConfigReader;

import java.util.List;

public class LoginPage extends HrmBasePage {

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By alertMessage = By.cssSelector(".oxd-alert-content-text");
    private final By fieldErrors = By.cssSelector("span.oxd-input-field-error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the login page")
    public LoginPage open() {
        log.info("Opening login page");
        driver.get(ConfigReader.baseUrl() + "auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        return this;
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        log.info("Entering username '{}'", username);
        type(usernameInput, username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        type(passwordInput, password);
        return this;
    }

    @Step("Click the Login button")
    public void clickLogin() {
        log.info("Clicking Login");
        click(loginButton);
    }

    @Step("Log in as '{username}'")
    public DashboardPage loginAs(String username, String password) {
        enterUsername(username).enterPassword(password).clickLogin();
        return new DashboardPage(driver);
    }

    @Step("Read the login error message")
    public String getErrorMessage() {
        return textOf(alertMessage);
    }

    @Step("Read the 'Required' validation messages")
    public List<String> getRequiredMessages() {
        // Wait until BOTH field errors have rendered, not just the first
        wait.until(d -> d.findElements(fieldErrors).size() >= 2);
        return textsOf(fieldErrors);
    }
}
