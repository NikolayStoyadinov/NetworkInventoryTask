package group.networkinventorytask.company.inventory.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ShelfTreeResponse {

    private Long id;

    private Long routerId;

    private String shelfNumber;

    private String serialNumber;

    private String totalSlots;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<SlotTreeResponse> slots;

    public List<SlotTreeResponse> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotTreeResponse> slots) {
        this.slots = slots;
    }
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

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
