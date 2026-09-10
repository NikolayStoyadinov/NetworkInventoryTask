package group.networkinventorytask.company.inventory.config;

import group.networkinventorytask.company.inventory.dto.response.RouterResponse;
import group.networkinventorytask.company.inventory.entity.Router;
import org.springframework.stereotype.Component;

@Component
public class RouterMapper {

    public RouterResponse toResponse(Router router){

        RouterResponse response = new RouterResponse();

        response.setSiteId(router.getNetworkSite().getId());
        response.setId(router.getId());
        response.setHostname(router.getHostname());
        response.setVendor(router.getVendor());
        response.setModel(router.getModel());
        response.setSerialNumber(router.getSerialNumber());
        response.setManagementIp(router.getManagementIp());
        response.setSoftwareVersion(router.getSoftwareVersion());
        response.setStatus(router.getStatus());
        response.setCreatedAt(router.getCreatedAt());
        response.setUpdatedAt(router.getUpdatedAt());

        return response;
    }

}

