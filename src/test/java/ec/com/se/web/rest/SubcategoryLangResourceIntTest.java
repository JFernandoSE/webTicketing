package ec.com.se.web.rest;

import ec.com.se.DemoApp;
import ec.com.se.domain.SubcategoryLang;
import ec.com.se.repository.SubcategoryLangRepository;
import ec.com.se.service.SubcategoryLangService;

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

import ec.com.se.domain.enumeration.Language;

/**
 * Test class for the SubcategoryLangResource REST controller.
 *
 * @see SubcategoryLangResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubcategoryLangResourceIntTest {


    private static final Language DEFAULT_LANGUAGE_CODE = Language.EN;
    private static final Language UPDATED_LANGUAGE_CODE = Language.ES;
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private SubcategoryLangRepository subcategoryLangRepository;

    @Inject
    private SubcategoryLangService subcategoryLangService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubcategoryLangMockMvc;

    private SubcategoryLang subcategoryLang;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubcategoryLangResource subcategoryLangResource = new SubcategoryLangResource();
        ReflectionTestUtils.setField(subcategoryLangResource, "subcategoryLangService", subcategoryLangService);
        this.restSubcategoryLangMockMvc = MockMvcBuilders.standaloneSetup(subcategoryLangResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subcategoryLang = new SubcategoryLang();
        subcategoryLang.setLanguageCode(DEFAULT_LANGUAGE_CODE);
        subcategoryLang.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createSubcategoryLang() throws Exception {
        int databaseSizeBeforeCreate = subcategoryLangRepository.findAll().size();

        // Create the SubcategoryLang

        restSubcategoryLangMockMvc.perform(post("/api/subcategory-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryLang)))
                .andExpect(status().isCreated());

        // Validate the SubcategoryLang in the database
        List<SubcategoryLang> subcategoryLangs = subcategoryLangRepository.findAll();
        assertThat(subcategoryLangs).hasSize(databaseSizeBeforeCreate + 1);
        SubcategoryLang testSubcategoryLang = subcategoryLangs.get(subcategoryLangs.size() - 1);
        assertThat(testSubcategoryLang.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
        assertThat(testSubcategoryLang.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkLanguageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryLangRepository.findAll().size();
        // set the field null
        subcategoryLang.setLanguageCode(null);

        // Create the SubcategoryLang, which fails.

        restSubcategoryLangMockMvc.perform(post("/api/subcategory-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryLang)))
                .andExpect(status().isBadRequest());

        List<SubcategoryLang> subcategoryLangs = subcategoryLangRepository.findAll();
        assertThat(subcategoryLangs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = subcategoryLangRepository.findAll().size();
        // set the field null
        subcategoryLang.setValue(null);

        // Create the SubcategoryLang, which fails.

        restSubcategoryLangMockMvc.perform(post("/api/subcategory-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subcategoryLang)))
                .andExpect(status().isBadRequest());

        List<SubcategoryLang> subcategoryLangs = subcategoryLangRepository.findAll();
        assertThat(subcategoryLangs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubcategoryLangs() throws Exception {
        // Initialize the database
        subcategoryLangRepository.saveAndFlush(subcategoryLang);

        // Get all the subcategoryLangs
        restSubcategoryLangMockMvc.perform(get("/api/subcategory-langs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subcategoryLang.getId().intValue())))
                .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSubcategoryLang() throws Exception {
        // Initialize the database
        subcategoryLangRepository.saveAndFlush(subcategoryLang);

        // Get the subcategoryLang
        restSubcategoryLangMockMvc.perform(get("/api/subcategory-langs/{id}", subcategoryLang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subcategoryLang.getId().intValue()))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubcategoryLang() throws Exception {
        // Get the subcategoryLang
        restSubcategoryLangMockMvc.perform(get("/api/subcategory-langs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubcategoryLang() throws Exception {
        // Initialize the database
        subcategoryLangService.save(subcategoryLang);

        int databaseSizeBeforeUpdate = subcategoryLangRepository.findAll().size();

        // Update the subcategoryLang
        SubcategoryLang updatedSubcategoryLang = new SubcategoryLang();
        updatedSubcategoryLang.setId(subcategoryLang.getId());
        updatedSubcategoryLang.setLanguageCode(UPDATED_LANGUAGE_CODE);
        updatedSubcategoryLang.setValue(UPDATED_VALUE);

        restSubcategoryLangMockMvc.perform(put("/api/subcategory-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubcategoryLang)))
                .andExpect(status().isOk());

        // Validate the SubcategoryLang in the database
        List<SubcategoryLang> subcategoryLangs = subcategoryLangRepository.findAll();
        assertThat(subcategoryLangs).hasSize(databaseSizeBeforeUpdate);
        SubcategoryLang testSubcategoryLang = subcategoryLangs.get(subcategoryLangs.size() - 1);
        assertThat(testSubcategoryLang.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testSubcategoryLang.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteSubcategoryLang() throws Exception {
        // Initialize the database
        subcategoryLangService.save(subcategoryLang);

        int databaseSizeBeforeDelete = subcategoryLangRepository.findAll().size();

        // Get the subcategoryLang
        restSubcategoryLangMockMvc.perform(delete("/api/subcategory-langs/{id}", subcategoryLang.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubcategoryLang> subcategoryLangs = subcategoryLangRepository.findAll();
        assertThat(subcategoryLangs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
