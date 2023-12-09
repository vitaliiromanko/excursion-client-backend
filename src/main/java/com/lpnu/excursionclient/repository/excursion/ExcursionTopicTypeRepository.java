package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.model.excursion.ExcursionTopicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExcursionTopicTypeRepository extends JpaRepository<ExcursionTopicType, UUID> {
}
