package ec.com.se.repository;

import ec.com.se.domain.CategoryLang;
import ec.com.se.domain.enumeration.Language;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Spring Data JPA repository for the CategoryLang entity.
 */
@SuppressWarnings("unused")
public interface CategoryLangRepository extends JpaRepository<CategoryLang,Long> {
  Page<CategoryLang> findByLanguageCodeAndCategoryEnabled(Language languaje, Boolean enabled, Pageable pag);
}
