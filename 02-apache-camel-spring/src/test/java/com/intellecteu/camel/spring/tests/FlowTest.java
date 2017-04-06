package com.intellecteu.camel.spring.tests;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;

public class FlowTest extends BaseTestHelper {

    @Produce(uri = "file://samples/Payments")
    private ProducerTemplate injectPayment;

    @EndpointInject(uri = "mock:smallPayment")
    private MockEndpoint smallPayment;

    @EndpointInject(uri = "mock:interestingPayment")
    private MockEndpoint interestingPayment;

    @Before
    public void startContext() throws Exception {
        context.getRouteDefinition("RoutePayments").adviceWith(context,
                new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        weaveById("smallPayment")
                                .replace()
                                .to("mock:smallPayment");
                    }
                }
        );
        context.getRouteDefinition("RoutePayments").adviceWith(context,
                new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        weaveById("interestingPayment")
                                .replace()
                                .to("mock:interestingPayment");
                    }
                }
        );
        context.start();
    }

    @Test
    public void testBigAmount() throws Exception {
        smallPayment.expectedMessageCount(0);
        interestingPayment.expectedMessageCount(1);

        injectPayment.sendBody(loadResource("samples/bigAmount.xml"));

        smallPayment.assertIsSatisfied();
        interestingPayment.assertIsSatisfied();
    }

    @Test
    public void testSmallAmount() throws Exception {
        smallPayment.expectedMessageCount(1);
        interestingPayment.expectedMessageCount(0);

        injectPayment.sendBody(loadResource("samples/smallAmount.xml"));

        smallPayment.assertIsSatisfied();
        interestingPayment.assertIsSatisfied();
    }
}
