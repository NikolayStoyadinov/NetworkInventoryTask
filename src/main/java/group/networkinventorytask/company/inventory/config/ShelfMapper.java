package group.networkinventorytask.company.inventory.config;

import group.networkinventorytask.company.inventory.dto.response.ShelfResponse;
import group.networkinventorytask.company.inventory.dto.response.ShelfTreeResponse;
import group.networkinventorytask.company.inventory.entity.Shelf;
import org.springframework.stereotype.Component;

@Component
public class ShelfMapper {

    public ShelfResponse toResponse(Shelf shelf){

        ShelfResponse response = new ShelfResponse();

        response.setRouterId(shelf.getRouter().getId());
        response.setId(shelf.getId());
        response.setShelfNumber(shelf.getShelfNumber());
        response.setSerialNumber(shelf.getSerialNumber());
        response.setTotalSlots(shelf.getTotalSlots());
        response.setStatus(shelf.getStatus());
        response.setCreatedAt(shelf.getCreatedAt());
        response.setUpdatedAt(shelf.getUpdatedAt());

        return response;
    }

    public ShelfTreeResponse toTreeResponse(Shelf shelf) {

        ShelfTreeResponse response = new ShelfTreeResponse();

        response.setRouterId(shelf.getRouter().getId());
        response.setId(shelf.getId());
        response.setShelfNumber(shelf.getShelfNumber());
        response.setSerialNumber(shelf.getSerialNumber());
        response.setTotalSlots(shelf.getTotalSlots());
        response.setStatus(shelf.getStatus());
        response.setCreatedAt(shelf.getCreatedAt());
        response.setUpdatedAt(shelf.getUpdatedAt());

        return response;
    }
}
