package pages.orangehrm;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Admin > User Management > Users, and its "Add User" form. */
public class AdminUsersPage extends HrmBasePage {

    private final By addButton = By.xpath("//button[normalize-space()='Add']");

    public AdminUsersPage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.urlContains("/admin/viewSystemUsers"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
        waitForLoader();
    }

    @Step("Click Add")
    public AdminUsersPage clickAdd() {
        click(addButton);
        wait.until(ExpectedConditions.urlContains("/admin/saveSystemUser"));
        return this;
    }

    /** Works for both the filter form and the Add User form - call it after clickAdd(). */
    @Step("Check the form field '{label}' is displayed")
    public boolean isFieldDisplayed(String label) {
        return isDisplayed(By.xpath("//label[normalize-space()='" + label + "']"));
    }
}
