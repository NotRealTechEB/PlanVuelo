package cl.dgac.planvuelo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.dgac.planvuelo.dto.DronResponseDTO;
import cl.dgac.planvuelo.dto.PilotoResponseDTO;


@Service
public class ComunicacionApi {

    private WebClient pilotoApiWebClient;
    private WebClient dronApiWebClient;

    public ComunicacionApi(
            @Qualifier("pilotoApiWebClient") WebClient pilotoApiWebClient,
            @Qualifier("dronApiWebClient") WebClient dronApiWebClient) {
        this.pilotoApiWebClient = pilotoApiWebClient;
        this.dronApiWebClient = dronApiWebClient;
    }
    
    // Comunicación a API de Pilotos 

    public PilotoResponseDTO obtenerResumenPiloto(String rutPiloto) {
        try {
            return pilotoApiWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/piloto/resumen").queryParam("rut", rutPiloto)
                .build()).retrieve().bodyToMono(PilotoResponseDTO.class).block();
        } catch (Exception ex) {
            return null;
        }
    }

    //Comunicación a API de Drones

    public DronResponseDTO obtenerDatosDron(String numDron){
        try {
            return dronApiWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/drones").queryParam("numeroDrone", numDron)
                .build()).retrieve().bodyToMono(DronResponseDTO.class).block();
        } catch (Exception ex) {
            return null; 
        }
    }
}
