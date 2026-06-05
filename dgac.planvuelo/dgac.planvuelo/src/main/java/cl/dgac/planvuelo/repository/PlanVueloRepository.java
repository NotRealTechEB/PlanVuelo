package cl.dgac.planvuelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.dgac.planvuelo.model.PlanVuelo;

@Repository
public interface PlanVueloRepository extends JpaRepository<PlanVuelo, Integer>{

    List<PlanVuelo> findByRutPiloto(String rutPiloto);
    PlanVuelo findByCodigoVuelo(String rutPiloto);
    List<PlanVuelo> findByRutEmpMandante(String rutEmpMandante);

}
