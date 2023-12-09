package com.lpnu.excursionclient.repository.excursion;

import com.lpnu.excursionclient.model.excursion.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, UUID> {
    @Query(value = """
            SELECT MIN(excursion.price - IFNULL(excursion.discount, 0)) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            """, nativeQuery = true)
    BigDecimal minActivePrice();

    @Query(value = """
            SELECT MAX(excursion.price - excursion.discount) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            """, nativeQuery = true)
    BigDecimal maxActivePrice();

    @Query(value = """
            SELECT MIN(excursion.duration) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            INNER JOIN excursion_duration_unit ON excursion.excursion_duration_unit_id = excursion_duration_unit.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND excursion_duration_unit.name = 'години'
            """, nativeQuery = true)
    Double minHourDuration();

    @Query(value = """
            SELECT MAX(excursion.duration) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            INNER JOIN excursion_duration_unit ON excursion.excursion_duration_unit_id = excursion_duration_unit.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND excursion_duration_unit.name = 'години'
            """, nativeQuery = true)
    Double maxHourDuration();

    @Query(value = """
            SELECT MIN(excursion.duration) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            INNER JOIN excursion_duration_unit ON excursion.excursion_duration_unit_id = excursion_duration_unit.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND excursion_duration_unit.name = 'дні'
            """, nativeQuery = true)
    Double minDayDuration();

    @Query(value = """
            SELECT MAX(excursion.duration) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            INNER JOIN excursion_duration_unit ON excursion.excursion_duration_unit_id = excursion_duration_unit.id
            WHERE excursion_status.name = 'АКТИВНА'
            AND excursion_duration_unit.name = 'дні'
            """, nativeQuery = true)
    Double maxDayDuration();

    @Query(value = """
            SELECT MIN(excursion.max_people_number) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            """, nativeQuery = true)
    Integer minMaxPeopleNumber();

    @Query(value = """
            SELECT MAX(excursion.max_people_number) FROM excursion
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            WHERE excursion_status.name = 'АКТИВНА'
            """, nativeQuery = true)
    Integer maxMaxPeopleNumber();
}
