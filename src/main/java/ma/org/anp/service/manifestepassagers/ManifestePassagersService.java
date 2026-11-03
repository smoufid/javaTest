package  ma.org.anp.service.manifestepassagers;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.org.anp.model.entity.manifestepassagers.ManifestePassagers;
import ma.org.anp.model.entity.manifestepassagers.ManifestePassagersSimple;

import ma.org.anp.repository.manifestepassagers.ManifestePassagersRepository;


@Service
public class ManifestePassagersService {
    private static final Logger logger = LoggerFactory.getLogger(ManifestePassagersService.class);
    @Autowired
    private ManifestePassagersRepository administrationRepository;

    /**
     * Récupère la liste de tous les utilisateurs
     */
    public List<ManifestePassagersSimple> listerParJour() {
        List<ManifestePassagersSimple> liste = administrationRepository.findByDateJour();
        return liste;
    }

    public List<ManifestePassagersSimple> listerParNumEscale(long numeroEscale) {
        logger.info("Recherche des Manifestes avec le numéro de message : " + numeroEscale);
        List<ManifestePassagersSimple> liste = administrationRepository.findByNumeroEscale(numeroEscale);
        logger.info("Nombre de manifestes trouvés : {}", liste);
        return liste;
    }

    public ManifestePassagersSimple listerParID(long id) {
        ManifestePassagersSimple manifeste = null;
        List<ManifestePassagers> liste = administrationRepository.findById(id);
        if (!liste.isEmpty()) {
            logger.info("Aucun manifeste trouvé avec l'ID : " + id);
            manifeste = new ManifestePassagersSimple();
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
