package eu.vitaliy.easyremote;

public interface EasyRemoteServer {
    Object invokeLocal(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, Object[] parameters);
}