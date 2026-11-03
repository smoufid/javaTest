package ma.org.anp.repository.manifestemarchandise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.manifestemarchandise.ManifesteMarchandise;
import ma.org.anp.model.entity.manifestemarchandise.ManifesteMarchandiseSimple;


@Repository
public interface ManifesteMarchandiseRepository extends JpaRepository<ManifesteMarchandiseSimple, Long> {

  @Query(name = "ManifesteMarchandiseSimple.findDateJour")
  List<ManifesteMarchandiseSimple> findByDateJour();

  @Query(name = "ManifesteMarchandiseSimple.findBynumeroEscale")
  List<ManifesteMarchandiseSimple> findByNumeroEscale(@Param("numeroEscale") long numeroEscale);

  @Query(name = "ManifesteMarchandise.findByID")
  List<ManifesteMarchandise> findByIdAvis(@Param("id") long id);

}
