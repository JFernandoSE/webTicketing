package ec.com.se.web.rest;

import ec.com.se.DemoApp;
import ec.com.se.domain.ActionLang;
import ec.com.se.repository.ActionLangRepository;
import ec.com.se.service.ActionLangService;

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
 * Test class for the ActionLangResource REST controller.
 *
 * @see ActionLangResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApp.class)
@WebAppConfiguration
@IntegrationTest
public class ActionLangResourceIntTest {


    private static final Language DEFAULT_LANGUAGE_CODE = Language.EN;
    private static final Language UPDATED_LANGUAGE_CODE = Language.ES;
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private ActionLangRepository actionLangRepository;

    @Inject
    private ActionLangService actionLangService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restActionLangMockMvc;

    private ActionLang actionLang;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActionLangResource actionLangResource = new ActionLangResource();
        ReflectionTestUtils.setField(actionLangResource, "actionLangService", actionLangService);
        this.restActionLangMockMvc = MockMvcBuilders.standaloneSetup(actionLangResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        actionLang = new ActionLang();
        actionLang.setLanguageCode(DEFAULT_LANGUAGE_CODE);
        actionLang.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createActionLang() throws Exception {
        int databaseSizeBeforeCreate = actionLangRepository.findAll().size();

        // Create the ActionLang

        restActionLangMockMvc.perform(post("/api/action-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionLang)))
                .andExpect(status().isCreated());

        // Validate the ActionLang in the database
        List<ActionLang> actionLangs = actionLangRepository.findAll();
        assertThat(actionLangs).hasSize(databaseSizeBeforeCreate + 1);
        ActionLang testActionLang = actionLangs.get(actionLangs.size() - 1);
        assertThat(testActionLang.getLanguageCode()).isEqualTo(DEFAULT_LANGUAGE_CODE);
        assertThat(testActionLang.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkLanguageCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionLangRepository.findAll().size();
        // set the field null
        actionLang.setLanguageCode(null);

        // Create the ActionLang, which fails.

        restActionLangMockMvc.perform(post("/api/action-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionLang)))
                .andExpect(status().isBadRequest());

        List<ActionLang> actionLangs = actionLangRepository.findAll();
        assertThat(actionLangs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionLangRepository.findAll().size();
        // set the field null
        actionLang.setValue(null);

        // Create the ActionLang, which fails.

        restActionLangMockMvc.perform(post("/api/action-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(actionLang)))
                .andExpect(status().isBadRequest());

        List<ActionLang> actionLangs = actionLangRepository.findAll();
        assertThat(actionLangs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActionLangs() throws Exception {
        // Initialize the database
        actionLangRepository.saveAndFlush(actionLang);

        // Get all the actionLangs
        restActionLangMockMvc.perform(get("/api/action-langs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(actionLang.getId().intValue())))
                .andExpect(jsonPath("$.[*].languageCode").value(hasItem(DEFAULT_LANGUAGE_CODE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getActionLang() throws Exception {
        // Initialize the database
        actionLangRepository.saveAndFlush(actionLang);

        // Get the actionLang
        restActionLangMockMvc.perform(get("/api/action-langs/{id}", actionLang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(actionLang.getId().intValue()))
            .andExpect(jsonPath("$.languageCode").value(DEFAULT_LANGUAGE_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActionLang() throws Exception {
        // Get the actionLang
        restActionLangMockMvc.perform(get("/api/action-langs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActionLang() throws Exception {
        // Initialize the database
        actionLangService.save(actionLang);

        int databaseSizeBeforeUpdate = actionLangRepository.findAll().size();

        // Update the actionLang
        ActionLang updatedActionLang = new ActionLang();
        updatedActionLang.setId(actionLang.getId());
        updatedActionLang.setLanguageCode(UPDATED_LANGUAGE_CODE);
        updatedActionLang.setValue(UPDATED_VALUE);

        restActionLangMockMvc.perform(put("/api/action-langs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedActionLang)))
                .andExpect(status().isOk());

        // Validate the ActionLang in the database
        List<ActionLang> actionLangs = actionLangRepository.findAll();
        assertThat(actionLangs).hasSize(databaseSizeBeforeUpdate);
        ActionLang testActionLang = actionLangs.get(actionLangs.size() - 1);
        assertThat(testActionLang.getLanguageCode()).isEqualTo(UPDATED_LANGUAGE_CODE);
        assertThat(testActionLang.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteActionLang() throws Exception {
        // Initialize the database
        actionLangService.save(actionLang);

        int databaseSizeBeforeDelete = actionLangRepository.findAll().size();

        // Get the actionLang
        restActionLangMockMvc.perform(delete("/api/action-langs/{id}", actionLang.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ActionLang> actionLangs = actionLangRepository.findAll();
        assertThat(actionLangs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
