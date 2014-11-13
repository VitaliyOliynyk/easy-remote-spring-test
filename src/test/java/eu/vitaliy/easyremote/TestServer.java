package eu.vitaliy.easyremote;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestServer {

    public static void run(String xmlApplicationContext) {
        new ClassPathXmlApplicationContext(xmlApplicationContext);
        System.out.println("..... start RMI server .....");
    }

}
