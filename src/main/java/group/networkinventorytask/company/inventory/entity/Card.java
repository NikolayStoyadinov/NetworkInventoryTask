package group.networkinventorytask.company.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "card")
public class Card {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @Column(name = "part_number", nullable = false)
    private String partNumber;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "card_type", nullable = false)
    private String cardType;

    @Column(name = "port_count")
    private String portCount;

    @Column(name = "hardware_revision")
    private String hardwareRevision;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Slot getSlot() {return slot;}

    public void setSlot(Slot slot) {this.slot = slot;}

    public String getPartNumber() {return partNumber;}

    public void setPartNumber(String partNumber) {this.partNumber = partNumber;}

    public String getSerialNumber() {return serialNumber;}

    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}

    public String getCardType() {return cardType;}

    public void setCardType(String cardType) {this.cardType = cardType;}

    public String getPortCount() {return portCount;}

    public void setPortCount(String portCount) {this.portCount = portCount;}

    public String getHardwareRevision() {return hardwareRevision;}

    public void setHardwareRevision(String hardwareRevision) {this.hardwareRevision = hardwareRevision;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
