package group.networkinventorytask.company.inventory.repository;

import group.networkinventorytask.company.inventory.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long>, JpaSpecificationExecutor<Slot> {
    boolean existsBySlotNumber (String slotNumber);

    List<Slot> findByShelfId(Long shelfId);
}
