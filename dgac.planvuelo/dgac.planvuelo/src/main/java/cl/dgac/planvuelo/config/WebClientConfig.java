package cl.dgac.planvuelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean(name="pilotoApiWebClient")
    public WebClient pilotoApiWebClient(){
        return WebClient.builder().baseUrl("http://localhost:8081").build();
    }

    @Bean(name="dronApiWebClient")
    public WebClient dronWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8085").build();
    }
}
