package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Employee "Personal Details" page that opens right after a successful save. */
public class PersonalDetailsPage extends HrmBasePage {

    private final By heading = By.xpath("//h6[normalize-space()='Personal Details']");
    private final By firstName = By.name("firstName");
    private final By lastName = By.name("lastName");

    public PersonalDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check the Personal Details page is open")
    public boolean isOpen() {
        return urlContains("/pim/viewPersonalDetails/empNumber/") && isDisplayed(heading);
    }

    /** The inputs are filled by an async call, so wait until the value is non-empty. */
    @Step("Read the saved first name")
    public String getFirstName() {
        wait.until(d -> !valueOf(firstName).isEmpty());
        return valueOf(firstName);
    }

    @Step("Read the saved last name")
    public String getLastName() {
        wait.until(d -> !valueOf(lastName).isEmpty());
        return valueOf(lastName);
    }

    @Step("Go back to the Employee List")
    public EmployeeListPage goToEmployeeList() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class,'oxd-main-menu-item--name')][normalize-space()='PIM']"))).click();
        EmployeeListPage page = new EmployeeListPage(driver);
        page.waitUntilLoaded();
        return page;
    }
}
