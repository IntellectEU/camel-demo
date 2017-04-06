package com.intellecteu.camel.simple.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class PaymentDataBase {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentDataBase.class);

    private Map<String, Integer> usualPayments = new HashMap<>();

    public PaymentDataBase(Map<String, Integer> payments){
        this.usualPayments = payments;
    }

    public boolean isUsualPayment(Exchange exchange) throws Exception {
        String sender = exchange.getIn().getHeader("Sender", String.class);
        if (usualPayments.containsKey(sender)) {
            Integer usualAmount = usualPayments.get(sender);
            LOG.info("\n Usual payment for {} is {}", sender, usualAmount);

            Integer currentAmount = exchange.getIn().getHeader("PaymentAmount", Integer.class);
            return currentAmount.compareTo(usualAmount) < 0;
        } else {
            LOG.error("\n We don't know him {}", sender);
            throw new Exception("Unknown sender");
        }
    }
}
