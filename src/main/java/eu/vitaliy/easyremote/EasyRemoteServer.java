package eu.vitaliy.easyremote;

import eu.vitaliy.easyremote.tx.TransactionAttribute;

public interface EasyRemoteServer {
    Object invokeLocal(String beanName, Class beanInterface, String methodName, Class[] methodParameterTypes, String marshaledParameters, TransactionAttribute transactionAttribute);
}
