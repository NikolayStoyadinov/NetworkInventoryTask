package group.networkinventorytask;

import group.networkinventorytask.company.inventory.Exception.SiteHasChildrenException;
import group.networkinventorytask.company.inventory.dto.Update.SiteUpdateRequest;
import group.networkinventorytask.company.inventory.dto.request.SiteCreateRequest;
import group.networkinventorytask.company.inventory.entity.NetworkSite;
import group.networkinventorytask.company.inventory.config.SiteMapper;
import group.networkinventorytask.company.inventory.entity.Router;
import group.networkinventorytask.company.inventory.repository.NetworkSiteRepository;
import group.networkinventorytask.company.inventory.repository.RouterRepository;
import group.networkinventorytask.company.inventory.dto.response.SiteResponse;

import group.networkinventorytask.company.inventory.service.NetworkSiteService;
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
class SiteServiceTest {

    @Mock
    private NetworkSiteRepository networkSiteRepository;

    @Mock
    private SiteMapper siteMapper;

    @InjectMocks
    private NetworkSiteService networkSiteService;

    //Create
    @Test
    void CreateSite() {

        SiteCreateRequest request = new SiteCreateRequest();
        request.setId(1L);
        request.setSiteCode("SITE-001");
        request.setName("Test Site");
        request.setAddress("Test Address");
        request.setCity("Sofia");
        request.setCountryCode("BG");
        request.setStatus("ACTIVE");

        NetworkSite savedSite = new NetworkSite();
        savedSite.setId(1L);
        savedSite.setSiteCode("SITE-001");
        savedSite.setName("Test Site");

        SiteResponse response = new SiteResponse();
        response.setId(1L);
        response.setSiteCode("SITE-001");

        when(networkSiteRepository.existsBySiteCode("SITE-001"))
                .thenReturn(false);

        when(networkSiteRepository.save(any(NetworkSite.class)))
                .thenReturn(savedSite);

        when(siteMapper.toResponse(savedSite))
                .thenReturn(response);

        SiteResponse result = networkSiteService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("SITE-001", result.getSiteCode());

        verify(networkSiteRepository).existsBySiteCode("SITE-001");
        verify(networkSiteRepository).save(any(NetworkSite.class));
        verify(siteMapper).toResponse(savedSite);
    }

    //Create - negative
    @Test
    void CreateSite_CodeAlreadyExists() {

        SiteCreateRequest request = new SiteCreateRequest();

        request.setId(1L);
        request.setSiteCode("SITE-001");

        when(networkSiteRepository.existsBySiteCode("SITE-001"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> networkSiteService.create(request)
        );

        assertEquals(
                "Site code already exists: SITE-001",
                exception.getMessage()
        );

        verify(networkSiteRepository, never()).save(any());
    }

    //Get all
    @Test
    void GetAllSites() {

        NetworkSite site1 = new NetworkSite();
        site1.setId(1L);
        site1.setSiteCode("SITE-001");
        site1.setName("Site One");

        NetworkSite site2 = new NetworkSite();
        site2.setId(2L);
        site2.setSiteCode("SITE-002");
        site2.setName("Site Two");

        SiteResponse response1 = new SiteResponse();
        response1.setId(1L);
        response1.setSiteCode("SITE-001");
        response1.setName("Site One");

        SiteResponse response2 = new SiteResponse();
        response2.setId(2L);
        response2.setSiteCode("SITE-002");
        response2.setName("Site Two");

        List<NetworkSite> sites = List.of(site1, site2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<NetworkSite> sitePage =
                new PageImpl<>(sites, pageable, sites.size());

        when(networkSiteRepository.findAll(pageable))
                .thenReturn(sitePage);

        when(siteMapper.toResponse(site1))
                .thenReturn(response1);

        when(siteMapper.toResponse(site2))
                .thenReturn(response2);

        Page<SiteResponse> result =
                networkSiteService.getAll(null, null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());

        assertEquals("SITE-001",
                result.getContent().get(0).getSiteCode());

        assertEquals("SITE-002",
                result.getContent().get(1).getSiteCode());

        verify(networkSiteRepository).findAll(pageable);

        verify(siteMapper).toResponse(site1);
        verify(siteMapper).toResponse(site2);
    }

    //Get all - negative
    @Test
    void GetAllSite_WhenNoSitesExist() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<NetworkSite> emptyPage =
                new PageImpl<>(List.of(), pageable, 0);

        when(networkSiteRepository.findAll(pageable))
                .thenReturn(emptyPage);

        Page<SiteResponse> result =
                networkSiteService.getAll(null, null, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verify(networkSiteRepository).findAll(pageable);

        verifyNoInteractions(siteMapper);
    }


    //Get by id
    @Test
    void getById() {

        Long siteId = 1L;

        NetworkSite site = new NetworkSite();
        site.setId(siteId);
        site.setSiteCode("SITE-001");

        SiteResponse response = new SiteResponse();
        response.setId(siteId);
        response.setSiteCode("SITE-001");

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.of(site));

        when(siteMapper.toResponse(site))
                .thenReturn(response);

        SiteResponse result =
                networkSiteService.getById(siteId);

        assertNotNull(result);
        assertEquals(siteId, result.getId());
        assertEquals("SITE-001", result.getSiteCode());

        verify(networkSiteRepository).findById(siteId);
    }

    //Get by id - negative
    @Test
    void getById_whenSiteDoesNotExist() {

        Long siteId = 999L;

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> networkSiteService.getById(siteId)
        );

        assertEquals(
                "Network site not found: " + siteId,
                exception.getMessage()
        );
    }

