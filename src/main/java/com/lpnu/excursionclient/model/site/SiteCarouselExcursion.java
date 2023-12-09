package com.lpnu.excursionclient.model.site;

import com.lpnu.excursionclient.model.excursion.Excursion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "site_carousel_excursion")
@Getter
@ToString
public class SiteCarouselExcursion {
    @Id
    private UUID id;
    @Column(name = "order_excursion_photo")
    private Integer orderExcursionPhoto;
    @Column(name = "order_carousel_excursion")
    private Integer orderCarouselExcursion;
    @ManyToOne
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;

    protected SiteCarouselExcursion() {
    }
}
