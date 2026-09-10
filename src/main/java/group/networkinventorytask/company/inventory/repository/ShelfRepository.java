package group.networkinventorytask.company.inventory.repository;

import group.networkinventorytask.company.inventory.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long>, JpaSpecificationExecutor<Shelf> {
    boolean existsByShelfNumber (String shelfNumber);

    List<Shelf> findByRouterId(Long router_id);
}
