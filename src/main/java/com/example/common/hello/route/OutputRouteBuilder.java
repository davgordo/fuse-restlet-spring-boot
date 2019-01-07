package com.example.common.hello.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OutputRouteBuilder extends RouteBuilder {

    private String in = "amq:queue:hello.message";

    private String out = "mock:out";

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Override
    public void configure() throws Exception {

        from(in)
                .id("dequeue-messages")
                .log("consumed message ${body} from AMQ")
                .to(out);

    }

}
