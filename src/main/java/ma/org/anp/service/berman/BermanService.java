package ma.org.anp.service.berman;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.berman.Berman;
import ma.org.anp.model.entity.berman.BermanSimple;
import ma.org.anp.repository.berman.BermanRepository;

@Service
public class BermanService {
    private static final Logger logger = LoggerFactory.getLogger(BermanService.class);
    @Autowired
    private BermanRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<BermanSimple> listerParJour() {
        List<BermanSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<BermanSimple> listerParNumMessage(long numeroEscale) {
        logger.info("Recherche des Berman avec le numéro d'échelle : " + numeroEscale);
        List<BermanSimple> liste = administrationRepository.findByNumeroMessage(numeroEscale);
        logger.info("Nombre de bermans trouvés : {}", liste);
        return liste;
    }

    public BermanSimple listerParID(long id) {
        BermanSimple avis = null;
        List<Berman> liste = administrationRepository.findByIdAvis(id);
        if (!liste.isEmpty()) {
            logger.info("Aucun berman trouvé avec l'ID : " + id);
            avis = new BermanSimple();
            avis.setId(liste.get(0).getId());
            avis.setDateTraitement(liste.get(0).getDateTraitement());
            avis.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            avis.setNomFichier(liste.get(0).getNomFichier());
            avis.setDocumentFile(Base64.getEncoder().encodeToString(liste.get(0).getDocument()));
            avis.setDateReponse(liste.get(0).getDateReponse());
            avis.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            avis.setStatutTraitement(liste.get(0).getStatutTraitement());
            avis.setTypeReponse(liste.get(0).getTypeReponse());
            avis.setTypeTraitement(liste.get(0).getTypeTraitement());
            avis.setNumeroEscale(liste.get(0).getNumeroEscale());
        }
        return avis;

    }

}
