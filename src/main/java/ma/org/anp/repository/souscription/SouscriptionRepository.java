package ma.org.anp.repository.souscription;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.souscription.Souscription;
import ma.org.anp.model.entity.souscription.SouscriptionSimple;

@Repository
public interface SouscriptionRepository extends JpaRepository<SouscriptionSimple, Long> {

  @Query(name = "SouscriptionSimple.findDateJour")
  List<SouscriptionSimple> findByDateJour();

  @Query(name = "SouscriptionSimple.findByNumAvis")
  List<SouscriptionSimple> findByNumeroLloyd(@Param("numeroLloyd") String numeroLloyd);

  @Query(name = "Souscription.findByID")
  List<Souscription> findByIdAvis(@Param("id") long id);

}
