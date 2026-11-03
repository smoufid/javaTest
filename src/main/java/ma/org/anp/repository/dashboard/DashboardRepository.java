package ma.org.anp.repository.dashboard;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.org.anp.model.entity.dashboard.Dashboard;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

  @Query(name = "Dashboard.findDateJour")
  List<Dashboard> findByDateJour();

  @Query(name = "Dashboard.findByDate")
  List<Dashboard> findByDate(@Param("date") Date date);

  @Query(name = "Dashboard.getListePeriodeAvis")
  List<Dashboard> getListePeriodeAvis(@Param("date1") Date date1, @Param("date2") Date date2);
  @Query(name = "Dashboard.getListePeriodeDAP")
  List<Dashboard> getListePeriodeDAP(@Param("date1") Date date1, @Param("date2") Date date2);
  @Query(name = "Dashboard.getListePeriodeManifeste")
  List<Dashboard> getListePeriodeManifeste(@Param("date1") Date date1, @Param("date2") Date date2);
}
