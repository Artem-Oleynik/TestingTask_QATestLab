package prestashopAutomation.props;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public abstract class Settings {
    private static final String PAGE_URL = "http://prestashop-automation.qatestlab.com.ua/ru/";

    public String getPageUrl() {
        return PAGE_URL;
    }

    public static WebDriver getDriver() {
        try {
            System.setProperty("webdriver.chrome.driver", new File(Settings.class.getResource("/chromedriver.exe").toURI()).toString());
            return new ChromeDriver();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Driver url error");
            return null;
        }
    }

    public static EventFiringWebDriver getConfiguredDriver() {
        WebDriver driver = getDriver();
        EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
        eventFiringWebDriver.register(new EventHandler());
        eventFiringWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return eventFiringWebDriver;
    }

    public static void setupLogs() {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("C:\\Users\\Freeman\\Desktop\\testing_task\\log4j2.xml");

        context.setConfigLocation(file.toURI());
    }
}
