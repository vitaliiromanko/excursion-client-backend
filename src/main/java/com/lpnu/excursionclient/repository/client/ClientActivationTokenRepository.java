package com.lpnu.excursionclient.repository.client;

import com.lpnu.excursionclient.model.client.ClientActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientActivationTokenRepository extends JpaRepository<ClientActivationToken, UUID> {
    Optional<ClientActivationToken> findByToken(String token);
}
