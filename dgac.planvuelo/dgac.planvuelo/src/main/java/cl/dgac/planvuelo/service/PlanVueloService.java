package cl.dgac.planvuelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ComunicacionApi comunicacionApis;

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

    public PlanVueloResponseDTO planVueloCompleto(String rutPiloto) {
        List<PlanVuelo> plan = planVueloRepository.findByRutPiloto(rutPiloto);
        if (plan == null) {
        throw new ResourceNotFoundException("ID Plan de vuelo " + rutPiloto + " no existe.");
    }
        PilotoResponseDTO piloto = comunicacionApis.obtenerResumenPiloto(rutPiloto)
        DronResponseDTO dron = comunicacionApis.obtenerDatosDron();

        return PlanVueloMapper.toModel(plan, piloto, dron);
    }
}
