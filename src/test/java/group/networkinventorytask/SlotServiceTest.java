package group.networkinventorytask;


import group.networkinventorytask.company.inventory.config.SlotMapper;
import group.networkinventorytask.company.inventory.dto.Update.SlotUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.SlotCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.SlotResponse;
import group.networkinventorytask.company.inventory.entity.*;
import group.networkinventorytask.company.inventory.repository.CardRepository;
import group.networkinventorytask.company.inventory.repository.ShelfRepository;
import group.networkinventorytask.company.inventory.repository.SlotRepository;
import group.networkinventorytask.company.inventory.service.SlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SlotServiceTest {

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ShelfRepository shelfRepository;

    @Mock
    private SlotMapper slotMapper;

    @InjectMocks
    private SlotService slotService;

    //Create
    @Test
    void CreateSlot() {

        SlotCreateRequest request = new SlotCreateRequest();
        request.setId(1L);
        request.setShelfId(1L);
        request.setSlotNumber("111");
        request.setSlotType("1B");
        request.setStatus("FREE");

        Shelf shelf = new Shelf();
        shelf.setId(1L);

        Slot savedSlot = new Slot();
        savedSlot.setId(1L);
        savedSlot.setShelf(shelf);
        savedSlot.setSlotNumber("111");
        savedSlot.setSlotType("18");
        savedSlot.setStatus("FREE");

        SlotResponse response = new SlotResponse();
        response.setId(1L);
        response.setSlotNumber("111");
        response.setSlotType("1B");
        response.setStatus("FREE");

        when(shelfRepository.findById(1L))
                .thenReturn(Optional.of(shelf));
        when(slotRepository.save(any(Slot.class)))
                .thenReturn(savedSlot);
        when(slotMapper.toResponse(savedSlot))
                .thenReturn(response);

        SlotResponse result = slotService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("111", result.getSlotNumber());
        assertEquals("1B", result.getSlotType());
        assertEquals("FREE", result.getStatus());

        verify(shelfRepository).findById(1L);
        verify(slotRepository).save(any(Slot.class));
        verify(slotMapper).toResponse(savedSlot);
    }

    //Create - negative
    @Test
    void CreateSlot_NumberAlreadyExists() {

        SlotCreateRequest request = new SlotCreateRequest();

        request.setShelfId(1L);
        request.setId(1L);
        request.setSlotNumber("111");

        Shelf shelf = new Shelf();
        shelf.setId(1L);

        when(shelfRepository.findById(1L))
                .thenReturn(Optional.of(shelf));

        when(slotRepository.existsBySlotNumber("111"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.create(request)
        );

        assertEquals(
                "Slot number already exists: 111",
                exception.getMessage()
        );

        verify(slotRepository, never()).save(any());
    }

    //Get all
    @Test
    void GetAllSlots() {

        Slot slot1 = new Slot();
        slot1.setId(1L);
        slot1.setSlotNumber("111");
        slot1.setSlotType("18");
        slot1.setStatus("FREE");

        Slot slot2 = new Slot();
        slot2.setId(2L);
        slot2.setSlotNumber("222");
        slot2.setSlotType("19");
        slot2.setStatus("OCCUPIED");

        SlotResponse response1 = new SlotResponse();
        response1.setId(1L);
        response1.setSlotNumber("111");
        response1.setSlotType("18");
        response1.setStatus("FREE");

        SlotResponse response2 = new SlotResponse();
        response2.setId(2L);
        response2.setSlotNumber("222");
        response2.setSlotType("19");
        response2.setStatus("OCCUPIED");

        List<Slot> slots = List.of(slot1, slot2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Slot> slotPage = new PageImpl<>(slots, pageable, slots.size());

        when(slotRepository.findAll(pageable))
                .thenReturn(slotPage);

        when(slotMapper.toResponse(slot1))
                .thenReturn(response1);

        when(slotMapper.toResponse(slot2))
                .thenReturn(response2);

        Page<SlotResponse> result = slotService.getAll(null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("111", result.getContent().get(0).getSlotNumber());
        assertEquals("222", result.getContent().get(1).getSlotNumber());

        verify(slotRepository).findAll(pageable);

        verify(slotMapper).toResponse(slot1);
        verify(slotMapper).toResponse(slot2);
    }

    //Get all - negative
    @Test
    void GetAllSlot_WhenNoSlotsExist() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Slot> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        when(slotRepository.findAll(pageable))
                .thenReturn(emptyPage);

        Page<SlotResponse> result =
                slotService.getAll(null, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(slotRepository).findAll(pageable);

        verifyNoInteractions(slotMapper);
    }

    //GetById
    @Test
    void GetById() {
        Long slotId = 1L;

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setSlotNumber("111");

        SlotResponse response = new SlotResponse();
        response.setId(slotId);
        response.setSlotNumber("111");

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(slotMapper.toResponse(slot))
                .thenReturn(response);

        SlotResponse result =
                slotService.getById(slotId);

        assertNotNull(result);
        assertEquals(slotId, result.getId());
        assertEquals("111", result.getSlotNumber());

        verify(slotRepository).findById(slotId);
    }

    //Get by id - negative
    @Test
    void getById_whenSlotDoesNotExist() {

        Long slotId = 999L;

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.getById(slotId)
        );

        assertEquals(
                "Slot not found: " + slotId,
                exception.getMessage()
        );
    }


    //Put
    @Test
    void UpdateSlot() {

        Long slotId = 1L;
        Shelf shelf = new Shelf();
        shelf.setId(1L);

        Slot slot = new Slot();
        slot.setId(1L);
        slot.setShelf(shelf);
        slot.setSlotNumber("111");
        slot.setSlotType("18");
        slot.setStatus("FREE");

        SlotUpdateRequest request = new SlotUpdateRequest();
        request.setShelfId(1L);
        request.setSlotNumber("111");
        request.setSlotType("20");
        request.setStatus("OCCUPIED");

        SlotResponse response = new SlotResponse();
        response.setId(slotId);
        response.setShelfId(1L);
        response.setSlotType("20");
        response.setSlotNumber("111");
        response.setStatus("OCCUPIED");

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(shelfRepository.findById(1L))
                .thenReturn(Optional.of(shelf));

        when(slotRepository.save(slot))
                .thenReturn(slot);

        when(slotMapper.toResponse(slot))
                .thenReturn(response);

        SlotResponse result = slotService.update(slotId, request);

        assertNotNull(result);
        assertEquals(slotId, result.getId());
        assertEquals("20", result.getSlotType());
        assertEquals("111", result.getSlotNumber());
        assertEquals("20", result.getSlotType());
        assertEquals("OCCUPIED", result.getStatus());

        verify(slotRepository).findById(slotId);
        verify(slotRepository).save(slot);
        verify(slotMapper).toResponse(slot);
    }

    //Put - Negative
    @Test
    void UpdateSlot_WhenSlotDoesNotExist() {

        Long slotId = 999L;

        SlotUpdateRequest request = new SlotUpdateRequest();

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> slotService.update(slotId, request)
        );

        verify(slotRepository, never())
                .save(any(Slot.class));
    }

    //Patch
    @Test
    void PatchUpdate() {

        Long slotId = 1L;
        Shelf shelf = new Shelf();
        shelf.setId(1L);

        Slot slot = new Slot();
        slot.setId(1L);
        slot.setShelf(shelf);
        slot.setSlotNumber("111");
        slot.setSlotType("18");
        slot.setStatus("OCCUPIED");

        SlotUpdateRequest request = new SlotUpdateRequest();
        request.setShelfId(1L);
        request.setStatus("EMPTY");

        SlotResponse response = new SlotResponse();
        response.setId(slotId);
        response.setShelfId(1L);
        response.setSlotNumber("111");
        response.setStatus("EMPTY");
        response.setSlotType("18");

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(shelfRepository.findById(1L))
                .thenReturn(Optional.of(shelf));

        when(slotRepository.save(slot))
                .thenReturn(slot);

        when(slotMapper.toResponse(slot))
                .thenReturn(response);

        SlotResponse result = slotService.patch(slotId, request);

        assertNotNull(result);
        assertEquals(slotId, result.getId());
        assertEquals("111", result.getSlotNumber());
        assertEquals("18", result.getSlotType());
        assertEquals("EMPTY", result.getStatus());

        verify(slotRepository).findById(slotId);
        verify(slotRepository).save(slot);
        verify(slotMapper).toResponse(slot);
        verify(shelfRepository).findById(1L);
    }

    //Delete
    @Test
    void DeleteSlot() {

        Long slotId = 1L;

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setCards(new ArrayList<>());

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        slotService.delete(slotId);

        verify(slotRepository).findById(slotId);
        verify(slotRepository).delete(slot);
    }

    //Delete - negative
    @Test
    void DeleteSlotWhenNotExist() {

        Long slotId = 1L;

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.delete(slotId)
        );

        assertEquals(
                "Slot not found: 1",
                exception.getMessage()
        );

        verify(slotRepository).findById(slotId);
        verify(slotRepository, never()).delete(any(Slot.class));
    }

    //Card - Slot Interactions
    @Test
    void InstallCardIntoSlot() {

        Long slotId = 1L;
        Long cardId = 10L;

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setCards(new ArrayList<>());

        Card card = new Card();
        card.setId(cardId);
        card.setSlot(null);

        SlotResponse response = new SlotResponse();
        response.setId(slotId);

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        when(slotMapper.toResponse(slot))
                .thenReturn(response);

        SlotResponse result =
                slotService.installCard(slotId, cardId);

        assertEquals(slotId, result.getId());
        assertEquals(slot, card.getSlot());
        assertTrue(slot.getCards().contains(card));

        verify(cardRepository).save(card);
    }

    @Test
    void CardDoesNotExist() {

        Long slotId = 1L;
        Long cardId = 10L;

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setCards(new ArrayList<>());

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.installCard(slotId, cardId)
        );

        assertEquals(
                "Card ID not found: " + cardId,
                exception.getMessage()
        );

        verify(cardRepository, never()).save(any());
    }

    @Test
    void SlotAlreadyContainsCard() {

        Long slotId = 1L;
        Long cardId = 10L;

        Card existingCard = new Card();
        existingCard.setId(5L);

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setCards(new ArrayList<>());
        slot.getCards().add(existingCard);

        Card newCard = new Card();
        newCard.setId(cardId);

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(newCard));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.installCard(slotId, cardId)
        );

        assertEquals(
                "Slot already contains a card.",
                exception.getMessage()
        );

        verify(cardRepository, never()).save(any());
    }

    @Test
    void CardAlreadyInstalled() {

        Long slotId = 1L;
        Long cardId = 10L;

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setCards(new ArrayList<>());

        Slot anotherSlot = new Slot();
        anotherSlot.setId(2L);

        Card card = new Card();
        card.setId(cardId);
        card.setSlot(anotherSlot);

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.installCard(slotId, cardId)
        );

        assertEquals(
                "Card is already installed in another slot.",
                exception.getMessage()
        );

        verify(cardRepository, never()).save(any());
    }

    @Test
    void RemoveCardFromSlot() {

        Long slotId = 1L;
        Long cardId = 10L;

        Slot slot = new Slot();
        slot.setId(slotId);
        slot.setCards(new ArrayList<>());

        Card card = new Card();
        card.setId(cardId);
        card.setSlot(slot);

        slot.getCards().add(card);

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        when(slotMapper.toResponse(slot))
                .thenReturn(new SlotResponse());

        slotService.removeCard(slotId, cardId);

        assertNull(card.getSlot());
        assertTrue(slot.getCards().isEmpty());

        verify(cardRepository).save(card);
    }

    @Test
    void CardIsInAnotherSlot() {

        Long slotId = 1L;
        Long cardId = 10L;

        Slot slot1 = new Slot();
        slot1.setId(slotId);

        Slot slot2 = new Slot();
        slot2.setId(2L);

        Card card = new Card();
        card.setId(cardId);
        card.setSlot(slot2);

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot1));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.removeCard(slotId, cardId)
        );

        assertEquals(
                "Card is not installed in this slot.",
                exception.getMessage()
        );

        verify(cardRepository, never()).save(any());
    }

    @Test
    void removeCardWhenCardNotInstalled() {

        Long slotId = 1L;
        Long cardId = 10L;

        Slot slot = new Slot();
        slot.setId(slotId);

        Card card = new Card();
        card.setId(cardId);
        card.setSlot(null);

        when(slotRepository.findById(slotId))
                .thenReturn(Optional.of(slot));

        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.removeCard(slotId, cardId)
        );

        assertEquals(
                "Card is not installed in this slot.",
                exception.getMessage()
        );

        verify(cardRepository, never()).save(any());

    }

    //Get slots of a shelf
    @Test
    void getSlotsByShelfId() {

        Long shelfId = 1L;

        Slot slot1 = new Slot();
        slot1.setId(1L);

        Slot slot2 = new Slot();
        slot2.setId(2L);

        List<Slot> slots = List.of(slot1, slot2);

        SlotResponse response1 = new SlotResponse();
        response1.setId(1L);

        SlotResponse response2 = new SlotResponse();
        response2.setId(2L);

        when(shelfRepository.existsById(shelfId))
                .thenReturn(true);

        when(slotRepository.findByShelfId(shelfId))
                .thenReturn(slots);

        when(slotMapper.toResponse(slot1))
                .thenReturn(response1);

        when(slotMapper.toResponse(slot2))
                .thenReturn(response2);

        List<SlotResponse> result =
                slotService.getSlotsByShelfId(shelfId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(shelfRepository).existsById(shelfId);
        verify(slotRepository).findByShelfId(shelfId);
        verify(slotMapper).toResponse(slot1);
        verify(slotMapper).toResponse(slot2);
    }

    //Get slots of a shelf - negative
    @Test
    void getSlotsByShelfId_ShelfNotFound() {

        Long shelfId = 999L;

        when(shelfRepository.existsById(shelfId))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> slotService.getSlotsByShelfId(shelfId)
        );

        assertEquals(
                "Shelf not found: " + shelfId,
                exception.getMessage()
        );

        verify(shelfRepository).existsById(shelfId);

        verify(slotRepository, never())
                .findByShelfId(shelfId);
    }
}

