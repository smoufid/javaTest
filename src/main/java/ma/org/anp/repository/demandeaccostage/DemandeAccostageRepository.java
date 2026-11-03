package ma.org.anp.repository.demandeaccostage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.demandeaccostage.DemandeAccostage;
import ma.org.anp.model.entity.demandeaccostage.DemandeAccostageSimple;

@Repository
public interface DemandeAccostageRepository extends JpaRepository<DemandeAccostageSimple, Long> {

  @Query(name = "DemandeAccostageSimple.findDateJour")
  List<DemandeAccostageSimple> findByDateJour();

  @Query(name = "DemandeAccostageSimple.findByMessage")
  List<DemandeAccostageSimple> findByNumeroEscale(@Param("numeroEscale") long numeroEscale);

  @Query(name = "DemandeAccostage.findByID")
  List<DemandeAccostage> findByIdDemande(@Param("id") Long id);

}
