package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.tx.TransactionAttribute;

import java.lang.reflect.Field;

public class EasyRemoteAnnotations {

    public static void init(Object target)  {
        try {
            initImpl(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initImpl(Object target) throws IllegalAccessException {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Proxy.class)) {
                String beanName = field.getName();
                Class fieldType = field.getType();
                field.setAccessible(true);
                Object bean = EasyRemote.makeBean(beanName, fieldType, createTransactionAttribute(field.getAnnotation(Proxy.class)));
                field.set(target, bean);
            }
        }
    }

    private static TransactionAttribute createTransactionAttribute(Proxy proxyAnnotation) {
        return new TransactionAttribute(proxyAnnotation.transactional(), proxyAnnotation.transactionManager(), proxyAnnotation.rollback());
    }
}
