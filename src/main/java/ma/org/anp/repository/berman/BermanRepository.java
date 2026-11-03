package ma.org.anp.repository.berman;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.berman.Berman;
import ma.org.anp.model.entity.berman.BermanSimple;


@Repository
public interface BermanRepository extends JpaRepository<BermanSimple, Long> {

  @Query(name = "BermanSimple.findDateJour")
  List<BermanSimple> findByDateJour();

  @Query(name = "BermanSimple.findByNumeroMessage")
  List<BermanSimple> findByNumeroMessage(@Param("numeroEscale") long numeroEscale);

  @Query(name = "Berman.findByID")
  List<Berman> findByIdAvis(@Param("id") long id);

}
