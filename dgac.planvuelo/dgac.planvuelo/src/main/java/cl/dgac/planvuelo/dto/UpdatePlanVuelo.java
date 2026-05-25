package cl.dgac.planvuelo.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.Size;

public record UpdatePlanVuelo (

    //Ubicación GPS
    @Size(max = 30, message = "La ubicación GPS no puede superar los 30 caracteres") String psGPS,

    //Fecha de plan de vuelo
    @NegativeOrZero(message = "Fecha del PLAN DE VUELO no puede ser negativa o igual a zero")
    @Digits(integer = 6, fraction=0, message = "La fecha debe contener 6 digitos en formato DDMMAAAA") int fechaPDV,

    //Altura máxima
    @NegativeOrZero(message = "La altura máxima no puede ser negativa o cero") double altMax,

    //Tiempo estimado
    @NegativeOrZero(message = "El tiempo estimado no puede ser negativo o cero") double tiEst,

    //Region
    @Size(max = 60, message = "La región no puede superar los 60 caracteres") String region
)
{
}
