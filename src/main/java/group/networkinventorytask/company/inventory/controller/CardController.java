package group.networkinventorytask.company.inventory.controller;

import group.networkinventorytask.company.inventory.dto.Update.CardUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.CardCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.CardResponse;
import group.networkinventorytask.company.inventory.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/card")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) { this.cardService = cardService; }

    //Post /api/v1/card
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse create(@RequestBody CardCreateRequest request) { return cardService.create(request); }

    //Get /api/v1/card
    @GetMapping
    public Page<CardResponse> getAll(
            @RequestParam(required = false) String status,
            Pageable pageable){

        return  cardService.getAll(status, pageable);
    }

    //Get /api/v1/card/{id}
    @GetMapping("/{id}")
    public CardResponse getById(
            @PathVariable Long id
    ){
        return cardService.getById(id);
    }

    //Put /api/v1/card/{id}
    @PutMapping("/{id}")
    public CardResponse update(
            @PathVariable Long id, @RequestBody CardUpdateRequest request
    ){
        return cardService.update(id, request);
    }

    //Patch /api/v1/card/{id}
    @PatchMapping("/{id}")
    public CardResponse patch(
            @PathVariable Long id, @RequestBody CardUpdateRequest request
    ){
        return cardService.patch(id, request);
    }

    //Delete /api/v1/card/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cardService.delete(id);
    }

}
