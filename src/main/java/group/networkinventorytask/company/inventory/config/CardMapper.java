package group.networkinventorytask.company.inventory.config;

import group.networkinventorytask.company.inventory.dto.response.CardResponse;
import group.networkinventorytask.company.inventory.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardResponse toResponse(Card card){
        CardResponse response = new CardResponse();

        response.setId(card.getId());
        if (card.getSlot() != null) {
            response.setSlotId(card.getSlot().getId());
        }
        response.setSerialNumber(card.getSerialNumber());
        response.setCardType(card.getCardType());
        response.setPortCount(card.getPortCount());
        response.setPartNumber(card.getPartNumber());
        response.setHardwareRevision(card.getHardwareRevision());
        response.setStatus(card.getStatus());
        response.setCreatedAt(card.getCreatedAt());
        response.setUpdatedAt(card.getUpdatedAt());

        return response;
    }
}
