package pages;

import commons.WebKeywords;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class HomePage extends WebKeywords {
    WebDriver driver;
    String fileName = "HomePage.json";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Verify login successfully")
    public boolean isLoginSuccess() {
        return isElementDisplayed(driver, fileName, "TXT_WELCOME", "TXT_WELCOME");
    }
    
}
