package tests.orangehrm;

import base.HrmBaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;
import pages.orangehrm.AddEmployeePage;
import pages.orangehrm.DashboardPage;
import pages.orangehrm.EmployeeListPage;
import pages.orangehrm.PersonalDetailsPage;
import utils.DataProviders;
import utils.TestData.NameData;
import utils.TestData.NewEmployee;

import java.util.concurrent.ThreadLocalRandom;

@Epic("OrangeHRM")
@Feature("PIM - Employees")
public class PimEmployeeTest extends HrmBaseTest {

    @Test(description = "TC4 - Search for an employee", dataProvider = "existingEmployees", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Searching a known name returns that employee in the results table")
    public void searchExistingEmployee(NameData employee) {
        EmployeeListPage employees = loginAsValidUser().goToEmployeeList();
        employees.searchByName(employee.name());

        Assert.assertTrue(employees.resultsContain(employee.name()),
                "Results table should contain '" + employee.name() + "' but rows were " + employees.getResultRows());
    }

    @Test(description = "TC5 - Search for a non-existing employee", dataProvider = "nonExistingEmployees", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Searching an unknown name shows 'No Records Found'")
    public void searchNonExistingEmployee(NameData employee) {
        EmployeeListPage employees = loginAsValidUser().goToEmployeeList();
        employees.searchByName(employee.name());

        Assert.assertTrue(employees.isNoRecordsFoundDisplayed(), "'No Records Found' message should be displayed");
        Assert.assertTrue(employees.getResultRows().isEmpty(), "Results table should be empty");
    }

    @Test(description = "TC6 - Open Add Employee page")
    @Severity(SeverityLevel.NORMAL)
    @Description("PIM > Add Employee shows the First Name and Last Name fields")
    public void openAddEmployeePage() {
        AddEmployeePage addEmployee = loginAsValidUser().goToAddEmployee();

        SoftAssert soft = new SoftAssert();
        soft.assertTrue(addEmployee.isOnAddEmployeeUrl(), "URL should contain /pim/addEmployee but was " + addEmployee.currentUrl());
        soft.assertTrue(addEmployee.isFirstNameFieldDisplayed(), "First Name field should be displayed");
        soft.assertTrue(addEmployee.isLastNameFieldDisplayed(), "Last Name field should be displayed");
        soft.assertAll();
    }

    @Test(description = "TC7 - Add employee with an empty required field", dataProvider = "newEmployees", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Last name only + Save shows 'Required' under First Name")
    public void addEmployeeWithoutFirstName(NewEmployee employee) {
        AddEmployeePage addEmployee = loginAsValidUser().goToAddEmployee();
        addEmployee.enterLastName(employee.lastName()).clickSave();

        Assert.assertEquals(addEmployee.getFirstNameError(), "Required", "First Name validation message");
    }

    @Test(description = "TC8 - End-to-end: add a new employee and find it", dataProvider = "newEmployees", dataProviderClass = DataProviders.class)
    @Severity(SeverityLevel.BLOCKER)
    @Description("Create an employee, see Personal Details, then find the employee in the list")
    public void addEmployeeEndToEnd(NewEmployee template) {
        // unique last name so the search can only match the employee created by THIS run
        String firstName = template.firstName();
        String lastName = template.lastName() + randomLetters(6);

        DashboardPage dashboard = loginAsValidUser();
        AddEmployeePage addEmployee = dashboard.goToAddEmployee();
        addEmployee.enterFirstName(firstName).enterLastName(lastName);
        PersonalDetailsPage details = addEmployee.saveAndOpenDetails();

        Assert.assertTrue(details.isOpen(), "Personal Details page should open, URL was " + details.currentUrl());
        Assert.assertEquals(details.getFirstName(), firstName, "Saved first name");
        Assert.assertEquals(details.getLastName(), lastName, "Saved last name");

        EmployeeListPage employees = details.goToEmployeeList();
        employees.searchByName(firstName + " " + lastName);

        Assert.assertTrue(employees.resultsContain(firstName + " " + lastName),
                "New employee should be in the results, rows were " + employees.getResultRows());
    }

    private static String randomLetters(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + ThreadLocalRandom.current().nextInt(26)));
        }
        return sb.toString();
    }
}
