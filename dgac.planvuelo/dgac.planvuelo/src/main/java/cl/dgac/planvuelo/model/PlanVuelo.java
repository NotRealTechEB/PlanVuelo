package cl.dgac.planvuelo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PlanVuelo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanVuelo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idPlanVuelo")
    private int idPlanVuelo;

    @Column(name="idPiloto")
    private int idPiloto;

    @Column(name="idDrone")
    private int idDrone;

    @Column(name = "ubicacionGPS", nullable=false, length = 30)
    private String psGPS;

    @Column(name = "fechaPV", nullable=false)
    private LocalDateTime fechaPDV;

    @Column(name = "alturaMaxima", nullable=false)
    private double altMax;

    @Column(name = "tiempoEstimadoMinutos", nullable=false)
    private int tiEst;

    @Column(name = "region", nullable=false, length=45)
    private String region;

    @Column(name= "estadoProceso", nullable=false, length=30)
    private String estado;

}
