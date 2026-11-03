package ma.org.anp.service.manifestemarchandise;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.manifestemarchandise.ManifesteMarchandise;
import ma.org.anp.model.entity.manifestemarchandise.ManifesteMarchandiseSimple;
import ma.org.anp.model.entity.souscription.Souscription;
import ma.org.anp.model.entity.souscription.SouscriptionSimple;
import ma.org.anp.repository.manifestemarchandise.ManifesteMarchandiseRepository;
import ma.org.anp.repository.souscription.SouscriptionRepository;

@Service
public class ManifesteMarchandiseService {
    private static final Logger logger = LoggerFactory.getLogger(ManifesteMarchandiseService.class);
    @Autowired
    private ManifesteMarchandiseRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<ManifesteMarchandiseSimple> listerParJour() {
        List<ManifesteMarchandiseSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<ManifesteMarchandiseSimple> listerParNumEscale(long numeroEscale) {
        logger.info("Recherche des Manifestes avec le numéro de message : " + numeroEscale);
        List<ManifesteMarchandiseSimple> liste = administrationRepository.findByNumeroEscale(numeroEscale);
        logger.info("Nombre de manifestes trouvés : {}", liste);
        return liste;
    }

    public ManifesteMarchandiseSimple listerParID(long id) {
        ManifesteMarchandiseSimple manifeste = null;
        List<ManifesteMarchandise> liste = administrationRepository.findByIdAvis(id);
        if (!liste.isEmpty()) {
            logger.info("Aucun manifeste trouvé avec l'ID : " + id);
            manifeste = new ManifesteMarchandiseSimple();
            manifeste.setId(liste.get(0).getId());
            manifeste.setDateTraitement(liste.get(0).getDateTraitement());
            manifeste.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            manifeste.setNomFichier(liste.get(0).getNomFichier());
            manifeste.setDocumentFile(Base64.getEncoder().encodeToString(liste.get(0).getDocument()));
            manifeste.setDateReponse(liste.get(0).getDateReponse());
            manifeste.setDescriptionTraitement(liste.get(0).getDescriptionTraitement());
            manifeste.setStatutTraitement(liste.get(0).getStatutTraitement());
            manifeste.setTypeReponse(liste.get(0).getTypeReponse());
            manifeste.setTypeTraitement(liste.get(0).getTypeTraitement());
        }
        return manifeste;

    }

}
