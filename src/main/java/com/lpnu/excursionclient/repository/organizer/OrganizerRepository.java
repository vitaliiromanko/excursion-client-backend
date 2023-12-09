package com.lpnu.excursionclient.repository.organizer;

import com.lpnu.excursionclient.model.organizer.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {
    @Query(value = """
            SELECT DISTINCT organizer.* FROM organizer
            INNER JOIN organizer_status ON organizer.organizer_status_id = organizer_status.id
            INNER JOIN excursion ON excursion.organizer_id = organizer.id
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND organizer_status.name = 'АКТИВНИЙ';
            """, nativeQuery = true)
    List<Organizer> getFilterOrganizerList();
}
