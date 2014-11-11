package eu.vitaliy.easyremote;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;
// -Djava.security.policy=D:\Rus-My-documents\idea-projects\easy-remote-spring-test\src\main\resources\client.policy
public class EasyRemoteServerTest {

    @Proxy
    private ITestBean testBean;

    @BeforeMethod
    public void setUp() throws Exception {
        EasyRemoteAnnotations.init(this);
    }

    @Test
    public void injectRemoteTest() throws Exception {
        EasyRemoteAnnotations.init(this);
        assertThat(testBean).isNotNull();
    }

    @Test//(enabled = false)
    public void remoteInvocationTest() throws Exception {
        //when
        String testResult = testBean.test();

        //then
        assertThat(testResult).isEqualTo(ITestBean.TEST);
    }
}
