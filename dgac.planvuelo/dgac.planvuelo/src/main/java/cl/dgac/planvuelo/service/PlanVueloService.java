package cl.dgac.planvuelo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.PilotoDTO;
import cl.dgac.planvuelo.dto.PlanVueloDTO;
import cl.dgac.planvuelo.exception.ResourceNotFoundException;
import cl.dgac.planvuelo.mapper.PlanVueloMapper;
import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.model.Region;
import cl.dgac.planvuelo.repository.PlanVueloRepository;

@Service
public class PlanVueloService {
    @Autowired
    private PlanVueloRepository planVueloRepository;
    private WebClient pilotoApiWebClient;
    private WebClient licenciaApiWebClient;

    public PlanVueloService(
            @Qualifier("pilotoApiWebClient") WebClient pilotoApiWebClient,
            @Qualifier("dronApiWebClient") WebClient dronApiWebClient,
            @Qualifier("licenciaApiWebClient") WebClient licenciaApiWebClient ){
        this.pilotoApiWebClient = pilotoApiWebClient;
        this.licenciaApiWebClient = licenciaApiWebClient;
    }

    //-------------------------------Metodos de administracion-------------------------------//

    //Metodos para mostrar los planes de vuelo registrados.

        //Todos los planes de vuelo
    public List<PlanVuelo> mostrarPlanesVuelo(){
        return planVueloRepository.findAll();
    }

        //Planes por ID
    public PlanVuelo encontrarPVById(int idPlanVuelo){
        return planVueloRepository.findById(idPlanVuelo).orElseThrow(() -> new ResourceNotFoundException("ID Plan de vuelo " + idPlanVuelo + " no existe."));
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

    
    //-------------------------------Metodos HU - Piloto-------------------------------//

    //Obtención del Plan de vuelos por RUT Piloto 

    public List<PlanVueloDTO> obtenerPlanByRut(String rutPiloto) {
        List<PlanVuelo> planes = planVueloRepository.findByRutPiloto(rutPiloto);

        if (planes.isEmpty()) {
            return new ArrayList<>();
        }

        PilotoDTO piloto = pilotoApiWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/pilotos/datos-piloto").queryParam("rut", rutPiloto).build())
            .retrieve().bodyToMono(PilotoDTO.class).block(); 
        
        return planes.stream().map(plan -> PlanVueloMapper.toModel(plan, piloto)).toList();
    }

    //Agregar un plan de vuelo

    public PlanVuelo agregarPlanesVuelo(PlanVuelo pV, CreatePlanVuelo cPV){
        pV.setRutPiloto(cPV.rutPiloto());
        pV.setRutEmpMandante(cPV.rutEmpMandante());
        pV.setNumeroRegistro(cPV.numeroRegistro());
        pV.setFechaPV(cPV.fecha());
        pV.setAltMax(cPV.altMax());
        pV.setPsGPS(cPV.psGPS());
        pV.setEstadoPV("Pendiente");

        Region region;
        try {
        String regionTexto = cPV.region().toUpperCase().trim();
        region = Region.valueOf(regionTexto);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La región '"+ cPV.region()+"' no es válida: ");
        }
        pV.setRegion(region);

        String codRut = cPV.rutPiloto().substring(2,6);
        String codReg = cPV.region().substring(0, 3).toUpperCase();
        String codDron = cPV.numeroRegistro().substring(0,2).toUpperCase();
        String codVuelo = "PV-"+ codRut + codReg + codDron;
        pV.setCodigoVuelo(codVuelo);
        return planVueloRepository.save(pV);
    }

    //Obtener datos por codigo de vuelo

    public PlanVueloDTO obtenerPlanByCodigo(String codigoVuelo) {
    PlanVuelo plan = planVueloRepository.findByCodigoVuelo(codigoVuelo); 
    if (plan == null) {
        throw new ResourceNotFoundException("El codigo '" + codigoVuelo + "' no coincide con un plan de vuelo registrado."); 
    }
    return PlanVueloMapper.toModel(plan, null); 
    }
}
