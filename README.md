## Retry Mechanism for Java

The following Java sample shows how to retry obtain a device or web vm from Perfecto mobile cloud.<br/>
Common reasons for retry obtain a device: The device is busy, connection failure, no available license, etc... 

### Quick start: 

- Clone the project and clone to Intellij (Recommended) / Eclipse IDE. 

- Set you Perfecto lab credentials at [Constants.java](src/test/java/Constants.java) : 

```Java 
public class Constants {

    //User pass
    static final String PERFECTO_HOST       = "MyHost";
    static final String PERFECTO_USER       = "MyUser";
    static final String PERFECTO_PASSWORD   = "MyPassword";
    ... 
```

- For Intellij IDE follow Jetbrains guide for configure AspectJ compiler [here](https://www.jetbrains.com/help/idea/2016.3/aspectj.html). 
- For Eclipse IDE follow the following guide [here](http://www.eclipse.org/aspectj/). 

### Hooking driver creation [RemoteWebDriverAspect.java](src/test/java/RemoteWebDriverAspect.java) : 
- By using [Before, After, Around](https://eclipse.org/aspectj/doc/next/progguide/semantics-advice.html) annotations we able to wrap RemoteWebDriver constructor. <br/>
Consider the following code: <br/>
```Java 
@Before("call(org.openqa.selenium.remote.RemoteWebDriver+.new(..))")
@SuppressWarnings("PMD.AvoidCatchingThrowable")
public void remoteWebDriverBeforeAspect(JoinPoint joinPoint) throws Throwable {
    System.out.println("Before Creating driver...");
}
```
The *remoteWebDriverBeforeAspect* method will run before each trying to construct RemoteWebDriver instance. <br/> 
*@Around* annotation enable to control what's happens before + after the method we wrap: <br/>
```Java 
@Around("call(org.openqa.selenium.remote.RemoteWebDriver+.new(..))")
@SuppressWarnings("PMD.AvoidCatchingThrowable")
public Object remoteWebDriverAspect(ProceedingJoinPoint point) throws Throwable {
    ... //Code before method 

    try{
        proceed = point.proceed();
    } catch (Throwable throwable) {
    ... // Code on failure 
    }
    
    ... // Code to call after the method 
}
```
