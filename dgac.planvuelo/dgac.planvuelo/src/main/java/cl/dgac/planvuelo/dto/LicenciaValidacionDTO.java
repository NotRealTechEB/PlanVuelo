package cl.dgac.planvuelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenciaValidacionDTO {
    private String rutPiloto;
    private boolean estValidacion;
    private String anotacion;
}
