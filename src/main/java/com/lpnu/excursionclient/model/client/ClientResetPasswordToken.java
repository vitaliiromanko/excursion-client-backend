package com.lpnu.excursionclient.model.client;

import com.lpnu.excursionclient.util.GenerateSimpleTokenUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "client_reset_password_token")
@Getter
@Setter
@ToString
public class ClientResetPasswordToken {
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

    protected ClientResetPasswordToken() {
    }

    public static ClientResetPasswordToken of(Client client) {
        return new ClientResetPasswordToken(null, GenerateSimpleTokenUtils.getToken(), client);
    }

    private ClientResetPasswordToken(UUID id, String token, Client client) {
        this.id = id;
        this.token = token;
        this.client = client;
    }
}
