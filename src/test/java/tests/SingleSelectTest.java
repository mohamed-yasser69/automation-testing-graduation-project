package tests;

import base.BaseTest;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SingleSelectPage;

import java.util.List;

public class SingleSelectTest extends BaseTest {

    private SingleSelectPage page;

    @BeforeMethod
    public void openSingleSelectPage() {
        openLab("lab2");
        page = new SingleSelectPage(driver);
    }

    // Each row: the text of the option, its value
    @DataProvider(name = "countries")
    public Object[][] countries() {
        return new Object[][]{
                {"Egypt", "eg"},
                {"Germany", "de"},
                {"Japan", "jp"},
                {"Canada", "ca"}
        };
    }

    // One row holding a LIST of countries to try one after the other
    @DataProvider(name = "countryTrip")
    public Object[][] countryTrip() {
        return new Object[][]{{List.of("Japan", "Egypt", "Canada")}};
    }

    @Test
    public void firstOptionIsSelectedAtStart() {
        Assert.assertEquals(page.getSelectedText(), "-- Select a country --",
                "The placeholder option should be selected when the page opens");
    }

    @Test
    public void dropdownHasAllOptions() {
        Assert.assertEquals(page.getAllOptionTexts(),
                List.of("-- Select a country --", "Egypt", "Germany", "Japan", "Canada", "Atlantis (not available)"),
                "The dropdown options (in order)");
    }

    @Test
    public void dropdownAllowsOnlyOneChoice() {
        Assert.assertFalse(page.isMultiple(), "A single-select dropdown should not be 'multiple'");
    }

    @Test(dataProvider = "countries")
    public void chooseByVisibleText(String text, String value) {
        page.chooseByText(text);

        Assert.assertEquals(page.getSelectedText(), text, "Selected option");
        Assert.assertEquals(page.getResultMessage(), "You selected: " + text, "Message under the dropdown");
    }

    @Test(dataProvider = "countries")
    public void chooseByValue(String text, String value) {
        page.chooseByValue(value);

        Assert.assertEquals(page.getSelectedText(), text, "Option chosen with value '" + value + "'");
    }

    @Test
    public void chooseByIndex() {
        page.chooseByIndex(2);

        Assert.assertEquals(page.getSelectedText(), "Germany", "The option at index 2");
    }

    @Test(dataProvider = "countryTrip")
    public void changingTheChoiceUpdatesTheMessage(List<String> trip) {
        for (String country : trip) {
            page.chooseByText(country);
            Assert.assertEquals(page.getResultMessage(), "You selected: " + country,
                    "Message after choosing " + country);
        }
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void unknownOptionThrowsException() {
        page.chooseByText("Narnia");
    }
}