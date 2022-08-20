package com.bankguru.account;

import com.github.javafaker.Faker;
import commons.BaseTest;
import commons.Environment;
import commons.PageManager;
import commons.WebConstants;
import data.CustomerData;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddNewCustomerPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SubMenuPage;

public class AddNewCustomerAccount extends BaseTest {
    WebDriver driver;
    Faker faker = new Faker();
    LoginPage loginPage;
    HomePage homePage;
    SubMenuPage subMenuPage;
    AddNewCustomerPage addNewCustomerPage;
    Environment env;
    String customerEmail, customerEditedEmail, customerPhone, customerEditedPhone;

    @Parameters({"browser", "environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environment) {
        ConfigFactory.setProperty("env", environment);
        env = ConfigFactory.create(Environment.class);
        customerEmail = getRadomEmail();
        CustomerData.CUSTOMER_DYNAMIC_EMAIL = customerEmail;
        customerEditedEmail = getRadomEditedEmail();
        CustomerData.CUSTOMER_DYNAMIC_EMAIL = customerEditedEmail;
        customerPhone = faker.number().digits(10);
        CustomerData.CUSTOMER_DYNAMIC_PHONE = customerPhone;
        customerEditedPhone = faker.number().digits(10);
        CustomerData.CUSTOMER_DYNAMIC_PHONE = customerEditedPhone;

        driver = openBrowser(browserName, env.URL());
        loginPage = PageManager.getLoginPage(driver);
        loginPage.switchToBankGuruPage(WebConstants.LOGIN_PAGE_TITLE);
    }

    @Test
    public void Add_New_Customer_Account() {
        loginPage.inputUserID(env.USER_NAME());
        loginPage.inputUserPassword(env.PASSWORD());
        loginPage.clickToButtonLogin();

        homePage = PageManager.getHomePage(driver);
        verifyTrue(homePage.isLoginSuccess());

        subMenuPage = PageManager.getSubMenuPage(driver);
        subMenuPage.clickToAddNewCustomer();

        addNewCustomerPage = PageManager.getAddNewCustomerPage(driver);
        addNewCustomerPage.inputCustomerName(CustomerData.CUSTOMER_NAME);
        addNewCustomerPage.inputCustomerBirthdate(CustomerData.CUSTOMER_BIRTHDATE_INPUT);
        addNewCustomerPage.inputCustomerAddress(CustomerData.CUSTOMER_ADDRESS_INPUT);
        addNewCustomerPage.inputCustomerCity(CustomerData.CUSTOMER_CITY);
        addNewCustomerPage.inputCustomerState(CustomerData.CUSTOMER_STATE);
        addNewCustomerPage.inputCustomerPIN(CustomerData.CUSTOMER_PIN);
        addNewCustomerPage.inputCustomerMobileNumber(CustomerData.CUSTOMER_DYNAMIC_PHONE);
        addNewCustomerPage.inputCustomerEmail(CustomerData.CUSTOMER_DYNAMIC_EMAIL);
        addNewCustomerPage.inputCustomerPassword(CustomerData.CUSTOMER_PASSWORD);
        addNewCustomerPage.clickToSubmitButton();
        addNewCustomerPage.waitForSuccessfullyTextDisplayed();
        verifyEquals(addNewCustomerPage.getCustomerName(), CustomerData.CUSTOMER_NAME);
        verifyEquals(addNewCustomerPage.getCustomerGender(), CustomerData.GENDER[0]);
        verifyEquals(addNewCustomerPage.getCustomerBirthdate(), CustomerData.CUSTOMER_BIRTHDATE_OUTPUT);
        verifyEquals(addNewCustomerPage.getCustomerAddress(), CustomerData.CUSTOMER_ADDRESS_OUTPUT);
        verifyEquals(addNewCustomerPage.getCustomerCity(), CustomerData.CUSTOMER_CITY);
        verifyEquals(addNewCustomerPage.getCustomerState(), CustomerData.CUSTOMER_STATE);
        verifyEquals(addNewCustomerPage.getCustomerPIN(), CustomerData.CUSTOMER_PIN);
        verifyEquals(addNewCustomerPage.getCustomerMobile(), CustomerData.CUSTOMER_DYNAMIC_PHONE);
        verifyEquals(addNewCustomerPage.getCustomerEmail(), CustomerData.CUSTOMER_DYNAMIC_EMAIL);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        quitDriver(driver);
    }

}
