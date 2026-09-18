package group.networkinventorytask.company.inventory.controller;

import group.networkinventorytask.company.inventory.dto.Update.ShelfUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.ShelfCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.RouterResponse;
import group.networkinventorytask.company.inventory.dto.response.ShelfResponse;
import group.networkinventorytask.company.inventory.dto.response.SlotResponse;
import group.networkinventorytask.company.inventory.service.ShelfService;
import group.networkinventorytask.company.inventory.service.SlotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/shelves")
public class ShelfController {

    private final ShelfService shelfService;
    private final SlotService slotService;

    public ShelfController(ShelfService shelfService, SlotService slotService) {
        this.shelfService = shelfService;
        this.slotService = slotService;
    }

    //Post /api/v1/shelves
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ShelfResponse create(@RequestBody ShelfCreateRequest request){
        return shelfService.create(request);
    }

    //Get /api/v1/shelves
    @GetMapping
    public Page<ShelfResponse> getAll(
            @RequestParam(required = false) String status,
            Pageable pageable){

        return  shelfService.getAll(status, pageable);
    }

    //Get slots of a shelf
    @GetMapping("/{id}/slots")
    public List<SlotResponse> getSlots(@PathVariable Long id) {
        return slotService.getSlotsByShelfId(id);
    }

    //Get /api/v1/shelves/{id}
    @GetMapping("/{id}")
    public ShelfResponse getById(
            @PathVariable Long id
    ){
        return shelfService.getById(id);
    }

    //Put /api/v1/shelves/{id}
    @PutMapping("/{id}")
    public ShelfResponse update(
            @PathVariable Long id, @RequestBody ShelfUpdateRequest request
            ){
        return shelfService.update(id, request);
    }

    //Patch /api/v1/shelves/{id}
    @PatchMapping("/{id}")
    public ShelfResponse patch(
            @PathVariable Long id, @RequestBody ShelfUpdateRequest request
    ){
        return shelfService.patch(id, request);
    }

    //Delete /api/v1/shelves/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        shelfService.delete(id);
    }

}
