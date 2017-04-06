package com.intellecteu.camel.simple;

import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class PaymentJavaDemoMain {

    public static void main(String[] args) throws Exception {
        DefaultCamelContext camel = new DefaultCamelContext();
        PaymentRoute routes = new PaymentRoute();
        camel.addRoutes(routes);
        camel.start();

        TimeUnit.SECONDS.sleep(60);

        camel.stop();
    }

}
