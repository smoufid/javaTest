package ma.org.anp.service.dap;

import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.dap.Dap;
import ma.org.anp.model.entity.dap.DapSimple;
 
import ma.org.anp.repository.dap.DapRepository;

@Service
public class DapService {
    private static final Logger logger = LoggerFactory.getLogger(DapService.class);
    @Autowired
    private DapRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<DapSimple> listerAvisJour() {
        List<DapSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<DapSimple> listerParNumAvis(String numeroMessage) {
        logger.info("Recherche des avis d'arrivée avec le numéro de message : " + numeroMessage);
        List<DapSimple> liste = administrationRepository.findByNumeroAvis(numeroMessage);
        logger.info("Nombre d'avis d'arrivée trouvés : {0}", liste);
        return liste;
    }

    public DapSimple listerParID(long id) {
        DapSimple avis = null;
        List<Dap> liste = administrationRepository.findByIdAvis(id);
        if (!liste.isEmpty()) {
            logger.info("Aucun avis d'arrivée trouvé avec l'ID : " + id);
            avis = new DapSimple();
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
