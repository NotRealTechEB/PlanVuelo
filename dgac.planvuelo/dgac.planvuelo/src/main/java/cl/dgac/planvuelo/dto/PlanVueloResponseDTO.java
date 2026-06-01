package cl.dgac.planvuelo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVueloResponseDTO {

    private LocalDateTime horaDespegue;
    private LocalDateTime horaAterrizajeEstimada;
    private int tiempoEstimado;

    private String psGPS;

    private double altMax;

    private String region;
    
    private String nomCompPiloto;
    private String rutPiloto;

    private String numeroRegistro;
}
