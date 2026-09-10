package group.networkinventorytask.company.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Shelf")
public class Shelf {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "router_id")
    private Router router;

    @Column(name = "shelf_number", nullable = false, unique = true)
    private String shelfNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "total_slots", nullable = false)
    private String totalSlots;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "shelf")
    @JsonIgnore
    private List<Slot> slots;

    public List<Slot> getSlots() {return slots;}

    public void setSlots(List<Slot> slots) {this.slots = slots;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public Router getRouter() {return router;}

    public void setRouter(Router router) {this.router = router;}
}
