package ec.com.se.web.rest;

import ec.com.se.DemoApp;
import ec.com.se.domain.Subcategory;
import ec.com.se.repository.SubcategoryRepository;
import ec.com.se.service.SubcategoryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SubcategoryResource REST controller.
 *
 * @see SubcategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubcategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_EXTERNAL_ID = "AAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Inject
    private SubcategoryRepository subcategoryRepository;

    @Inject
    private SubcategoryService subcategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubcategoryMockMvc;

    private Subcategory subcategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubcategoryResource subcategoryResource = new SubcategoryResource();
        ReflectionTestUtils.setField(subcategoryResource, "subcategoryService", subcategoryService);
        this.restSubcategoryMockMvc = MockMvcBuilders.standaloneSetup(subcategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subcategory = new Subcategory();
        subcategory.setName(DEFAULT_NAME);
        subcategory.setExternalId(DEFAULT_EXTERNAL_ID);
        subcategory.setEnabled(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createSubcategory() throws Exception {
        int databaseSizeBeforeCreate = subcategoryRepository.findAll().size();

        // Create the Subcategory

        restSubcategoryMockMvc.perform(post("/api/subcategories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategory)))
                .andExpect(status().isCreated());

        // Validate the Subcategory in the database
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertThat(subcategories).hasSize(databaseSizeBeforeCreate + 1);
        Subcategory testSubcategory = subcategories.get(subcategories.size() - 1);
        assertThat(testSubcategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubcategory.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testSubcategory.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryRepository.findAll().size();
        // set the field null
        subcategory.setName(null);

        // Create the Subcategory, which fails.

        restSubcategoryMockMvc.perform(post("/api/subcategories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategory)))
                .andExpect(status().isBadRequest());

        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertThat(subcategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExternalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryRepository.findAll().size();
        // set the field null
        subcategory.setExternalId(null);

        // Create the Subcategory, which fails.

        restSubcategoryMockMvc.perform(post("/api/subcategories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategory)))
                .andExpect(status().isBadRequest());

        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertThat(subcategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnabledIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryRepository.findAll().size();
        // set the field null
        subcategory.setEnabled(null);

        // Create the Subcategory, which fails.

        restSubcategoryMockMvc.perform(post("/api/subcategories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategory)))
                .andExpect(status().isBadRequest());

        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertThat(subcategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubcategories() throws Exception {
        // Initialize the database
        subcategoryRepository.saveAndFlush(subcategory);

        // Get all the subcategories
        restSubcategoryMockMvc.perform(get("/api/subcategories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subcategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID.toString())))
                .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getSubcategory() throws Exception {
        // Initialize the database
        subcategoryRepository.saveAndFlush(subcategory);

        // Get the subcategory
        restSubcategoryMockMvc.perform(get("/api/subcategories/{id}", subcategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subcategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSubcategory() throws Exception {
        // Get the subcategory
        restSubcategoryMockMvc.perform(get("/api/subcategories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubcategory() throws Exception {
        // Initialize the database
        subcategoryService.save(subcategory);

        int databaseSizeBeforeUpdate = subcategoryRepository.findAll().size();

        // Update the subcategory
        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setId(subcategory.getId());
        updatedSubcategory.setName(UPDATED_NAME);
        updatedSubcategory.setExternalId(UPDATED_EXTERNAL_ID);
        updatedSubcategory.setEnabled(UPDATED_ENABLED);

        restSubcategoryMockMvc.perform(put("/api/subcategories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubcategory)))
                .andExpect(status().isOk());

        // Validate the Subcategory in the database
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertThat(subcategories).hasSize(databaseSizeBeforeUpdate);
        Subcategory testSubcategory = subcategories.get(subcategories.size() - 1);
        assertThat(testSubcategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubcategory.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testSubcategory.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void deleteSubcategory() throws Exception {
        // Initialize the database
        subcategoryService.save(subcategory);

        int databaseSizeBeforeDelete = subcategoryRepository.findAll().size();

        // Get the subcategory
        restSubcategoryMockMvc.perform(delete("/api/subcategories/{id}", subcategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        assertThat(subcategories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
