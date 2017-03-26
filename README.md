## Retry Mechanism for Java

The following Java project presents a method to identify problems when trying to allocate a device or web vm from Perfecto CQ Lab and retry the request. Common problems in allocating a device include: 
  * The device is busy, 
  * Connection failure, 
  * No available license 

### Quick start: 

- Clone the project and import to Intellij (Recommended) / Eclipse IDE. 

- Add your Perfecto Lab credentials at [Constants.java](src/test/java/Constants.java) : 

```Java 
public class Constants {

    //User pass
    static final String PERFECTO_HOST       = "MyHost";
    static final String PERFECTO_USER       = "MyUser";
    static final String PERFECTO_PASSWORD   = "MyPassword";
    ... 
```

### Configure AspectJ for your IDE
- For Intellij IDE follow Jetbrains' [AspectJ guide](https://www.jetbrains.com/help/idea/2016.3/aspectj.html). 
- For Eclipse IDE follow the [AspectJ guide](http://www.eclipse.org/aspectj/). 

### Hooking driver creation [RemoteWebDriverAspect.java](src/test/java/RemoteWebDriverAspect.java) : 
We use @Aspect annotations to control the RemoteWebDriver instance creation:<br/>
- By using [Before, After, Around](https://eclipse.org/aspectj/doc/next/progguide/semantics-advice.html) annotations we are able to wrap the RemoteWebDriver constructor and add some control operations. <br/>
For example, in the following code: <br/>
```Java 
@Before("call(org.openqa.selenium.remote.RemoteWebDriver+.new(..))")
@SuppressWarnings("PMD.AvoidCatchingThrowable")
public void remoteWebDriverBeforeAspect(JoinPoint joinPoint) throws Throwable {
    System.out.println("Before Creating driver...");
}
```
The *remoteWebDriverBeforeAspect* method will be executed before calling the constructor of every RemoteWebDriver instance. <br/> 
The *@Around* annotation enables us to control what happens both before and after the method we wrap with the annotation: <br/>
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

### Advanced Options (Retry count, delay ....)
We use [Constants](src/test/java/Constants.java) to determine the retry mechanism settings such as: Maximum times to retry and delay between each retry.<br/>
For example in [Constants](src/test/java/Constants.java) file: 
```Java
    // Java code ...
    //General constants
    static final int ImplicitlyWait     = 25;
    static final int PageLoadTimeout    = 25;
    static final int WebDriverWait      = 10;
    static final int MaxTimesToRetry    = 10;
    static final long WaitOnRetry       = 10000L;
     
    // Java code
```

The following code snippet includes the variables: <br/>
*MaxTimesToRetry* -  Maximum number of retries to create WebDriver instance. <br/>
*WaitOnRetry* - How long (milliseconds) to wait between each retry.

### RemoteWebDriverAspect overview
In this project we wrap the RemoteWebDriver constructor in an @Around annotation and execute the try-catch clause within a while loop. If the constructor throws an exception, the catch clause increments the count of retries and then lets the while loop retry the constructor.
