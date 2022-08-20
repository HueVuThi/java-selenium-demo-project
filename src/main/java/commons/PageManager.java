package commons;

import org.openqa.selenium.WebDriver;
import pages.AddNewCustomerPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SubMenuPage;

public class PageManager {
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static SubMenuPage subMenuPage;
    private static AddNewCustomerPage addNewCustomerPage;


    public static LoginPage getLoginPage(WebDriver driver) {
        if (loginPage == null) {
            return new LoginPage(driver);
        }
        return loginPage;
    }

    public static HomePage getHomePage(WebDriver driver) {
        if (homePage == null) {
            return new HomePage(driver);
        }
        return homePage;
    }

    public static SubMenuPage getSubMenuPage(WebDriver driver) {
        if (subMenuPage == null) {
            return new SubMenuPage(driver);
        }
        return subMenuPage;
    }
    public static AddNewCustomerPage getAddNewCustomerPage(WebDriver driver) {
        if (addNewCustomerPage == null) {
            return new AddNewCustomerPage(driver);
        }
        return addNewCustomerPage;
    }
}
