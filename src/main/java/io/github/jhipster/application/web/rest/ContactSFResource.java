package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.ContactSF;
import io.github.jhipster.application.repository.ContactSFRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContactSF.
 */
@RestController
@RequestMapping("/api")
public class ContactSFResource {

    private final Logger log = LoggerFactory.getLogger(ContactSFResource.class);

    private static final String ENTITY_NAME = "contactSF";

    private final ContactSFRepository contactSFRepository;

    public ContactSFResource(ContactSFRepository contactSFRepository) {
        this.contactSFRepository = contactSFRepository;
    }

    /**
     * POST  /contact-sfs : Create a new contactSF.
     *
     * @param contactSF the contactSF to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactSF, or with status 400 (Bad Request) if the contactSF has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-sfs")
    public ResponseEntity<ContactSF> createContactSF(@Valid @RequestBody ContactSF contactSF) throws URISyntaxException {
        log.debug("REST request to save ContactSF : {}", contactSF);
        if (contactSF.getId() != null) {
            throw new BadRequestAlertException("A new contactSF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactSF result = contactSFRepository.save(contactSF);
        return ResponseEntity.created(new URI("/api/contact-sfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-sfs : Updates an existing contactSF.
     *
     * @param contactSF the contactSF to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactSF,
     * or with status 400 (Bad Request) if the contactSF is not valid,
     * or with status 500 (Internal Server Error) if the contactSF couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-sfs")
    public ResponseEntity<ContactSF> updateContactSF(@Valid @RequestBody ContactSF contactSF) throws URISyntaxException {
        log.debug("REST request to update ContactSF : {}", contactSF);
        if (contactSF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactSF result = contactSFRepository.save(contactSF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactSF.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-sfs : get all the contactSFS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactSFS in body
     */
    @GetMapping("/contact-sfs")
    public List<ContactSF> getAllContactSFS() {
        log.debug("REST request to get all ContactSFS");
        return contactSFRepository.findAll();
    }

    /**
     * GET  /contact-sfs/:id : get the "id" contactSF.
     *
     * @param id the id of the contactSF to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactSF, or with status 404 (Not Found)
     */
    @GetMapping("/contact-sfs/{id}")
    public ResponseEntity<ContactSF> getContactSF(@PathVariable Long id) {
        log.debug("REST request to get ContactSF : {}", id);
        Optional<ContactSF> contactSF = contactSFRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contactSF);
    }

    /**
     * DELETE  /contact-sfs/:id : delete the "id" contactSF.
     *
     * @param id the id of the contactSF to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-sfs/{id}")
    public ResponseEntity<Void> deleteContactSF(@PathVariable Long id) {
        log.debug("REST request to delete ContactSF : {}", id);
        contactSFRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
