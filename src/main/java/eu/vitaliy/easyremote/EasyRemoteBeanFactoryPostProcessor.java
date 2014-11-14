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
        new EasyRemoteBeanDefinition().registerEasyRemoteBeanDefinition(beanDefinitionRegistry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
       /* EMPTY */
    }
}
