package ec.com.se.web.rest;

import ec.com.se.DemoApp;
import ec.com.se.domain.CategoryLang;
import ec.com.se.repository.CategoryLangRepository;
import ec.com.se.service.CategoryLangService;

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
 * Test class for the CategoryLangResource REST controller.
 *
 * @see CategoryLangResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApp.class)
@WebAppConfiguration
@IntegrationTest
public class CategoryLangResourceIntTest {


    private static final Language DEFAULT_LANGUAGE_CODE = Language.EN;
    private static final Language UPDATED_LANGUAGE_CODE = Language.ES;
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private CategoryLangRepository categoryLangRepository;

    @Inject
    private CategoryLangService categoryLangService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCategoryLangMockMvc;

    private CategoryLang categoryLang;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryLangResource categoryLangResource = new CategoryLangResource();
        ReflectionTestUtils.setField(categoryLangResource, "categoryLangService", categoryLangService);
        this.restCategoryLangMockMvc = MockMvcBuilders.standaloneSetup(categoryLangResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        categoryLang = new CategoryLang();
        categoryLang.setLanguageCode(DEFAULT_LANGUAGE_CODE);
        categoryLang.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCategoryLang() throws Exception {
        int databaseSizeBeforeCreate = categoryLangRepository.findAll().size();

        // Create the CategoryLang

        restCategoryLangMockMvc.perform(post("/api/category-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryLang)))
                .andExpect(status().isCreated());

        // Validate the CategoryLang in the database
        List<CategoryLang> categoryLangs = categoryLangRepository.findAll();
        assertThat(categoryLangs).hasSize(databaseSizeBeforeCreate + 1);
        CategoryLang testCategoryLang = categoryLangs.get(categoryLangs.size() - 1);
        assertThat(testCategoryLang.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
        assertThat(testCategoryLang.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkLanguageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryLangRepository.findAll().size();
        // set the field null
        categoryLang.setLanguageCode(null);

        // Create the CategoryLang, which fails.

        restCategoryLangMockMvc.perform(post("/api/category-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryLang)))
                .andExpect(status().isBadRequest());

        List<CategoryLang> categoryLangs = categoryLangRepository.findAll();
        assertThat(categoryLangs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryLangRepository.findAll().size();
        // set the field null
        categoryLang.setValue(null);

        // Create the CategoryLang, which fails.

        restCategoryLangMockMvc.perform(post("/api/category-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(categoryLang)))
                .andExpect(status().isBadRequest());

        List<CategoryLang> categoryLangs = categoryLangRepository.findAll();
        assertThat(categoryLangs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryLangs() throws Exception {
        // Initialize the database
        categoryLangRepository.saveAndFlush(categoryLang);

        // Get all the categoryLangs
        restCategoryLangMockMvc.perform(get("/api/category-langs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(categoryLang.getId().intValue())))
                .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCategoryLang() throws Exception {
        // Initialize the database
        categoryLangRepository.saveAndFlush(categoryLang);

        // Get the categoryLang
        restCategoryLangMockMvc.perform(get("/api/category-langs/{id}", categoryLang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(categoryLang.getId().intValue()))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryLang() throws Exception {
        // Get the categoryLang
        restCategoryLangMockMvc.perform(get("/api/category-langs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryLang() throws Exception {
        // Initialize the database
        categoryLangService.save(categoryLang);

        int databaseSizeBeforeUpdate = categoryLangRepository.findAll().size();

        // Update the categoryLang
        CategoryLang updatedCategoryLang = new CategoryLang();
        updatedCategoryLang.setId(categoryLang.getId());
        updatedCategoryLang.setLanguageCode(UPDATED_LANGUAGE_CODE);
        updatedCategoryLang.setValue(UPDATED_VALUE);

        restCategoryLangMockMvc.perform(put("/api/category-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCategoryLang)))
                .andExpect(status().isOk());

        // Validate the CategoryLang in the database
        List<CategoryLang> categoryLangs = categoryLangRepository.findAll();
        assertThat(categoryLangs).hasSize(databaseSizeBeforeUpdate);
        CategoryLang testCategoryLang = categoryLangs.get(categoryLangs.size() - 1);
        assertThat(testCategoryLang.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testCategoryLang.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCategoryLang() throws Exception {
        // Initialize the database
        categoryLangService.save(categoryLang);

        int databaseSizeBeforeDelete = categoryLangRepository.findAll().size();

        // Get the categoryLang
        restCategoryLangMockMvc.perform(delete("/api/category-langs/{id}", categoryLang.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoryLang> categoryLangs = categoryLangRepository.findAll();
        assertThat(categoryLangs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
