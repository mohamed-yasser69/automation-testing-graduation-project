package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EmployeesPage;

import java.util.List;

public class EmployeesTest extends BaseTest {

    private EmployeesPage employees;

    @BeforeMethod
    public void openEmployeesPage() {
        openLab("lab1");
        employees = new EmployeesPage(driver);
    }

    // ---------- Test data: each row holds a LIST of names ----------

    @DataProvider(name = "namesToSelect")
    public Object[][] namesToSelect() {
        return new Object[][]{
                {List.of("Sara Ahmed")},
                {List.of("Sara Ahmed", "Omar Khaled")},
                {List.of("Laila Hassan", "Hana Samir", "Nour Mostafa")}
        };
    }

    // Each row: name, expected role, expected status
    @DataProvider(name = "employeeDetails")
    public Object[][] employeeDetails() {
        return new Object[][]{
                {"Sara Ahmed", "Tester", "Active"},
                {"Youssef Ali", "Test Lead", "Active"},
                {"Laila Hassan", "Recruiter", "Inactive"},
                {"Nour Mostafa", "Developer", "Inactive"}
        };
    }

    @DataProvider(name = "namesToEdit")
    public Object[][] namesToEdit() {
        return new Object[][]{{"Omar Khaled"}, {"Hana Samir"}};
    }

    @DataProvider(name = "activeEmployees")
    public Object[][] activeEmployees() {
        return new Object[][]{
                {List.of("Sara Ahmed", "Omar Khaled", "Youssef Ali", "Hana Samir")}
        };
    }

    // ---------- Tests ----------

    @Test(dataProvider = "namesToSelect")
    public void selectEmployeesByName(List<String> names) {
        employees.selectEmployees(names);

        Assert.assertEquals(employees.getSelectedCountText(), "Selected: " + names.size(),
                "The counter should match the number of ticked employees");
        for (String name : names) {
            Assert.assertTrue(employees.isSelected(name), name + " should be ticked");
        }
    }

    @Test
    public void onlyChosenEmployeeIsTicked() {
        employees.selectEmployee("Youssef Ali");

        Assert.assertTrue(employees.isSelected("Youssef Ali"), "Youssef Ali should be ticked");
        Assert.assertFalse(employees.isSelected("Sara Ahmed"), "Sara Ahmed should NOT be ticked");
    }

    @Test(dataProvider = "employeeDetails")
    public void rowShowsRoleAndStatus(String name, String role, String status) {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(employees.getRole(name), role, "Role of " + name);
        soft.assertEquals(employees.getStatus(name), status, "Status of " + name);
        soft.assertAll();
    }

    @Test(dataProvider = "namesToEdit")
    public void editButtonOpensTheRightEmployee(String name) {
        employees.clickEdit(name);

        Assert.assertEquals(employees.getEditMessage(), "Editing: " + name,
                "The message should show the employee whose Edit button we clicked");
    }

    @Test(dataProvider = "activeEmployees")
    public void everyEmployeeInTheListIsActive(List<String> names) {
        SoftAssert soft = new SoftAssert();
        for (String name : names) {
            soft.assertEquals(employees.getStatus(name), "Active", "Status of " + name);
        }
        soft.assertAll();
    }

    @Test
    public void unknownEmployeeIsNotListed() {
        Assert.assertTrue(employees.isEmployeeListed("Sara Ahmed"), "Sara Ahmed should be in the table");
        Assert.assertFalse(employees.isEmployeeListed("Mona Zaki"), "Mona Zaki should not be in the table");
    }
}