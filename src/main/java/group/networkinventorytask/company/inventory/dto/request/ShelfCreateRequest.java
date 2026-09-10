package group.networkinventorytask.company.inventory.dto.request;

public class ShelfCreateRequest {

    private Long id;

    private Long routerId;

    private String shelfNumber;

    private String serialNumber;

    private String totalSlots;

    private String status;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getRouterId() {return routerId;}

    public void setRouterId(Long routerId) {this.routerId = routerId;}

    public String getShelfNumber() {return shelfNumber;}

    public void setShelfNumber(String shelfNumber) {this.shelfNumber = shelfNumber;}

    public String getSerialNumber() {return serialNumber;}

    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}

    public String getTotalSlots() {return totalSlots;}

    public void setTotalSlots(String totalSlots) {this.totalSlots = totalSlots;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
