package ec.com.se.service;

import ec.com.se.domain.SubcategoryLang;
import ec.com.se.repository.SubcategoryLangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SubcategoryLang.
 */
@Service
@Transactional
public class SubcategoryLangService {

    private final Logger log = LoggerFactory.getLogger(SubcategoryLangService.class);
    
    @Inject
    private SubcategoryLangRepository subcategoryLangRepository;
    
    /**
     * Save a subcategoryLang.
     * 
     * @param subcategoryLang the entity to save
     * @return the persisted entity
     */
    public SubcategoryLang save(SubcategoryLang subcategoryLang) {
        log.debug("Request to save SubcategoryLang : {}", subcategoryLang);
        SubcategoryLang result = subcategoryLangRepository.save(subcategoryLang);
        return result;
    }

    /**
     *  Get all the subcategoryLangs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SubcategoryLang> findAll(Pageable pageable) {
        log.debug("Request to get all SubcategoryLangs");
        Page<SubcategoryLang> result = subcategoryLangRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one subcategoryLang by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SubcategoryLang findOne(Long id) {
        log.debug("Request to get SubcategoryLang : {}", id);
        SubcategoryLang subcategoryLang = subcategoryLangRepository.findOne(id);
        return subcategoryLang;
    }

    /**
     *  Delete the  subcategoryLang by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SubcategoryLang : {}", id);
        subcategoryLangRepository.delete(id);
    }
}
