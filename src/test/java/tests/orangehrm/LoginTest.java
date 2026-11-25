package tests.orangehrm;

import base.HrmBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.orangehrm.DashboardPage;
import pages.orangehrm.LoginPage;
import utils.DataProviders;
import utils.TestData.Credentials;
import utils.TestData.InvalidLogin;

import java.util.List;

@Epic("OrangeHRM")
@Feature("Login")
public class LoginTest extends HrmBaseTest {

    @Test(description = "TC1 - Login with valid credentials", dataProvider = "validUser", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Valid credentials land on the dashboard")
    public void loginWithValidCredentials(Credentials user) {
        DashboardPage dashboard = new LoginPage(driver).open().loginAs(user.username(), user.password());

        Assert.assertTrue(dashboard.isOnDashboardUrl(), "URL should contain /dashboard/index but was " + dashboard.currentUrl());
        Assert.assertEquals(dashboard.getHeaderText(), "Dashboard", "Dashboard header text");
    }

    @Test(description = "TC2 - Login with invalid credentials", dataProvider = "invalidLogins", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Wrong username/password shows 'Invalid credentials'")
    public void loginWithInvalidCredentials(InvalidLogin login) {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.loginAs(login.username(), login.password());

        Assert.assertEquals(loginPage.getErrorMessage(), login.expectedError(), "Login error message");
    }

    @Test(description = "TC3 - Login with empty fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Submitting empty fields shows 'Required' under both fields")
    public void loginWithEmptyFields() {
        LoginPage loginPage = new LoginPage(driver).open();
        loginPage.clickLogin();

        List<String> messages = loginPage.getRequiredMessages();
        Assert.assertEquals(messages.size(), 2, "Expected a validation message under both fields, got " + messages);
        Assert.assertTrue(messages.stream().allMatch(m -> m.equals("Required")), "All messages should be 'Required': " + messages);
    }
}
