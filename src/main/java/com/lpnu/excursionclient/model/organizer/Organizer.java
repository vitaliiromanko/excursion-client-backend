package com.lpnu.excursionclient.model.organizer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "organizer")
@Getter
@ToString
public class Organizer {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "organizer_photo_data_id")
    private OrganizerLogoData organizerLogoData;

    @Column(name = "email")
    private String email;

    @OneToMany(targetEntity = OrganizerContactPerson.class,
            mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<OrganizerContactPerson> organizerContactPersonSet = new LinkedHashSet<>();

    protected Organizer() {
    }
}
