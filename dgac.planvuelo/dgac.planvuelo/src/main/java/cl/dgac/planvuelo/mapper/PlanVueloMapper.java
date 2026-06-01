package cl.dgac.planvuelo.mapper;

import java.util.ArrayList;
import java.util.List;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.DronResponseDTO;
import cl.dgac.planvuelo.dto.PilotoResponseDTO;
import cl.dgac.planvuelo.dto.PlanVueloResponseDTO;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.model.PlanVuelo;

public class PlanVueloMapper {
    public static PlanVuelo toModel(CreatePlanVuelo request){
        return new PlanVuelo(0, null, null, null, null, 0, 0, null);
    }

    public static PlanVuelo toModel(UpdatePlanVuelo request){
        return new PlanVuelo(0, null, null, null, null, 0, 0, null);
    }



    public static PlanVueloResponseDTO toModel(PlanVuelo plan, PilotoResponseDTO piloto, DronResponseDTO dron) {
        if (plan == null) return null;

        PlanVueloResponseDTO dto = new PlanVueloResponseDTO();

        dto.setHoraDespegue(plan.getHoraDespegue());
        dto.setTiempoEstimado(plan.getTiempoEstimado());

        if (plan.getHoraDespegue() != null) {
            dto.setHoraAterrizajeEstimada(plan.getHoraDespegue().plusMinutes(plan.getTiempoEstimado()));
        }

        dto.setPsGPS(plan.getPsGPS());
        if (plan.getRegion() != null) {
            dto.setRegion(plan.getRegion().name());
        }
        
        if (piloto != null) {
            dto.setRutPiloto(piloto.getRutPiloto());

            String pNombre = piloto.getPNombrePiloto() != null ? piloto.getPNombrePiloto() : "";
            String sNombre = piloto.getSNombrePiloto() != null ? piloto.getSNombrePiloto() : "";
            String apPaterno = piloto.getApPaternoPiloto() != null ? piloto.getApPaternoPiloto() : "";
            String apMaterno = piloto.getApMaternoPiloto() != null ? piloto.getApMaternoPiloto() : "";

            dto.setNomCompPiloto((pNombre + " " + sNombre + " " + apPaterno + " " + apMaterno).trim());
        }

        if (dron != null) {
            dto.setNumeroRegistro(dron.getNumeroDrone()); 
        }
        
        return dto;
    }

    public static List<PlanVueloResponseDTO> toModelList(List<PlanVuelo> listaPlanes) {
        if (listaPlanes == null) return new ArrayList<>();
        
        List<PlanVueloResponseDTO> listaDtos = new ArrayList<>();
        
        for (PlanVuelo plan : listaPlanes) {
            PlanVueloResponseDTO dto = toModel(plan, null, null); 
            listaDtos.add(dto);
        }
        
        return listaDtos;
}
}
