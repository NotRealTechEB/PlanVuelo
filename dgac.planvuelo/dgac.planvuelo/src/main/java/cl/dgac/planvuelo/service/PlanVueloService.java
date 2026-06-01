package cl.dgac.planvuelo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import cl.dgac.planvuelo.dto.DronResponseDTO;
import cl.dgac.planvuelo.dto.PilotoResponseDTO;
import cl.dgac.planvuelo.dto.PlanVueloResponseDTO;
import cl.dgac.planvuelo.exception.ResourceNotFoundException;
import cl.dgac.planvuelo.mapper.PlanVueloMapper;
import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.repository.PlanVueloRepository;

@Service
public class PlanVueloService {
    @Autowired
    private PlanVueloRepository planVueloRepository;
    private WebClient pilotoApiWebClient;
    private WebClient dronApiWebClient;

    public PlanVueloService(
            @Qualifier("pilotoApiWebClient") WebClient pilotoApiWebClient,
            @Qualifier("dronApiWebClient") WebClient dronApiWebClient) {
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
        return planVueloRepository.findById(idPlanVuelo).orElseThrow(() -> new ResourceNotFoundException("ID Plan de vuelo " + idPlanVuelo + " no existe."));
    }

        //Planes por Rut de piloto
    public List<PlanVuelo> pVByRutPiloto(String rutPiloto){
        List<PlanVuelo> listPV = planVueloRepository.findByRutPiloto(rutPiloto);
        if(listPV.isEmpty()){
            throw new ResourceNotFoundException("No hay planes registrados por el piloto, rut: " +rutPiloto);
        }
        return listPV;
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

    //Obtención del Plan de vuelo completo

    public List<PlanVueloResponseDTO> historialPlanesPorRut(String rutPiloto) {
    List<PlanVuelo> planes = planVueloRepository.findByRutPiloto(rutPiloto);
    
    if (planes.isEmpty()) {
        throw new ResourceNotFoundException("No hay planes de vuelo registrados del Rut: " + rutPiloto);
    }

    PilotoResponseDTO pilotoDto = obtenerResumenPiloto(rutPiloto);

    List<PlanVueloResponseDTO> respuestaDtos = new ArrayList<>();
        for (PlanVuelo plan : planes) {
            DronResponseDTO dronDto = obtenerDatosDron(plan.getNumeroDrone());
            
            PlanVueloResponseDTO dto = PlanVueloMapper.toModel(plan, pilotoDto, dronDto);
            respuestaDtos.add(dto);
        }

    return respuestaDtos;
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
