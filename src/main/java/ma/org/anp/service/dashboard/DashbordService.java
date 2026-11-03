package ma.org.anp.service.dashboard;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.dto.FluxDashboard;
import ma.org.anp.model.entity.dashboard.Dashboard;
import ma.org.anp.repository.dashboard.DashboardRepository;

@Service
public class DashbordService {
        private static final Logger logger = LoggerFactory.getLogger(DashbordService.class);
        @Autowired
        private DashboardRepository administrationRepository;

        /**
         * Récupère la liste de tous les utilisateurs
         */
        public List<FluxDashboard> listerDashboard() {
                List<Dashboard> liste = administrationRepository.findByDateJour();
                List<FluxDashboard> ret = liste.stream()
                                .collect(Collectors.groupingBy(
                                                Dashboard::getDescriptionTraitement))
                                .entrySet()
                                .stream()
                                .map(entry -> {
                                        List<Dashboard> group = entry.getValue();

                                        // Calcul des délais en minutes (0 si dateMessage est null)
                                        List<Long> delais = group.stream()
                                                        .map(d -> {
                                                                if (d.getDateMessage() == null
                                                                                || d.getDateTraitement() == null) {
                                                                        return 0L;
                                                                }
                                                                long diffMs = d.getDateTraitement().getTime()
                                                                                - d.getDateMessage().getTime();
                                                                return Math.abs(diffMs) / 60000L;
                                                        })
                                                        .collect(Collectors.toList());

                                        long delaiMoyen = (long) delais.stream()
                                                        .mapToLong(Long::longValue)
                                                        .average()
                                                        .orElse(0);

                                        long delaiMax = delais.stream()
                                                        .mapToLong(Long::longValue)
                                                        .max()
                                                        .orElse(0);

                                        long delaiMin = delais.stream()
                                                        .mapToLong(Long::longValue)
                                                        .min()
                                                        .orElse(0);

                                        FluxDashboard fd = new FluxDashboard();
                                        fd.setTypeFichier(entry.getKey());
                                        fd.setNombreFichier(group.size());
                                        fd.setDelaiMoyen(delaiMoyen);
                                        fd.setDelaiMax(delaiMax);
                                        fd.setDelaiMin(delaiMin);
                                        return fd;
                                })
                                .sorted(Comparator.comparing(FluxDashboard::getTypeFichier))
                                .collect(Collectors.toList());
                return ret;
        }

        public List<FluxDashboard> listerDashboardByDate(Date date) {
                List<Dashboard> liste = administrationRepository.findByDate(date);
                List<FluxDashboard> ret = liste.stream()
                                .collect(Collectors.groupingBy(
                                                Dashboard::getDescriptionTraitement))
                                .entrySet()
                                .stream()
                                .map(entry -> {
                                        List<Dashboard> group = entry.getValue();

                                        // Calcul des délais en minutes (0 si dateMessage est null)
                                        List<Long> delais = group.stream()
                                                        .map(d -> {
                                                                if (d.getDateMessage() == null
                                                                                || d.getDateTraitement() == null) {
                                                                        return 0L;
                                                                }
                                                                long diffMs = d.getDateTraitement().getTime()
                                                                                - d.getDateMessage().getTime();
                                                                return Math.abs(diffMs) / 60000L;
                                                        })
                                                        .collect(Collectors.toList());

                                        long delaiMoyen = (long) delais.stream()
                                                        .mapToLong(Long::longValue)
                                                        .average()
                                                        .orElse(0);

                                        long delaiMax = delais.stream()
                                                        .mapToLong(Long::longValue)
                                                        .max()
                                                        .orElse(0);

                                        long delaiMin = delais.stream()
                                                        .mapToLong(Long::longValue)
                                                        .min()
                                                        .orElse(0);

                                        FluxDashboard fd = new FluxDashboard();
                                        fd.setTypeFichier(entry.getKey());
                                        fd.setNombreFichier(group.size());
                                        fd.setDelaiMoyen(delaiMoyen);
                                        fd.setDelaiMax(delaiMax);
                                        fd.setDelaiMin(delaiMin);
                                        return fd;
                                })
                                .sorted(Comparator.comparing(FluxDashboard::getTypeFichier))
                                .collect(Collectors.toList());
                return ret;
        }

}
