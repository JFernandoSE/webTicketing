package ec.com.se.repository;

import ec.com.se.domain.ActionLang;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ActionLang entity.
 */
@SuppressWarnings("unused")
public interface ActionLangRepository extends JpaRepository<ActionLang,Long> {

}
