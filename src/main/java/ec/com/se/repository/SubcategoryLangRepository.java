package ec.com.se.repository;

import ec.com.se.domain.SubcategoryLang;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ec.com.se.domain.enumeration.Language;
import ec.com.se.domain.Category;

import java.util.List;

/**
 * Spring Data JPA repository for the SubcategoryLang entity.
 */
@SuppressWarnings("unused")
public interface SubcategoryLangRepository extends JpaRepository<SubcategoryLang,Long> {
  Page<SubcategoryLang> findByLanguageCodeAndSubcategoryCategoryAndSubcategoryEnabled(Language languaje, Category category, Boolean enabled, Pageable pag);
}
