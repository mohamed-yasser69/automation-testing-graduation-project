# Automation-Practice – OrangeHRM graduation project (Selenium + TestNG + Allure)

Your original practice labs are untouched (`pages/*Page.java`, `tests/*Test.java`, `practice-site.html`).
The graduation project lives next to them in its own sub-packages:

```
src/main/java
  pages/BasePage.java            (yours)  <- HrmBasePage extends it
  pages/orangehrm/               HrmBasePage, LoginPage, DashboardPage, EmployeeListPage,
                                 AddEmployeePage, PersonalDetailsPage, AdminUsersPage, FooterComponent
  utils/ConfigReader.java        reads config.properties (java.util.Properties)
src/test/java
  base/BaseTest.java             (yours, now uses DriverManager)
  base/DriverManager.java        ThreadLocal<WebDriver>
  base/HrmBaseTest.java          login helper for the OrangeHRM tests
  tests/orangehrm/               LoginTest (TC1-3), PimEmployeeTest (TC4-8), AdminTest (TC9), UiTest (TC10-11)
  listeners/                     TestListener (logs + failure screenshot), RetryAnalyzer, RetryListener
  utils/                         JsonUtils, DataProviders, TestData
src/test/resources
  config.properties  testdata.json  log4j2.xml  allure.properties  testng.xml
```

## Run
```bash
mvn clean test                    # runs the OrangeHRM suite (testng.xml, parallel="tests", thread-count=3)
mvn clean test -Dheadless=true    # headless
mvn clean test -Dbrowser=firefox  # chrome (default) | firefox | edge
```

## Allure
```bash
mvn clean test
allure generate allure-results --clean -o allure-report
allure open allure-report
```
Commit `allure-report/`; `allure-results/` is git-ignored.

## GitHub
Create a new **public** repo `automation-testing-graduation-project`, then:
```bash
git remote set-url origin https://github.com/<your-user>/automation-testing-graduation-project.git
git add . && git commit -m "OrangeHRM automation framework" && git push -u origin main
```
