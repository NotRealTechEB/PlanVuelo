package cl.dgac.planvuelo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.mapper.PlanVueloMapper;
import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.service.PlanVueloService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/dgac/PlanVuelo")
public class PlanVueloController {

    private final PlanVueloService pVueloService;

    public PlanVueloController(PlanVueloService pVueloService){
        this.pVueloService = pVueloService;
    }

    @GetMapping
    public ResponseEntity<List<PlanVuelo>> listarPV(){
        List<PlanVuelo> listPV = pVueloService.mostrarPlanesVuelo();
        return ResponseEntity.ok(listPV);
    }

    @PostMapping
    public ResponseEntity<PlanVuelo> guardarPV(@Valid @RequestBody CreatePlanVuelo request){
        PlanVuelo pV = pVueloService.agregarPlanesVuelo(PlanVueloMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(pV);
    }

    @PutMapping
    public ResponseEntity<PlanVuelo> actualizarPV(@Valid @RequestBody UpdatePlanVuelo request){
        PlanVuelo pV = pVueloService.actualizarPlanesVuelo(PlanVueloMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(pV);
    }
    
    @DeleteMapping
    public ResponseEntity<String> eliminarPV(@PathVariable int idPlanVuelo){
        pVueloService.eliminarPlanesVuelo(idPlanVuelo);
        return ResponseEntity.noContent().build();
    }
}
