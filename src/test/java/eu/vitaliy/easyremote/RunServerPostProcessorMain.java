package eu.vitaliy.easyremote;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunServerPostProcessorMain {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("easy-remote-spring-test-postprocessor-test-context.xml");
    }
}
