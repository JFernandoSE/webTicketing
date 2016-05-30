package ec.com.se.web.rest;

import com.codahale.metrics.annotation.Timed;
import ec.com.se.domain.ActionLang;
import ec.com.se.service.ActionLangService;
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
 * REST controller for managing ActionLang.
 */
@RestController
@RequestMapping("/api")
public class ActionLangResource {

    private final Logger log = LoggerFactory.getLogger(ActionLangResource.class);
        
    @Inject
    private ActionLangService actionLangService;
    
    /**
     * POST  /action-langs : Create a new actionLang.
     *
     * @param actionLang the actionLang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actionLang, or with status 400 (Bad Request) if the actionLang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/action-langs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ActionLang> createActionLang(@Valid @RequestBody ActionLang actionLang) throws URISyntaxException {
        log.debug("REST request to save ActionLang : {}", actionLang);
        if (actionLang.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("actionLang", "idexists", "A new actionLang cannot already have an ID")).body(null);
        }
        ActionLang result = actionLangService.save(actionLang);
        return ResponseEntity.created(new URI("/api/action-langs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("actionLang", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /action-langs : Updates an existing actionLang.
     *
     * @param actionLang the actionLang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actionLang,
     * or with status 400 (Bad Request) if the actionLang is not valid,
     * or with status 500 (Internal Server Error) if the actionLang couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/action-langs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ActionLang> updateActionLang(@Valid @RequestBody ActionLang actionLang) throws URISyntaxException {
        log.debug("REST request to update ActionLang : {}", actionLang);
        if (actionLang.getId() == null) {
            return createActionLang(actionLang);
        }
        ActionLang result = actionLangService.save(actionLang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("actionLang", actionLang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /action-langs : get all the actionLangs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actionLangs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/action-langs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ActionLang>> getAllActionLangs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ActionLangs");
        Page<ActionLang> page = actionLangService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/action-langs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /action-langs/:id : get the "id" actionLang.
     *
     * @param id the id of the actionLang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actionLang, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/action-langs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ActionLang> getActionLang(@PathVariable Long id) {
        log.debug("REST request to get ActionLang : {}", id);
        ActionLang actionLang = actionLangService.findOne(id);
        return Optional.ofNullable(actionLang)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /action-langs/:id : delete the "id" actionLang.
     *
     * @param id the id of the actionLang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/action-langs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteActionLang(@PathVariable Long id) {
        log.debug("REST request to delete ActionLang : {}", id);
        actionLangService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("actionLang", id.toString())).build();
    }

}
