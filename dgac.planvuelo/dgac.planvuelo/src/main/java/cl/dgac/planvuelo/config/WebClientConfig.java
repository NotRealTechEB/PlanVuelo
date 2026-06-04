package cl.dgac.planvuelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean(name="pilotoApiWebClient")
    public WebClient pilotoApiWebClient(){
        return WebClient.builder().baseUrl("http://localhost:8086").build();
    }

    @Bean(name="dronApiWebClient")
    public WebClient dronWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8083").build();
    }

    @Bean(name="empApiWebClient")
    public WebClient empWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8088").build();
    }
}
