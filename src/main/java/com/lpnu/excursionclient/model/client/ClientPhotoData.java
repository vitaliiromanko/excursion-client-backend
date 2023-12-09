package com.lpnu.excursionclient.model.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client_photo_data")
@Getter
@Setter
@ToString
public class ClientPhotoData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Lob
    @Column(name = "photo", columnDefinition = "longblob")
    private String photo;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @OneToOne(mappedBy = "clientPhotoData", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private Client client;

    @PrePersist
    protected void prePersist() {
        creationDate = LocalDateTime.now();
    }

    protected ClientPhotoData() {
    }

    public static ClientPhotoData of(String photo, Client client) {
        return new ClientPhotoData(null, photo, client);
    }

    private ClientPhotoData(UUID id, String photo, Client client) {
        this.id = id;
        this.photo = photo;
        this.client = client;
    }
}
