package ec.com.se.service;

import ec.com.se.domain.ActionLang;
import ec.com.se.domain.Category;
import ec.com.se.domain.Subcategory;
import ec.com.se.domain.enumeration.Language;
import ec.com.se.repository.ActionLangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ActionLang.
 */
@Service
@Transactional
public class ActionLangService {

    private final Logger log = LoggerFactory.getLogger(ActionLangService.class);

    @Inject
    private ActionLangRepository actionLangRepository;

    /**
     * Save a actionLang.
     *
     * @param actionLang the entity to save
     * @return the persisted entity
     */
    public ActionLang save(ActionLang actionLang) {
        log.debug("Request to save ActionLang : {}", actionLang);
        ActionLang result = actionLangRepository.save(actionLang);
        return result;
    }

    /**
     *  Get all the actionLangs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ActionLang> findAll(Pageable pageable) {
        log.debug("Request to get all ActionLangs");
        Page<ActionLang> result = actionLangRepository.findAll(pageable);
        return result;
    }

    /*  Return ActionLang Enabled and Language Code */
    @Transactional(readOnly = true)
    public Page<ActionLang> findByLanguage(Language language, Boolean enabled, List<Subcategory> subcategories, Pageable pageable) {
        log.debug("Request to get all enabled Category");
        Page<ActionLang> result = actionLangRepository.findByLanguageCodeAndActionEnabledAndActionSubcategories(language, enabled, subcategories, pageable);
        return result;
    }

    /**
     *  Get one actionLang by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ActionLang findOne(Long id) {
        log.debug("Request to get ActionLang : {}", id);
        ActionLang actionLang = actionLangRepository.findOne(id);
        return actionLang;
    }

    /**
     *  Delete the  actionLang by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ActionLang : {}", id);
        actionLangRepository.delete(id);
    }
}
