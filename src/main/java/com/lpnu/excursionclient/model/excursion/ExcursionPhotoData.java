package com.lpnu.excursionclient.model.excursion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "excursion_photo_data")
@Getter
@ToString
public class ExcursionPhotoData {
    @Id
    private UUID id;
    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private String photo;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "excursion_id")
    private Excursion excursion;
    @Column(name = "order_photo")
    private Integer orderPhoto;

    protected ExcursionPhotoData() {
    }
}
