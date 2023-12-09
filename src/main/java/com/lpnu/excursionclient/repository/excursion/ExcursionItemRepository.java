package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.model.excursion.ExcursionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ExcursionItemRepository extends JpaRepository<ExcursionItem, UUID> {
    @Query(value = """
            SELECT MIN(excursion_item.start_date) FROM excursion_item
            INNER JOIN excursion ON excursion_item.excursion_id = excursion.id
            INNER JOIN excursion_conducting_type ON excursion.excursion_conducting_type_id = excursion_conducting_type.id
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            INNER JOIN excursion_item_status ON excursion_item.excursion_item_status_id = excursion_item_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND excursion_conducting_type.name = 'запланована'
            AND excursion_item_status.name = 'НАБІР УЧАСНИКІВ'
            """, nativeQuery = true)
    LocalDateTime minStartDate();

    @Query(value = """
            SELECT MAX(excursion_item.start_date) FROM excursion_item
            INNER JOIN excursion ON excursion_item.excursion_id = excursion.id
            INNER JOIN excursion_conducting_type ON excursion.excursion_conducting_type_id = excursion_conducting_type.id
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            INNER JOIN excursion_item_status ON excursion_item.excursion_item_status_id = excursion_item_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND excursion_conducting_type.name = 'запланована'
            AND excursion_item_status.name = 'НАБІР УЧАСНИКІВ'
            """, nativeQuery = true)
    LocalDateTime maxStartDate();
}
