package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.marshal.Marshaler;
import eu.vitaliy.easyremote.tx.TransactionAttribute;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.function.Supplier;

import static eu.vitaliy.easyremote.util.ExceptionUtil.wrapException;

public class EasyRemoteServerImpl implements EasyRemoteServer, Remote, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invokeLocal(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, String marshaledParameters, TransactionAttribute transactionAttribute) {
        try {
            return invokeImpl(beanName, beanInterface, methodName, methodParameterTypes, marshaledParameters, transactionAttribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeImpl(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, String marshaledParameters, TransactionAttribute transactionAttribute) throws Exception {
        Object bean = lookupLocal(beanName);
        Method beanMethod = bean.getClass().getMethod(methodName, methodParameterTypes);
        Object[] unmarshalledParams = Marshaler.unmarshal(methodParameterTypes, marshaledParameters);
        Object result = invokeBeanMethod(bean, beanMethod, unmarshalledParams, transactionAttribute);

        String marshaled = Marshaler.marshal(beanMethod.getReturnType(), result);
        return marshaled;
    }

    private Object invokeBeanMethod(Object bean, Method beanMethod, Object[] unmarshalledParams, TransactionAttribute transactionAttribute) throws IllegalAccessException, InvocationTargetException {
        Supplier<Object> supplierResult = () -> wrapException(() -> {
            return beanMethod.invoke(bean, unmarshalledParams);
        });

        if (transactionAttribute.isTransactional()) {
            PlatformTransactionManager transactionManager = applicationContext.getBean(transactionAttribute.getTransactionManager(), PlatformTransactionManager.class);
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            return transactionTemplate.execute(transactionStatus -> {
                Object result = supplierResult.get();
                if (transactionAttribute.isRollback()) {
                    transactionStatus.setRollbackOnly();
                }
                return result;
            });
        } else {
            return supplierResult.get();
        }
    }

    private Object lookupLocal(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
