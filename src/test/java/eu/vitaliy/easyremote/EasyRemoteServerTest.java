package eu.vitaliy.easyremote;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
// -Djava.security.policy=D:\Rus-My-documents\idea-projects\easy-remote-spring-test\src\main\resources\client.policy
public class EasyRemoteServerTest {

    @Proxy
    private ITestBean testBeanInterface;

    @Proxy
    private TestBean testBeanClass;

    @BeforeMethod
    public void setUp() throws Exception {
        EasyRemoteAnnotations.init(this);
    }

    @Test
    public void injectRemoteTest() throws Exception {
        EasyRemoteAnnotations.init(this);
        assertThat(testBeanInterface).isNotNull();
        assertThat(testBeanClass).isNotNull();
    }

    @Test(enabled = false)
    public void remoteInvocationInterfaceTest() throws Exception {
        //when
        String testResult = testBeanInterface.test();

        //then
        assertThat(testResult).isEqualTo(ITestBean.TEST);
    }

    @Test(enabled = false)
    public void remoteInvocationClassTest() throws Exception {
        //when
        String testResult = testBeanClass.test();

        //then
        assertThat(testResult).isEqualTo(ITestBean.TEST);
    }
}
