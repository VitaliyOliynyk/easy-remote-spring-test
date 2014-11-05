package eu.vitaliy.easyremote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.rmi.Remote;

public class EasyRemoteSpringServerImpl implements EasyRemoteSpringServer, Remote, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}