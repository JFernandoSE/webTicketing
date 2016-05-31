package ec.com.se.service;

import ec.com.se.domain.Category;
import ec.com.se.domain.Subcategory;
import ec.com.se.repository.SubcategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Subcategory.
 */
@Service
@Transactional
public class SubcategoryService {

    private final Logger log = LoggerFactory.getLogger(SubcategoryService.class);

    @Inject
    private SubcategoryRepository subcategoryRepository;

    /**
     * Save a subcategory.
     *
     * @param subcategory the entity to save
     * @return the persisted entity
     */
    public Subcategory save(Subcategory subcategory) {
        log.debug("Request to save Subcategory : {}", subcategory);
        Subcategory result = subcategoryRepository.save(subcategory);
        return result;
    }

    /**
     *  Get all the subcategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Subcategory> findAll(Pageable pageable) {
        log.debug("Request to get all Subcategories");
        Page<Subcategory> result = subcategoryRepository.findAll(pageable);
        return result;
    }

    /*  Return Subcategories list enabled */
    @Transactional(readOnly = true)
    public Page<Subcategory> findAllEnabled(Category category, Boolean enabled, Pageable pageable) {
        log.debug("Request to get all enabled Category");
        Page<Subcategory> result = subcategoryRepository.findByCategoryAndEnabled(category, enabled, pageable);
        return result;
    }

    /**
     *  Get one subcategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Subcategory findOne(Long id) {
        log.debug("Request to get Subcategory : {}", id);
        Subcategory subcategory = subcategoryRepository.findOneWithEagerRelationships(id);
        return subcategory;
    }

    /**
     *  Delete the  subcategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Subcategory : {}", id);
        subcategoryRepository.delete(id);
    }
}
