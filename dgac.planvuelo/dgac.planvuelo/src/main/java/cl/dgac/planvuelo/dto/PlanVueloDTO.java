package cl.dgac.planvuelo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVueloDTO {

    private String codigoVuelo;
    private String numeroRegistro;
    private LocalDateTime horaDespegue;
    private LocalDateTime horaAterrizajeEstimada;
    private int tiempoEstimado;
    private String psGPS;
    private double altMax;
    private String region;
    private String estadoPV;
    
    PilotoDTO pilotoDTO;
    EmpresaMandanteDTO empDTO;
}
