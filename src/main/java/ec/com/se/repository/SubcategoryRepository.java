package ec.com.se.repository;

import ec.com.se.domain.Category;
import ec.com.se.domain.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Subcategory entity.
 */
@SuppressWarnings("unused")
public interface SubcategoryRepository extends JpaRepository<Subcategory,Long> {

    @Query("select distinct subcategory from Subcategory subcategory left join fetch subcategory.actions")
    List<Subcategory> findAllWithEagerRelationships();

    @Query("select subcategory from Subcategory subcategory left join fetch subcategory.actions where subcategory.id =:id")
    Subcategory findOneWithEagerRelationships(@Param("id") Long id);

    Page<Subcategory> findByCategoryAndEnabled(Category category, Boolean enabled, Pageable pag);

}
