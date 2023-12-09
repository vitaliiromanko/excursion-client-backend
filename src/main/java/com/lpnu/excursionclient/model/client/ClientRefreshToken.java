package com.lpnu.excursionclient.model.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "client_refresh_token")
@Getter
@Setter
@ToString
public class ClientRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "token")
    private String token;
    @Column(name = "issued_at")
    private Date issuedAt;
    @Column(name = "expired_at")
    private Date expiredAt;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    protected ClientRefreshToken() {
    }

    public static ClientRefreshToken of(String token, Date issuedAt, Date expiredAt, Client client) {
        return new ClientRefreshToken(null, token, issuedAt, expiredAt, client);
    }

    private ClientRefreshToken(UUID id, String token, Date issuedAt, Date expiredAt, Client client) {
        this.id = id;
        this.token = token;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.client = client;
    }
}
