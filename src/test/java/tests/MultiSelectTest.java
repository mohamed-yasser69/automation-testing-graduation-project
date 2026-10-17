package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.MultiSelectPage;

import java.util.List;

public class MultiSelectTest extends BaseTest {

    private MultiSelectPage page;

    @BeforeMethod
    public void openMultiSelectPage() {
        openLab("lab3");
        page = new MultiSelectPage(driver);
    }

    // Each row holds a LIST of skills (in the same order as the dropdown)
    @DataProvider(name = "skillSets")
    public Object[][] skillSets() {
        return new Object[][]{
                {List.of("Java")},
                {List.of("Java", "TestNG")},
                {List.of("Selenium", "Maven", "Appium")}
        };
    }

    @Test
    public void dropdownAllowsManyChoices() {
        Assert.assertTrue(page.isMultiple(), "A multi-select dropdown should be 'multiple'");
    }

    @Test(dataProvider = "skillSets")
    public void selectManySkills(List<String> skills) {
        page.selectSkills(skills);

        Assert.assertEquals(page.getSelectedSkills(), skills, "Selected skills");
        page.clickShowSkills();
        Assert.assertEquals(page.getResultText(), "Skills: " + String.join(", ", skills),
                "Message after clicking 'Show my skills'");
    }

    @Test
    public void deselectOneSkill() {
        page.selectSkills(List.of("Java", "Selenium", "TestNG"));
        page.deselectSkill("Selenium");

        Assert.assertEquals(page.getSelectedSkills(), List.of("Java", "TestNG"),
                "Only Selenium should be removed");
    }

    @Test
    public void deselectAllClearsTheSelection() {
        page.selectSkills(List.of("Java", "Maven"));
        page.deselectAll();
        page.clickShowSkills();

        Assert.assertTrue(page.getSelectedSkills().isEmpty(), "No option should stay selected");
        Assert.assertEquals(page.getResultText(), "Skills: none", "Message with nothing selected");
    }
}