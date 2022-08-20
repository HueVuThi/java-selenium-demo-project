package drivers;

public class DriverManagerFactory {
    public static DriverManager getManager(DriverTypes type) {
        DriverManager driverManager;

        switch (type) {
            case FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
            case CHROME:
            default:
                driverManager = new ChromeDriverManager();
                break;
        }
        return driverManager;

    }
}
