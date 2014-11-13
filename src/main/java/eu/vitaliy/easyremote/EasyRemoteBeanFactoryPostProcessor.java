package eu.vitaliy.easyremote;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.remoting.rmi.RmiServiceExporter;

public class EasyRemoteBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    public static final String EASY_REMOTE_SERVER_BEAN_NAME = "easyRemoteServer";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        if (beanDefinitionRegistry.containsBeanDefinition(EASY_REMOTE_SERVER_BEAN_NAME)) {
            return;
        }

        RootBeanDefinition beanDef = new RootBeanDefinition(EasyRemoteServerImpl.class);
        beanDefinitionRegistry.registerBeanDefinition(EASY_REMOTE_SERVER_BEAN_NAME, beanDef);

        beanDef = new RootBeanDefinition(RmiServiceExporter.class);
        beanDef.setScope(BeanDefinition.SCOPE_SINGLETON);

        MutablePropertyValues mpv = new MutablePropertyValues();

        mpv.addPropertyValue("serviceName", "EasyRemoteServer");
        mpv.addPropertyValue("service", new RuntimeBeanReference(EASY_REMOTE_SERVER_BEAN_NAME));
        mpv.addPropertyValue("serviceInterface", EasyRemoteServer.class.getName());
        mpv.addPropertyValue("registryPort", "11199");
        beanDef.setPropertyValues(mpv);
        beanDefinitionRegistry.registerBeanDefinition("easyRemoteServerRmiServiceExporter", beanDef);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
       /* EMPTY */
    }
}
