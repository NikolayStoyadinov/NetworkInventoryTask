package group.networkinventorytask.company.inventory.controller;

import group.networkinventorytask.company.inventory.dto.request.RouterCreateRequest;
import group.networkinventorytask.company.inventory.dto.Update.RouterUpdateRequest;
import group.networkinventorytask.company.inventory.dto.response.RouterResponse;
import group.networkinventorytask.company.inventory.dto.response.ShelfResponse;
import group.networkinventorytask.company.inventory.service.RouterService;
import group.networkinventorytask.company.inventory.service.ShelfService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routers")
public class RouterController {

    private final RouterService routerService;
    private final ShelfService shelfService;

    public RouterController(RouterService routerService, ShelfService shelfService) {
        this.routerService = routerService;
        this.shelfService = shelfService;
    }

    // POST /api/v1/routers
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RouterResponse create(
            @RequestBody RouterCreateRequest request) {

        return routerService.create(request);
    }

    //Get router hierarchy
    @GetMapping("/{id}/tree")
    public RouterResponse getRouterTree(
            @PathVariable Long id) {

        return routerService.getRouterTree(id);
    }

    // GET /api/v1/routers
    @GetMapping
    public Page<RouterResponse> getAll(
            @RequestParam(required = false) String status,
            Pageable pageable) {

        return routerService.getAll(status, pageable);
    }

    // GET /api/v1/routers/{id}
    @GetMapping("/{id}")
    public RouterResponse getById(
            @PathVariable Long id) {

        return routerService.getById(id);
    }

    // PUT /api/v1/routers/{id}
    @PutMapping("/{id}")
    public RouterResponse update(
            @PathVariable Long id,
            @RequestBody RouterUpdateRequest request) {

        return routerService.update(id, request);
    }

    // PATCH /api/v1/routers/{id}
    @PatchMapping("/{id}")
    public RouterResponse patch(
            @PathVariable Long id,
            @RequestBody RouterUpdateRequest request) {

        return routerService.patch(id, request);
    }

    // DELETE /api/v1/routers/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        routerService.delete(id);
    }

// Get Shelves of a router
    @GetMapping("/{id}/shelves")
    public ResponseEntity<List<ShelfResponse>> getShelves(
            @PathVariable Long id) {

        List<ShelfResponse> shelves =
                shelfService.getShelvesByRouterId(id);

        if (shelves.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(shelves);
    }
}


