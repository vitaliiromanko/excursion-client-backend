package com.lpnu.excursionclient.model.client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "client_status")
@Getter
@ToString
public class ClientStatus {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;

    protected ClientStatus() {
    }
}
