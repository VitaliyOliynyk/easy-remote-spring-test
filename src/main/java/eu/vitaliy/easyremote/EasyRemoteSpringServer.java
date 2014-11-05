package eu.vitaliy.easyremote;

public interface EasyRemoteSpringServer {
    Object invokeLocal(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, Object[] parameters);
}