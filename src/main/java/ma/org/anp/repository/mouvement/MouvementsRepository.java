package ma.org.anp.repository.mouvement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.mouvement.Mouvements;
import ma.org.anp.model.entity.mouvement.MouvementsSimple;

@Repository
public interface MouvementsRepository extends JpaRepository<MouvementsSimple, Long> {

  @Query(name = "MouvementsSimple.findDateJour")
  List<MouvementsSimple> findByDateJour();

  @Query(name = "MouvementsSimple.findByNumAvis")
  List<MouvementsSimple> findByNumeroAvis(@Param("numeroAvis") String numeroAvis);

  @Query(name = "Mouvements.findByID")
  List<Mouvements> findByIdAvis(@Param("id") long id);

}
