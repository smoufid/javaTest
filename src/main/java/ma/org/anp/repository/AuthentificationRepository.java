package ma.org.anp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import ma.org.anp.model.entity.administration.Utilisateurs;

 

@Repository
public interface AuthentificationRepository extends JpaRepository<Utilisateurs, Long> {
    
    /**
     * Recherche un utilisateur par son nom d'utilisateur
     * @param utilisateur le nom d'utilisateur
     * @return Optional contenant l'utilisateur s'il existe
     */
    @Query(name = "Utilisateurs.getParUtilisateur")
    Optional<Utilisateurs> findByUtilisateur(@Param("utilisateur") String utilisateur);

      @Query(name = "Utilisateurs.findAll")
     List<Utilisateurs> findAllUtilisateurs();

     


}
