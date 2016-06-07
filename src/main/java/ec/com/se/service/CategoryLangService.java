package ec.com.se.service;

import ec.com.se.domain.CategoryLang;
import ec.com.se.domain.enumeration.Language;
import ec.com.se.repository.CategoryLangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing CategoryLang.
 */
@Service
@Transactional
public class CategoryLangService {

    private final Logger log = LoggerFactory.getLogger(CategoryLangService.class);

    @Inject
    private CategoryLangRepository categoryLangRepository;

    /**
     * Save a categoryLang.
     *
     * @param categoryLang the entity to save
     * @return the persisted entity
     */
    public CategoryLang save(CategoryLang categoryLang) {
        log.debug("Request to save CategoryLang : {}", categoryLang);
        CategoryLang result = categoryLangRepository.save(categoryLang);
        return result;
    }

    /**
     *  Get all the categoryLangs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CategoryLang> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryLangs");
        Page<CategoryLang> result = categoryLangRepository.findAll(pageable);
        return result;
    }

    /*  Return CategoryLang Enabled and Language Code */
    @Transactional(readOnly = true)
    public Page<CategoryLang> findByLanguage(Language language, Boolean enabled, Pageable pageable) {
        log.debug("Request to get all enabled Category");
        Page<CategoryLang> result = categoryLangRepository.findByLanguageCodeAndCategoryEnabled(language, enabled, pageable);
        return result;
    }


    /**
     *  Get one categoryLang by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CategoryLang findOne(Long id) {
        log.debug("Request to get CategoryLang : {}", id);
        CategoryLang categoryLang = categoryLangRepository.findOne(id);
        return categoryLang;
    }

    /**
     *  Delete the  categoryLang by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoryLang : {}", id);
        categoryLangRepository.delete(id);
    }
}
