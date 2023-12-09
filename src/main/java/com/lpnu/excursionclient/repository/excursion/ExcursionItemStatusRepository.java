package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.model.excursion.ExcursionItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExcursionItemStatusRepository extends JpaRepository<ExcursionItemStatus, UUID> {
    Optional<ExcursionItemStatus> findByName(String name);
}
