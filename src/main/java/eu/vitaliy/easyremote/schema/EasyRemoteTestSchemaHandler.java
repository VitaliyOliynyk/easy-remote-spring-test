package eu.vitaliy.easyremote.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class EasyRemoteTestSchemaHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("configure", new EasyRemoteTestSchemaParser());
    }
}