    //Put
    @Test
    void UpdateSite() {

        Long siteId = 1L;

        NetworkSite existingSite = new NetworkSite();
        existingSite.setId(siteId);
        existingSite.setSiteCode("SITE-001");
        existingSite.setName("Old Name");
        existingSite.setCity("Sofia");

        SiteUpdateRequest request = new SiteUpdateRequest();
        request.setSiteCode("SITE-001");
        request.setName("New Name");
        request.setAddress("New Address");
        request.setCity("Plovdiv");
        request.setCountryCode("BG");
        request.setStatus("ACTIVE");

        SiteResponse response = new SiteResponse();
        response.setId(siteId);
        response.setSiteCode("SITE-001");
        response.setName("New Name");

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.of(existingSite));

        when(networkSiteRepository.save(existingSite))
                .thenReturn(existingSite);

        when(siteMapper.toResponse(existingSite))
                .thenReturn(response);

        SiteResponse result =
                networkSiteService.update(siteId, request);

        assertNotNull(result);
        assertEquals(siteId, result.getId());
        assertEquals("New Name", result.getName());

        verify(networkSiteRepository).findById(siteId);
        verify(networkSiteRepository).save(existingSite);
        verify(siteMapper).toResponse(existingSite);
    }

    //Put - negative
    @Test
    void UpdateSite_WhenSiteDoesNotExist() {

        Long siteId = 999L;

        SiteUpdateRequest request = new SiteUpdateRequest();

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> networkSiteService.update(siteId, request)
        );

        verify(networkSiteRepository, never())
                .save(any(NetworkSite.class));
    }

    //Patch
    @Test
    void PatchSite() {

        Long siteId = 1L;

        NetworkSite existingSite = new NetworkSite();
        existingSite.setId(siteId);
        existingSite.setSiteCode("SITE-001");
        existingSite.setName("Old Name");
        existingSite.setCity("Sofia");

        SiteUpdateRequest request = new SiteUpdateRequest();
        request.setName("Updated Name");

        SiteResponse response = new SiteResponse();
        response.setId(siteId);
        response.setSiteCode("SITE-001");
        response.setName("Updated Name");

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.of(existingSite));

        when(networkSiteRepository.save(existingSite))
                .thenReturn(existingSite);

        when(siteMapper.toResponse(existingSite))
                .thenReturn(response);

        SiteResponse result =
                networkSiteService.patch(siteId, request);

        assertNotNull(result);
        assertEquals(siteId, result.getId());
        assertEquals("Updated Name", result.getName());

        verify(networkSiteRepository).findById(siteId);
        verify(networkSiteRepository).save(existingSite);
        verify(siteMapper).toResponse(existingSite);
    }

    //Patch - negative
    @Test
    void PatchSite_WhenSiteDoesNotExist() {

        Long siteId = 999L;

        SiteUpdateRequest request = new SiteUpdateRequest();

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> networkSiteService.patch(siteId, request)
        );

        verify(networkSiteRepository, never())
                .save(any(NetworkSite.class));
    }

    //delete
    @Test
    void DeleteSite() {

        Long siteId = 1L;

        NetworkSite site = new NetworkSite();
        site.setId(siteId);
        site.setRouters(new ArrayList<>());

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.of(site));

        networkSiteService.delete(siteId, false);

        verify(networkSiteRepository).delete(site);
    }

    //Delete - negative
    @Test
    void DeleteSite_WhenSiteHasRouters() {

        Long siteId = 1L;

        NetworkSite site = new NetworkSite();
        site.setId(siteId);

        Router router = new Router();
        router.setId(10L);

        List<Router> routers = new ArrayList<>();
        routers.add(router);

        site.setRouters(routers);

        when(networkSiteRepository.findById(siteId))
                .thenReturn(Optional.of(site));

        assertThrows(
                SiteHasChildrenException.class,
                () -> networkSiteService.delete(siteId, false)
        );

        verify(networkSiteRepository, never()).delete(site);
    }

}
