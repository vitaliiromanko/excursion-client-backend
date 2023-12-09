package com.lpnu.excursionclient.repository.site;

import com.lpnu.excursionclient.model.site.SiteCarouselExcursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SiteCarouselExcursionRepository extends JpaRepository<SiteCarouselExcursion, UUID> {
}
