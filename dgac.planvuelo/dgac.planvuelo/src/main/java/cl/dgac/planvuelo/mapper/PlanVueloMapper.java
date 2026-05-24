package cl.dgac.planvuelo.mapper;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.model.PlanVuelo;

public class PlanVueloMapper {
    public static PlanVuelo toModel(CreatePlanVuelo request){
        return new PlanVuelo(idPlanVuelo, psGPS, fechaPDV, altMax, tiEst, region)
    }

    public static PlanVuelo toModel(UpdatePlanVuelo request){

    }
}
