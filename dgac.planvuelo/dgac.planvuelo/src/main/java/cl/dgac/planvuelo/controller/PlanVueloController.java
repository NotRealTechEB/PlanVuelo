package cl.dgac.planvuelo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.PlanVueloResponseDTO;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.mapper.PlanVueloMapper;
import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.service.PlanVueloService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/plan-vuelo")
public class PlanVueloController {

    private final PlanVueloService pVueloService;
    private final WebClient pilotoApiWebClient;

    public PlanVueloController(PlanVueloService pVueloService, WebClient pilotoApiWebClient){
        this.pVueloService = pVueloService;
        this.pilotoApiWebClient = pilotoApiWebClient;
    }

    //Mostrar todos los planes de vuelo

    @GetMapping
    public ResponseEntity<List<PlanVuelo>> listarPV(){
        List<PlanVuelo> listPV = pVueloService.mostrarPlanesVuelo();
        return ResponseEntity.ok(listPV);
    }

    //Obtener plan de vuelo por ID

    @GetMapping("/resumen")
    public ResponseEntity<List<PlanVueloResponseDTO>> obtenerPVPorRut(@RequestParam("rut") String rutPiloto) {
        List<PlanVueloResponseDTO> pV = pVueloService.historialPlanesPorRut(rutPiloto);
        return ResponseEntity.ok(pV);
    }

    //Registrar nuevos planes de vuelo

    @PostMapping
    public ResponseEntity<PlanVuelo> guardarPV(@Valid @RequestBody CreatePlanVuelo request){
        PlanVuelo pV = pVueloService.agregarPlanesVuelo(PlanVueloMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(pV);
    }

    //Actualizar datos de planes de vuelo

    @PutMapping({"idPlanVuelo"})
    public ResponseEntity<PlanVuelo> actualizarPV(@Valid @RequestBody UpdatePlanVuelo request){
        PlanVuelo pV = pVueloService.actualizarPlanesVuelo(PlanVueloMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(pV);
    }

    //Eliminar planes de vuelo
    
    @DeleteMapping
    public ResponseEntity<String> eliminarPV(@RequestParam("idPlanVuelo") int idPlanVuelo){
        pVueloService.eliminarPlanesVuelo(idPlanVuelo);
        return ResponseEntity.noContent().build();
    }
}
