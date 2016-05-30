package ec.com.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import ec.com.se.domain.SubcategoryLang;
import ec.com.se.service.SubcategoryLangService;
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
 * REST controller for managing SubcategoryLang.
 */
@RestController
@RequestMapping("/api")
public class SubcategoryLangResource {

    private final Logger log = LoggerFactory.getLogger(SubcategoryLangResource.class);
        
    @Inject
    private SubcategoryLangService subcategoryLangService;
    
    /**
     * POST  /subcategory-langs : Create a new subcategoryLang.
     *
     * @param subcategoryLang the subcategoryLang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subcategoryLang, or with status 400 (Bad Request) if the subcategoryLang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subcategory-langs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubcategoryLang> createSubcategoryLang(@Valid @RequestBody SubcategoryLang subcategoryLang) throws URISyntaxException {
        log.debug("REST request to save SubcategoryLang : {}", subcategoryLang);
        if (subcategoryLang.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subcategoryLang", "idexists", "A new subcategoryLang cannot already have an ID")).body(null);
        }
        SubcategoryLang result = subcategoryLangService.save(subcategoryLang);
        return ResponseEntity.created(new URI("/api/subcategory-langs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subcategoryLang", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subcategory-langs : Updates an existing subcategoryLang.
     *
     * @param subcategoryLang the subcategoryLang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subcategoryLang,
     * or with status 400 (Bad Request) if the subcategoryLang is not valid,
     * or with status 500 (Internal Server Error) if the subcategoryLang couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/subcategory-langs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubcategoryLang> updateSubcategoryLang(@Valid @RequestBody SubcategoryLang subcategoryLang) throws URISyntaxException {
        log.debug("REST request to update SubcategoryLang : {}", subcategoryLang);
        if (subcategoryLang.getId() == null) {
            return createSubcategoryLang(subcategoryLang);
        }
        SubcategoryLang result = subcategoryLangService.save(subcategoryLang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subcategoryLang", subcategoryLang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subcategory-langs : get all the subcategoryLangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of subcategoryLangs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/subcategory-langs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SubcategoryLang>> getAllSubcategoryLangs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SubcategoryLangs");
        Page<SubcategoryLang> page = subcategoryLangService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subcategory-langs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subcategory-langs/:id : get the "id" subcategoryLang.
     *
     * @param id the id of the subcategoryLang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subcategoryLang, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/subcategory-langs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubcategoryLang> getSubcategoryLang(@PathVariable Long id) {
        log.debug("REST request to get SubcategoryLang : {}", id);
        SubcategoryLang subcategoryLang = subcategoryLangService.findOne(id);
        return Optional.ofNullable(subcategoryLang)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subcategory-langs/:id : delete the "id" subcategoryLang.
     *
     * @param id the id of the subcategoryLang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/subcategory-langs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubcategoryLang(@PathVariable Long id) {
        log.debug("REST request to delete SubcategoryLang : {}", id);
        subcategoryLangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subcategoryLang", id.toString())).build();
    }

}
