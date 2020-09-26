package com.manning.ssia.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

//    @Autowired
//    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Add a simple re-route from: /get to: http://httpbin.org:80
                .route("httpbin", p -> p
                        .path(true, "/httpbin/**") // intercept calls to the /demo path
                       // .filters(f -> f.addRequestHeader("Hello", "World")) // add header
                        .filters(f -> f.stripPrefix( 1)) // strip the "/httpbin" prefix
                        .uri("http://httpbin.org:80")) // forward to httpbin
//                .route("oauth-server", p -> p
//                        .path("/oauth")
//                        .uri("http://localhost:9191"))
                .route("resource-server", p -> p
                        .path("/api/**")
 //                       .filters(f -> f.filter(filterFactory.apply())) // forward oauth filters
                          .filters(f -> f.stripPrefix( 1)) // strip the "/httpbin" prefix
                          .uri("http://localhost:7070"))
                .build();
    }
}
