package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.AccountSF;
import io.github.jhipster.application.repository.AccountSFRepository;
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
 * REST controller for managing AccountSF.
 */
@RestController
@RequestMapping("/api")
public class AccountSFResource {

    private final Logger log = LoggerFactory.getLogger(AccountSFResource.class);

    private static final String ENTITY_NAME = "accountSF";

    private final AccountSFRepository accountSFRepository;

    public AccountSFResource(AccountSFRepository accountSFRepository) {
        this.accountSFRepository = accountSFRepository;
    }

    /**
     * POST  /account-sfs : Create a new accountSF.
     *
     * @param accountSF the accountSF to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accountSF, or with status 400 (Bad Request) if the accountSF has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/account-sfs")
    public ResponseEntity<AccountSF> createAccountSF(@Valid @RequestBody AccountSF accountSF) throws URISyntaxException {
        log.debug("REST request to save AccountSF : {}", accountSF);
        if (accountSF.getId() != null) {
            throw new BadRequestAlertException("A new accountSF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountSF result = accountSFRepository.save(accountSF);
        return ResponseEntity.created(new URI("/api/account-sfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /account-sfs : Updates an existing accountSF.
     *
     * @param accountSF the accountSF to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accountSF,
     * or with status 400 (Bad Request) if the accountSF is not valid,
     * or with status 500 (Internal Server Error) if the accountSF couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/account-sfs")
    public ResponseEntity<AccountSF> updateAccountSF(@Valid @RequestBody AccountSF accountSF) throws URISyntaxException {
        log.debug("REST request to update AccountSF : {}", accountSF);
        if (accountSF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccountSF result = accountSFRepository.save(accountSF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accountSF.getId().toString()))
            .body(result);
    }

    /**
     * GET  /account-sfs : get all the accountSFS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accountSFS in body
     */
    @GetMapping("/account-sfs")
    public List<AccountSF> getAllAccountSFS() {
        log.debug("REST request to get all AccountSFS");
        return accountSFRepository.findAll();
    }

    /**
     * GET  /account-sfs/:id : get the "id" accountSF.
     *
     * @param id the id of the accountSF to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accountSF, or with status 404 (Not Found)
     */
    @GetMapping("/account-sfs/{id}")
    public ResponseEntity<AccountSF> getAccountSF(@PathVariable Long id) {
        log.debug("REST request to get AccountSF : {}", id);
        Optional<AccountSF> accountSF = accountSFRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accountSF);
    }

    /**
     * DELETE  /account-sfs/:id : delete the "id" accountSF.
     *
     * @param id the id of the accountSF to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/account-sfs/{id}")
    public ResponseEntity<Void> deleteAccountSF(@PathVariable Long id) {
        log.debug("REST request to delete AccountSF : {}", id);
        accountSFRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
