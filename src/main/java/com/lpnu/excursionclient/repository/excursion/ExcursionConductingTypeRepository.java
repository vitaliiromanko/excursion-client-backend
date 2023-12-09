package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.model.excursion.ExcursionConductingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExcursionConductingTypeRepository extends JpaRepository<ExcursionConductingType, UUID> {
}
