package group.networkinventorytask.company.inventory.config;

import group.networkinventorytask.company.inventory.dto.response.SlotResponse;
import group.networkinventorytask.company.inventory.entity.Slot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SlotMapper {

    public SlotResponse toResponse(Slot slot){

        SlotResponse response = new SlotResponse();

        response.setId(slot.getId());
        response.setShelfId(slot.getShelf().getId());
        response.setSlotNumber(slot.getSlotNumber());
        response.setSlotType(slot.getSlotType());
        response.setStatus(slot.getStatus());
        response.setCreatedAt(slot.getCreatedAt());
        response.setUpdatedAt(slot.getUpdatedAt());

        return response;
    }
}
