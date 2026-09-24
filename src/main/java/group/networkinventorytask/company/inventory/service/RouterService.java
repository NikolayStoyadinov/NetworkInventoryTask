package group.networkinventorytask.company.inventory.service;

import group.networkinventorytask.company.inventory.config.CardMapper;
import group.networkinventorytask.company.inventory.config.RouterMapper;
import group.networkinventorytask.company.inventory.config.ShelfMapper;
import group.networkinventorytask.company.inventory.dto.request.RouterCreateRequest;
import group.networkinventorytask.company.inventory.dto.Update.RouterUpdateRequest;
import group.networkinventorytask.company.inventory.dto.response.*;
import group.networkinventorytask.company.inventory.entity.NetworkSite;
import group.networkinventorytask.company.inventory.entity.Router;
import group.networkinventorytask.company.inventory.repository.NetworkSiteRepository;
import group.networkinventorytask.company.inventory.repository.RouterRepository;
import group.networkinventorytask.company.inventory.repository.ShelfRepository;
import group.networkinventorytask.company.inventory.repository.SlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RouterService {

    private final RouterRepository routerRepository;
    private final NetworkSiteRepository networkSiteRepository;
    private final ShelfRepository shelfRepository;
    private final SlotRepository slotRepository;
    private final RouterMapper routerMapper;
    private final ShelfMapper shelfMapper;
    private final CardMapper cardMapper;


    public RouterService(
            RouterRepository routerRepository,
            NetworkSiteRepository networkSiteRepository,
            ShelfRepository shelfRepository,
            SlotRepository slotRepository,
            RouterMapper routerMapper,
            ShelfMapper shelfMapper,
            CardMapper cardMapper) {

        this.routerRepository = routerRepository;
        this.networkSiteRepository = networkSiteRepository;
        this.shelfRepository = shelfRepository;
        this.slotRepository = slotRepository;
        this.routerMapper = routerMapper;
        this.shelfMapper = shelfMapper;
        this.cardMapper = cardMapper;
    }

    // CREATE
    public RouterResponse create(RouterCreateRequest request) {

        NetworkSite site = networkSiteRepository
                .findById(request.getSiteId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Network site not found: "
                                        + request.getSiteId()));

        if (request.getId() != null &&
                routerRepository.existsById(request.getId())) {

            throw new RuntimeException(
                    "Router ID already exists: " + request.getId());
        }

        if (routerRepository.existsByHostname(request.getHostname())) {
            throw new RuntimeException(
                    "Hostname already exists");
        }

        if (routerRepository.existsBySerialNumber(
                request.getSerialNumber())) {

            throw new RuntimeException(
                    "Serial number already exists");
        }
        Router router = new Router();

        router.setId(request.getId());
        router.setNetworkSite(site);
        router.setHostname(request.getHostname());
        router.setVendor(request.getVendor());
        router.setModel(request.getModel());
        router.setSerialNumber(request.getSerialNumber());
        router.setManagementIp(request.getManagementIp());
        router.setSoftwareVersion(request.getSoftwareVersion());
        router.setStatus(request.getStatus());

        LocalDateTime now = LocalDateTime.now();

        router.setCreatedAt(now);
        router.setUpdatedAt(now);

        Router savedRouter = routerRepository.save(router);

        return routerMapper.toResponse(savedRouter);
    }

    //Get all
    public Page<RouterResponse> getAll(
            String status,
            Pageable pageable) {

        Page<Router> routers;

        if (status != null) {

            routers = routerRepository.findAll((root, query, cb) ->
                    cb.and(
                            cb.equal(root.get("status"), status)
                            ),
                    pageable);

        } else {

            routers = routerRepository.findAll(pageable);
        }

        return routers.map(routerMapper::toResponse);
    }


    // GET BY ID
    public RouterResponse getById(Long id) {

        Router router = routerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: " + id));

        return routerMapper.toResponse(router);
    }


    // PUT
    public RouterResponse update(
            Long id,
            RouterUpdateRequest request) {

        Router router = routerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: " + id));

        NetworkSite site = networkSiteRepository
                .findById(request.getSiteId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Network site not found: "
                                        + request.getSiteId()));

        router.setNetworkSite(site);
        router.setHostname(request.getHostname());
        router.setVendor(request.getVendor());
        router.setModel(request.getModel());
        router.setSerialNumber(request.getSerialNumber());
        router.setManagementIp(request.getManagementIp());
        router.setSoftwareVersion(request.getSoftwareVersion());
        router.setStatus(request.getStatus());

        router.setUpdatedAt(LocalDateTime.now());

        Router updatedRouter = routerRepository.save(router);

        return routerMapper.toResponse(updatedRouter);
    }

    // PATCH
    public RouterResponse patch(
            Long id,
            RouterUpdateRequest request) {

        Router router = routerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: " + id));

        if (request.getSiteId() != null) {

            NetworkSite site = networkSiteRepository
                    .findById(request.getSiteId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Network site not found: "
                                            + request.getSiteId()));

            router.setNetworkSite(site);
        }

        if (request.getHostname() != null) {
            router.setHostname(request.getHostname());
        }

        if (request.getVendor() != null) {
            router.setVendor(request.getVendor());
        }

        if (request.getModel() != null) {
            router.setModel(request.getModel());
        }

        if (request.getSerialNumber() != null) {
            router.setSerialNumber(
                    request.getSerialNumber());
        }

        if (request.getManagementIp() != null) {
            router.setManagementIp(
                    request.getManagementIp());
        }

        if (request.getSoftwareVersion() != null) {
            router.setSoftwareVersion(
                    request.getSoftwareVersion());
        }

        if (request.getStatus() != null) {
            router.setStatus(request.getStatus());
        }

        router.setUpdatedAt(LocalDateTime.now());

        Router updatedRouter = routerRepository.save(router);

        return routerMapper.toResponse(updatedRouter);
    }

    // DELETE
    public void delete(Long id) {

        Router router = routerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: " + id));

        routerRepository.delete(router);
    }


    // GET ROUTERS AT SITE
    public List<RouterResponse> getRoutersBySiteId(Long siteId) {

        if (!networkSiteRepository.existsById(siteId)) {
            throw new RuntimeException(
                    "Network site not found: " + siteId);
        }

        List<Router> routers =
                routerRepository.findByNetworkSiteId(siteId);

        return routers.stream()
                .map(routerMapper::toResponse)
                .toList();
    }

    // Get Router tree hierarchy
    public RouterResponse getRouterTree(Long routerId) {

        Router router = routerRepository.findById(routerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: " + routerId));

        // Convert Router entity to RouterResponse
        RouterResponse routerResponse =
                routerMapper.toResponse(router);

        // Find all shelves belonging to this router
        List<ShelfTreeResponse> shelfResponses =
                shelfRepository.findByRouterId(routerId)
                        .stream()
                        .map(shelf -> {

                            // Convert Shelf entity to ShelfTreeResponse
                            ShelfTreeResponse shelfResponse =
                                    shelfMapper.toTreeResponse(shelf);

                            // Find all slots belonging to this shelf
                            List<SlotTreeResponse> slotResponses =
                                    slotRepository
                                            .findByShelfId(shelf.getId())
                                            .stream()
                                            .map(slot -> {

                                                // Create the tree-specific Slot response
                                                SlotTreeResponse slotTreeResponse =
                                                        new SlotTreeResponse();

                                                slotTreeResponse.setId(slot.getId());

                                                slotTreeResponse.setSlotNumber(
                                                        slot.getSlotNumber());

                                                slotTreeResponse.setSlotType(
                                                        slot.getSlotType());

                                                slotTreeResponse.setStatus(
                                                        slot.getStatus());

                                                slotTreeResponse.setCreatedAt(
                                                        slot.getCreatedAt());

                                                slotTreeResponse.setUpdatedAt(
                                                        slot.getUpdatedAt());

                                                // Add Cards if this slot has any
                                                if (slot.getCards() != null
                                                        && !slot.getCards().isEmpty()) {

                                                    List<CardResponse> cardResponses =
                                                            slot.getCards()
                                                                    .stream()
                                                                    .map(cardMapper::toResponse)
                                                                    .toList();

                                                    slotTreeResponse.setCards(
                                                            cardResponses);
                                                }

                                                return slotTreeResponse;
                                            })
                                            .toList();

                            // Add slots to the shelf tree response
                            shelfResponse.setSlots(slotResponses);

                            return shelfResponse;
                        })
                        .toList();

        // Add shelves to the router response
        routerResponse.setShelves(shelfResponses);

        return routerResponse;
    }

}
