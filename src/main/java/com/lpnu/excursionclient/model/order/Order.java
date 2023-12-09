package com.lpnu.excursionclient.model.order;

import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.excursion.ExcursionItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excursion_item_id")
    private ExcursionItem excursionItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Column(name = "people_number")
    private Integer peopleNumber;

    @Column(name = "comment")
    private String comment;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "organizer_blocked")
    private Boolean organizerBlocked;

    @Column(name = "excursion_blocked")
    private Boolean excursionBlocked;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @PrePersist
    protected void prePersist() {
        creationDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
        organizerBlocked = false;
        excursionBlocked = false;
    }

    @PreUpdate
    protected void preUpdate() {
        updateDate = LocalDateTime.now();
    }

    protected Order() {
    }

    public static Order of(Client client, ExcursionItem excursionItem, OrderStatus orderStatus,
                           Integer peopleNumber, String comment, BigDecimal price) {
        return new Order(null, client, excursionItem, orderStatus, peopleNumber, comment, price);
    }

    private Order(UUID id, Client client, ExcursionItem excursionItem, OrderStatus orderStatus,
                 Integer peopleNumber, String comment, BigDecimal price) {
        this.id = id;
        this.client = client;
        this.excursionItem = excursionItem;
        this.orderStatus = orderStatus;
        this.peopleNumber = peopleNumber;
        this.comment = comment;
        this.price = price;
    }
}
