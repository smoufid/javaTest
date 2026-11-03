package ma.org.anp.controller.dashboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.org.anp.model.dto.EchangesDelaisParJour;
import ma.org.anp.service.reporting.EchangesDelaisService;

@RestController
@RequestMapping("/api/reporting")
@Tag(name = "Reporting", description = "API de reporting")
@SecurityRequirement(name = "bearerAuth")
public class ReportingController {
    private static final Logger logger = LoggerFactory.getLogger(ReportingController.class);
    @Autowired
    private EchangesDelaisService administrationService;

    @GetMapping("/listeavis/date")
    @Operation(summary = "Lister Avis Arrivée les types par date", description = "Récupère la liste des Avis Avis pour une date donnée. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de date invalide, attendu dd-MM-yyyy"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<EchangesDelaisParJour>> listerTypeParDate(
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("debut") String date1,
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("fin") String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        logger.info("Received request for date range: " + date1 + " to " + date2);
        java.util.Date dateParsee1;
        java.util.Date dateParsee2;
        try {
            dateParsee1 = sdf.parse(date1);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        try {
            dateParsee2 = sdf.parse(date2);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Date1: " + dateParsee1 + ", Date2: " + dateParsee2);
        List<EchangesDelaisParJour> flux = administrationService.calculerRepartitionAvis(dateParsee1, dateParsee2);
        return ResponseEntity.ok(flux);
    }



@GetMapping("/listedap/date")
    @Operation(summary = "Lister DAP les types par date", description = "Récupère la liste des DAP pour une date donnée. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de date invalide, attendu dd-MM-yyyy"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<EchangesDelaisParJour>> listerTypeParDateDAP(
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("debut") String date1,
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("fin") String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        logger.info("Received request for date range: " + date1 + " to " + date2);
        java.util.Date dateParsee1;
        java.util.Date dateParsee2;
        try {
            dateParsee1 = sdf.parse(date1);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        try {
            dateParsee2 = sdf.parse(date2);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Date1: " + dateParsee1 + ", Date2: " + dateParsee2);
        List<EchangesDelaisParJour> flux = administrationService.calculerRepartitionDAP(dateParsee1, dateParsee2);
        return ResponseEntity.ok(flux);
    }


    @GetMapping("/listemanifeste/date")
    @Operation(summary = "Lister Manifeste les types par date", description = "Récupère la liste des Manifeste pour une date donnée. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de date invalide, attendu dd-MM-yyyy"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<EchangesDelaisParJour>> listerTypeParDateManifeste(
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("debut") String date1,
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("fin") String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        logger.info("Received request for date range: " + date1 + " to " + date2);
        java.util.Date dateParsee1;
        java.util.Date dateParsee2;
        try {
            dateParsee1 = sdf.parse(date1);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        try {
            dateParsee2 = sdf.parse(date2);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Date1: " + dateParsee1 + ", Date2: " + dateParsee2);
        List<EchangesDelaisParJour> flux = administrationService.calculerRepartitionManifeste(dateParsee1, dateParsee2);
        return ResponseEntity.ok(flux);
    }


}
