package eu.vitaliy.easyremote;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.*;

public class EasyRemoteSpringServerTest extends EasyRemoteSpringTest {

    @Autowired(required = false)
    private ITestBean testBean;

    @Test
    public void contextTest() throws Exception {
        injectResources();
        assertThat(testBean).isNotNull();
    }
}
