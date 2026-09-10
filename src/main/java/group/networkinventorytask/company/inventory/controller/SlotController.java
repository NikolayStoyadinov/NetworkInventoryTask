package group.networkinventorytask.company.inventory.controller;

import group.networkinventorytask.company.inventory.dto.Update.SlotUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.SlotCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.SlotResponse;
import group.networkinventorytask.company.inventory.service.SlotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    //Post /api/v1/slots
    @PostMapping
    public SlotResponse create(@RequestBody SlotCreateRequest request){
        return slotService.create(request);
    }

    //Get /api/v1/slots
    @GetMapping
    public Page<SlotResponse> getAll(
            @RequestParam(required = false) String status,
            Pageable pageable){

        return  slotService.getAll(status, pageable);
    }

    //Get /api/v1/slots/{id}
    @GetMapping("/{id}")
    public SlotResponse getById(
            @PathVariable Long id
    ){
        return slotService.getById(id);
    }

    //Put /api/v1/slots/{id}
    @PutMapping("/{id}")
    public SlotResponse update(
            @PathVariable Long id, @RequestBody SlotUpdateRequest request
    ){
        return slotService.update(id, request);
    }

    //Patch /api/v1/slots/{id}
    @PatchMapping("/{id}")
    public SlotResponse patch(
            @PathVariable Long id, @RequestBody SlotUpdateRequest request
    ){
        return slotService.patch(id, request);
    }

    //Delete /api/v1/slots/{id}
//    @DeleteMapping("/{id}")
//    public void delete(
//            @PathVariable Long id, @RequestParam(defaultValue = "false")
//            boolean cascade
//    ){
//        slotService.delete(id, cascade);
//    }

}
