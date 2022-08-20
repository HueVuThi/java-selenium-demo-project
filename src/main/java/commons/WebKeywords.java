package commons;

import log.LogHelper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Set;
import java.util.function.Function;

public class WebKeywords {
    protected static Logger logger = LogHelper.getLogger();

    private static String getFileOnResources(String fileName) throws URISyntaxException {
        URL res = WebKeywords.class.getClassLoader().getResource(fileName);
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    private String getXpathOnJsonFile(String fileRepo, String objName) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(getFileOnResources("repository/" + fileRepo)));
            JSONObject jsonObject = (JSONObject) obj;
            String value = (String) jsonObject.get(objName);
            return value;
        } catch (Exception e) {
            logger.error(MessageFormat.format("Cant parse to Json file {0} by locator {1}", fileRepo, objName));
            return null;
        }
    }

    private WebElement getElement(WebDriver driver, String fileRepo, String xpath, String webId) {
        logger.info("Getting locator from element " + webId);
        xpath = getXpathOnJsonFile(fileRepo, xpath);
        logger.info(MessageFormat.format("Got locator from element {0}. Locator is {1}", webId, xpath));
        int poolingTime = WebConstants.TIMEOUT / 6;
        logger.info(MessageFormat.format("Finding web element {0}", webId));
        WebElement we = null;
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(WebConstants.TIMEOUT)).
                    pollingEvery(Duration.ofSeconds(poolingTime)).ignoring(NoSuchElementException.class);

            String finalXpath = xpath;
            we = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(By.xpath(finalXpath));
                }
            });
            if (we != null) {
                logger.info(MessageFormat.format("Found {0} web element {1} ", 1, webId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Reached timeout. Cannot find web element {0}. Root cause: {1}", webId, e.getMessage()));
        }
        return we;
    }

    private void scrollIntoView(WebDriver driver, String fileRepo, String xpath, String webId) {
        JavascriptExecutor jvexecutor = (JavascriptExecutor) driver;
        WebElement element = getElement(driver, fileRepo, xpath, webId);

        try {
            logger.info(MessageFormat.format(" Scrolling into view element {0}", webId));
            jvexecutor.executeScript("arguments[0].scrollIntoView(true)", element);
            logger.info(MessageFormat.format("Scrolled into viewed  element {0}", webId));
        } catch (Exception e) {
            logger.info(MessageFormat.format(" Cant't scroll into view element {0} . Root cause {1}", webId, e.getMessage()));
        }
    }

    protected void clickToElement(WebDriver driver, String fileRepo, String xpath, String webId) {
        WebElement we = getElement(driver, fileRepo, xpath, webId);
        try {
            logger.info(MessageFormat.format("Clicking web element {0}", webId));
            if (we != null && we.isDisplayed()) {
                scrollIntoView(driver, fileRepo, xpath, webId);
                we.click();
                logger.info(MessageFormat.format("Clicked web element {0} successfully", webId));
            } else {
                logger.error(MessageFormat.format("Cannot click web element {0}. Root cause: web element is not enabled", webId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Cannot click web element {0}. Root cause: {1}", webId, e.getMessage()));
        }
    }

    protected void setText(WebDriver driver, String fileRepo, String xpath, String webId, String text) {
        WebElement we = getElement(driver, fileRepo, xpath, webId);
        try {
            if (we != null && we.isDisplayed()) {
                scrollIntoView(driver, fileRepo, xpath, webId);
                logger.info(MessageFormat.format("Clearing web element ''{0}''", webId));
                we.clear();
                logger.info(MessageFormat.format("Cleared web element ''{0}'' successfully", webId));
                we.sendKeys(text);
                logger.info(MessageFormat.format("Set text {0} to web element {1} successfully", text, webId));
            } else {
                logger.error(MessageFormat.format("Cannot set text {0} to  web element {1}. Root cause: web element is not enabled", text, webId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Cannot set text {0} to web element {1}. Root cause {2}", text, webId, e.getMessage()));
        }
    }

    protected void clearTextAttribute(WebDriver driver, String fileRepo, String xpath, String webId) {
        WebElement element = getElement(driver, fileRepo, xpath, webId);
        String computer = System.getProperty("os.name");
        try {
            logger.info(MessageFormat.format("Clearing text to web element {0}", webId));
            if (element.isEnabled()) {
                scrollIntoView(driver, fileRepo, xpath, webId);
                logger.info("Sending key CMD or CONTROL and a ");
                if (computer.toLowerCase().contains("mac")) {
                    element.sendKeys(Keys.COMMAND, Keys.chord("a"));

                }
                if (computer.toLowerCase().contains("window")) {
                    element.sendKeys(Keys.CONTROL, "a");
                }
                logger.info("Sending key Back space");
                element.sendKeys(Keys.BACK_SPACE);
                logger.info("Sent key Back space");
                logger.info(MessageFormat.format("Cleared text  to web element {0} successfully", webId));

            } else {
                logger.error(MessageFormat.format("Cannot clear text to  web element {0}. Root cause: web element is not enabled", webId));
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Cannot clear text to web element {0}. Root cause {1}", webId, e.getMessage()));
        }
    }

    protected boolean isElementDisplayed(WebDriver driver, String fileRepo, String xpath, String webId) {
        WebElement element = getElement(driver, fileRepo, xpath, webId);
        try {
            if (element.isDisplayed()) {
                logger.info(MessageFormat.format("Web element {0} is displayed", webId));
                return true;
            } else {
                logger.error(MessageFormat.format("Web element {0} is not displayed. Root cause: Element is not visible", webId));
                return false;
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("Web element {0} is not displayed. Root cause: {1}", webId, e.getMessage()));
            return false;
        }
    }

    protected boolean waitForElementVisible(WebDriver driver, String fileRepo, String xpath, String webId) {
        xpath = getXpathOnJsonFile(fileRepo, xpath);
        try {
            WebDriverWait wait = new WebDriverWait(driver, WebConstants.TIMEOUT);
            logger.info(MessageFormat.format("Waiting for  element {0} visible", webId));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            if (element != null || element.isDisplayed()) {
                logger.info(MessageFormat.format("Verified element {0} is visible", webId));
            }
            return true;
        } catch (TimeoutException e) {
            logger.error(MessageFormat.format("Can't find element {0} is visible after {1}", webId, WebConstants.TIMEOUT));
            return false;
        }
    }

    public String getText(WebDriver driver, String fileRepo, String xpath, String webId) {
        WebElement element = getElement(driver, fileRepo, xpath, webId);
        String text = "";
        try {
            logger.info(MessageFormat.format("Getting text from element {0}", webId));
            text = element.getText();
            logger.info(MessageFormat.format("Got text from element {0} successfully. Text is {1}", webId, text));
            return text;
        } catch (Exception e) {
            logger.error(MessageFormat.format("Can't get text from {0}. Root cause: {1}", webId, e.getMessage()));
            return null;
        }
    }

    protected void getToTab(WebDriver driver, String tabTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            logger.info("Switching window");
            driver.switchTo().window(id);
            String actualTitle = driver.getTitle();
            if (actualTitle.equals(tabTitle)) {
                logger.info(MessageFormat.format("Switched to window {0} successfully", tabTitle));
                break;
            }
        }
    }
}
