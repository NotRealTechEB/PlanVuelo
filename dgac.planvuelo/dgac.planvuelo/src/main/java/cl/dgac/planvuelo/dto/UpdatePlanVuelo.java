package cl.dgac.planvuelo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import cl.dgac.planvuelo.model.Region;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdatePlanVuelo (

    //RUT PILOTO
    @NotNull(message = "El RUT del piloto es obligatorio")
    @Size(min = 8, max = 12, message = "El RUT debe tener entre 8 y 12 caracteres")String rutPiloto,

    //RUT EMPRESA MANDANTE
    @NotNull(message = "El RUT del piloto es obligatorio")
    @Size(min = 15, max = 15, message = "El RUT debe tener entre 8 y 12 caracteres")String rutEmpMandante,

    //REGISTRO DRON
    @NotNull(message = "Debe agregar el número de registro del dron")String numeroRegistro,

    //POSICION GPS
    @NotNull(message = "La ubicación GPS es obligatoria")
    @Size(max = 30, message = "La ubicación GPS no puede superar los 30 caracteres") String psGPS,

    //HORA DE DESPEGUE
    @NotNull(message = "La hora de despegue no puede ser nula") 
    @Future(message = "La hora de despegue debe ser una fecha futura")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime fecha,

    //ALTURA MAXIMA
    @NotNull(message = "La altura maxima no puede ser nula") 
    @Positive(message = "La altura máxima debe ser un valor positivo") Double altMax,

    //REGION
    @NotNull(message = "Debe ingresar la región") String region,

    //CODIGO VUELO
    String codVuelo
)
{
    public boolean isRegionValida() {
        if (region == null) 
            return false;
        try {
            Region.valueOf(region.toUpperCase().trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
