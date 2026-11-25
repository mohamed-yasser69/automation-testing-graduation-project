package base;

import pages.orangehrm.DashboardPage;
import pages.orangehrm.LoginPage;
import utils.ConfigReader;
import utils.JsonUtils;
import utils.TestData.Credentials;

/**
 * Base class for the OrangeHRM tests. The browser itself is started/stopped by BaseTest
 * (via the ThreadLocal DriverManager); this class only adds OrangeHRM helpers.
 */
public abstract class HrmBaseTest extends BaseTest {

    /** Opens the login page and signs in with the valid user from testdata.json. */
    protected DashboardPage loginAsValidUser() {
        Credentials user = JsonUtils.readList("validUser", Credentials.class).get(0);
        DashboardPage dashboard = new LoginPage(driver).open().loginAs(user.username(), user.password());
        dashboard.isOnDashboardUrl(); // wait until we are really logged in
        return dashboard;
    }

    protected String baseUrl() {
        return ConfigReader.baseUrl();
    }
}
