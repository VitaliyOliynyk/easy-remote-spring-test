package eu.vitaliy.easyremote;

import org.mockito.MockitoAnnotations;

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
        MockitoAnnotations.initMocks(target);
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Proxy.class)) {
                String beanName = field.getName();
                Class fieldType = field.getType();
                field.setAccessible(true);
                Object bean = EasyRemote.makeBean(beanName, fieldType);
                field.set(target, bean);
            }
        }
    }
}