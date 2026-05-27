package cl.dgac.planvuelo.dto;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePlanVuelo (

    //ID Piloto
    @NotNull(message="Debe agregar ID de piloto")
    @NegativeOrZero(message="ID de piloto no puede ser negativo ni cero") int idPiloto,

    //ID Drone
    @NotNull(message="Debe agregar ID de dron")
    @NegativeOrZero(message="ID de dron no puede ser negativo ni cero") int idDrone,

    //Ubicación GPS
    @Size(max = 30, message = "La ubicación GPS no puede superar los 30 caracteres") String psGPS,

    //Fecha de plan de vuelo
    @NotNull(message = "Fecha del PLAN DE VUELO no puede ser NULL") LocalDateTime fechaPDV,

    //Altura máxima
    @NegativeOrZero(message = "La altura máxima no puede ser negativa o cero") double altMax,

    //Tiempo estimado
    @NegativeOrZero(message = "El tiempo estimado no puede ser negativo o cero") int tiEst,

    //Region
    @Size(max = 60, message = "La región no puede superar los 45 caracteres") String region,

    //Estado proceso
    @NotNull(message="El estado debe ser declarado") 
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")String estado


)
{

}
