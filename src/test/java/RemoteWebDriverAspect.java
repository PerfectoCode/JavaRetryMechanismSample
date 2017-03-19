import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class RemoteWebDriverAspect {

    @Before("call(org.openqa.selenium.remote.RemoteWebDriver+.new(..))")
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public void remoteWebDriverBeforeAspect(JoinPoint joinPoint) throws Throwable {
        System.out.println("Before Creating driver...");
    }

    @Around("call(org.openqa.selenium.remote.RemoteWebDriver+.new(..))")
    @SuppressWarnings("PMD.AvoidCatchingThrowable")
    public Object remoteWebDriverAspect(ProceedingJoinPoint point) throws Throwable {

        //Code to run before creating the driver
        long start = System.currentTimeMillis() / 1000;
        System.out.println("\n[" + System.currentTimeMillis() + "] Trying to create a Remote Web Driver");
        Object driver = null;
        int numOfRetries = 0;
        while (driver == null & numOfRetries < Constants.MaxTimesToRetry) {
            try {
                System.out.println("[" + System.currentTimeMillis() + "] Try number : " + numOfRetries);
                driver = point.proceed();
            } catch (Throwable throwable) {
                System.out.println("[" + System.currentTimeMillis() + "] Device allocation failed");
                String message = throwable.getMessage();
                System.out.println(message);
                numOfRetries++;
                Thread.sleep(Constants.WaitOnRetry);
            }
        }

        if (driver != null) {
            //Code to run after successfully creating a driver
            System.out.println("[" + System.currentTimeMillis() / 1000 + "] Remote Web Driver initialized successfully");
        }

        else {
            //Code to run when used up retries with no success
            System.out.println("[" + System.currentTimeMillis() + "] Failed to initialize a Remote Web Driver");
            //Throw exception?
        }

        return driver;
    }


}
