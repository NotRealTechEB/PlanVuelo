package cl.dgac.planvuelo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import cl.dgac.planvuelo.dto.CreatePlanVuelo;
import cl.dgac.planvuelo.dto.PlanVueloDTO;
import cl.dgac.planvuelo.dto.UpdatePlanVuelo;
import cl.dgac.planvuelo.mapper.PlanVueloMapper;
import cl.dgac.planvuelo.model.PlanVuelo;
import cl.dgac.planvuelo.service.PlanVueloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/planvuelo")
public class PlanVueloController {

    private final PlanVueloService pVueloService;
    private final WebClient pilotoApiWebClient;
    private final WebClient dronApiWebClient;
    private final WebClient licenciaApiWebClient;

    public PlanVueloController(PlanVueloService pVueloService, @Qualifier("pilotoApiWebClient")WebClient pilotoApiWebClient,
        @Qualifier("dronApiWebClient")WebClient dronApiWebClient,@Qualifier("licenciaApiWebClient")WebClient licenciaApiWebClient)
    {
        this.pVueloService = pVueloService;
        this.pilotoApiWebClient = pilotoApiWebClient;
        this.dronApiWebClient = dronApiWebClient;
        this.licenciaApiWebClient = licenciaApiWebClient;
    }

    //-------------------------------Metodos de administracion-------------------------------//

    //Mostrar todos los planes de vuelo

