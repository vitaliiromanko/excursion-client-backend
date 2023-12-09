package com.lpnu.excursionclient.repository.client;

import com.lpnu.excursionclient.model.client.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientStatusRepository extends JpaRepository<ClientStatus, UUID> {
    Optional<ClientStatus> findByName(String name);
}
