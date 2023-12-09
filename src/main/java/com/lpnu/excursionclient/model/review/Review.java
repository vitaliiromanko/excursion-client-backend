package com.lpnu.excursionclient.model.review;

import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.excursion.Excursion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "rating")
    private Integer rating;
    @ManyToOne
    @JoinColumn(name = "review_status_id")
    private ReviewStatus reviewStatus;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @PrePersist
    protected void prePersist() {
        creationDate = LocalDateTime.now();
    }

    protected Review() {
    }

    public static Review of(String comment, Integer rating, ReviewStatus reviewStatus, Client client, Excursion excursion) {
        return new Review(null, comment, rating, reviewStatus, client, excursion);
    }

    private Review(UUID id, String comment, Integer rating, ReviewStatus reviewStatus, Client client, Excursion excursion) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.reviewStatus = reviewStatus;
        this.client = client;
        this.excursion = excursion;
    }
}
