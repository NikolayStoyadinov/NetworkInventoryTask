package group.networkinventorytask;

import group.networkinventorytask.company.inventory.config.CardMapper;
import group.networkinventorytask.company.inventory.config.RouterMapper;
import group.networkinventorytask.company.inventory.config.ShelfMapper;
import group.networkinventorytask.company.inventory.dto.Update.RouterUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.RouterCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.CardResponse;
import group.networkinventorytask.company.inventory.dto.response.RouterResponse;
import group.networkinventorytask.company.inventory.dto.response.ShelfTreeResponse;
import group.networkinventorytask.company.inventory.dto.response.SlotTreeResponse;
import group.networkinventorytask.company.inventory.entity.*;
import group.networkinventorytask.company.inventory.repository.*;
import group.networkinventorytask.company.inventory.service.RouterService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouterServiceTest {

    @Mock
    private RouterRepository routerRepository;

    @Mock
    private SlotRepository slotRepository;

    @Mock
    private RouterMapper routerMapper;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private ShelfMapper shelfMapper;

    @Mock
    private NetworkSiteRepository networkSiteRepository;

    @Mock
    private ShelfRepository shelfRepository;

    @InjectMocks
    private RouterService routerService;

    //Create
    @Test
    void CreateRouter() {

        RouterCreateRequest request = new RouterCreateRequest();
        request.setId(1L);
        request.setSiteId(1L);
        request.setHostname("TEST");
        request.setManagementIp("190.091.90");
        request.setModel("MODEL-01");
        request.setSerialNumber("19090");
        request.setSoftwareVersion("1.9.10");
        request.setVendor("A1");
        request.setStatus("ONLINE");

        NetworkSite networkSite = new NetworkSite();
        networkSite.setId(1L);

        Router savedrouter = new Router();
        savedrouter.setId(1L);
        savedrouter.setNetworkSite(networkSite);
        savedrouter.setHostname("TEST");
        savedrouter.setManagementIp("190.091.90");
        savedrouter.setModel("MODEL-01");
        savedrouter.setSerialNumber("19090");
        savedrouter.setSoftwareVersion("1.9.10");
        savedrouter.setVendor("A1");
        savedrouter.setStatus("ONLINE");

        RouterResponse response = new RouterResponse();
        response.setId(1L);
        response.setHostname("TEST");
        response.setManagementIp("190.091.90");
        response.setModel("MODEL-01");
        response.setSerialNumber("19090");
        response.setSoftwareVersion("1.9.10");
        response.setVendor("A1");
        response.setStatus("ONLINE");

        when(networkSiteRepository.findById(1L))
                .thenReturn(Optional.of(networkSite));
        when(routerRepository.save(any(Router.class)))
                .thenReturn(savedrouter);
        when(routerMapper.toResponse(savedrouter))
                .thenReturn(response);

        RouterResponse result = routerService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TEST", result.getHostname());
        assertEquals("19090", result.getSerialNumber());
        assertEquals("ONLINE", result.getStatus());

        verify(networkSiteRepository).findById(1L);
        verify(routerRepository).save(any(Router.class));
        verify(routerMapper).toResponse(savedrouter);
    }

    //Get all
    @Test
    void GetAllRouters() {

        Router router1 = new Router();
        router1.setId(1L);
        router1.setHostname("TEST");
        router1.setManagementIp("190.091.90");
        router1.setModel("MODEL-01");
        router1.setSerialNumber("19090");
        router1.setSoftwareVersion("1.9.10");
        router1.setVendor("A1");
        router1.setStatus("ONLINE");

        Router router2 = new Router();
        router2.setId(2L);
        router2.setHostname("TEST-2");
        router2.setManagementIp("190.091.91");
        router2.setModel("MODEL-02");
        router2.setSerialNumber("19091");
        router2.setSoftwareVersion("1.9.10");
        router2.setVendor("A1");
        router2.setStatus("ONLINE");

        RouterResponse response1 = new RouterResponse();
        response1.setId(1L);
        response1.setHostname("TEST");
        response1.setManagementIp("190.091.90");
        response1.setModel("MODEL-01");
        response1.setSerialNumber("19090");
        response1.setSoftwareVersion("1.9.10");
        response1.setVendor("A1");
        response1.setStatus("ONLINE");

        RouterResponse response2 = new RouterResponse();
        response2.setId(2L);
        response2.setHostname("TEST-2");
        response2.setManagementIp("190.091.91");
        response2.setModel("MODEL-02");
        response2.setSerialNumber("19091");
        response2.setSoftwareVersion("1.9.10");
        response2.setVendor("A1");
        response2.setStatus("ONLINE");

        List<Router> routers = List.of(router1, router2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Router> routerPage = new PageImpl<>(routers, pageable, routers.size());

        when(routerRepository.findAll(pageable))
                .thenReturn(routerPage);
        when(routerMapper.toResponse(router1))
                .thenReturn(response1);
        when(routerMapper.toResponse(router2))
                .thenReturn(response2);

        Page<RouterResponse> result = routerService.getAll(null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("19090", result.getContent().get(0).getSerialNumber());
        assertEquals("19091", result.getContent().get(1).getSerialNumber());

        verify(routerRepository).findAll(pageable);
        verify(routerMapper).toResponse(router1);
        verify(routerMapper).toResponse(router2);

    }

    //Get by id
    @Test
    void GetByID() {

        Long routerId = 1L;

        Router router = new Router();
        router.setId(routerId);
        router.setSerialNumber("19090");

        RouterResponse response = new RouterResponse();
        response.setId(1L);
        response.setSerialNumber("19090");

        when(routerRepository.findById(routerId))
                .thenReturn(Optional.of(router));
        when(routerMapper.toResponse(router))
                .thenReturn(response);

        RouterResponse result = routerService.getById(routerId);

        assertNotNull(result);
        assertEquals(routerId, result.getId());
        assertEquals("19090", result.getSerialNumber());

        verify(routerRepository).findById(routerId);
    }

    //Put
    @Test
    void UpdateRouter() {

        Long routerId = 1L;
        NetworkSite site = new NetworkSite();
        site.setId(1L);

        Router router = new Router();
        router.setId(1L);
        router.setNetworkSite(site);
        router.setHostname("TEST");
        router.setManagementIp("190.091.90");
        router.setModel("MODEL-01");
        router.setSerialNumber("19090");
        router.setSoftwareVersion("1.9.10");
        router.setVendor("A1");
        router.setStatus("ONLINE");

        RouterUpdateRequest request = new RouterUpdateRequest();
        request.setSiteId(1L);
        request.setHostname("TEST-02");
        request.setManagementIp("190.091.90");
        request.setModel("MODEL-01x2");
        request.setSerialNumber("19092");
        request.setSoftwareVersion("1.9.10");
        request.setVendor("A1");
        request.setStatus("ONLINE");

        RouterResponse response = new RouterResponse();
        response.setId(routerId);
        response.setSiteId(1L);
        response.setHostname("TEST-02");
        response.setManagementIp("190.091.90");
        response.setModel("MODEL-01x2");
        response.setSerialNumber("19092");
        response.setSoftwareVersion("1.9.10");
        response.setVendor("A1");
        response.setStatus("ONLINE");

        when(routerRepository.findById(routerId))
                .thenReturn(Optional.of(router));
        when(networkSiteRepository.findById(1L))
                .thenReturn(Optional.of(site));
        when(routerRepository.save(router))
                .thenReturn(router);
        when(routerMapper.toResponse(router))
                .thenReturn(response);
        RouterResponse result = routerService.update(routerId, request);

        assertNotNull(result);
        assertEquals(routerId, result.getId());
        assertEquals("TEST-02", result.getHostname());
        assertEquals("MODEL-01x2", result.getModel());
        assertEquals("19092", result.getSerialNumber());

        verify(routerRepository).findById(routerId);
        verify(routerRepository).save(router);
        verify(routerMapper).toResponse(router);
    }

    //Patch
    @Test
    void PatchRouter() {

        Long routerId = 1L;
        NetworkSite site = new NetworkSite();
        site.setId(1L);

        Router router = new Router();
        router.setId(1L);
        router.setNetworkSite(site);
        router.setHostname("TEST");
        router.setManagementIp("190.091.90");
        router.setModel("MODEL-01");
        router.setSerialNumber("19090");
        router.setSoftwareVersion("1.9.10");
        router.setVendor("A1");
        router.setStatus("ONLINE");

        RouterUpdateRequest request = new RouterUpdateRequest();
        request.setSiteId(1L);
        request.setHostname("TEST-02");
        request.setManagementIp("190.091.90");
        request.setModel("MODEL-01x2");
        request.setSerialNumber("19092");
        request.setSoftwareVersion("1.9.10");
        request.setVendor("A1");
        request.setStatus("OFFLINE");

        RouterResponse response = new RouterResponse();
        response.setId(routerId);
        response.setSiteId(1L);
        response.setHostname("TEST-02");
        response.setManagementIp("190.091.90");
        response.setModel("MODEL-01x2");
        response.setSerialNumber("19092");
        response.setSoftwareVersion("1.9.10");
        response.setVendor("A1");
        response.setStatus("OFFLINE");

        when(routerRepository.findById(routerId))
                .thenReturn(Optional.of(router));
        when(networkSiteRepository.findById(1L))
                .thenReturn(Optional.of(site));
        when(routerRepository.save(router))
                .thenReturn(router);
        when(routerMapper.toResponse(router))
                .thenReturn(response);
        RouterResponse result = routerService.patch(routerId, request);

        assertNotNull(result);
        assertEquals(routerId, result.getId());
        assertEquals(site, router.getNetworkSite());
        assertEquals("TEST-02", router.getHostname());
        assertEquals("MODEL-01x2", router.getModel());
        assertEquals("19092", router.getSerialNumber());
        assertEquals("OFFLINE", router.getStatus());

        verify(routerRepository).findById(routerId);
        verify(routerRepository).save(router);
        verify(routerMapper).toResponse(router);
        verify(networkSiteRepository).findById(1L);

    }

    //Delete
    @Test
    void DeleteRouter() {

        Long routerId = 1L;

        Router router = new Router();
        router.setId(routerId);
        router.setShelves(new ArrayList<>());

        when(routerRepository.findById(routerId))
                .thenReturn(Optional.of(router));

        routerService.delete(routerId);

        verify(routerRepository).findById(routerId);
        verify(routerRepository).delete(router);

    }

    //Get routers of a site
    @Test
    void GetRoutersOfSite() {


        Long siteId = 1L;

        Router router1 = new Router();
        router1.setId(1L);

        Router router2 = new Router();
        router2.setId(2L);

        List<Router> routers = List.of(router1, router2);

        RouterResponse response1 = new RouterResponse();
        response1.setId(1L);

        RouterResponse response2 = new RouterResponse();
        response2.setId(2L);

        when(networkSiteRepository.existsById(siteId))
                .thenReturn(true);

        when(routerRepository.findByNetworkSiteId(siteId))
                .thenReturn(routers);

        when(routerMapper.toResponse(router1))
                .thenReturn(response1);

        when(routerMapper.toResponse(router2))
                .thenReturn(response2);

        List<RouterResponse> result =
                routerService.getRoutersBySiteId(siteId);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(networkSiteRepository).existsById(siteId);
        verify(routerRepository).findByNetworkSiteId(siteId);
        verify(routerMapper).toResponse(router1);
        verify(routerMapper).toResponse(router2);

    }

    @Test
    void GetRouterTree() {

        Long routerId = 1L;

        Router router = new Router();
        router.setId(routerId);
        router.setHostname("TEST-ROUTER");

        RouterResponse routerResponse = new RouterResponse();
        routerResponse.setId(routerId);
        routerResponse.setHostname("TEST-ROUTER");

        Shelf shelf = new Shelf();
        shelf.setId(1L);

        ShelfTreeResponse shelfResponse = new ShelfTreeResponse();
        shelfResponse.setId(1L);

        Slot slot1 = new Slot();
        slot1.setId(1L);
        slot1.setSlotNumber("1");
        slot1.setSlotType("ETHERNET");
        slot1.setStatus("ACTIVE");

        Slot slot2 = new Slot();
        slot2.setId(2L);
        slot2.setSlotNumber("2");
        slot2.setSlotType("ETHERNET");
        slot2.setStatus("ACTIVE");

        Card card = new Card();
        card.setId(1L);
        card.setSerialNumber("CARD-001");

        slot1.setCards(List.of(card));
        slot2.setCards(List.of());

        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(1L);
        cardResponse.setSerialNumber("CARD-001");

        when(routerRepository.findById(routerId))
                .thenReturn(Optional.of(router));

        when(routerMapper.toResponse(router))
                .thenReturn(routerResponse);

        when(shelfRepository.findByRouterId(routerId))
                .thenReturn(List.of(shelf));

        when(shelfMapper.toTreeResponse(shelf))
                .thenReturn(shelfResponse);

        when(slotRepository.findByShelfId(shelf.getId()))
                .thenReturn(List.of(slot1, slot2));

        when(cardMapper.toResponse(card))
                .thenReturn(cardResponse);

        RouterResponse result =
                routerService.getRouterTree(routerId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TEST-ROUTER", result.getHostname());

        assertNotNull(result.getShelves());
        assertEquals(1, result.getShelves().size());

        ShelfTreeResponse resultShelf =
                result.getShelves().getFirst();

        assertEquals(1L, resultShelf.getId());

        assertNotNull(resultShelf.getSlots());
        assertEquals(2, resultShelf.getSlots().size());

        SlotTreeResponse resultSlot1 =
                resultShelf.getSlots().get(0);

        SlotTreeResponse resultSlot2 =
                resultShelf.getSlots().get(1);

        assertEquals(1L, resultSlot1.getId());
        assertEquals("1", resultSlot1.getSlotNumber());

        assertEquals(2L, resultSlot2.getId());
        assertEquals("2", resultSlot2.getSlotNumber());

        assertNotNull(resultSlot1.getCards());
        assertEquals(1, resultSlot1.getCards().size());

        CardResponse resultCard =
                resultSlot1.getCards().getFirst();

        assertEquals(1L, resultCard.getId());
        assertEquals("CARD-001", resultCard.getSerialNumber());

        assertTrue(
                resultSlot2.getCards() == null
                        || resultSlot2.getCards().isEmpty()
        );

        verify(routerRepository).findById(routerId);
        verify(routerMapper).toResponse(router);
        verify(shelfRepository).findByRouterId(routerId);
        verify(shelfMapper).toTreeResponse(shelf);
        verify(slotRepository).findByShelfId(shelf.getId());
        verify(cardMapper).toResponse(card);
    }

}
