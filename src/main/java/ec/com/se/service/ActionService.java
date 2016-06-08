package ec.com.se.service;

import ec.com.se.domain.Action;
import ec.com.se.repository.ActionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Action.
 */
@Service
@Transactional
public class ActionService {

    private final Logger log = LoggerFactory.getLogger(ActionService.class);

    @Inject
    private ActionRepository actionRepository;

    /**
     * Save a action.
     *
     * @param action the entity to save
     * @return the persisted entity
     */
    public Action save(Action action) {
        log.debug("Request to save Action : {}", action);
        Action result = actionRepository.save(action);
        return result;
    }

    /**
     *  Get all the actions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Action> findAll(Pageable pageable) {
        log.debug("Request to get all Actions");
        Page<Action> result = actionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one action by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Action findOne(Long id) {
        log.debug("Request to get Action : {}", id);
        Action action = actionRepository.findOne(id);
        return action;
    }

    /**
     *  Delete the  action by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Action : {}", id);
        actionRepository.delete(id);
    }
}
