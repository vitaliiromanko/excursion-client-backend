package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.model.excursion.ExcursionMovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExcursionMovementTypeRepository extends JpaRepository<ExcursionMovementType, UUID> {
}
