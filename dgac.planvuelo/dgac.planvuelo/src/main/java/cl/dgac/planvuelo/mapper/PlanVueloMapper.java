package cl.dgac.planvuelo.mapper;

import java.util.ArrayList;
import java.util.List;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.PilotoDTO;
import cl.dgac.planvuelo.dto.PlanVueloDTO;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.model.PlanVuelo;

public class PlanVueloMapper {
    public static PlanVuelo toModel(CreatePlanVuelo request){
        return new PlanVuelo(0, request.rutPiloto(), request.numeroRegistro(),request.psGPS(), request.horaDespegue(), request.tiempoEstimado()
        , request.altMax(), null, null, null);
    }

    public static PlanVuelo toModel(UpdatePlanVuelo request){
        return new PlanVuelo(0, request.rutPiloto(), request.numeroRegistro(),request.psGPS(), request.horaDespegue(), request.tiempoEstimado()
        , request.altMax(), null, null, null);
    }

    public static PlanVueloDTO toModel(PlanVuelo plan, PilotoDTO piloto) {
        PlanVueloDTO dto = new PlanVueloDTO();
        dto.setCodigoVuelo(plan.getCodigoVuelo());
        dto.setNumeroRegistro(plan.getNumeroRegistro());
        dto.setHoraDespegue(plan.getHoraDespegue());
        dto.setTiempoEstimado(plan.getTiempoEstimado());
        dto.setPsGPS(plan.getPsGPS());
        dto.setAltMax(plan.getAltMax());
        dto.setHoraAterrizajeEstimada(plan.getHoraDespegue().plusMinutes(plan.getTiempoEstimado()));
        dto.setRegion(plan.getRegion().name());
        dto.setEstadoPV(plan.getEstadoPV());
        dto.setPilotoDTO(piloto);

        return dto;
    }

    public static List<PlanVueloDTO> toModelList(List<PlanVuelo> listaPlanes) {
        if (listaPlanes == null) return new ArrayList<>();
        
        List<PlanVueloDTO> listaDtos = new ArrayList<>();
        
        for (PlanVuelo plan : listaPlanes) {
            PlanVueloDTO dto = toModel(plan, null);
            listaDtos.add(dto);
        }
        
        return listaDtos;
}
}
