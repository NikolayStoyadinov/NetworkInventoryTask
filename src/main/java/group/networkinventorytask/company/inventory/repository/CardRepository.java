package group.networkinventorytask.company.inventory.repository;

import group.networkinventorytask.company.inventory.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    boolean existsBySerialNumber (String serialNumber);

    List<Card> findBySlotId(Long slotId);
}
