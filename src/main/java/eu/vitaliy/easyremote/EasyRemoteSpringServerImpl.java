package eu.vitaliy.easyremote;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.rmi.Remote;

public class EasyRemoteSpringServerImpl implements EasyRemoteSpringServer, Remote, ApplicationContextAware {

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
        Object bean;
        bean = lookupLocal(beanName);
        Method beanMethod = bean.getClass().getMethod(methodName, methodParameterTypes);
        Object result = beanMethod.invoke(bean, parameters);
        return result;
    }

    private Object lookupLocal(String beanName) {
        return applicationContext.getBean(beanName);
    }
}