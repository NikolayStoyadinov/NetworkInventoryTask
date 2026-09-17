package group.networkinventorytask.company.inventory.dto.response;

import java.time.LocalDateTime;

public class CardResponse {

    private Long id;
    private Long slotId;
    private String partNumber;
    private String serialNumber;
    private String cardType;
    private String portCount;
    private String hardwareRevision;
    private String status;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getHardwareRevision() {return hardwareRevision;}

    public void setHardwareRevision(String hardwareRevision) {this.hardwareRevision = hardwareRevision;}

    public String getPortCount() {return portCount;}

    public void setPortCount(String portCount) {this.portCount = portCount;}

    public String getCardType() {return cardType;}

    public void setCardType(String cardType) {this.cardType = cardType;}

    public String getSerialNumber() {return serialNumber;}

    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}

    public String getPartNumber() {return partNumber;}

    public void setPartNumber(String partNumber) {this.partNumber = partNumber;}

    public Long getSlotId() {return slotId;}

    public void setSlotId(Long slotId) {this.slotId = slotId;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
