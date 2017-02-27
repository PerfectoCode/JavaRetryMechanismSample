import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniela on 2/27/17.
 */
public class PerfectoWebScenarioAspectJ {

    RemoteWebDriver driver;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion", "deviceName"})
    @BeforeClass
    public void beforeClass(String platformName, String platformVersion, String browserName,
                            String browserVersion, String deviceName) throws MalformedURLException {
        System.out.println("============ Before Test ============");
        driver = Utils.getWebDriver(platformName, platformVersion, browserName, browserVersion, deviceName);
        driver.manage().timeouts().implicitlyWait(Constants.ImplicitlyWait, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(Constants.PageLoadTimeout, TimeUnit.SECONDS);
    }

    @Test
    public void jAspectSampleTest() {
        System.out.println("============ Test Start ============");
        driver.get("https://www.google.com");
        driver.findElement(Constants.GoogleSearchBar).sendKeys("PerfectoCode GitHub");
        driver.findElement(Constants.GoogleSearchButton).click();
        driver.findElement(Constants.PerfectoGitHubSearchResult).click();

        // Waits until logo load
        (new WebDriverWait(driver, Constants.WebDriverWait)).until(
                ExpectedConditions.visibilityOfElementLocated(Constants.GitHubLogo));
    }

    @AfterClass
    public void afterClass() {
        System.out.println("============ Test Finish ============");
        try {
            driver.close();
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
