package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.marshal.Marshaler;
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
    public Object invokeLocal(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, String marshaledParameters) {
        try {
            return invokeImpl(beanName, beanInterface, methodName, methodParameterTypes, marshaledParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeImpl(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, String marshaledParameters) throws Exception {
        Object bean = lookupLocal(beanName);
        Method beanMethod = bean.getClass().getMethod(methodName, methodParameterTypes);
        Object[] unmarshalazed = Marshaler.unmarshal(methodParameterTypes, marshaledParameters);
        Object result = beanMethod.invoke(bean, unmarshalazed);

        String marshaled = Marshaler.marshal(beanMethod.getReturnType(), result);
        return marshaled;
    }

    private Object lookupLocal(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
