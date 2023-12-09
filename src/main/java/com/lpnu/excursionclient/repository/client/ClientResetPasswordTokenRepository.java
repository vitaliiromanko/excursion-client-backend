package com.lpnu.excursionclient.repository.client;

import com.lpnu.excursionclient.model.client.ClientResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientResetPasswordTokenRepository extends JpaRepository<ClientResetPasswordToken, UUID> {
    Optional<ClientResetPasswordToken> findByToken(String token);
}
