package group.networkinventorytask.company.inventory.service;

import group.networkinventorytask.company.inventory.Exception.CardNotFound;
import group.networkinventorytask.company.inventory.Exception.CardNotInSlot;
import group.networkinventorytask.company.inventory.config.CardMapper;
import group.networkinventorytask.company.inventory.dto.Update.CardUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.CardCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.CardResponse;
import group.networkinventorytask.company.inventory.entity.Card;
import group.networkinventorytask.company.inventory.entity.Slot;
import group.networkinventorytask.company.inventory.repository.CardRepository;
import group.networkinventorytask.company.inventory.repository.SlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final SlotRepository slotRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, SlotRepository slotRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.slotRepository = slotRepository;
        this.cardMapper = cardMapper;
    }

    //Create
    public CardResponse create(CardCreateRequest request){
        Slot slot = slotRepository
                .findById(request.getSlotId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Slot not found"
                                        + request.getSlotId()));

        if (cardRepository.existsById(request.getId())){
            throw new RuntimeException(
                    "Card ID already exists " + request.getId());
        }

        if(cardRepository.existsBySerialNumber(request.getSerialNumber())){
            throw new RuntimeException(
                    "Serial number already exists");
        }

        Card card = new Card();

        card.setSlot(slot);
        card.setId(request.getId());
        card.setPartNumber(request.getPartNumber());
        card.setCardType(request.getCardType());
        card.setSerialNumber(request.getSerialNumber());
        card.setPortCount(request.getPortCount());
        card.setHardwareRevision(request.getHardwareRevision());
        card.setStatus(request.getStatus());

        LocalDateTime now = LocalDateTime.now();

        card.setCreatedAt(now);
        card.setUpdatedAt(now);

        Card savedCard = cardRepository.save(card);

        return cardMapper.toResponse(savedCard);
    }

    //Get all
    public Page<CardResponse> getAll(
            String status,
            Pageable pageable) {

        Page<Card> cards;

        if (status != null) {

            cards = cardRepository.findAll((root, query, cb) ->
                            cb.and(
                                    cb.equal(root.get("status"), status)
                            ),
                    pageable);

        } else {

            cards = cardRepository .findAll(pageable);
        }

        return cards.map(cardMapper::toResponse);
    }

    // GET BY ID
    public CardResponse getById(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Card not found: " + id));

        return cardMapper.toResponse(card);
    }

    // PUT
    public CardResponse update(
            Long id,
            CardUpdateRequest request) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFound(
                                "Card not found: " + id));

        Slot slot = slotRepository
                .findById(request.getSlotId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Slot not found: "
                                        + request.getSlotId()));

        card.setSlot(slot);
        card.setId(request.getId());
        card.setPartNumber(request.getPartNumber());
        card.setCardType(request.getCardType());
        card.setSerialNumber(request.getSerialNumber());
        card.setPortCount(request.getPortCount());
        card.setHardwareRevision(request.getHardwareRevision());
        card.setStatus(request.getStatus());
        card.setUpdatedAt(LocalDateTime.now());

        Card updatedCard = cardRepository.save(card);

        return cardMapper.toResponse(updatedCard);
    }

    //Patch
    public CardResponse patch(
            Long id,
            CardUpdateRequest request) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Card not found: " + id));

        if (request.getSlotId() != null) {
            Slot slot = slotRepository
                    .findById(request.getSlotId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Slot not found: "
                                            + request.getSlotId()));
            card.setSlot(slot);
        }

        if (request.getPartNumber() != null) {
            card.setPartNumber(request.getPartNumber());
        }

        if (request.getCardType() != null) {
            card.setCardType(request.getCardType());
        }

        if (request.getSerialNumber() != null) {
            card.setSerialNumber(request.getSerialNumber());
        }

        if (request.getPortCount() != null) {
            card.setPortCount(request.getPortCount());
        }

        if (request.getHardwareRevision() != null) {
            card.setHardwareRevision(request.getHardwareRevision());
        }

        if (request.getStatus() != null) {
            card.setStatus(request.getStatus());
        }

        card.setUpdatedAt(LocalDateTime.now());

        Card updateCard = cardRepository.save(card);

        return cardMapper.toResponse(updateCard);
    }

    //Delete
    public void delete(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Card not found: " + id));

        cardRepository.delete(card);
    }

    //Get card of a slot
    public List<CardResponse> getCardBySlotId(Long slotId) {

        if (!slotRepository.existsById(slotId)) {
            throw new RuntimeException(
                    "Slot not found: " + slotId);
        }

        List<Card> cards =
                cardRepository.findBySlotId(slotId);

        return cards.stream()
                .map(cardMapper::toResponse)
                .toList();
    }
}
