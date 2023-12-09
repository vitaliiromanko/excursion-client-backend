package com.lpnu.excursionclient.model.excursion;

import com.lpnu.excursionclient.model.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "excursion_item")
@Getter
@Setter
@ToString
public class ExcursionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @ManyToOne
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;
    @ManyToOne
    @JoinColumn(name = "excursion_item_status_id")
    private ExcursionItemStatus excursionItemStatus;
    @OneToMany(targetEntity = Order.class, mappedBy = "excursionItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Order> orders = new LinkedHashSet<>();
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @PrePersist
    protected void prePersist() {
        creationDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updateDate = LocalDateTime.now();
    }

    protected ExcursionItem() {
    }

    public static ExcursionItem of(LocalDateTime startDate, Excursion excursion,
                                   ExcursionItemStatus excursionItemStatus) {
        return new ExcursionItem(null, startDate, excursion, excursionItemStatus, Collections.emptySet());
    }

    private ExcursionItem(UUID id, LocalDateTime startDate, Excursion excursion,
                         ExcursionItemStatus excursionItemStatus, Set<Order> orders) {
        this.id = id;
        this.startDate = startDate;
        this.excursion = excursion;
        this.excursionItemStatus = excursionItemStatus;
        this.orders = orders;
    }
}
