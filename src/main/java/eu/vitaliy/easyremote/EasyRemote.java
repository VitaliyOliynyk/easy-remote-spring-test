package eu.vitaliy.easyremote;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.InitialContext;
import java.lang.reflect.Method;

public class EasyRemote {
    private static InitialContext context;
    private static ApplicationContext applicationContext;
    private static EasyRemoteServer easyRemoteServer;

    public static  <T> T makeBean(String beanName,  Class<T> proxyType) {
        if (proxyType.isInterface()) {
            return makeProxyForInterface(new EasyRemoteSpringInvocationHandler(beanName, proxyType), proxyType);
        } else {
            return makeProxyForClass(new EasyRemoteSpringInvocationHandler(beanName, proxyType), proxyType);
        }

    }

    private static ApplicationContext getApplicationContext() {
         if (applicationContext == null) {
             applicationContext = new ClassPathXmlApplicationContext("easy-remote-spring-test-client-context.xml");
         }
        return applicationContext;
    }

    private static EasyRemoteServer getEasyRemoteServer(){
        if (easyRemoteServer == null) {
            easyRemoteServer =  getApplicationContext().getBean("easyRemoteClient", EasyRemoteServer.class);
        }
        return easyRemoteServer;
    }

    private static <T> T getRemoteBean(String beanName, Class<T> beanInterface){
        return getApplicationContext().getBean(beanName, beanInterface);
    }

    private static <T> T makeProxyForInterface(MethodHandler methodHandler, Class<T> ... beanInterface){
         return makeProxyFor(methodHandler, null, beanInterface);
    }

    private static <T> T makeProxyForClass(MethodHandler methodHandler, Class<T> superclass,  Class<?> ... beanInterface){
       return makeProxyFor(methodHandler, superclass, beanInterface);
    }

    /*
    * //factory.setHandler(methodHandler);
    * ((ProxyObject) instance ).setHandler(methodHandler);  //(instead deprecated)
    */
    private static <T> T makeProxyFor(MethodHandler methodHandler, Class<T> superclass,  Class<?> ... beanInterface){

        ProxyFactory factory = new ProxyFactory();
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method m) {
                // ignore finalize()
                return !m.getName().equals("finalize");
            }
        });

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
        protected Class proxyType;

        public EasyRemoteSpringInvocationHandler(String beanName, Class proxyType) {
            this.beanName = beanName;
            this.proxyType = proxyType;
        }

        @Override
        public Object invoke(Object o, Method thisMethod, Method proceed, Object[] objects) throws Throwable {
            Object remoteBean = getEasyRemoteServer();
            EasyRemoteServer easyRemoteServer = (EasyRemoteServer) remoteBean;

            System.out.println("Method name:" + thisMethod.getName());
            return invokeImpl(easyRemoteServer, o, thisMethod, proceed, objects);
        }

        protected Object invokeImpl(EasyRemoteServer easyRemoteServer, Object o, Method thisMethod, Method proceed, Object[] objects) {
            System.out.println("invokeRemote");
            return easyRemoteServer.invokeLocal(beanName, proxyType, thisMethod.getName(), thisMethod.getParameterTypes(), objects);
        }
    }
}