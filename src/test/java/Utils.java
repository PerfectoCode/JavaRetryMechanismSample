import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daniela on 2/27/17.
 */
public class Utils {

    public static RemoteWebDriver getWebDriver(String platformName, String platformVersion, String browserName,
                                               String browserVersion, String deviceName) throws MalformedURLException {

        // Set cloud host and credentials values from CI, else use local values
        String PERFECTO_HOST     = System.getProperty("np.testHost", Constants.PERFECTO_HOST);
        String PERFECTO_USER     = System.getProperty("np.testUsername", Constants.PERFECTO_USER);
        String PERFECTO_PASSWORD = System.getProperty("np.testPassword", Constants.PERFECTO_PASSWORD);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("user", PERFECTO_USER);
        capabilities.setCapability("password", PERFECTO_PASSWORD);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("browserVersion", browserVersion);
        capabilities.setCapability("deviceName", deviceName);

        System.out.println("Getting " + platformName + " Device.");

        return new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
    }

}
