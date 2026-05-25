package cl.dgac.planvuelo.mapper;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.model.PlanVuelo;

public class PlanVueloMapper {
    public static PlanVuelo toModel(CreatePlanVuelo request){
        return new PlanVuelo(0, request.psGPS(), request.fechaPDV(), request.altMax(), request.tiEst(), request.region());
    }

    public static PlanVuelo toModel(UpdatePlanVuelo request){
        return new PlanVuelo(0, request.psGPS(), request.fechaPDV(), request.altMax(), request.tiEst(), request.region());
    }
}
