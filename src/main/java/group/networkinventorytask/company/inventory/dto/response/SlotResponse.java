package group.networkinventorytask.company.inventory.dto.response;

import group.networkinventorytask.company.inventory.entity.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SlotResponse {

    private Long id;

    private Long shelfId;

    private String slotNumber;

    private String slotType;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getShelfId() {return shelfId;}

    public void setShelfId(Long shelfId) {this.shelfId = shelfId;}

    public String getSlotNumber() {return slotNumber;}

    public void setSlotNumber(String slotNumber) {this.slotNumber = slotNumber;}

    public String getSlotType() {return slotType;}

    public void setSlotType(String slotType) {this.slotType = slotType;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
