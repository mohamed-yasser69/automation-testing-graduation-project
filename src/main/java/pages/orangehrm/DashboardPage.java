package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/** The landing page after login. Also owns the left sidebar, which is on every logged-in page. */
public class DashboardPage extends HrmBasePage {

    private final By header = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By sidebarItems = By.cssSelector("ul.oxd-main-menu span.oxd-main-menu-item--name");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for the dashboard URL")
    public boolean isOnDashboardUrl() {
        return urlContains("/dashboard/index");
    }

    @Step("Read the dashboard header")
    public String getHeaderText() {
        return textOf(header);
    }

    @Step("Read the sidebar menu items")
    public List<String> getSidebarMenuItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sidebarItems));
        return textsOf(sidebarItems);
    }

    @Step("Click sidebar menu: {menuName}")
    public void clickSidebarMenu(String menuName) {
        log.info("Clicking sidebar menu '{}'", menuName);
        click(By.xpath("//span[contains(@class,'oxd-main-menu-item--name')][normalize-space()='" + menuName + "']"));
    }

    @Step("Open PIM > Employee List")
    public EmployeeListPage goToEmployeeList() {
        clickSidebarMenu("PIM");
        EmployeeListPage page = new EmployeeListPage(driver);
        page.waitUntilLoaded();
        return page;
    }

    @Step("Open PIM > Add Employee")
    public AddEmployeePage goToAddEmployee() {
        clickSidebarMenu("PIM");
        clickTopNavTab("Add Employee");
        return new AddEmployeePage(driver).waitUntilLoaded();
    }

    @Step("Open Admin > User Management > Users")
    public AdminUsersPage goToAdminUsers() {
        clickSidebarMenu("Admin");
        AdminUsersPage page = new AdminUsersPage(driver);
        page.waitUntilLoaded();
        return page;
    }
}
