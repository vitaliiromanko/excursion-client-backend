package com.lpnu.excursionclient.repository.review;

import com.lpnu.excursionclient.model.review.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewStatusRepository extends JpaRepository<ReviewStatus, UUID> {
    Optional<ReviewStatus> findByName(String name);
}
