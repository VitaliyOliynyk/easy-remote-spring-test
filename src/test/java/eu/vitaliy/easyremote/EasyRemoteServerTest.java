package eu.vitaliy.easyremote;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
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
       EasyRemoteAnnotations.init(this);
    }

    @Test
    public void injectRemoteTest() throws Exception {
        EasyRemoteAnnotations.init(this);
        assertThat(testBeanInterface).isNotNull();
        assertThat(testBeanClass).isNotNull();
    }

    @Test(dataProvider = "remoteInvocationTestData")
    public void remoteInvocationTest(String contextXmlFile) throws Throwable {
        //given
        assertThat(startServer(contextXmlFile)).as("Server is not started").isTrue();

        //when then
        checkBean(testBeanClass);
        checkBean(testBeanInterface);
    }

    private void checkBean(ITestBean testBean) {
        //when
        String testResult = testBean.test();

        //then
        assertThat(testResult).isEqualTo(ITestBean.TEST);
    }

    @DataProvider(name = "remoteInvocationTestData")
	Object[][] remoteInvocationTestData() {
		EasyRemoteAnnotations.init(this);
		return new Object[][] {
                new Object[] { "easy-remote-spring-test-test-context.xml" },
				new Object[] { "easy-remote-spring-test-postprocessor-test-context.xml" },
				new Object[] { "easy-remote-spring-test-schema-test-context.xml" },
        };
	}

    private boolean startServer(final String contextXmlFile) throws Throwable {
        return  Executors.newSingleThreadExecutor().submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    TestServer.run(contextXmlFile);
                    return true;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }).get();
    }
}
