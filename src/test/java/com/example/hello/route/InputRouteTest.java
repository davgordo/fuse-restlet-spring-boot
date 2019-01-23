package com.example.hello.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;


public class InputRouteTest extends CamelTestSupport {

    @Produce(uri = "direct:input")
    protected ProducerTemplate in;

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        InputRouteBuilder rb = new InputRouteBuilder();
        rb.setOut("mock:out");
        return rb;
    }

    @Test
    public void testSampleRoute() throws Exception {

        // expectations
        out.expectedMessageCount(1);
        out.expectedBodiesReceived("Hello Fuse");

        // exercise the route
        in.sendBody("Hello Fuse");

        // make assertions
        out.assertIsSatisfied(1);

    }

}
