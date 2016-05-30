package ec.com.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import ec.com.se.domain.CategoryLang;
import ec.com.se.service.CategoryLangService;
import ec.com.se.web.rest.util.HeaderUtil;
import ec.com.se.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CategoryLang.
 */
@RestController
@RequestMapping("/api")
public class CategoryLangResource {

    private final Logger log = LoggerFactory.getLogger(CategoryLangResource.class);
        
    @Inject
    private CategoryLangService categoryLangService;
    
    /**
     * POST  /category-langs : Create a new categoryLang.
     *
     * @param categoryLang the categoryLang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryLang, or with status 400 (Bad Request) if the categoryLang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/category-langs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CategoryLang> createCategoryLang(@Valid @RequestBody CategoryLang categoryLang) throws URISyntaxException {
        log.debug("REST request to save CategoryLang : {}", categoryLang);
        if (categoryLang.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("categoryLang", "idexists", "A new categoryLang cannot already have an ID")).body(null);
        }
        CategoryLang result = categoryLangService.save(categoryLang);
        return ResponseEntity.created(new URI("/api/category-langs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("categoryLang", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /category-langs : Updates an existing categoryLang.
     *
     * @param categoryLang the categoryLang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryLang,
     * or with status 400 (Bad Request) if the categoryLang is not valid,
     * or with status 500 (Internal Server Error) if the categoryLang couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/category-langs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CategoryLang> updateCategoryLang(@Valid @RequestBody CategoryLang categoryLang) throws URISyntaxException {
        log.debug("REST request to update CategoryLang : {}", categoryLang);
        if (categoryLang.getId() == null) {
            return createCategoryLang(categoryLang);
        }
        CategoryLang result = categoryLangService.save(categoryLang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("categoryLang", categoryLang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /category-langs : get all the categoryLangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categoryLangs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/category-langs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CategoryLang>> getAllCategoryLangs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CategoryLangs");
        Page<CategoryLang> page = categoryLangService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/category-langs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /category-langs/:id : get the "id" categoryLang.
     *
     * @param id the id of the categoryLang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryLang, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/category-langs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CategoryLang> getCategoryLang(@PathVariable Long id) {
        log.debug("REST request to get CategoryLang : {}", id);
        CategoryLang categoryLang = categoryLangService.findOne(id);
        return Optional.ofNullable(categoryLang)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /category-langs/:id : delete the "id" categoryLang.
     *
     * @param id the id of the categoryLang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/category-langs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCategoryLang(@PathVariable Long id) {
        log.debug("REST request to delete CategoryLang : {}", id);
        categoryLangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("categoryLang", id.toString())).build();
    }

}
