package tests.orangehrm;

import base.HrmBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.orangehrm.AdminUsersPage;

@Epic("OrangeHRM")
@Feature("Admin")
public class AdminTest extends HrmBaseTest {

    @Test(description = "TC9 - Verify Admin > Add User page")
    @Severity(SeverityLevel.NORMAL)
    @Description("The Add User form has User Role, Employee Name, Username and Password fields")
    public void addUserFormHasRequiredFields() {
        AdminUsersPage users = loginAsValidUser().goToAdminUsers();
        users.clickAdd();

        SoftAssert soft = new SoftAssert();
        for (String field : new String[]{"User Role", "Employee Name", "Username", "Password"}) {
            soft.assertTrue(users.isFieldDisplayed(field), "Add User form should contain field: " + field);
        }
        soft.assertAll();
    }
}
