package ma.org.anp.repository.dap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import ma.org.anp.model.entity.dap.Dap;
import ma.org.anp.model.entity.dap.DapSimple;

@Repository
public interface DapRepository extends JpaRepository<DapSimple, Long> {

  @Query(name = "DapSimple.findDateJour")
  List<DapSimple> findByDateJour();

  @Query(name = "DapSimple.findByNumAvis")
  List<DapSimple> findByNumeroAvis(@Param("numeroMessage") String numeroMessage);

  @Query(name = "Dap.findByID")
  List<Dap> findByIdAvis(@Param("id") long id);

}
