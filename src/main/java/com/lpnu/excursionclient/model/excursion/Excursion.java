package com.lpnu.excursionclient.model.excursion;

import com.lpnu.excursionclient.model.category.Category;
import com.lpnu.excursionclient.model.organizer.Organizer;
import com.lpnu.excursionclient.model.review.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "excursion")
@Getter
@ToString
public class Excursion {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @OneToMany(targetEntity = ExcursionPhotoData.class, mappedBy = "excursion", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<ExcursionPhotoData> excursionPhotoDataSet = new HashSet<>();

    @Column(name = "duration")
    private Double duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excursion_duration_unit_id")
    private ExcursionDurationUnit excursionDurationUnit;

    @Column(name = "rating")
    private Double rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excursion_movement_type_id")
    private ExcursionMovementType excursionMovementType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "excursion_excursion_topic_type",
            joinColumns = {
                    @JoinColumn(name = "excursion_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "excursion_topic_type_id")
            }
    )
    private Set<ExcursionTopicType> excursionTopicTypes = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excursion_conducting_type_id")
    private ExcursionConductingType excursionConductingType;

    @Column(name = "max_people_number")
    private Integer maxPeopleNumber;

    @OneToMany(targetEntity = ExcursionItem.class, mappedBy = "excursion", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<ExcursionItem> excursionItems = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "excursion_status_id")
    private ExcursionStatus excursionStatus;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @OneToMany(targetEntity = Review.class, mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Review> reviews = new LinkedHashSet<>();

    @Column(name = "organizer_blocked")
    private Boolean organizerBlocked;

    @Column(name = "allowed_show")
    private Boolean allowedShow;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Formula("price - IFNULL(discount, 0)")
    private BigDecimal activePrice;

    protected Excursion() {
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
