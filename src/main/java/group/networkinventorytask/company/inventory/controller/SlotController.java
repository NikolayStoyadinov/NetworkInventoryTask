package group.networkinventorytask.company.inventory.controller;

import group.networkinventorytask.company.inventory.dto.Update.SlotUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.SlotCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.CardResponse;
import group.networkinventorytask.company.inventory.dto.response.RouterResponse;
import group.networkinventorytask.company.inventory.dto.response.SlotResponse;
import group.networkinventorytask.company.inventory.service.CardService;
import group.networkinventorytask.company.inventory.service.SlotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/slots")
public class SlotController {

    private final SlotService slotService;
    private final CardService cardService;

    public SlotController(SlotService slotService, CardService cardService) {
        this.slotService = slotService;
        this.cardService = cardService;
    }

    //Post /api/v1/slots
    @ResponseStatus(HttpStatus.CREATED)
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

    //Get card of a slot
    @GetMapping("/{id}/card")
    public List<CardResponse> getCards(@PathVariable Long id) {
        return cardService.getCardBySlotId(id);
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        slotService.delete(id);
    }

    //Post /api/v1/slots/{id}/card?cardId={id}
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}/card")
    public SlotResponse installCard(
            @PathVariable Long id,
            @RequestParam Long cardId) {

        return slotService.installCard(id, cardId);
    }

    //Delete /api/v1/slots/{id}/card?cardId={id}
    @DeleteMapping("/{id}/card")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SlotResponse removeCard(
            @PathVariable Long id,
            @RequestParam Long cardId) {

        return slotService.removeCard(id, cardId);
    }
}
