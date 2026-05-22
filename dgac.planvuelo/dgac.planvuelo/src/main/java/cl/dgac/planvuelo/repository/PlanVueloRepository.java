package cl.dgac.planvuelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.dgac.planvuelo.model.PlanVuelo;

@Repository
public interface PlanVueloRepository extends JpaRepository<PlanVuelo, Integer>{

}
