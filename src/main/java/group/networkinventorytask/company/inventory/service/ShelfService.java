package group.networkinventorytask.company.inventory.service;

import group.networkinventorytask.company.inventory.config.ShelfMapper;
import group.networkinventorytask.company.inventory.dto.Update.ShelfUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.ShelfCreateRequest;
import group.networkinventorytask.company.inventory.dto.response.ShelfResponse;
import group.networkinventorytask.company.inventory.entity.Router;
import group.networkinventorytask.company.inventory.entity.Shelf;
import group.networkinventorytask.company.inventory.repository.RouterRepository;
import group.networkinventorytask.company.inventory.repository.ShelfRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShelfService {

private final ShelfRepository shelfRepository;
private final RouterRepository routerRepository;
private final ShelfMapper shelfMapper;

    public ShelfService(ShelfRepository shelfRepository, RouterRepository routerRepository, ShelfMapper shelfMapper) {
        this.shelfRepository = shelfRepository;
        this.routerRepository = routerRepository;
        this.shelfMapper = shelfMapper;
    }

    //Create
    public ShelfResponse create(ShelfCreateRequest request){
        Router router = routerRepository
                .findById(request.getRouterId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found"
                                + request.getRouterId()));

        if (shelfRepository.existsById(request.getId())){
            throw new RuntimeException(
                    "Shelf ID already exists " + request.getId());
        }
        
        if(shelfRepository.existsByShelfNumber(request.getShelfNumber())){
            throw new RuntimeException(
                    "Shelf number already exists");
        }

        Shelf shelf = new Shelf();

        shelf.setRouter(router);
        shelf.setId(shelf.getId());
        shelf.setShelfNumber(shelf.getShelfNumber());
        shelf.setSerialNumber(shelf.getSerialNumber());
        shelf.setTotalSlots(shelf.getTotalSlots());
        shelf.setStatus(shelf.getStatus());

        LocalDateTime now = LocalDateTime.now();

        shelf.setCreatedAt(now);
        shelf.setUpdatedAt(now);

        Shelf savedShelf = shelfRepository.save(shelf);

        return shelfMapper.toResponse(savedShelf);
    }

    //Get all
    public Page<ShelfResponse> getAll(
            String status,
            Pageable pageable) {

        Page<Shelf> shelves;

        if (status != null) {

            shelves = shelfRepository.findAll((root, query, cb) ->
                            cb.and(
                                    cb.equal(root.get("status"), status)
                            ),
                    pageable);

        } else {

            shelves = shelfRepository .findAll(pageable);
        }

        return shelves.map(shelfMapper::toResponse);
    }

    // GET BY ID
    public ShelfResponse getById(Long id) {

        Shelf shelf = shelfRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: " + id));

        return shelfMapper.toResponse(shelf);
    }

    // PUT
    public ShelfResponse update(
            Long id,
            ShelfUpdateRequest request) {

        Shelf shelf = shelfRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shelf not found: " + id));

        Router router = routerRepository
                .findById(request.getRouterId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Router not found: "
                                        + request.getRouterId()));

        shelf.setRouter(router);
        shelf.setShelfNumber(request.getShelfNumber());
        shelf.setSerialNumber(request.getSerialNumber());
        shelf.setTotalSlots(request.getTotalSlots());
        shelf.setStatus(request.getStatus());
        shelf.setUpdatedAt(LocalDateTime.now());

        Shelf updatedShelf = shelfRepository.save(shelf);

        return shelfMapper.toResponse(updatedShelf);
    }

    //Patch
    public ShelfResponse patch(
            Long id,
            ShelfUpdateRequest request) {

        Shelf shelf = shelfRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shelf not found: " + id));

        if (request.getRouterId() != null) {
            Router router = routerRepository
                    .findById(request.getRouterId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Router not found: "
                                            + request.getRouterId()));
            shelf.setRouter(router);
        }

        if (request.getShelfNumber() != null) {
            shelf.setShelfNumber(request.getShelfNumber());
        }

        if (request.getSerialNumber() != null) {
            shelf.setShelfNumber(request.getShelfNumber());
        }

        if (request.getTotalSlots() != null) {
            shelf.setTotalSlots(request.getTotalSlots());
        }

        if (request.getStatus() != null) {
            shelf.setStatus(request.getStatus());
        }

        shelf.setUpdatedAt(LocalDateTime.now());

        Shelf updateShelf = shelfRepository.save(shelf);

        return shelfMapper.toResponse(updateShelf);
    }

    //Delete
//    public void delete(Long id, boolean cascade) {
//
//        Shelf shelf = shelfRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException(
//                        "Shelf ID not found: " + id));
//
//        if (cascade) {
//            shelf.getSlots().clear();
//        }
//
//        shelfRepository.delete(shelf);
//    }

    //Get shelves of a router
    public List<ShelfResponse> getShelvesByRouterId(Long router_id) {

        if (!routerRepository.existsById(router_id)) {
            throw new RuntimeException(
                    "Router not found: " + router_id);
        }

        List<Shelf> shelves =
                shelfRepository.findByRouterId(router_id);

        return shelves.stream()
                .map(shelfMapper::toResponse)
                .toList();
    }

}
