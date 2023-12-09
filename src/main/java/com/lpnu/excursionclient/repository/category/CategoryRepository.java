package com.lpnu.excursionclient.repository.category;

import com.lpnu.excursionclient.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query(value = """
            SELECT DISTINCT category.* FROM category
            INNER JOIN excursion ON excursion.category_id = category.id
            INNER JOIN excursion_status ON excursion.excursion_status_id = excursion_status.id
            WHERE excursion_status.name = 'АКТИВНА';
            """, nativeQuery = true)
    List<Category> getFilterCategoryList();
}
