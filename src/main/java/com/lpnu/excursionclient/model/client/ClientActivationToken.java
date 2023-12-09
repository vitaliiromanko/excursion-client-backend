package com.lpnu.excursionclient.model.client;

import com.lpnu.excursionclient.util.GenerateSimpleTokenUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client_activation_token")
@Getter
@Setter
@ToString
public class ClientActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "token")
    private String token;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @PrePersist
    protected void prePersist() {
        creationDate = LocalDateTime.now();
    }

    protected ClientActivationToken() {
    }

    public static ClientActivationToken of(Client client) {
        return new ClientActivationToken(null, GenerateSimpleTokenUtils.getToken(), client);
    }

    private ClientActivationToken(UUID id, String token, Client client) {
        this.id = id;
        this.token = token;
        this.client = client;
    }
}
