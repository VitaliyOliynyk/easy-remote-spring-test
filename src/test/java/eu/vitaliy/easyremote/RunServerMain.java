package eu.vitaliy.easyremote;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunServerMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("easy-remote-spring-test-test-context.xml");
    }
}
