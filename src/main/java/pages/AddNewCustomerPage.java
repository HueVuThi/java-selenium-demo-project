package pages;

import commons.WebKeywords;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class AddNewCustomerPage extends WebKeywords {
    WebDriver driver;
    String fileName = "AddNewCustomerPage.json";

    public AddNewCustomerPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Input customer name")
    public void inputCustomerName(String name) {
        setText(driver, fileName, "TXT_CUSTOMER_NAME", "TXT_CUSTOMER_NAME", name);
    }

    @Step("Input customer birth date")
    public void inputCustomerBirthdate(String date) {
        setText(driver, fileName, "DPK_DATE_OF_BIRTH", "DPK_DATE_OF_BIRTH", date);
    }

    @Step("Input customer address")
    public void inputCustomerAddress(String address) {
        setText(driver, fileName, "TXA_ADDRESS", "TXA_ADDRESS", address);
    }

    @Step("Input customer city")
    public void inputCustomerCity(String city) {
        setText(driver, fileName, "TXT_CITY", "TXT_CITY", city);
    }

    @Step("Input customer state")
    public void inputCustomerState(String state) {
        setText(driver, fileName, "TXT_STATE", "TXT_STATE", state);
    }

    @Step("Input customer PIN")
    public void inputCustomerPIN(String state) {
        setText(driver, fileName, "TXT_PIN", "TXT_PIN", state);
    }

    @Step("Input customer mobile number")
    public void inputCustomerMobileNumber(String phone) {
        setText(driver, fileName, "TXT_MOBILE_NUMBER", "TXT_MOBILE_NUMBER", phone);
    }

    @Step("Input customer email")
    public void inputCustomerEmail(String email) {
        setText(driver, fileName, "TXT_EMAIL", "TXT_EMAIL", email);
    }

    @Step("Input customer password")
    public void inputCustomerPassword(String password) {
        setText(driver, fileName, "TXT_PASSWORD", "TXT_PASSWORD", password);
    }

    @Step("Click to submit button")
    public void clickToSubmitButton() {
        clickToElement(driver, fileName, "BTN_SUBMIT", "BTN_SUBMIT");
    }

    @Step("Wait fo page load successfully")
    public void waitForSuccessfullyTextDisplayed() {
        waitForElementVisible(driver, fileName, "MSG_SUCCESSFULLY_REGISTERED", "MSG_SUCCESSFULLY_REGISTERED");

    }

    @Step("Verify customer ID")
    public String getCustomerID(){
        return getText(driver, fileName, "TEXT_CUSTOMER_ID", "TEXT_CUSTOMER_ID");
    }
    @Step("Verify customer name")
    public String getCustomerName(){
        return getText(driver, fileName, "TEXT_CUSTOMER_NAME", "TEXT_CUSTOMER_NAME");
    }
    @Step("Verify customer gender")
    public String getCustomerGender(){
        return getText(driver, fileName, "TEXT_CUSTOMER_GENDER", "TEXT_CUSTOMER_GENDER");
    }
    @Step("Verify customer birthdate")
    public String getCustomerBirthdate(){
        return getText(driver, fileName, "TEXT_CUSTOMER_BIRTHDATE", "TEXT_CUSTOMER_BIRTHDATE");
    }
    @Step("Verify customer address")
    public String getCustomerAddress(){
        return getText(driver, fileName,  "TEXT_CUSTOMER_ADDRESS",  "TEXT_CUSTOMER_ADDRESS");
    }
    @Step("Verify customer city")
    public String getCustomerCity(){
        return getText(driver, fileName, "TEXT_CUSTOMER_CITY", "TEXT_CUSTOMER_CITY");
    }
    @Step("Verify customer state")
    public String getCustomerState(){
        return getText(driver, fileName, "TEXT_CUSTOMER_STATE", "TEXT_CUSTOMER_STATE");
    }
    @Step("Verify customer PIN")
    public String getCustomerPIN(){
        return getText(driver, fileName, "TEXT_CUSTOMER_PIN", "TEXT_CUSTOMER_PIN");
    }
    @Step("Verify customer phone")
    public String getCustomerMobile(){
        return getText(driver, fileName, "TEXT_CUSTOMER_MOBILE", "TEXT_CUSTOMER_MOBILE");
    }
    @Step("Verify customer email")
    public String getCustomerEmail(){
        return getText(driver, fileName, "TEXT_CUSTOMER_EMAIL", "TEXT_CUSTOMER_EMAIL");
    }

}
