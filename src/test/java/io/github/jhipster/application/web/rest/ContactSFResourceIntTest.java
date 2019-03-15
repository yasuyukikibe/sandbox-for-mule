package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SandboxForMuleApp;

import io.github.jhipster.application.domain.ContactSF;
import io.github.jhipster.application.repository.ContactSFRepository;
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
 * Test class for the ContactSFResource REST controller.
 *
 * @see ContactSFResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SandboxForMuleApp.class)
public class ContactSFResourceIntTest {

    private static final String DEFAULT_CUSTOM_EXT_ID_FIELD = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_EXT_ID_FIELD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private ContactSFRepository contactSFRepository;

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

    private MockMvc restContactSFMockMvc;

    private ContactSF contactSF;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactSFResource contactSFResource = new ContactSFResource(contactSFRepository);
        this.restContactSFMockMvc = MockMvcBuilders.standaloneSetup(contactSFResource)
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
    public static ContactSF createEntity(EntityManager em) {
        ContactSF contactSF = new ContactSF()
            .customExtIdField(DEFAULT_CUSTOM_EXT_ID_FIELD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return contactSF;
    }

    @Before
    public void initTest() {
        contactSF = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactSF() throws Exception {
        int databaseSizeBeforeCreate = contactSFRepository.findAll().size();

        // Create the ContactSF
        restContactSFMockMvc.perform(post("/api/contact-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSF)))
            .andExpect(status().isCreated());

        // Validate the ContactSF in the database
        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeCreate + 1);
        ContactSF testContactSF = contactSFList.get(contactSFList.size() - 1);
        assertThat(testContactSF.getCustomExtIdField()).isEqualTo(DEFAULT_CUSTOM_EXT_ID_FIELD);
        assertThat(testContactSF.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContactSF.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createContactSFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactSFRepository.findAll().size();

        // Create the ContactSF with an existing ID
        contactSF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactSFMockMvc.perform(post("/api/contact-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSF)))
            .andExpect(status().isBadRequest());

        // Validate the ContactSF in the database
        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustomExtIdFieldIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactSFRepository.findAll().size();
        // set the field null
        contactSF.setCustomExtIdField(null);

        // Create the ContactSF, which fails.

        restContactSFMockMvc.perform(post("/api/contact-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSF)))
            .andExpect(status().isBadRequest());

        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactSFRepository.findAll().size();
        // set the field null
        contactSF.setLastName(null);

        // Create the ContactSF, which fails.

        restContactSFMockMvc.perform(post("/api/contact-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSF)))
            .andExpect(status().isBadRequest());

        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactSFS() throws Exception {
        // Initialize the database
        contactSFRepository.saveAndFlush(contactSF);

        // Get all the contactSFList
        restContactSFMockMvc.perform(get("/api/contact-sfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactSF.getId().intValue())))
            .andExpect(jsonPath("$.[*].customExtIdField").value(hasItem(DEFAULT_CUSTOM_EXT_ID_FIELD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContactSF() throws Exception {
        // Initialize the database
        contactSFRepository.saveAndFlush(contactSF);

        // Get the contactSF
        restContactSFMockMvc.perform(get("/api/contact-sfs/{id}", contactSF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactSF.getId().intValue()))
            .andExpect(jsonPath("$.customExtIdField").value(DEFAULT_CUSTOM_EXT_ID_FIELD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactSF() throws Exception {
        // Get the contactSF
        restContactSFMockMvc.perform(get("/api/contact-sfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactSF() throws Exception {
        // Initialize the database
        contactSFRepository.saveAndFlush(contactSF);

        int databaseSizeBeforeUpdate = contactSFRepository.findAll().size();

        // Update the contactSF
        ContactSF updatedContactSF = contactSFRepository.findById(contactSF.getId()).get();
        // Disconnect from session so that the updates on updatedContactSF are not directly saved in db
        em.detach(updatedContactSF);
        updatedContactSF
            .customExtIdField(UPDATED_CUSTOM_EXT_ID_FIELD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);

        restContactSFMockMvc.perform(put("/api/contact-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactSF)))
            .andExpect(status().isOk());

        // Validate the ContactSF in the database
        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeUpdate);
        ContactSF testContactSF = contactSFList.get(contactSFList.size() - 1);
        assertThat(testContactSF.getCustomExtIdField()).isEqualTo(UPDATED_CUSTOM_EXT_ID_FIELD);
        assertThat(testContactSF.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContactSF.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContactSF() throws Exception {
        int databaseSizeBeforeUpdate = contactSFRepository.findAll().size();

        // Create the ContactSF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactSFMockMvc.perform(put("/api/contact-sfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactSF)))
            .andExpect(status().isBadRequest());

        // Validate the ContactSF in the database
        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactSF() throws Exception {
        // Initialize the database
        contactSFRepository.saveAndFlush(contactSF);

        int databaseSizeBeforeDelete = contactSFRepository.findAll().size();

        // Delete the contactSF
        restContactSFMockMvc.perform(delete("/api/contact-sfs/{id}", contactSF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactSF> contactSFList = contactSFRepository.findAll();
        assertThat(contactSFList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSF.class);
        ContactSF contactSF1 = new ContactSF();
        contactSF1.setId(1L);
        ContactSF contactSF2 = new ContactSF();
        contactSF2.setId(contactSF1.getId());
        assertThat(contactSF1).isEqualTo(contactSF2);
        contactSF2.setId(2L);
        assertThat(contactSF1).isNotEqualTo(contactSF2);
        contactSF1.setId(null);
        assertThat(contactSF1).isNotEqualTo(contactSF2);
    }
}
