package drivers;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    protected ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>() {
        @Override
        protected WebDriver initialValue() {
            return null;
        }
    };


    protected abstract void createDriver();

    public WebDriver getDriver() {
        if (null == driverThreadLocal.get()) {
            createDriver();
        }
        return driverThreadLocal.get();
    }

}
