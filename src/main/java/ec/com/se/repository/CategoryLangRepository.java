package ec.com.se.repository;

import ec.com.se.domain.CategoryLang;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CategoryLang entity.
 */
@SuppressWarnings("unused")
public interface CategoryLangRepository extends JpaRepository<CategoryLang,Long> {

}
