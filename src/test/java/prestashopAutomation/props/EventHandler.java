package prestashopAutomation.props;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.File;
import java.util.Arrays;

public class EventHandler implements WebDriverEventListener {
    private static final Logger LOG = LogManager.getLogger(EventHandler.class);


    public static void startTestCase(String sTestCaseName){
        LOG.info("****************************************************************************************");

        LOG.info("****************************************************************************************");

        LOG.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");

        LOG.info("****************************************************************************************");

        LOG.info("****************************************************************************************");

    }

    public static void endTestCase(){
        LOG.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
    }

    @Override
    public void beforeAlertAccept(WebDriver driver) {
        LOG.info("Trying to accept Alert ...");
    }

    @Override
    public void afterAlertAccept(WebDriver driver) {
        LOG.info("Alert is accepted.");
    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {
        LOG.info("Alert is canseled.");
    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {
        LOG.info("Trying to cancel Alert ...");
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        LOG.info("Trying to open the " + url);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        LOG.info("Navigated to " + url);
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {
        LOG.info("Trying to navigate back ...");
    }

    @Override
    public void afterNavigateBack(WebDriver driver) {
        LOG.info("Navigated back.");
    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {
        LOG.info("Trying to navigate forward ...");
    }

    @Override
    public void afterNavigateForward(WebDriver driver) {
        LOG.info("Navigated forward.");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {
        LOG.info("Trying to refresh the page ...");
    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {
        LOG.info("The page is refreshed.");
    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        LOG.info("Trying to find element by " + by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        LOG.info("Successfully found element by " + by);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        LOG.info("Trying to click on " + element);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        LOG.info("Successfully clicked on " + element);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        LOG.info("Trying to change value of " + element + " on " +
                Arrays.toString(keysToSend));
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        LOG.info("Successfully changed the value of " + element + " on" + Arrays.toString(keysToSend));
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {
        LOG.info("Trying to execute script " + script);
    }

    @Override
    public void afterScript(String script, WebDriver driver) {
        LOG.info("Successfully executed script " + script);
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        LOG.info("The next exception has been occured " + throwable);
    }

    private void improvedEventLogs(String event) {
        LOG.info(event + "\n");
    }
}
