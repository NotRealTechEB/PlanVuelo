package cl.dgac.planvuelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.dgac.planvuelo.dto.DatosDronDTO;
import cl.dgac.planvuelo.dto.DatosPilotoDTO;
import cl.dgac.planvuelo.dto.PlanVueloResponseDTO;
import cl.dgac.planvuelo.mapper.PlanVueloMapper;
import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.repository.PlanVueloRepository;

@Service
public class PlanVueloService {
    @Autowired
    private PlanVueloRepository planVueloRepository;
    private WebClient pilotoApiWebClient;
    private WebClient dronApiWebClient;

    public PlanVueloService(@Qualifier("pilotoApiWebClient")WebClient pilotoApiWebClient, 
                            @Qualifier("dronApiWebClient")WebClient dronApiWebClient) {
        this.pilotoApiWebClient = pilotoApiWebClient;
        this.dronApiWebClient = dronApiWebClient;
    }

    //Metodos para mostrar los planes de vuelo registrados.

        //Todos los planes de vuelo
    public List<PlanVuelo> mostrarPlanesVuelo(){
        return planVueloRepository.findAll();
    }

        //Planes por ID
    public PlanVuelo encontrarPVById(int idPlanVuelo){
        return planVueloRepository.findById(idPlanVuelo).orElseThrow(() -> new RuntimeException("ID Plan de vuelo " + idPlanVuelo + " no existe."));
    }

    //Método para agregar un plan de vuelo

    public PlanVuelo agregarPlanesVuelo(PlanVuelo pV){
        return planVueloRepository.save(pV);
    }

    //Actualizar datos de plan de vuelo

    public PlanVuelo actualizarPlanesVuelo(PlanVuelo planPV){
        return planVueloRepository.save(planPV);
    }

    //Eliminar plan de vuelo

    public String eliminarPlanesVuelo(int idPlanVuelo){
        planVueloRepository.deleteById(idPlanVuelo);
        return "El plan de vuelo ha sido eliminado";
    }


    public PlanVueloResponseDTO planVueloCompleto(int idPlanVuelo) {
        PlanVuelo plan = encontrarPVById(idPlanVuelo);

        DatosPilotoDTO piloto = obtenerResumenPiloto(plan.getIdPiloto());
        DatosDronDTO dron = obtenerDatosDron(null);

        return PlanVueloMapper.toModel(plan, piloto, dron);
    }


    // Comunicación a API de Pilotos 

    @Qualifier("pilotoApiWebClient")
    public DatosPilotoDTO obtenerResumenPiloto(int idPiloto) {
        try {
            return pilotoApiWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/dgac/piloto/resumen").queryParam("idPiloto", idPiloto)
                .build()).retrieve().bodyToMono(DatosPilotoDTO.class).block();
        } catch (Exception ex) {
            return null; 
        }
    }

    //Comunicación a API de Drones

    @Qualifier("dronApiWebClient")
    public DatosDronDTO obtenerDatosDron(Long idDrone){
        try {
            return dronApiWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/drones").queryParam("idDrone", idDrone)
                .build()).retrieve().bodyToMono(DatosDronDTO.class).block();
        } catch (Exception ex) {
            return null; 
        }
    }


}
