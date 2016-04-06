package com.ameliant.training.day2;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SplitRouteTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:out")
    MockEndpoint mockOut;

    @EndpointInject(uri = "mock:split")
    MockEndpoint mockSplit;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new SplitRoute();
    }

    @Test
    public void testRoute() throws InterruptedException {
        mockSplit.setExpectedMessageCount(3);
        mockSplit.expectedBodiesReceivedInAnyOrder("one", "two", "three");

        mockSplit.whenAnyExchangeReceived((Exchange exchange) -> {
            Message in = exchange.getIn();
            String body = in.getBody(String.class);
            in.setBody("Modified " + body);
        });

        mockOut.setExpectedMessageCount(1);
        mockOut.expectedBodiesReceived(
                "Modified one,Modified two,Modified three");

        template.sendBody("direct:in", "one,two,three");

        assertMockEndpointsSatisfied();
    }

}