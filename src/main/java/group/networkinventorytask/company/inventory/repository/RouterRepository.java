package group.networkinventorytask.company.inventory.repository;

import group.networkinventorytask.company.inventory.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouterRepository extends JpaRepository<Router, Long>, JpaSpecificationExecutor<Router> {
    boolean existsByHostname(String hostname);
    boolean existsBySerialNumber(String serialNumber);
    boolean existsByNetworkSiteId(Long siteId);

    List<Router> findByNetworkSiteId(Long siteId);
}
