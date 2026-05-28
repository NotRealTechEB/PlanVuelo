package cl.dgac.planvuelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.repository.PlanVueloRepository;

@Service
public class PlanVueloService {
    @Autowired

    private PlanVueloRepository planVueloRepository;

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

}
