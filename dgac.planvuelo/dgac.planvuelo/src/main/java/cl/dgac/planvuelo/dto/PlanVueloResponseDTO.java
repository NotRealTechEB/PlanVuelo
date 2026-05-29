package cl.dgac.planvuelo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVueloResponseDTO {
    private int idPlanVuelo;
    private String psGPS;
    private LocalDateTime fechaPDV;
    private double altMax;
    private int tiEst;
    private String region;
    
    private int idPiloto;
    private String nomCompPiloto;
    private int rutPiloto;

    private Long idDrone;
    private String numeroRegistro;
}
