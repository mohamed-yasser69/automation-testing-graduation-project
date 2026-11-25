package tests.orangehrm;

import base.HrmBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.orangehrm.FooterComponent;
import pages.orangehrm.LoginPage;
import utils.DataProviders;

import java.util.List;

@Epic("OrangeHRM")
@Feature("UI")
public class UiTest extends HrmBaseTest {

    @Test(description = "TC10 - Verify the OrangeHRM footer/branding link")
    @Severity(SeverityLevel.MINOR)
    @Description("Footer text contains 'OrangeHRM, Inc' and its link opens orangehrm.com in a new tab")
    public void footerBrandingLink() {
        new LoginPage(driver).open(); // the footer is rendered on the login page
        FooterComponent footer = new FooterComponent(driver).scrollToFooter();

        Assert.assertTrue(footer.getFooterText().contains("OrangeHRM, Inc"),
                "Footer text should contain 'OrangeHRM, Inc' but was: " + footer.getFooterText());

        String newTabUrl = footer.clickBrandLinkAndGetNewTabUrl();
        Assert.assertTrue(newTabUrl.contains("orangehrm.com"), "New tab URL should contain orangehrm.com but was " + newTabUrl);
    }

    @Test(description = "TC11 - Verify sidebar menu UI", dataProvider = "sidebarMenus", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("The sidebar contains Admin, PIM, Leave, Time, Recruitment, My Info, Performance, Dashboard, Directory")
    public void sidebarMenuItems(List<String> expectedMenus) {
        List<String> actual = loginAsValidUser().getSidebarMenuItems();

        Assert.assertTrue(actual.containsAll(expectedMenus),
                "Sidebar should contain " + expectedMenus + " but was " + actual);
    }
}
