package base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest extends DriverManager {

    @Parameters({"appPackage", "appActivity","isRealDevice"})
    @BeforeMethod
    public void appSetup(String appPackage, String appActivity,  String isRealDevice)  {
        initializeDriver(appPackage, appActivity,  isRealDevice );
    }

    @AfterMethod
    public void tearDown() {
        try {
            androidDriver.closeApp();
            androidDriver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
