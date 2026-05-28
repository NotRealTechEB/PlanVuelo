package cl.dgac.planvuelo.mapper;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
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

    public static PlanVueloResponseDTO toResponseDTO(PlanVuelo request) {
        if (request == null) {
            return null;
        }
        return new PlanVueloResponseDTO(0, request.getIdPiloto(), request.getIdDrone(), request.getPsGPS(), null, request.getAltMax(), 
        request.getTiEst(), request.getRegion());
    }
}
