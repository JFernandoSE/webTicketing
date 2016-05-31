package ec.com.se.repository;

import ec.com.se.domain.Category;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
public interface CategoryRepository extends JpaRepository<Category,Long> {
  Page<Category> findByEnabled(Boolean enabled, Pageable pag);
}
