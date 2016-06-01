package ec.com.se.repository;

import ec.com.se.domain.Request;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
public interface RequestRepository extends JpaRepository<Request,Long> {
  Page<Request> findByCreatedBy(String user, Pageable pag);
}
