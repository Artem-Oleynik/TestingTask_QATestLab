package prestashopAutomation.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class Page {
    final WebDriver driver;
    WebDriverWait wait;

    Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    void waitUntilElementIsClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    void waitUntilElementIsVisable(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
