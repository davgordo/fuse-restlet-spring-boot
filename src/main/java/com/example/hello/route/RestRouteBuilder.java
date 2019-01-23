package com.example.hello.route;

import com.example.hello.model.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .contextPath("/hello").apiContextPath("/api-doc")
                    .apiProperty("api.title", "Hello API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true")
                    .apiProperty("schemes", "https" )
                    .apiContextRouteId("doc-api")
                .bindingMode(RestBindingMode.json)
                .component("servlet");

        rest("/message").description("Hello REST service")

                .get().produces("application/json").type(Message.class).description("get message")
                    .responseMessage().code(200).message("OK").endResponseMessage()
                    .route().routeId("get-message-api")
                    .removeHeaders("*")
                    .setBody(constant(new Message("Hello Platform")))
                    .log("mocking a response for a GET request")
                .endRest()

                .post().consumes("application/json").type(Message.class).description("create message")
                    .responseMessage().code(201).message("Created").endResponseMessage()
                    .route().routeId("create-message-api")
                    .to("direct:input")
                .endRest();

    }

}
