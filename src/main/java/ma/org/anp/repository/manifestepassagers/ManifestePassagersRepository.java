package ma.org.anp.repository.manifestepassagers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 
import ma.org.anp.model.entity.manifestepassagers.ManifestePassagers;
import ma.org.anp.model.entity.manifestepassagers.ManifestePassagersSimple;


@Repository
public interface ManifestePassagersRepository extends JpaRepository<ManifestePassagersSimple, Long> {

  @Query(name = "ManifestePassagersSimple.findDateJour")
  List<ManifestePassagersSimple> findByDateJour();

  @Query(name = "ManifestePassagersSimple.findBynumeroEscale")
  List<ManifestePassagersSimple> findByNumeroEscale(@Param("numeroEscale") long numeroEscale);

  @Query(name = "ManifestePassagers.findByID")
  List<ManifestePassagers> findById(@Param("id") long id);

}
