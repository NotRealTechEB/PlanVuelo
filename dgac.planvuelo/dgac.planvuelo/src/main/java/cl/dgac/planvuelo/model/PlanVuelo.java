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

    @Column(name="rutPiloto", nullable = false, length=12)
    private String rutPiloto;

    @Column(name = "numeroRegistro", nullable = false, length = 12)
    private String numeroRegistro;

    @Column(name = "ubicacionGPS", nullable=false, length = 30)
    private String psGPS;

    @Column(name = "horaDespegue", nullable=false)
    private LocalDateTime horaDespegue;

    @Column(name = "tiempoEstimadoMinutos", nullable=false)
    private int tiempoEstimado;

    @Column(name = "alturaMaxima", nullable=false)
    private double altMax;

    @Column(name = "region", nullable=false, length=18)
    private Region region;

    @Column(name = "codigoVuelo", length = 12)
    private String codigoVuelo;

    @Column(name="estado", length=10)
    private String estadoPV;
}