    @Operation(
        summary = "Presenta planes de vuelo",
        description= "Muestra todos los planes de vuelo realizados, no se usan filtros"
    )
    @ApiResponse(
        responseCode = "200",
        description = "OK",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Lista de planes de vuelo",
                value = "[{\"idPlanVuelo\": 1, \"rutPiloto\": \"12345678-9\", \"rutEmpMandante\": \"76123456-K\", \"numeroRegistro\": \"CC-ABC-12345\", \"psGPS\": \"-33.4489,-70.6693\", \"fechaPV\": \"2026-07-02T23:00:00\", \"altMax\": 3500.5, \"region\": \"Metropolitana\", \"codigoVuelo\": \"FLIGHT1234\", \"estadoPV\": \"ACTIVO\"}]"
            )
        ) 
    )
    @GetMapping
    public ResponseEntity<List<PlanVuelo>> listarPV(){
        List<PlanVuelo> listPV = pVueloService.mostrarPlanesVuelo();
        return ResponseEntity.ok(listPV);
    }

    //Registrar nuevos planes de vuelo

    @Operation(
        summary = "Crear plan de vuelo",
        description= "Permite crear nuevos planes de vuelo"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos necesarios para crear un nuevo plan de vuelo",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Ejemplo de crear plan de vuelo",
                value = "{\"idPlanVuelo\": 1, \"rutPiloto\": \"12345678-9\", \"rutEmpMandante\": \"76123456-K\", \"numeroRegistro\": \"CC-ABC-12345\", \"psGPS\": \"-33.4489,-70.6693\", \"fechaPV\": \"2026-07-02T23:00:00\", \"altMax\": 3500.5, \"region\": \"Metropolitana\", \"codigoVuelo\": \"FLIGHT1234\", \"estadoPV\": \"ACTIVO\"}"
            )
        ))
    @ApiResponse(
        responseCode = "201",
        description = "CREATED",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Plan de vuelo creado",
                value = "{\"idPlanVuelo\": 1, \"rutPiloto\": \"12345678-9\", \"rutEmpMandante\": \"76123456-K\", \"numeroRegistro\": \"CC-ABC-12345\", \"psGPS\": \"-33.4489,-70.6693\", \"fechaPV\": \"2026-07-02T23:00:00\", \"altMax\": 3500.5, \"region\": \"Metropolitana\", \"codigoVuelo\": \"FLIGHT1234\", \"estadoPV\": \"ACTIVO\"}"
            )
        ) 
    )
    @PostMapping
    public ResponseEntity<PlanVuelo> guardarPV(@Valid @RequestBody CreatePlanVuelo request){
        PlanVuelo pV = new PlanVuelo();
        PlanVuelo pVCrear = pVueloService.agregarPlanesVuelo(pV, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pVCrear);
    }

    //Actualizar datos de planes de vuelo

    @Operation(
        summary = "Crear plan de vuelo",
        description= "Permite crear nuevos planes de vuelo"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos necesarios para actualizar datos de un plan de vuelo",
        required = true,
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Ejemplo de actualizar plan de vuelo",
                value = "{\"idPlanVuelo\": 1, \"rutPiloto\": \"12345678-9\", \"rutEmpMandante\": \"76123456-K\", \"numeroRegistro\": \"CC-ABC-12345\", \"psGPS\": \"-33.4489,-70.6693\", \"fechaPV\": \"2026-07-02T23:00:00\", \"altMax\": 3500.5, \"region\": \"Metropolitana\", \"codigoVuelo\": \"FLIGHT1234\", \"estadoPV\": \"ACTIVO\"}"
            )
        ))
    @ApiResponse(
        responseCode = "200",
        description = "OK",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Plan de vuelo creado",
                value = "{\"idPlanVuelo\": 1, \"rutPiloto\": \"12345678-9\", \"rutEmpMandante\": \"76123456-K\", \"numeroRegistro\": \"CC-ABC-12345\", \"psGPS\": \"-33.4489,-70.6693\", \"fechaPV\": \"2026-07-02T23:00:00\", \"altMax\": 3500.5, \"region\": \"Metropolitana\", \"codigoVuelo\": \"FLIGHT1234\", \"estadoPV\": \"ACTIVO\"}"
            )
        ) 
    )
    @PutMapping("/{idPlanVuelo}")
    public ResponseEntity<PlanVuelo> actualizarPV(@PathVariable("idPlanVuelo") int idPlanVuelo, @Valid @RequestBody UpdatePlanVuelo request){
        PlanVuelo pV = pVueloService.actualizarPlanesVuelo(PlanVueloMapper.toModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(pV);
    }

    //Eliminar planes de vuelo
    
    @Operation(
        summary = "Eliminar plan de vuelo",
        description= "Permite eliminar planes de vuelo"
    )
    @ApiResponse(
        responseCode = "204",
        description = "No Content - Plan de vuelo eliminado con éxito"
        ) 
    @DeleteMapping("/{idPlanVuelo}")
    public ResponseEntity<String> eliminarPV(@PathVariable("idPlanVuelo") int idPlanVuelo){
        pVueloService.eliminarPlanesVuelo(idPlanVuelo);
        return ResponseEntity.noContent().build();
    }


    //-------------------------------Metodos HU - Piloto-------------------------------//

    //Obtener plan de vuelo por RUT de piloto

    @Operation(
        summary = "Presenta planes de vuelo",
        description= "Muestra todos los planes de vuelo realizados, se usa el rut del piloto como filtro"
    )
    @ApiResponse(
        responseCode = "200",
        description = "OK",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(
                name = "Lista de planes de vuelo",
                value = "[{\"idPlanVuelo\": 1, \"rutPiloto\": \"12345678-9\", \"rutEmpMandante\": \"76123456-K\", \"numeroRegistro\": \"CC-ABC-12345\", \"psGPS\": \"-33.4489,-70.6693\", \"fechaPV\": \"2026-07-02T23:00:00\", \"altMax\": 3500.5, \"region\": \"Metropolitana\", \"codigoVuelo\": \"FLIGHT1234\", \"estadoPV\": \"ACTIVO\"}]"
            )
        ) 
    )
    @GetMapping("/planes")
    public ResponseEntity<List<PlanVueloDTO>> listarPorPiloto(@RequestParam("rut") String rut) {
        List<PlanVueloDTO> planes = pVueloService.obtenerPlanByRut(rut);
        return ResponseEntity.ok(planes);
    }

    @GetMapping("/integrar")
    public ResponseEntity<PlanVueloDTO> datosPorCodigo(@RequestParam("codVuelo") String codVuelo){
        PlanVueloDTO datos = pVueloService.obtenerPlanByCodigo(codVuelo);
        return ResponseEntity.ok(datos);
    }

    //_______________metodos para las empresa mandante _______________________________________//
    @GetMapping("/listarPoRut")
    public ResponseEntity<List<PlanVueloDTO>> ListarPorRut(@RequestParam(name = "rut") String rut) {
        return new ResponseEntity<List<PlanVueloDTO>> (pVueloService.listarRutempresa(rut),HttpStatus.OK) ;
    }
    
}
