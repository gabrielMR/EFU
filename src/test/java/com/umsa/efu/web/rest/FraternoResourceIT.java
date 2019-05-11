package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Fraterno;
import com.umsa.efu.repository.FraternoRepository;
import com.umsa.efu.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.umsa.efu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FraternoResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class FraternoResourceIT {

    private static final Integer DEFAULT_CI = 1;
    private static final Integer UPDATED_CI = 2;

    private static final String DEFAULT_NOMBRE_FRATERNO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_FRATERNO = "BBBBBBBBBB";

    private static final Integer DEFAULT_RU = 1;
    private static final Integer UPDATED_RU = 2;

    private static final Integer DEFAULT_ITEM = 1;
    private static final Integer UPDATED_ITEM = 2;

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private FraternoRepository fraternoRepository;

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

    private MockMvc restFraternoMockMvc;

    private Fraterno fraterno;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FraternoResource fraternoResource = new FraternoResource(fraternoRepository);
        this.restFraternoMockMvc = MockMvcBuilders.standaloneSetup(fraternoResource)
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
    public static Fraterno createEntity(EntityManager em) {
        Fraterno fraterno = new Fraterno()
            .ci(DEFAULT_CI)
            .nombreFraterno(DEFAULT_NOMBRE_FRATERNO)
            .ru(DEFAULT_RU)
            .item(DEFAULT_ITEM)
            .observaciones(DEFAULT_OBSERVACIONES);
        return fraterno;
    }

    @BeforeEach
    public void initTest() {
        fraterno = createEntity(em);
    }

    @Test
    @Transactional
    public void createFraterno() throws Exception {
        int databaseSizeBeforeCreate = fraternoRepository.findAll().size();

        // Create the Fraterno
        restFraternoMockMvc.perform(post("/api/fraternos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraterno)))
            .andExpect(status().isCreated());

        // Validate the Fraterno in the database
        List<Fraterno> fraternoList = fraternoRepository.findAll();
        assertThat(fraternoList).hasSize(databaseSizeBeforeCreate + 1);
        Fraterno testFraterno = fraternoList.get(fraternoList.size() - 1);
        assertThat(testFraterno.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testFraterno.getNombreFraterno()).isEqualTo(DEFAULT_NOMBRE_FRATERNO);
        assertThat(testFraterno.getRu()).isEqualTo(DEFAULT_RU);
        assertThat(testFraterno.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testFraterno.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createFraternoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fraternoRepository.findAll().size();

        // Create the Fraterno with an existing ID
        fraterno.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraternoMockMvc.perform(post("/api/fraternos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraterno)))
            .andExpect(status().isBadRequest());

        // Validate the Fraterno in the database
        List<Fraterno> fraternoList = fraternoRepository.findAll();
        assertThat(fraternoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFraternos() throws Exception {
        // Initialize the database
        fraternoRepository.saveAndFlush(fraterno);

        // Get all the fraternoList
        restFraternoMockMvc.perform(get("/api/fraternos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].nombreFraterno").value(hasItem(DEFAULT_NOMBRE_FRATERNO.toString())))
            .andExpect(jsonPath("$.[*].ru").value(hasItem(DEFAULT_RU)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())));
    }
    
    @Test
    @Transactional
    public void getFraterno() throws Exception {
        // Initialize the database
        fraternoRepository.saveAndFlush(fraterno);

        // Get the fraterno
        restFraternoMockMvc.perform(get("/api/fraternos/{id}", fraterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fraterno.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.nombreFraterno").value(DEFAULT_NOMBRE_FRATERNO.toString()))
            .andExpect(jsonPath("$.ru").value(DEFAULT_RU))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFraterno() throws Exception {
        // Get the fraterno
        restFraternoMockMvc.perform(get("/api/fraternos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFraterno() throws Exception {
        // Initialize the database
        fraternoRepository.saveAndFlush(fraterno);

        int databaseSizeBeforeUpdate = fraternoRepository.findAll().size();

        // Update the fraterno
        Fraterno updatedFraterno = fraternoRepository.findById(fraterno.getId()).get();
        // Disconnect from session so that the updates on updatedFraterno are not directly saved in db
        em.detach(updatedFraterno);
        updatedFraterno
            .ci(UPDATED_CI)
            .nombreFraterno(UPDATED_NOMBRE_FRATERNO)
            .ru(UPDATED_RU)
            .item(UPDATED_ITEM)
            .observaciones(UPDATED_OBSERVACIONES);

        restFraternoMockMvc.perform(put("/api/fraternos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFraterno)))
            .andExpect(status().isOk());

        // Validate the Fraterno in the database
        List<Fraterno> fraternoList = fraternoRepository.findAll();
        assertThat(fraternoList).hasSize(databaseSizeBeforeUpdate);
        Fraterno testFraterno = fraternoList.get(fraternoList.size() - 1);
        assertThat(testFraterno.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testFraterno.getNombreFraterno()).isEqualTo(UPDATED_NOMBRE_FRATERNO);
        assertThat(testFraterno.getRu()).isEqualTo(UPDATED_RU);
        assertThat(testFraterno.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testFraterno.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingFraterno() throws Exception {
        int databaseSizeBeforeUpdate = fraternoRepository.findAll().size();

        // Create the Fraterno

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraternoMockMvc.perform(put("/api/fraternos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraterno)))
            .andExpect(status().isBadRequest());

        // Validate the Fraterno in the database
        List<Fraterno> fraternoList = fraternoRepository.findAll();
        assertThat(fraternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFraterno() throws Exception {
        // Initialize the database
        fraternoRepository.saveAndFlush(fraterno);

        int databaseSizeBeforeDelete = fraternoRepository.findAll().size();

        // Delete the fraterno
        restFraternoMockMvc.perform(delete("/api/fraternos/{id}", fraterno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Fraterno> fraternoList = fraternoRepository.findAll();
        assertThat(fraternoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fraterno.class);
        Fraterno fraterno1 = new Fraterno();
        fraterno1.setId(1L);
        Fraterno fraterno2 = new Fraterno();
        fraterno2.setId(fraterno1.getId());
        assertThat(fraterno1).isEqualTo(fraterno2);
        fraterno2.setId(2L);
        assertThat(fraterno1).isNotEqualTo(fraterno2);
        fraterno1.setId(null);
        assertThat(fraterno1).isNotEqualTo(fraterno2);
    }
}
