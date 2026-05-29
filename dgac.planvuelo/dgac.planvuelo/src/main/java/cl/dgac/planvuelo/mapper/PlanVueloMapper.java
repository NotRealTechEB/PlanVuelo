package cl.dgac.planvuelo.mapper;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.DatosDronDTO;
import cl.dgac.planvuelo.dto.DatosPilotoDTO;
import cl.dgac.planvuelo.dto.PlanVueloResponseDTO;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.model.PlanVuelo;

public class PlanVueloMapper {
    public static PlanVuelo toModel(CreatePlanVuelo request){
        return new PlanVuelo(0, request.idPiloto(), request.idDrone(), request.psGPS(), request.fechaPDV(), 
        request.altMax(), request.tiEst(), request.region(), request.estado());
    }

    public static PlanVuelo toModel(UpdatePlanVuelo request){
        return new PlanVuelo(0, request.idPiloto(), request.idDrone(), request.psGPS(), request.fechaPDV(), 
        request.altMax(), request.tiEst(), request.region(), request.estado());
    }

    public static PlanVueloResponseDTO toModel(PlanVuelo plan, DatosPilotoDTO piloto, DatosDronDTO dron) {
        PlanVueloResponseDTO dto = new PlanVueloResponseDTO();
        
        dto.setIdPlanVuelo(plan.getIdPlanVuelo());
        dto.setPsGPS(plan.getPsGPS());
        dto.setFechaPDV(plan.getFechaPDV());
        dto.setAltMax(plan.getAltMax());
        dto.setTiEst(plan.getTiEst());
        dto.setRegion(plan.getRegion());
        
        dto.setIdPiloto(piloto.getIdPiloto());

        dto.setIdDrone(dron.getIdDrone());
        
        return dto;
    }
}
