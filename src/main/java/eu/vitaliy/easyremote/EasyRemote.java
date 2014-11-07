package eu.vitaliy.easyremote;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Method;

public class EasyRemote {
    private static InitialContext context;
    private static ApplicationContext applicationContext;

    public static  <T> T makeBean(String beanName,  Class<T> beanInterface) {
         return makeProxyForInterface(new EasyRemoteSpringInvocationHandler(beanName, beanInterface), beanInterface);
    }

    private static ApplicationContext getApplicationContext() {
         if (applicationContext == null) {
             applicationContext = new ClassPathXmlApplicationContext("easy-remote-spring-test-client-context.xml");
         }
        return applicationContext;
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
        return getApplicationContext().getBean(beanName, beanInterface);
    }

    public static <T> T makeProxyForInterface(MethodHandler methodHandler, Class<T> ... beanInterface){
         return makeProxyFor(methodHandler, null, beanInterface);
    }

    public static <T> T makeProxyForClass(MethodHandler methodHandler, Class<T> superclass,  Class<?> ... beanInterface){
       return makeProxyFor(methodHandler, superclass, beanInterface);
    }

    /*
    * //factory.setHandler(methodHandler);
    * ((ProxyObject) instance ).setHandler(methodHandler);  //(instead deprecated)
    */
    private static <T> T makeProxyFor(MethodHandler methodHandler, Class<T> superclass,  Class<?> ... beanInterface){

        ProxyFactory factory = new ProxyFactory();
        if (superclass != null) {
            factory.setSuperclass(superclass);
        }

        if(beanInterface != null && beanInterface.length > 0){
            factory.setInterfaces(beanInterface);
        }
        factory.setHandler(methodHandler);

        Class<T> proxyClass = factory.createClass();

        try {
            return proxyClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static  class EasyRemoteSpringInvocationHandler implements MethodHandler {
        protected String beanName;
        protected Class beanInterface;

        public EasyRemoteSpringInvocationHandler(String beanName, Class beanInterface) {
            this.beanName = beanName;
            this.beanInterface = beanInterface;
        }

        @Override
        public Object invoke(Object o, Method thisMethod, Method proceed, Object[] objects) throws Throwable {
            Object remoteBean = getRemoteBean("easyRemoteClient", EasyRemoteServer.class);
            EasyRemoteServer easyRemoteServer = (EasyRemoteServer) remoteBean;
            System.out.println("Method name:" + thisMethod.getName());

            Object result =  invokeImpl(easyRemoteServer, o, thisMethod, proceed, objects);

            return result;
        }

        protected Object invokeImpl(EasyRemoteServer easyRemoteServer, Object o, Method thisMethod, Method proceed, Object[] objects) {
            System.out.println("invokeRemote");
            return easyRemoteServer.invokeLocal(beanName, beanInterface, thisMethod.getName(), thisMethod.getParameterTypes(), objects);
        }
    }
}