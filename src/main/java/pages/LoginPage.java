package pages;

import commons.WebKeywords;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class LoginPage extends WebKeywords {
    WebDriver driver;
    String fileName = "LoginPage.json";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Input user ID  ")
    public void inputUserID(String userID) {
        setText(driver, fileName, "TXT_USER_ID", "TXT_USER_ID", userID);
    }

    @Step("Input user password ")
    public void inputUserPassword(String password) {
        setText(driver, fileName, "TXT_USER_PASSWORD", "TXT_USER_PASSWORD", password);
    }

    @Step("Click to login button")
    public void clickToButtonLogin() {
        clickToElement(driver, fileName, "BTN_LOGIN", "BTN_LOGIN");
    }

    @Step("Switch to Guru 99 Bank Customer Registration Page ")
    public void switchToBankGuruPage(String pageTitle) {
        getToTab(driver, pageTitle);
    }
}
