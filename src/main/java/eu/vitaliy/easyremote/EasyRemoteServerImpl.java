package eu.vitaliy.easyremote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.rmi.Remote;

public class EasyRemoteServerImpl implements EasyRemoteServer, Remote, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invokeLocal(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, Object[] parameters) {
        try {
            return invokeImpl(beanName, beanInterface, methodName, methodParameterTypes, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeImpl(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, Object[] parameters) throws Exception {
        Object bean = lookupLocal(beanName);
        Method beanMethod = bean.getClass().getMethod(methodName, methodParameterTypes);
        return beanMethod.invoke(bean, parameters);
    }

    private Object lookupLocal(String beanName) {
        return applicationContext.getBean(beanName);
    }
}