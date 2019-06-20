package prestashopAutomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.DecimalFormat;
import java.util.List;

public class SearchResultsPage extends Page {
    private By productTile = By.cssSelector(".h3.product-title");
    private By foundProductsLabel = By.cssSelector(".col-md-6.hidden-sm-down.total-products");
    private By productPrice = By.className("price");
    private By productOnlyRegularPrice = By.xpath("//div[@class='product-price-and-shipping']/span[1]");
    private By productsWithDiscount = By.className("discount");
    private By labelOfDiscount = By.className("discount-percentage");
    private By actualPriceOfDiscountProducts = By.xpath("//span[@class='discount-percentage']/following-sibling::span");
    private By regularPriceOfDiscountProducts = By.xpath("//span[@class='discount-percentage']/preceding-sibling::span");
    private By sortingMethodsField = By.className("select-title");
    private By sortingMethodFirst = By.xpath("//a[@class='select-list js-search-link'][1]");
    private By sortingMethodSecond = By.xpath("//a[@class='select-list js-search-link'][2]");
    private By sortingMethodThird = By.xpath("//a[@class='select-list js-search-link'][3]");
    private By sortingMethodFourth = By.xpath("//a[@class='select-list js-search-link'][4]");

    SearchResultsPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(sortingMethodsField));
        if (!"Search".equals(driver.getTitle()) && !"Поиск".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not a Search Results page!");
        }
    }

    public List<WebElement> getAllFoundProducts() {
        waitUntilElementIsClickable(productTile);
        return driver.findElements(productTile);
    }

    public String getFoundProductsLabel() {
        waitUntilElementIsVisable(this.foundProductsLabel);
        WebElement foundProductsLabel = driver.findElement(this.foundProductsLabel);
        String label = foundProductsLabel.getText();
        return label.substring(0, label.length() - 1);
    }

    public boolean checkTheCurrencyOfFoundProducts(String currency) {
        waitUntilElementIsClickable(productPrice);
        List<WebElement> prices = driver.findElements(productPrice);
        switch (currency) {
            case "usd":
                for (WebElement value : prices) {
                    if (!value.getText().contains("USD") && !value.getText().contains("$")) {
                        System.out.println("Some of the products are not displayed in USD currency. Failed.");
                        return false;
                    }
                }
                System.out.println("Checking currencies of products prices ...");
                System.out.println("All products prices are displayed in USD currency. Checked.");
                return true;
            default:
                throw new IllegalArgumentException("Please, enter the correct currency name: eur, uah or usd");
        }
    }

    public boolean checkHighToLowSorting() {
        wait.until((ExpectedConditions.urlContains("search?controller=search&order=product.price.desc&s=")));
        List<WebElement> regularPrices = driver.findElements(productOnlyRegularPrice);
        if (regularPrices.size() >= 2) {
            System.out.println("Checking the High to Low price sorting method...");
            for (int i = 0; i + 1 < regularPrices.size(); i++) {
                float price = Float.parseFloat(regularPrices.get(i).getText().substring(0, 4).replace(",", "."));
                float nextPrice = Float.parseFloat(regularPrices.get(i + 1).getText().substring(0, 4).replace(",", "."));
                if (!(price >= nextPrice)) {
                    System.out.println(price + " is less than " + nextPrice + " Failed.");
                    return false;
                }
                System.out.println(price + " is more than or equals " + nextPrice + " Checked.");
            }
            return true;
        } else {
            throw new IllegalStateException("Impossible to check sorting method, there are less than 2 products on page");
        }
    }

    public boolean checkDiscountProductPriceAndLabel() {
        waitUntilElementIsClickable(productTile);
        List<WebElement> productsWithDiscount = driver.findElements(this.productsWithDiscount);
        List<WebElement> labelsOfDiscount = driver.findElements(this.labelOfDiscount);
        List<WebElement> regularPriceOfDiscountProducts = driver.findElements(this.regularPriceOfDiscountProducts);
        List<WebElement> actualPriceOfDiscountProducts = driver.findElements(this.actualPriceOfDiscountProducts);
        System.out.println("Checking each On-Sale product has its own discount label, actual and regular price ...");
        System.out.println("Found on page: " + productsWithDiscount.size() + " products On-Sale.");
        System.out.println("Found on page: " + labelsOfDiscount.size() + " labels of products On-Sale.");
        System.out.println("Found on page: " + regularPriceOfDiscountProducts.size() + " regular prices of products On-Sale.");
        System.out.println("Found on page: " + actualPriceOfDiscountProducts.size() + " actual prices of products On-Sale.");
        System.out.println("Checking the price is displayed in percentages ...");
        for (WebElement label : labelsOfDiscount) {
            if (!label.getText().contains("%")) {
                System.out.println("Label: " + label.getText() + " Failed.");
                return false;
            }
            System.out.println("Label: " + label.getText() + " Checked.");
        }
        if (productsWithDiscount.size() == labelsOfDiscount.size() && productsWithDiscount.size() == regularPriceOfDiscountProducts.size() && productsWithDiscount.size() == actualPriceOfDiscountProducts.size()) {
            System.out.println("Quantity of On-Sale products: " + productsWithDiscount.size() + " equals the quantity of discount labels: " + labelsOfDiscount.size() + " quantity of regular prices: " + regularPriceOfDiscountProducts.size() + " quantity of actual prices: " + actualPriceOfDiscountProducts.size() + ". Checked.");
            return true;
        } else {
            System.out.println("Quantity of On-Sale products: " + productsWithDiscount.size() + " IS NOT equal the quantity of discount labels: " + labelsOfDiscount.size() + " quantity of regular prices: " + regularPriceOfDiscountProducts.size() + " quantity of actual prices: " + actualPriceOfDiscountProducts.size() + ". Checked.");
            return false;
        }
    }

    public boolean checkDiscountCalculation() {
        waitUntilElementIsClickable(productTile);
        List<WebElement> productsWithDiscount = driver.findElements(this.productsWithDiscount);
        List<WebElement> labelsOfDiscount = driver.findElements(this.labelOfDiscount);
        List<WebElement> regularPriceOfDiscountProducts = driver.findElements(this.regularPriceOfDiscountProducts);
        List<WebElement> actualPriceOfDiscountProducts = driver.findElements(this.actualPriceOfDiscountProducts);

        System.out.println("Checking the discount calculation ...");
        for (int i = 0; i < productsWithDiscount.size(); i++) {
            int discountPercentage = Integer.parseInt(labelsOfDiscount.get(i).getText().replace("%", ""));
            double regularPrice = Double.parseDouble(regularPriceOfDiscountProducts.get(i).getText().substring(0, 4).replace(",", "."));
            double actualPrice = Double.parseDouble(actualPriceOfDiscountProducts.get(i).getText().substring(0, 4).replace(",", "."));

            double discountAmount = regularPrice * (Math.abs(discountPercentage)/100.0);
            double roundDiscountAmount = Math.round(discountAmount * 100.0) / 100.0;

            if (!((Math.round((regularPrice - actualPrice) * 100.0) / 100.0) == roundDiscountAmount)) {
                System.out.println(productsWithDiscount.get(i).getTagName() + " product has " + labelsOfDiscount.get(i).getText() + " . Actual price from " + regularPrice + " is " + actualPrice + " Failed.");
                return false;
            }
            System.out.println("Product #" + (i + 1) + " has " + labelsOfDiscount.get(i).getText() + "($" + discountAmount + ")" + " discount amount. Actual price from " + regularPrice + " is " + actualPrice + " Checked.");

        }
        return true;

    }

    public void changeSortingMethod(int id) {
        waitUntilElementIsClickable(this.sortingMethodsField);
        WebElement sortingMethodsField = driver.findElement(this.sortingMethodsField);
        WebElement sortingMethodFirst = driver.findElement(this.sortingMethodFirst);
        WebElement sortingMethodSecond = driver.findElement(this.sortingMethodSecond);
        WebElement sortingMethodThird = driver.findElement(this.sortingMethodThird);
        WebElement sortingMethodFourth = driver.findElement(this.sortingMethodFourth);
        switch (id) {
            case 1:
                sortingMethodsField.click();
                waitUntilElementIsClickable(this.sortingMethodsField);
                sortingMethodFirst.click();
                break;
            case 2:
                sortingMethodsField.click();
                waitUntilElementIsClickable(this.sortingMethodSecond);
                sortingMethodSecond.click();
                break;
            case 3:
                sortingMethodsField.click();
                waitUntilElementIsClickable(this.sortingMethodThird);
                sortingMethodThird.click();
                break;
            case 4:
                sortingMethodsField.click();
                waitUntilElementIsClickable(this.sortingMethodFourth);
                sortingMethodFourth.click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported argument. Please use 1, 2, 3, 4 to identify the sorting method");
        }
    }
}
