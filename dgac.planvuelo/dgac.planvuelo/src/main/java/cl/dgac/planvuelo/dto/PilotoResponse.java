package cl.dgac.planvuelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PilotoResponse {
    private int rutPiloto;
    private String pNombrePiloto;
    private String sNombrePiloto;
    private String apPaternoPiloto;
    private String apMatenoPiloto;

}
