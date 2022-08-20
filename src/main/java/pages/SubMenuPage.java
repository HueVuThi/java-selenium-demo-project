package pages;

import commons.WebKeywords;
import org.openqa.selenium.WebDriver;

public class SubMenuPage extends WebKeywords {
    WebDriver driver;
    String fileName = "SubMenuPage.json";

    public SubMenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void  clickToAddNewCustomer(){
        clickToElement(driver, fileName,"LNK_NEW_CUSTOMER","LNK_NEW_CUSTOMER");

    }

}
