package group.networkinventorytask.company.inventory.service;

import group.networkinventorytask.company.inventory.config.SlotMapper;
import group.networkinventorytask.company.inventory.dto.Update.SlotUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.SlotCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.SlotResponse;
import group.networkinventorytask.company.inventory.entity.Card;
import group.networkinventorytask.company.inventory.entity.Shelf;
import group.networkinventorytask.company.inventory.entity.Slot;
import group.networkinventorytask.company.inventory.repository.CardRepository;
import group.networkinventorytask.company.inventory.repository.ShelfRepository;
import group.networkinventorytask.company.inventory.repository.SlotRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotService {

    private final CardRepository cardRepository;
    private final SlotRepository slotRepository;
    private final ShelfRepository shelfRepository;
    private final SlotMapper slotMapper;

    public SlotService(SlotRepository slotRepository, ShelfRepository shelfRepository, SlotMapper slotMapper,
                       CardRepository cardRepository) {
        this.slotRepository = slotRepository;
        this.shelfRepository = shelfRepository;
        this.slotMapper = slotMapper;
        this.cardRepository = cardRepository;
    }

    //Create
    public SlotResponse create(SlotCreateRequest request){
        Shelf shelf = shelfRepository
                .findById(request.getShelfId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shelf not found"
                                        + request.getShelfId()));

        if (slotRepository.existsById(request.getId())){
            throw new RuntimeException(
                    "Slot ID already exists " + request.getId());
        }

        if(slotRepository.existsBySlotNumber(request.getSlotNumber())){
            throw new RuntimeException(
                    "Slot number already exists");
        }

        Slot slot = new Slot();

        slot.setShelf(shelf);
        slot.setId(request.getId());
        slot.setSlotNumber(request.getSlotNumber());
        slot.setSlotType(request.getSlotType());
        slot.setStatus(request.getStatus());

        LocalDateTime now = LocalDateTime.now();

        slot.setCreatedAt(now);
        slot.setUpdatedAt(now);

        Slot savedSlot = slotRepository.save(slot);

        return slotMapper.toResponse(savedSlot);
    }

    //Get all
    public Page<SlotResponse> getAll(
            String status,
            Pageable pageable) {

        Page<Slot> slots;

        if (status != null) {

            slots = slotRepository.findAll((root, query, cb) ->
                            cb.and(
                                    cb.equal(root.get("status"), status)
                            ),
                    pageable);

        } else {

            slots = slotRepository .findAll(pageable);
        }

        return slots.map(slotMapper::toResponse);
    }

    // GET BY ID
    public SlotResponse getById(Long id) {

        Slot slot = slotRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Slot not found: " + id));

        return slotMapper.toResponse(slot);
    }

    // PUT
    public SlotResponse update(
            Long id,
            SlotUpdateRequest request) {

        Slot slot = slotRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Slot not found: " + id));

        Shelf shelf = shelfRepository
                .findById(request.getShelfId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shelf not found: "
                                        + request.getShelfId()));

        slot.setShelf(shelf);
        slot.setSlotNumber(request.getSlotNumber());
        slot.setSlotType(request.getSlotType());
        slot.setStatus(request.getStatus());
        slot.setUpdatedAt(LocalDateTime.now());

        Slot updatedSlot = slotRepository.save(slot);

        return slotMapper.toResponse(updatedSlot);
    }

    //Patch
    public SlotResponse patch(
            Long id,
            SlotUpdateRequest request) {

        Slot slot = slotRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Slot not found: " + id));

        if (request.getShelfId() != null) {
            Shelf shelf = shelfRepository
                    .findById(request.getShelfId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Shelf not found: "
                                            + request.getShelfId()));
            slot.setShelf(shelf);
        }

        if (request.getSlotNumber() != null) {
            slot.setSlotNumber(request.getSlotNumber());
        }

        if (request.getSlotType() != null) {
            slot.setSlotType(request.getSlotType());
        }

        if (request.getStatus() != null) {
            slot.setStatus(request.getStatus());
        }

        slot.setUpdatedAt(LocalDateTime.now());

        Slot updateSlot = slotRepository.save(slot);

        return slotMapper.toResponse(updateSlot);
    }

    //Delete
    public void delete(Long id) {

        Slot slot = slotRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Slot not found: " + id));

        slotRepository.delete(slot);
    }

    //Get slots of a shelf
    public List<SlotResponse> getSlotsByShelfId(Long shelfId) {

        if (!shelfRepository.existsById(shelfId)) {
            throw new RuntimeException(
                    "Shelf not found: " + shelfId);
        }

        List<Slot> slots =
                slotRepository.findByShelfId(shelfId);

        return slots.stream()
                .map(slotMapper::toResponse)
                .toList();
    }

    //Insert card into slot
    @Transactional
    public SlotResponse installCard(Long slotId, Long cardId) {

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException(
                        "Slot ID not found: " + slotId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException(
                        "Card ID not found: " + cardId));

        if (card.getSlot() != null) {
            throw new RuntimeException(
                    "Card is already installed in another slot.");
        }

        if (slot.getCards() != null && !slot.getCards().isEmpty()) {
            throw new RuntimeException(
                    "Slot already contains a card.");
        }

        card.setSlot(slot);

        if (slot.getCards() == null) {
            slot.setCards(new ArrayList<>());
        }

        slot.getCards().add(card);

        cardRepository.save(card);

        return slotMapper.toResponse(slot);
    }

    @Transactional
    public SlotResponse removeCard(Long slotId, Long cardId) {

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException(
                        "Slot ID not found: " + slotId));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException(
                        "Card ID not found: " + cardId));

        if (card.getSlot() == null
                || !card.getSlot().getId().equals(slotId)) {

            throw new RuntimeException(
                    "Card is not installed in this slot.");
        }

        card.setSlot(null);

        if (slot.getCards() != null) {
            slot.getCards().removeIf(
                    existingCard ->
                            existingCard.getId().equals(cardId));
        }

        cardRepository.save(card);

        return slotMapper.toResponse(slot);
    }
}
