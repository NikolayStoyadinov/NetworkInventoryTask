package group.networkinventorytask.company.inventory.controller;


import group.networkinventorytask.company.inventory.dto.request.SiteCreateRequest;
import group.networkinventorytask.company.inventory.dto.Update.SiteUpdateRequest;
import group.networkinventorytask.company.inventory.dto.response.RouterResponse;
import group.networkinventorytask.company.inventory.dto.response.SiteResponse;
import group.networkinventorytask.company.inventory.service.NetworkSiteService;
import group.networkinventorytask.company.inventory.service.RouterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sites")
public class NetworkSiteController {

    private final NetworkSiteService networkSiteService;
    private final RouterService routerService;

    public NetworkSiteController(NetworkSiteService networkSiteService, RouterService routerService){
        this.networkSiteService = networkSiteService;
        this.routerService = routerService;
    }

    //Post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SiteResponse create(
            @RequestBody SiteCreateRequest request) {

        return networkSiteService.create(request);
    }

    //Get
    @GetMapping
    public Page<SiteResponse> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String city,
            Pageable pageable) {return networkSiteService.getAll(status, city, pageable);}

    //Get by id
    @GetMapping("/{id}")
    public SiteResponse getById(
            @PathVariable Long id)
    {
        return networkSiteService.getById(id);
    }

    //Get routers at a site
    @GetMapping("/{id}/routers")
    public List<RouterResponse> getRouters(@PathVariable Long id) {
        return routerService.getRoutersBySiteId(id);
    }

    //Put
    @PutMapping("/{id}")
    public SiteResponse update(
            @PathVariable Long id,
            @RequestBody SiteUpdateRequest request) {

        return networkSiteService.update(id, request);
    }

    //Patch
    @PatchMapping("/{id}")
    public SiteResponse patch(
            @PathVariable Long id,
            @RequestBody SiteUpdateRequest request) {
        return networkSiteService.patch(id, request);
    }

    //Delete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean cascade) {
                networkSiteService.delete(id,cascade);
    }
}