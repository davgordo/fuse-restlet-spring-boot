package com.example.common.hello.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;


public class OutputRouteTest extends CamelTestSupport {

    @Produce(uri = "direct:in")
    protected ProducerTemplate in;

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        OutputRouteBuilder rb = new OutputRouteBuilder();
        rb.setIn("direct:in");
        rb.setOut("mock:out");
        return rb;
    }

    @Test
    public void testSampleRoute() throws Exception {

        // expectations
        out.expectedMessageCount(1);
        out.expectedBodiesReceived("Test Message");

        // exercise the route
        in.sendBody("Test Message");

        // make assertions
        out.assertIsSatisfied(1);

    }

}
