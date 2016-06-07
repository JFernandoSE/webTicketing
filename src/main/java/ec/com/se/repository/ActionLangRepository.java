package ec.com.se.repository;

import ec.com.se.domain.ActionLang;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ec.com.se.domain.enumeration.Language;
import ec.com.se.domain.Subcategory;
import java.util.List;

/**
 * Spring Data JPA repository for the ActionLang entity.
 */
@SuppressWarnings("unused")
public interface ActionLangRepository extends JpaRepository<ActionLang,Long> {
  Page<ActionLang> findByLanguageCodeAndActionEnabledAndActionSubcategories(Language languaje, Boolean enabled, List<Subcategory> subcategories, Pageable pag);
}
