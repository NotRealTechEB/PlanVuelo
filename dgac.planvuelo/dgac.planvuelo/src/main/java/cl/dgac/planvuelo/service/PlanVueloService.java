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
import cl.dgac.planvuelo.dto.EmpresaMandanteDTO;
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
    private WebClient empApiWebClient;

    public PlanVueloService(
            @Qualifier("pilotoApiWebClient") WebClient pilotoApiWebClient,
            @Qualifier("dronApiWebClient") WebClient dronApiWebClient,
            @Qualifier("empApiWebClient") WebClient empApiWebClient ){
        this.pilotoApiWebClient = pilotoApiWebClient;
        this.empApiWebClient = empApiWebClient;
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
        pV.setNumeroRegistro(cPV.numeroRegistro());
        pV.setHoraDespegue(cPV.horaDespegue());
        pV.setAltMax(cPV.altMax());
        pV.setPsGPS(cPV.psGPS());
        pV.setTiempoEstimado(cPV.tiempoEstimado());
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
        String codReg = cPV.region().substring(0, 2).toUpperCase();
        String codDron = cPV.numeroRegistro().substring(0,1).toUpperCase();
        String codVuelo = "PV-"+ codRut + codReg + codDron;
        pV.setCodigoVuelo(codVuelo);
        return planVueloRepository.save(pV);
    }

    //Comunicación a API de empresa mandante

    public EmpresaMandanteDTO obtenerDatosEmpresa(String nombre){
        try {
            return empApiWebClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1.5/Emandante/buscaNombre").queryParam("nombre", nombre)
                .build()).retrieve().bodyToMono(EmpresaMandanteDTO.class).block();
        } catch (Exception ex) {
            return null; 
        }
    }
}
