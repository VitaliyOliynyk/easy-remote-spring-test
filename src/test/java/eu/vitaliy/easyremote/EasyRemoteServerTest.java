package eu.vitaliy.easyremote;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import static org.fest.assertions.api.Assertions.assertThat;

// -Djava.security.policy=D:\Rus-My-documents\idea-projects\easy-remote-spring-test\src\main\resources\client.policy
public class EasyRemoteServerTest {

    @Proxy
    private ITestBean testBeanInterface;

    @Proxy
    private TestBean testBeanClass;

    @BeforeClass
    public void setUp() throws Throwable {
        assertThat(startServer()).as("Server is not started").isTrue();
        EasyRemoteAnnotations.init(this);
    }

    @Test
    public void injectRemoteTest() throws Exception {
        EasyRemoteAnnotations.init(this);
        assertThat(testBeanInterface).isNotNull();
        assertThat(testBeanClass).isNotNull();
    }

    @Test
    public void remoteInvocationInterfaceTest() throws Exception {
        //when
        String testResult = testBeanInterface.test();

        //then
        assertThat(testResult).isEqualTo(ITestBean.TEST);
    }

    @Test
    public void remoteInvocationClassTest() throws Exception {
        //when
        String testResult = testBeanClass.test();

        //then
        assertThat(testResult).isEqualTo(ITestBean.TEST);
    }

    private boolean startServer() throws Throwable {
        return  Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    RunServerMain.main(null);
                    return true;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }).get();
    }
}
