package group.networkinventorytask.company.inventory.config;


import group.networkinventorytask.company.inventory.dto.response.SiteResponse;
import group.networkinventorytask.company.inventory.entity.NetworkSite;
import org.springframework.stereotype.Component;

@Component
public class SiteMapper {

    public SiteResponse toResponse(NetworkSite site) {

        SiteResponse response = new SiteResponse();

        response.setId(site.getId());
        response.setSiteCode(site.getSiteCode());
        response.setName(site.getName());
        response.setAddress(site.getAddress());
        response.setCity(site.getCity());
        response.setCountryCode(site.getCountryCode());
        response.setLatitude(site.getLatitude());
        response.setLongitude(site.getLongitude());
        response.setStatus(site.getStatus());
        response.setCreatedAt(site.getCreatedAt());
        response.setUpdatedAt(site.getUpdatedAt());

        return response;
    }
}

