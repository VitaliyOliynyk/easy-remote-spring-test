package eu.vitaliy.easyremote;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RMISecurityManager;

public class EasyRemoteTest {
    protected static InitialContext context;

    public static Object makeBean(String beanName, Class fieldType) {
        return null;
    }

    protected static InitialContext getContext() {

        if(context == null){
            try {
                context = new InitialContext();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
        return context;
    }

    protected static <T> T getRemoteBean(String beanName, Class<T> beanInterface){
        System.setSecurityManager(new RMISecurityManager());
        String url = "rmi://localhost/";
        try {
            return (T) getContext().lookup(url+"/"+beanName);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}