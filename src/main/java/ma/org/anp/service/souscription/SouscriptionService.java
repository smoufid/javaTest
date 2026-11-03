package ma.org.anp.service.souscription;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.souscription.Souscription;
import ma.org.anp.model.entity.souscription.SouscriptionSimple;
import ma.org.anp.repository.souscription.SouscriptionRepository;

@Service
public class SouscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(SouscriptionService.class);
    @Autowired
    private SouscriptionRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<SouscriptionSimple> listerParJour() {
        List<SouscriptionSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<SouscriptionSimple> listerParNumLloyd(String numeroLloyd) {
        logger.info("Recherche des Souscriptions avec le numéro de message : " + numeroLloyd);
        List<SouscriptionSimple> liste = administrationRepository.findByNumeroLloyd(numeroLloyd);
        logger.info("Nombre de souscriptions trouvées : {}", liste);
        return liste;
    }

    public SouscriptionSimple listerParID(long id) {
        SouscriptionSimple avis = null;
        List<Souscription> liste = administrationRepository.findByIdAvis(id);
        if (!liste.isEmpty()) {
            logger.info("Aucune souscription trouvée avec l'ID : " + id);
            avis = new SouscriptionSimple();
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
        }
        return avis;

    }

}
