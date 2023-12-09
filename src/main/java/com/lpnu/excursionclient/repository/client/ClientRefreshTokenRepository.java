package com.lpnu.excursionclient.repository.client;

import com.lpnu.excursionclient.model.client.ClientRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRefreshTokenRepository extends JpaRepository<ClientRefreshToken, UUID> {
    Optional<ClientRefreshToken> findByToken(String token);
}
