#LiveProject Milestone 3 gateway

### overview

### testing barebones gateway

#### initializer

#### Test gateway with a simple route 
```
@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // Add a simple re-route from: /get to: http://httpbin.org:80/get
                .route("get", p -> p
                        .path("/get") // intercept calls to the /demo path
                        .filters(f -> f.addRequestHeader("Hello", "World")) // add header
                        .uri("http://httpbin.org:80")) // forward to httpbin
               
    }
}
```
send a postman request to gateway
```

```
#### enable the /oauth/token_keys endpoint on the OAUTH server 
```
@Configuration
@EnableAuthorizationServer
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {

...

    @Override
    public void configure( AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("isAuthenticated()");  // enable /oauth/token_key  endpoint for public for any authenticated request
    }
}
```



### Usage
1. Start OAuth Server, Resource serer and  Gateway Server on differet ports
1. you may have to add/update the ```server.port``` property in the application.properties file for each server


