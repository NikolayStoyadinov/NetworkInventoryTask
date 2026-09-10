package group.networkinventorytask.company.inventory.repository;

import group.networkinventorytask.company.inventory.entity.NetworkSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NetworkSiteRepository extends JpaRepository<NetworkSite, Long>, JpaSpecificationExecutor<NetworkSite> {
 boolean existsBySiteCode(String siteCode);
}

