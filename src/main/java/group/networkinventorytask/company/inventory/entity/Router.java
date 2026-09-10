package group.networkinventorytask.company.inventory.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="router")
public class Router {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private NetworkSite networkSite;

    @Column(name = "hostname", nullable = false, unique = true)
    private String hostname;

    @Column(name = "vendor", nullable = false)
    private String vendor;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "management_ip")
    private String managementIp;

    @Column(name = "software_version")
    private String softwareVersion;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "router")
    @JsonIgnore
    private List<Shelf> shelves;

    public List<Shelf> getShelves() {return shelves;}

    public void setShelves(List<Shelf> shelves) {this.shelves = shelves;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getManagementIp() {
        return managementIp;
    }

    public void setManagementIp(String managementIp) {
        this.managementIp = managementIp;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public NetworkSite getNetworkSite() {
        return networkSite;
    }

    public void setNetworkSite(NetworkSite networkSite) {
        this.networkSite = networkSite;
    }

}
