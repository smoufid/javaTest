package ma.org.anp.service.demandeaccostage;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.demandeaccostage.DemandeAccostage;
import ma.org.anp.model.entity.demandeaccostage.DemandeAccostageSimple;
import ma.org.anp.repository.demandeaccostage.DemandeAccostageRepository;

 

@Service
public class DemandeAccostageService {
    private static final Logger logger = LoggerFactory.getLogger(DemandeAccostageService.class);
    @Autowired
    private DemandeAccostageRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<DemandeAccostageSimple> listerParJour() {
        List<DemandeAccostageSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<DemandeAccostageSimple> listerParNumEscale(long numeorEscale) {
        logger.info("Recherche des Souscriptions avec le numéro de message : " + numeorEscale);
        List<DemandeAccostageSimple> liste = administrationRepository.findByNumeroEscale(numeorEscale);
        logger.info("Nombre de souscriptions trouvées : {}", liste);
        return liste;
    }

    public DemandeAccostageSimple listerParID(long id) {
        DemandeAccostageSimple avis = null;
        List<DemandeAccostage> liste = administrationRepository.findByIdDemande(id);
        if (!liste.isEmpty()) {
            logger.info("Aucune souscription trouvée avec l'ID : " + id);
            avis = new DemandeAccostageSimple();
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
