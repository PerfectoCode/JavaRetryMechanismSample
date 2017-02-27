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
        System.out.println("\nTrying to create the driver current time: " + System.currentTimeMillis());
        Object proceed = null;
        int numOfRetries = 0;
        while (proceed ==null && numOfRetries < Constants.MaxTimesToRetry) {
            try {
                System.out.println("Retry : " + numOfRetries);
                proceed = point.proceed();
            } catch (Throwable throwable) {
                System.out.println("Failed to allocate device ...");
                String message = throwable.getMessage();
                System.out.println(message);
                numOfRetries++;
                Thread.sleep(Constants.WaitOnRetry);
            }
        }
        System.out.println("Got a driver current time: "+ System.currentTimeMillis());
        return proceed;
    }




}
