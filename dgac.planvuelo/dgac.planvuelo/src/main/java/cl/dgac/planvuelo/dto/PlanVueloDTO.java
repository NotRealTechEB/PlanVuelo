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
    private String rutEmpresaMandante;
    private String numeroRegistro;
    private LocalDateTime fechaPV;
    private String psGPS;
    private double altMax;
    private String region;
    private String estadoPV;
    
    PilotoDTO pilotoDTO;
}
