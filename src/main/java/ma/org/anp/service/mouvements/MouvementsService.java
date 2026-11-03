package ma.org.anp.service.mouvements;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.mouvement.Mouvements;
import ma.org.anp.model.entity.mouvement.MouvementsSimple;
import ma.org.anp.repository.mouvement.MouvementsRepository;

@Service
public class MouvementsService {
    private static final Logger logger = LoggerFactory.getLogger(MouvementsService.class);
    @Autowired
    private MouvementsRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<MouvementsSimple> listerParJour() {
        List<MouvementsSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<MouvementsSimple> listerParNumLloyd(String numeroAvis) {
        logger.info("Recherche des Mouvements avec le numéro de message : " + numeroAvis);
        List<MouvementsSimple> liste = administrationRepository.findByNumeroAvis(numeroAvis);
        logger.info("Nombre de mouvements trouvés : {}", liste);
        return liste;
    }

    public MouvementsSimple listerParID(long id) {
        MouvementsSimple avis = null;
        List<Mouvements> liste = administrationRepository.findByIdAvis(id);
        if (!liste.isEmpty()) {
            logger.info("Aucun mouvement trouvé avec l'ID : " + id);
            avis = new MouvementsSimple();
            avis.setId(liste.get(0).getId());
            avis.setDateTraitement(liste.get(0).getDateTraitement());
            avis.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            avis.setNomFichier(liste.get(0).getNomFichier());
            avis.setDocumentFile(Base64.getEncoder().encodeToString(liste.get(0).getDocument()));
           
            avis.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            avis.setStatutTraitement(liste.get(0).getStatutTraitement());

            avis.setTypeTraitement(liste.get(0).getTypeTraitement());
        }
        return avis;

    }

}
