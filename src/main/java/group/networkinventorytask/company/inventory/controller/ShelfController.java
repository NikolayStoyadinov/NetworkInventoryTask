package group.networkinventorytask.company.inventory.controller;

import group.networkinventorytask.company.inventory.dto.Update.ShelfUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.ShelfCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.ShelfResponse;
import group.networkinventorytask.company.inventory.service.ShelfService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/shelves")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    //Post /api/v1/shelves
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
//    @DeleteMapping("/{id}")
//    public void delete(
//            @PathVariable Long id, @RequestParam(defaultValue = "false")
//            boolean cascade
//    ){
//        shelfService.delete(id, cascade);
//    }



}
