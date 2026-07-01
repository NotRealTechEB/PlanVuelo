package cl.dgac.planvuelo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean(name="pilotoApiWebClient")
    public WebClient pilotoApiWebClient(){
        return WebClient.builder().baseUrl("https://piloto-dfcf.onrender.com").build();
    }

    @Bean(name="dronApiWebClient")
    public WebClient dronWebClient() {
        return WebClient.builder().baseUrl("http://localhost:8083").build();
    }

    @Bean(name="licenciaApiWebClient")
    public WebClient empWebClient() {
        return WebClient.builder().baseUrl("https://licencia-bvee.onrender.com").build();
    }
}
