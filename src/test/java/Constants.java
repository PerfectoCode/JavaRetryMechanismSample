import org.openqa.selenium.By;

/**
 * Created by daniela on 2/27/17.
 */
public class Constants {

    //User pass
    static final String PERFECTO_HOST       = "MyHost";
    static final String PERFECTO_USER       = "MyUser";
    static final String PERFECTO_PASSWORD   = "MyPassword";

    //General constants
    static final int ImplicitlyWait     = 25;
    static final int PageLoadTimeout    = 25;
    static final int WebDriverWait      = 10;
    static final int MaxTimesToRetry    = 10;
    static final long WaitOnRetry       = 10000L;

    //Test locators
    static final By GoogleSearchBar             = By.name("q");
    static final By GoogleSearchButton          = By.name("btnG");
    static final By PerfectoGitHubSearchResult  = By.xpath("//*[contains(text(), 'PerfectoCode - GitHub')]");
    static final By GitHubLogo                  = By.xpath("//*[@class ='avatar avatar']");
}
