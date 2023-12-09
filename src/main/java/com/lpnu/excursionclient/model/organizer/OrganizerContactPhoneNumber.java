package com.lpnu.excursionclient.model.organizer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "organizer_contact_phone_number")
@Getter
@ToString
public class OrganizerContactPhoneNumber {
    @Id
    private UUID id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "organizer_contact_person_id")
    private OrganizerContactPerson organizerContactPerson;

    protected OrganizerContactPhoneNumber() {
    }
}
