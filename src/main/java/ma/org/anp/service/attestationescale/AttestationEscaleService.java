package ma.org.anp.service.attestationescale;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.attestationescale.AttestationEscale;
import ma.org.anp.model.entity.attestationescale.AttestationEscaleSimple;
import ma.org.anp.model.entity.mouvement.Mouvements;
import ma.org.anp.model.entity.mouvement.MouvementsSimple;
import ma.org.anp.repository.attestationescale.AttestationEscaleRepository;
import ma.org.anp.repository.mouvement.MouvementsRepository;

@Service
public class AttestationEscaleService {
    private static final Logger logger = LoggerFactory.getLogger(AttestationEscaleService.class);
    @Autowired
    private AttestationEscaleRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<AttestationEscaleSimple> listerParJour() {
        List<AttestationEscaleSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<AttestationEscaleSimple> listerParEscale(long numeroEscale) {
        logger.info("Recherche des AttestationEscale avec le numéro d'échelle : " + numeroEscale);
        List<AttestationEscaleSimple> liste = administrationRepository.findByNumeroEscale(numeroEscale);
        logger.info("Nombre de attestationescale trouvés : {}", liste);
        return liste;
    }

    public AttestationEscaleSimple listerParID(long id) {
        AttestationEscaleSimple avis = null;
        List<AttestationEscale> liste = administrationRepository.findByIdAvis(id);
        if (!liste.isEmpty()) {
            logger.info("Aucun mouvement trouvé avec l'ID : " + id);
            avis = new AttestationEscaleSimple();
            avis.setId(liste.get(0).getId());
            avis.setDateTraitement(liste.get(0).getDateTraitement());
            avis.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            avis.setNomFichier(liste.get(0).getNomFichier());
            avis.setDocumentFile(Base64.getEncoder().encodeToString(liste.get(0).getDocument()));
            avis.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            avis.setStatutTraitement(liste.get(0).getStatutTraitement());
            avis.setNumeroEscale(liste.get(0).getNumeroEscale());
            avis.setTypeTraitement(liste.get(0).getTypeTraitement());
        }
        return avis;

    }

}
