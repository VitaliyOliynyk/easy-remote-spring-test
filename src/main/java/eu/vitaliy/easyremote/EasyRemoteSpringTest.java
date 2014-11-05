package eu.vitaliy.easyremote;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class EasyRemoteSpringTest {

    protected void injectResources() throws IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String beanName = field.getName();
                Class fieldType = field.getType();
                field.setAccessible(true);
                Object bean = EasyRemoteTest.makeBean(beanName, fieldType);
                field.set(this, bean);
            }
        }
    }
}