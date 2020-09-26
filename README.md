# LiveProject Milestone 3 gateway
the gateway forwards requests with an access_token to the health metricsresource server(milestone2)

# Timing 
- 5 hours reading and research
- 3 hours spring gateway implementation https://github.com/doubleirish/ssia-milestone3-gateway
- 2 hours zuul gateway implementation https://github.com/doubleirish/ssia-milestone3-zuul

## Observations & Suggestions
- I was new to Project reactor and Webflux so reading up on those two chapters from Spring In Action and also the SSIA reactive chapter took a little time. 

- There's not a lot of documentation on the older Spring Zuul or newer Spring-Gateway projects in the Mannings resources so  Students might need to hit up some external resources 

- Students might need to return to the earlier projects to tweak ports and setup data. 

- I built both the Zuul and Spring gateway.  One interesting difference between then is that Zuul dropped the access_token in the header while Spring-Gateway kept the authentication tokens. This is easy to see by setting up a route to relay to httpbin.org/headers

- I created a development home page in the spring-gateway to make it easier to look up the stored routes 

- I'm glad that this gateway project was not paricularly difficult on it's own , as there was a little bit more research required and  the increased complexity of    working with  more servers 

- I did attempt to use a Spring gateway token relay but was unable to get it to function 


## Steps
1. configure the gateway server   to forward all requests with paths matching "/httpbin/**" to  http://httpbin.org 
1. configure the gateway server   to forward all requests with paths matching "/api/**" to the resource server(7070) 
1. start up the OAUTH(9191), Resource(7070) and Gateway(8080) servers

## Postman tests
1. verify a gateway route e.g sending a GET /httpbin/headers request to GATEWAY should relay to http://httpbin.org/headers
1. sending a GET /profile/admin to RESOURCE server should fail without an access token
1. sending a GET /api/profile/admin to GATEWAY server should fail without an access token 
1. send a POST to the OAUTH server  get an access_token for a user e.g admin or john
1. send a GET /profile/admin to RESOURCE server -> success
1. send a GET /api/profile/admin request to the GATEWAY server .The gateway server should proxy your request to the resource server  at the /profile/john endpoint. And you should see the result of your successful request
1. send a GET /api/metric/admin request to the GATEWAY ->success 

 

## configure gateway with a  simple route 
```
@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Add a simple re-route from: /httbin/** to: http://httpbin.org:80/**
                .route("httpbin", p -> p
                        .path(true, "/httpbin/**") // intercept calls to the /httpbin path
                        .filters(f -> f.stripPrefix( 1)) // strip the "/httpbin" prefix
                        .uri("http://httpbin.org:80")) // forward to httpbin
               .build();
    }
}
```
## send a /httpbin request to gateway
```
curl --location --request GET 'localhost:8080/httpbin/get'
```
 