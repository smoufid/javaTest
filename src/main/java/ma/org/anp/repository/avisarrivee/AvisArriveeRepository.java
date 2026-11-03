package ma.org.anp.repository.avisarrivee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.avisarrivee.AvisArrivee;
import ma.org.anp.model.entity.avisarrivee.AvisArriveeSimple;

@Repository
public interface AvisArriveeRepository extends JpaRepository<AvisArriveeSimple, Long> {

  @Query(name = "AvisArriveeSimple.findDateJour")
  List<AvisArriveeSimple> findByDateJour();

  @Query(name = "AvisArriveeSimple.findByNumAvis")
  List<AvisArriveeSimple> findByNumeroAvis(@Param("numeroMessage") String numeroMessage);

  @Query(name = "AvisArrivee.findByID")
  List<AvisArrivee> findByIdAvis(@Param("id") long id);

}
