package group.networkinventorytask.company.inventory.dto.Update;

public class RouterUpdateRequest {

    private Long siteId;

    private String hostname;

    private String vendor;

    private String model;

    private String serialNumber;

    private String managementIp;

    private String softwareVersion;

    private String status;

    public String getHostname() {return hostname;}

    public void setHostname(String hostname) {this.hostname = hostname;}

    public String getVendor() {return vendor;}

    public void setVendor(String vendor) {this.vendor = vendor;}

    public String getModel() {return model;}

    public void setModel(String model) {this.model = model;}

    public String getSerialNumber() {return serialNumber;}

    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}

    public String getManagementIp() {return managementIp;}

    public void setManagementIp(String managementIp) {this.managementIp = managementIp;}

    public String getSoftwareVersion() {return softwareVersion;}

    public void setSoftwareVersion(String softwareVersion) {this.softwareVersion = softwareVersion;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public Long getSiteId() {return siteId;}

    public void setSiteId(Long siteId) {this.siteId = siteId;}
}
