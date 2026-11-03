package ma.org.anp.service.reporting;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.dto.EchangesDelaisParJour;
import ma.org.anp.model.entity.dashboard.Dashboard;
import ma.org.anp.repository.attestationescale.AttestationEscaleRepository;
import ma.org.anp.repository.dashboard.DashboardRepository;

@Service
public class EchangesDelaisService {
    @Autowired
    private DashboardRepository administrationRepository;
    private static final ZoneId ZONE = ZoneId.of("GMT+1");
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final String TRANCHE_1_5 = "1-5 Minute";
    private static final String TRANCHE_5_10 = "5-10 Minute";
    private static final String TRANCHE_SUP10 = "Sup 10 Minute";

    private static final List<String> TRANCHES = Arrays.asList(TRANCHE_1_5, TRANCHE_5_10, TRANCHE_SUP10);

    /**
     * Regroupe les échanges par jour de traitement et par tranche de délai.
     * Seuls les jours présents dans les données apparaissent, avec 3 tranches
     * chacun.
     */
    public List<EchangesDelaisParJour> calculerRepartitionAvis(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Les dates de début et de fin sont obligatoires");
        }
        List<Dashboard> dashboards = administrationRepository.getListePeriodeAvis(date1, date2);

        if (dashboards == null || dashboards.isEmpty()) {
            return Collections.emptyList();
        }

        List<EchangesDelaisParJour> resultat = new ArrayList<>();

        regrouper(dashboards).forEach((jour, parTranche) -> resultat.add(construireLigne(jour, parTranche)));

        return resultat;
    }
 public List<EchangesDelaisParJour> calculerRepartitionDAP(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Les dates de début et de fin sont obligatoires");
        }
        List<Dashboard> dashboards = administrationRepository.getListePeriodeDAP(date1, date2);

        if (dashboards == null || dashboards.isEmpty()) {
            return Collections.emptyList();
        }

        List<EchangesDelaisParJour> resultat = new ArrayList<>();

        regrouper(dashboards).forEach((jour, parTranche) -> resultat.add(construireLigne(jour, parTranche)));

        return resultat;
    }

public List<EchangesDelaisParJour> calculerRepartitionManifeste(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("Les dates de début et de fin sont obligatoires");
        }
        List<Dashboard> dashboards = administrationRepository.getListePeriodeManifeste(date1, date2);

        if (dashboards == null || dashboards.isEmpty()) {
            return Collections.emptyList();
        }

        List<EchangesDelaisParJour> resultat = new ArrayList<>();

        regrouper(dashboards).forEach((jour, parTranche) -> resultat.add(construireLigne(jour, parTranche)));

        return resultat;
    }

    /**
     * Variante avec période explicite : les jours sans échange apparaissent
     * également, avec les 3 tranches à 0.
     *
     * @param debut date de début (incluse)
     * @param fin   date de fin (incluse)
     */
    public List<EchangesDelaisParJour> calculerRepartition(List<Dashboard> dashboards,
            Date debut,
            Date fin) {

        if (debut == null || fin == null) {
            throw new IllegalArgumentException("Les dates de début et de fin sont obligatoires");
        }

        LocalDate jourDebut = toLocalDate(debut);
        LocalDate jourFin = toLocalDate(fin);

        if (jourDebut.isAfter(jourFin)) {
            throw new IllegalArgumentException(
                    "Période invalide : debut=" + jourDebut.format(FORMAT)
                            + ", fin=" + jourFin.format(FORMAT));
        }

        Map<LocalDate, Map<String, Long>> groupes = (dashboards == null || dashboards.isEmpty())
                ? Collections.emptyMap()
                : regrouper(dashboards);

        List<EchangesDelaisParJour> resultat = new ArrayList<>();

        for (LocalDate jour = jourDebut; !jour.isAfter(jourFin); jour = jour.plusDays(1)) {
            resultat.add(construireLigne(jour, groupes.getOrDefault(jour, Collections.emptyMap())));
        }

        return resultat;
    }

    // ------------------------------------------------------------------
    // Méthodes internes
    // ------------------------------------------------------------------

    private EchangesDelaisParJour construireLigne(LocalDate jour, Map<String, Long> parTranche) {
        EchangesDelaisParJour ligne = new EchangesDelaisParJour(jour.format(FORMAT));
        for (String tranche : TRANCHES) {
            ligne.addTranche(tranche, parTranche.getOrDefault(tranche, 0L));
        }
        return ligne;
    }

    /** jour -> tranche -> nombre d'échanges. TreeMap pour le tri chronologique. */
    private Map<LocalDate, Map<String, Long>> regrouper(List<Dashboard> dashboards) {
        return dashboards.stream()
                .filter(d -> d.getDateTraitement() != null && d.getDateMessage() != null)
                .collect(Collectors.groupingBy(
                        d -> toLocalDate(d.getDateTraitement()),
                        TreeMap::new,
                        Collectors.groupingBy(
                                this::resoudreTranche,
                                Collectors.counting())));
    }

    /** Délai = dateTraitement - dateMessage, exprimé en minutes. */
    private String resoudreTranche(Dashboard d) {
        long minutes = Duration.between(
                d.getDateMessage().toInstant(),
                d.getDateTraitement().toInstant()).toMinutes();

        if (minutes < 0) {
            minutes = 0;
        }
        if (minutes <= 5) {
            return TRANCHE_1_5;
        }
        if (minutes <= 10) {
            return TRANCHE_5_10;
        }
        return TRANCHE_SUP10;
    }

    private LocalDate toLocalDate(Date date) {
        // java.sql.Date ne supporte pas toInstant() : on passe par le temps en
        // millisecondes
        return java.time.Instant.ofEpochMilli(date.getTime()).atZone(ZONE).toLocalDate();
    }
}