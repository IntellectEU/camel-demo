package com.intellecteu.camel.spring;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class PaymentSpringDemoMain {

    public static void main(String[] args) throws Exception {
        String contextName = "classpath:META-INF/spring/simple-spring.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(contextName);
        SpringCamelContext camel = SpringCamelContext.springCamelContext(context, false);

        camel.start();

        TimeUnit.SECONDS.sleep(60);

        camel.stop();
    }

}
