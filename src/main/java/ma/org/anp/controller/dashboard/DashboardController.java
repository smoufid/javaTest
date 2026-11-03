package ma.org.anp.controller.dashboard;

import java.sql.Date;
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
import ma.org.anp.model.dto.FluxDashboard;
import ma.org.anp.service.dashboard.DashbordService;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "API de dashboard")
@SecurityRequirement(name = "bearerAuth")
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    @Autowired
    private DashbordService administrationService;

    
    @GetMapping("/liste")
    @Operation(summary = "Lister tous les types", description = "Récupère la liste complète des echanges. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<FluxDashboard>> listerType() {
        List<FluxDashboard> flux = administrationService.listerDashboard();
        return ResponseEntity.ok(flux);
    }

    @GetMapping("/liste/date")
    @Operation(summary = "Lister les types par date", description = "Récupère la liste des echanges pour une date donnée. Nécessite une authentification JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Format de date invalide, attendu dd-MM-yyyy"),
            @ApiResponse(responseCode = "401", description = "Non authentifié - Token JWT manquant ou invalide")
    })
    public ResponseEntity<List<FluxDashboard>> listerTypeParDate(
            @Parameter(description = "Date au format dd-MM-yyyy", example = "21-08-2026") @RequestParam("date") String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        java.util.Date dateParsee;
        try {
            dateParsee = sdf.parse(date);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        List<FluxDashboard> flux = administrationService.listerDashboardByDate(new Date(dateParsee.getTime()));
        return ResponseEntity.ok(flux);
    }

}
