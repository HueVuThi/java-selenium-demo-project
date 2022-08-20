package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class ChromeDriverManager  extends DriverManager {
    String projectPath= System.getProperty("user.dir");
    String blockExtensionPath= projectPath + File.separator + "src" + File.separator + "main" + File.separator +"resources" +
            File.separator + "extension"+ File.separator+ "adblock.crx";

    @Override
    protected void createDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(blockExtensionPath));
        DesiredCapabilities desiredCapabilities= DesiredCapabilities.chrome();
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
         WebDriver driver = new ChromeDriver( desiredCapabilities);
        driverThreadLocal.set(driver);
    }
}
