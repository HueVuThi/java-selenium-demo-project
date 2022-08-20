package commons;

import drivers.DriverManager;
import drivers.DriverManagerFactory;
import drivers.DriverTypes;
import log.LogHelper;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static listeners.VerificationFailures.getFailures;

public class BaseTest {
    public static WebDriver driver;
    public static Logger logger;
    private static DriverManager driverManager;

    protected BaseTest() {
        logger = LogHelper.getLogger();
    }

    private boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            if (condition == true) {
                logger.info(" === PASSED === ");
            } else {
                logger.info(" === FAILED === ");
            }
            Assert.assertTrue(condition);
        } catch (Throwable e) {
            pass = false;
            getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    protected boolean verifyFalse(boolean condition, String message) {
        return checkFailed(condition, message);
    }

    private boolean checkFailed(boolean condition, String message) {
        boolean pass = true;
        try {
            if (condition == false) {
                logger.info(" === PASSED === ");
            } else {
                logger.info(" === FAILED === ");
            }
            Assert.assertFalse(condition);
        } catch (Throwable e) {
            pass = false;
            getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        boolean status;
        try {
            if (actual instanceof String && expected instanceof String) {
                actual = actual.toString().trim();
                logger.info("Actual = " + actual);
                expected = expected.toString().trim();
                logger.info("Expected = " + expected);
                status = (actual.equals(expected));
            } else {
                status = (actual == expected);
            }

            logger.info("Compare value = " + status);
            if (status) {
                logger.info("=== PASSED ===");
            } else {
                logger.info("=== FAILED ===");
            }
            Assert.assertEquals(actual, expected, "Value is not matching!");
        } catch (Throwable e) {
            pass = false;
            getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }

    public WebDriver openBrowser(String browser, String... url) {
        logger.info(MessageFormat.format("Opening {0} browser", browser.toUpperCase()));
        driverManager = DriverManagerFactory.getManager(DriverTypes.valueOf(browser.toUpperCase()));
        WebDriver driver = driverManager.getDriver();
        logger.info(MessageFormat.format("Opened {0} browser successfully", browser.toUpperCase()));
        String rawUrl = url.length > 0 ? url[0] : "";
        if (rawUrl != null && !rawUrl.isEmpty()) {
            driver.get(rawUrl);
            logger.info(MessageFormat.format("Navigated to {0} successfully", rawUrl));
        }
        driver.manage().timeouts().implicitlyWait(WebConstants.TIMEOUT, TimeUnit.SECONDS );
        driver.manage().window().maximize();
        return driver;
    }

    protected void quitDriver(WebDriver driver) {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            logger.info("OS name = " + osName);

            String cmd = "";
            if (driver != null) {
                driver.quit();
            }

            if (driver.toString().toLowerCase().contains("chrome")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill chromedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                }
            } else if (driver.toString().toLowerCase().contains("internetexplorer")) {
                if (osName.toLowerCase().contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                }
            } else if (driver.toString().toLowerCase().contains("firefox")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill geckodriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
                }
            } else if (driver.toString().toLowerCase().contains("edge")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill msedgedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
                }
            }

            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

            logger.info("---------- QUIT BROWSER SUCCESS ----------");
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    protected static long randomByTime() {
        return Calendar.getInstance().getTimeInMillis() % 1000000;
    }

    protected String getRadomEmail() {
        return "companyname" + randomByTime() + "@gmail.com";

    }
    protected String getRadomEditedEmail() {
        return "companyEdited" + randomByTime() + "@gmail.com";

    }

    public WebDriver getDriver() {
        return driver;
    }
}
