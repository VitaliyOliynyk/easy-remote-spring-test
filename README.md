Welcome to "Easy remote spring test" project
-----------------------------
"Easy remote spring test" picks up where unit tests leave off, focusing on the integration of application code inside a real runtime environment.

Quick start
===========
-   Step 1 - clone and install the easy-remote-spring-test git repository: 
```bash
git clone git@github.com:VitaliyOliynyk/easy-remote-spring-test.git
cd easy-remote-spring-test
mvn install
```
-   Step 2 - add maven dependency:
```xml
<dependency>
    <groupId>eu.vitaliy</groupId>
    <artifactId>easy-remote-spring-test</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

```
- Step 3 - add configurations tp spring context
```xml
<bean class="eu.vitaliy.easyremote.EasyRemoteBeanFactoryPostProcessor"/>
```
- Step 4 - Run Your App
- Step 5 - Write integration test, use it like this: 
```java
import eu.vitaliy.easyremote.EasyRemoteAnnotations;
import eu.vitaliy.easyremote.Proxy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
...
public class EasyRemoteSpringTestExample {

    @Proxy
    YourRemoteService yourRemoteService;

    @BeforeClass
    public void setUp() {
        EasyRemoteAnnotations.init(this);
    }

    @Test
    public void testRemoteMethod() {
		String expectedResult = ...
        String result = yourRemoteService.getResult();
        assertEquals(result, expectedResult);
    }
}

```
