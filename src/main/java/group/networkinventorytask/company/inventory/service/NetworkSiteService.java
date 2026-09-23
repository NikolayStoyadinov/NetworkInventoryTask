package group.networkinventorytask.company.inventory.service;

import group.networkinventorytask.company.inventory.Exception.SiteException;
import group.networkinventorytask.company.inventory.Exception.SiteHasChildrenException;
import group.networkinventorytask.company.inventory.config.SiteMapper;
import group.networkinventorytask.company.inventory.dto.request.SiteCreateRequest;
import group.networkinventorytask.company.inventory.dto.Update.SiteUpdateRequest;
import group.networkinventorytask.company.inventory.dto.response.SiteResponse;
import group.networkinventorytask.company.inventory.entity.NetworkSite;
import group.networkinventorytask.company.inventory.repository.NetworkSiteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NetworkSiteService {

    private final NetworkSiteRepository networkSiteRepository;
    private final SiteMapper siteMapper;

    public NetworkSiteService(NetworkSiteRepository networkSiteRepository, SiteMapper siteMapper) {
        this.networkSiteRepository = networkSiteRepository;
        this.siteMapper = siteMapper;
    }

    // Create
    public SiteResponse create(SiteCreateRequest request) {

        if (networkSiteRepository.existsBySiteCode(
                request.getSiteCode())) {

            throw new SiteException(
                    "Site code already exists: "
                            + request.getSiteCode());
        }

        NetworkSite site = new NetworkSite();

        site.setId(request.getId());
        site.setSiteCode(request.getSiteCode());
        site.setName(request.getName());
        site.setAddress(request.getAddress());
        site.setCity(request.getCity());
        site.setCountryCode(request.getCountryCode());
        site.setStatus(request.getStatus());
        site.setLongitude(request.getLongitude());
        site.setLatitude(request.getLatitude());

        LocalDateTime now = LocalDateTime.now();

        site.setCreatedAt(now);
        site.setUpdatedAt(now);

        NetworkSite savedSite = networkSiteRepository.save(site);

        return siteMapper.toResponse(savedSite);
    }

    // Get all
    public Page<SiteResponse> getAll(String status,
                                     String city,
                                     Pageable pageable) {

        Page<NetworkSite> sites;

        if (status != null && city != null) {

            sites = networkSiteRepository.findAll((root, query, cb) ->
                            cb.and(
                                    cb.equal(root.get("status"), status),
                                    cb.equal(root.get("city"), city)
                            ),
                    pageable
            );

        } else if (status != null) {

            sites = networkSiteRepository.findAll(
                    (root, query, cb) ->
                            cb.equal(root.get("status"), status),
                    pageable
            );

        } else if (city != null) {

            sites = networkSiteRepository.findAll(
                    (root, query, cb) ->
                            cb.equal(root.get("city"), city),
                    pageable
            );

        } else {

            sites = networkSiteRepository.findAll(pageable);
        }

        return sites.map(siteMapper::toResponse);
    }

    // Get by id
    public SiteResponse getById(Long id) {

        NetworkSite site = networkSiteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Network site not found: " + id));

        return siteMapper.toResponse(site);
    }

    // Put
    public SiteResponse update(Long id, SiteUpdateRequest request) {

        NetworkSite site = networkSiteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Network site not found: " + id));

        site.setSiteCode(request.getSiteCode());
        site.setName(request.getName());
        site.setAddress(request.getAddress());
        site.setCity(request.getCity());
        site.setCountryCode(request.getCountryCode());
        site.setLatitude(request.getLatitude());
        site.setLongitude(request.getLongitude());
        site.setStatus(request.getStatus());

        site.setUpdatedAt(LocalDateTime.now());

        NetworkSite updatedSite = networkSiteRepository.save(site);

        return siteMapper.toResponse(updatedSite);
    }

    // Patch
    public SiteResponse patch(Long id, SiteUpdateRequest request) {

        NetworkSite site = networkSiteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Network site not found: " + id));

        if (request.getSiteCode() != null) {site.setSiteCode(request.getSiteCode());}

        if (request.getName() != null) {site.setName(request.getName());}

        if (request.getAddress() != null) {site.setAddress(request.getAddress());}

        if (request.getCity() != null) {site.setCity(request.getCity());}

        if (request.getCountryCode() != null) {site.setCountryCode(request.getCountryCode());}

        if (request.getLatitude() != null) {site.setLatitude(request.getLatitude());}

        if (request.getLongitude() != null) {site.setLongitude(request.getLongitude());}

        if (request.getStatus() != null) {site.setStatus(request.getStatus());}

        site.setUpdatedAt(LocalDateTime.now());

        NetworkSite updatedSite = networkSiteRepository.save(site);

        return siteMapper.toResponse(updatedSite);
    }


    // Delete
    public void delete(Long id, boolean cascade) {

        NetworkSite site = networkSiteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Network site not found: " + id));

        if (!cascade && !site.getRouters().isEmpty()) {
            throw new SiteHasChildrenException(
                    "Cannot delete site because it has routers");
        }

        if (cascade) {
            site.getRouters().clear();
        }

        networkSiteRepository.delete(site);
    }
}
