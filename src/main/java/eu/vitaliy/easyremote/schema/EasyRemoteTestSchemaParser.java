package eu.vitaliy.easyremote.schema;

import eu.vitaliy.easyremote.EasyRemoteBeanDefinition;
import eu.vitaliy.easyremote.EasyRemoteServerImpl;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


public class EasyRemoteTestSchemaParser extends AbstractSingleBeanDefinitionParser {


    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        new EasyRemoteBeanDefinition().registerEasyRemoteBeanDefinition(parserContext);
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return EasyRemoteServerImpl.class;
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }
}
