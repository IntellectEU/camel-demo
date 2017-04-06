package com.intellecteu.camel.simple;

import com.intellecteu.camel.simple.beans.PaymentDataBase;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;

import java.util.HashMap;
import java.util.Map;

public class PaymentRoute extends RouteBuilder {
    PaymentDataBase paymentDB;

    public PaymentRoute() {
        super();
        setupDB();
    }

    @Override
    public void configure() throws Exception {
        String inputPayments = "file://samples/Payments?delay=2000&delete=true";
        String fileNamePattern = "${date:now:yyyyMMdd.HHmmss}_${file:name.noext}.txt";
        String processed = "file://samples/Process?fileName=" + fileNamePattern;
        String verify = "file://samples/Verify?fileName=" + fileNamePattern;

        Namespaces xsd = new Namespaces("xsd", "simple.payment.xsd");

        from(inputPayments)
//                .placeholder("xmlns:xsd", "simple.payment.xsd")
                .routeId("RoutePayments")
                .log("Received payment : [${body}]")

                .setHeader("PaymentAmount")
                .xpath("/xsd:Payment/xsd:Amount/text()", String.class, xsd)

                .setHeader("Sender")
                .xpath("/xsd:Payment/xsd:Sender/text()", String.class, xsd)

                .choice()
                .when(method(paymentDB, "isUsualPayment"))
                .log("Usual boring payment, send to network")
                .to(processed)
                .otherwise()
                .log("OMG, so huge amount! Send to verification")
                .to(verify);
    }

    public void setupDB() {
        Map<String, Integer> paymentsInfo = new HashMap<>();
        paymentsInfo.put("Jon", 40);
        paymentsInfo.put("Fred", 200);

        this.paymentDB = new PaymentDataBase(paymentsInfo);
    }

}
