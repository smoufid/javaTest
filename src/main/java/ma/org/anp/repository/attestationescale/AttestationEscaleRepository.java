package ma.org.anp.repository.attestationescale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.attestationescale.AttestationEscale;
import ma.org.anp.model.entity.attestationescale.AttestationEscaleSimple;


@Repository
public interface AttestationEscaleRepository extends JpaRepository<AttestationEscaleSimple, Long> {

  @Query(name = "AttestationEscaleSimple.findDateJour")
  List<AttestationEscaleSimple> findByDateJour();

  @Query(name = "AttestationEscaleSimple.findByNumEscale")
  List<AttestationEscaleSimple> findByNumeroEscale(@Param("numeroEscale") long numeroEscale);

  @Query(name = "AttestationEscale.findByID")
  List<AttestationEscale> findByIdAvis(@Param("id") long id);

}
