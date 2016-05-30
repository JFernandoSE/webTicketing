package ec.com.se.service;

import ec.com.se.domain.Request;
import ec.com.se.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Request.
 */
@Service
@Transactional
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);
    
    @Inject
    private RequestRepository requestRepository;
    
    /**
     * Save a request.
     * 
     * @param request the entity to save
     * @return the persisted entity
     */
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        Request result = requestRepository.save(request);
        return result;
    }

    /**
     *  Get all the requests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Request> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        Page<Request> result = requestRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one request by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Request findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        Request request = requestRepository.findOne(id);
        return request;
    }

    /**
     *  Delete the  request by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.delete(id);
    }
}
