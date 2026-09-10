package group.networkinventorytask.company.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "slot")
public class Slot {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shelf_id")
    private Shelf shelf;

    @Column(name = "slot_number", nullable = false, unique = true)
    private String slotNumber;

    @Column(name = "slot_type")
    private String slotType;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    @OneToMany(mappedBy = "slot")
//    @JsonIgnore
//    private List<Card> cards;
//
//    public List<Card> getCards() {return cards;}
//
//    public void setCards(List<Card> cards) {this.cards = cards;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Shelf getShelf() {return shelf;}

    public void setShelf(Shelf shelf) {this.shelf = shelf;}

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
