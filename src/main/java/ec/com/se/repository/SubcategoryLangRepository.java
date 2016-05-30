package ec.com.se.repository;

import ec.com.se.domain.SubcategoryLang;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubcategoryLang entity.
 */
@SuppressWarnings("unused")
public interface SubcategoryLangRepository extends JpaRepository<SubcategoryLang,Long> {

}
