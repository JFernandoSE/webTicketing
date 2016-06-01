package ec.com.se.repository;

import ec.com.se.domain.Action;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Action entity.
 */
@SuppressWarnings("unused")
public interface ActionRepository extends JpaRepository<Action,Long> {
  Page<Action> findByEnabled(Boolean enabled, Pageable pag);
  
}
