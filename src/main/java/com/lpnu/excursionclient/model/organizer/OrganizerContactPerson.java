package com.lpnu.excursionclient.model.organizer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Formula;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "organizer_contact_person")
@Getter
@ToString
public class OrganizerContactPerson {
    @Id
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @OneToMany(targetEntity = OrganizerContactPhoneNumber.class,
            mappedBy = "organizerContactPerson", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<OrganizerContactPhoneNumber> organizerContactPhoneNumbers = new LinkedHashSet<>();

    @Formula("CONCAT(first_name, \" \", last_name)")
    private String fullName;

    protected OrganizerContactPerson() {
    }
}
