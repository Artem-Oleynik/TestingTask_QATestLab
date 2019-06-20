package prestashopAutomation.testcases;

import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;
import prestashopAutomation.pageObjects.HomePage;
import prestashopAutomation.pageObjects.SearchResultsPage;
import prestashopAutomation.props.EventHandler;
import prestashopAutomation.props.Settings;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class MainTestCase extends Settings {
    private EventFiringWebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driver = getConfiguredDriver();
        System.setProperty("log4j2.xml",Settings.class.getResource("/log4j2.xml").getPath());
        EventHandler.startTestCase("Test_001");
    }

    @Test
    public void MainTestCase() {
        driver.get(getPageUrl());

        HomePage homePage = new HomePage(driver);

        checkCurrencyChanged("eur", homePage);
        checkCurrencyChanged("uah", homePage);
        checkCurrencyChanged("usd", homePage);

        SearchResultsPage searchResultsPage = homePage.searchProduct("dress");
        assertEquals(searchResultsPage.getFoundProductsLabel(), "Товаров: " + searchResultsPage.getAllFoundProducts().size());
        System.out.println("Checking that the \"Товары\" label is displayed and displays the correct number of products ...");
        System.out.println("The label: \"Товаров: " + searchResultsPage.getAllFoundProducts().size() + "\" is displayed and correct. Checked." );

        assertTrue(searchResultsPage.checkTheCurrencyOfFoundProducts("usd"), "The currency type of one or more of the found products doesn't match the selected USD currency in header");
        searchResultsPage.changeSortingMethod(4);
        assertTrue(searchResultsPage.checkHighToLowSorting(), "The products are not sorted correctly");
        assertTrue(searchResultsPage.checkDiscountProductPriceAndLabel(), "Some of the products doesn't have discount label, actual or regular price displayed.");
        assertTrue(searchResultsPage.checkDiscountCalculation(), "Some of the products has incorrect discount calculation");
    }

    private void checkCurrencyChanged(String currency, HomePage homePage){
        homePage.changeCurrencyType(currency);
        assertTrue(homePage.checkCurrency(currency), "The currency type of the products doesn't match the selected EUR currency in header");
        System.out.println("The price of the goods coincides with the established currency in the site header ("+currency.toUpperCase()+") - Checked");
    }

    @AfterTest
    public void afterTest(){
        if (driver != null) {
            driver.close();
        }
        EventHandler.endTestCase();
    }
}
