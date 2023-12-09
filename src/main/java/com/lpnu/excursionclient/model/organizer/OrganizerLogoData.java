package com.lpnu.excursionclient.model.organizer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "organizer_photo_data")
@Getter
@ToString
public class OrganizerLogoData {
    @Id
    private UUID id;
    @Lob
    @Column(name = "logo", columnDefinition = "longblob")
    private String logo;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @OneToOne(mappedBy = "organizerLogoData")
    private Organizer organizer;

    protected OrganizerLogoData() {
    }
}
