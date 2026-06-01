package cl.dgac.planvuelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PilotoResponseDTO {
    private String rutPiloto;
    private String pNombrePiloto;
    private String sNombrePiloto;
    private String apPaternoPiloto;
    private String apMaternoPiloto;

}
 