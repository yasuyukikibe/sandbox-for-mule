package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SandboxForMuleApp;

import io.github.jhipster.application.domain.AccountSF;
import io.github.jhipster.application.repository.AccountSFRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccountSFResource REST controller.
 *
 * @see AccountSFResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SandboxForMuleApp.class)
public class AccountSFResourceIntTest {

    private static final String DEFAULT_CUSTOM_EXT_ID_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_EXT_ID_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AccountSFRepository accountSFRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAccountSFMockMvc;

    private AccountSF accountSF;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountSFResource accountSFResource = new AccountSFResource(accountSFRepository);
        this.restAccountSFMockMvc = MockMvcBuilders.standaloneSetup(accountSFResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountSF createEntity(EntityManager em) {
        AccountSF accountSF = new AccountSF()
            .customExtIdField(DEFAULT_CUSTOM_EXT_ID_FIELD)
            .name(DEFAULT_NAME);
        return accountSF;
    }

    @Before
    public void initTest() {
        accountSF = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountSF() throws Exception {
        int databaseSizeBeforeCreate = accountSFRepository.findAll().size();

        // Create the AccountSF
        restAccountSFMockMvc.perform(post("/api/account-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountSF)))
            .andExpect(status().isCreated());

        // Validate the AccountSF in the database
        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeCreate + 1);
        AccountSF testAccountSF = accountSFList.get(accountSFList.size() - 1);
        assertThat(testAccountSF.getCustomExtIdField()).isEqualTo(DEFAULT_CUSTOM_EXT_ID_FIELD);
        assertThat(testAccountSF.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAccountSFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountSFRepository.findAll().size();

        // Create the AccountSF with an existing ID
        accountSF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountSFMockMvc.perform(post("/api/account-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountSF)))
            .andExpect(status().isBadRequest());

        // Validate the AccountSF in the database
        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustomExtIdFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountSFRepository.findAll().size();
        // set the field null
        accountSF.setCustomExtIdField(null);

        // Create the AccountSF, which fails.

        restAccountSFMockMvc.perform(post("/api/account-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountSF)))
            .andExpect(status().isBadRequest());

        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountSFRepository.findAll().size();
        // set the field null
        accountSF.setName(null);

        // Create the AccountSF, which fails.

        restAccountSFMockMvc.perform(post("/api/account-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountSF)))
            .andExpect(status().isBadRequest());

        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountSFS() throws Exception {
        // Initialize the database
        accountSFRepository.saveAndFlush(accountSF);

        // Get all the accountSFList
        restAccountSFMockMvc.perform(get("/api/account-sfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountSF.getId().intValue())))
            .andExpect(jsonPath("$.[*].customExtIdField").value(hasItem(DEFAULT_CUSTOM_EXT_ID_FIELD.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAccountSF() throws Exception {
        // Initialize the database
        accountSFRepository.saveAndFlush(accountSF);

        // Get the accountSF
        restAccountSFMockMvc.perform(get("/api/account-sfs/{id}", accountSF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountSF.getId().intValue()))
            .andExpect(jsonPath("$.customExtIdField").value(DEFAULT_CUSTOM_EXT_ID_FIELD.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountSF() throws Exception {
        // Get the accountSF
        restAccountSFMockMvc.perform(get("/api/account-sfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountSF() throws Exception {
        // Initialize the database
        accountSFRepository.saveAndFlush(accountSF);

        int databaseSizeBeforeUpdate = accountSFRepository.findAll().size();

        // Update the accountSF
        AccountSF updatedAccountSF = accountSFRepository.findById(accountSF.getId()).get();
        // Disconnect from session so that the updates on updatedAccountSF are not directly saved in db
        em.detach(updatedAccountSF);
        updatedAccountSF
            .customExtIdField(UPDATED_CUSTOM_EXT_ID_FIELD)
            .name(UPDATED_NAME);

        restAccountSFMockMvc.perform(put("/api/account-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccountSF)))
            .andExpect(status().isOk());

        // Validate the AccountSF in the database
        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeUpdate);
        AccountSF testAccountSF = accountSFList.get(accountSFList.size() - 1);
        assertThat(testAccountSF.getCustomExtIdField()).isEqualTo(UPDATED_CUSTOM_EXT_ID_FIELD);
        assertThat(testAccountSF.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountSF() throws Exception {
        int databaseSizeBeforeUpdate = accountSFRepository.findAll().size();

        // Create the AccountSF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountSFMockMvc.perform(put("/api/account-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountSF)))
            .andExpect(status().isBadRequest());

        // Validate the AccountSF in the database
        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountSF() throws Exception {
        // Initialize the database
        accountSFRepository.saveAndFlush(accountSF);

        int databaseSizeBeforeDelete = accountSFRepository.findAll().size();

        // Delete the accountSF
        restAccountSFMockMvc.perform(delete("/api/account-sfs/{id}", accountSF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AccountSF> accountSFList = accountSFRepository.findAll();
        assertThat(accountSFList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountSF.class);
        AccountSF accountSF1 = new AccountSF();
        accountSF1.setId(1L);
        AccountSF accountSF2 = new AccountSF();
        accountSF2.setId(accountSF1.getId());
        assertThat(accountSF1).isEqualTo(accountSF2);
        accountSF2.setId(2L);
        assertThat(accountSF1).isNotEqualTo(accountSF2);
        accountSF1.setId(null);
        assertThat(accountSF1).isNotEqualTo(accountSF2);
    }
}
