package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Niusta;
import com.umsa.efu.repository.NiustaRepository;
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
 * Integration tests for the {@Link NiustaResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class NiustaResourceIT {

    private static final Integer DEFAULT_CI = 1;
    private static final Integer UPDATED_CI = 2;

    private static final String DEFAULT_NOMBRE_NIUSTA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_NIUSTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_RU = 1;
    private static final Integer UPDATED_RU = 2;

    private static final Integer DEFAULT_ITEM = 1;
    private static final Integer UPDATED_ITEM = 2;

    private static final Integer DEFAULT_EDAD = 1;
    private static final Integer UPDATED_EDAD = 2;

    private static final String DEFAULT_GUSTOS = "AAAAAAAAAA";
    private static final String UPDATED_GUSTOS = "BBBBBBBBBB";

    private static final Float DEFAULT_ESTATURA = 1F;
    private static final Float UPDATED_ESTATURA = 2F;

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private NiustaRepository niustaRepository;

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

    private MockMvc restNiustaMockMvc;

    private Niusta niusta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NiustaResource niustaResource = new NiustaResource(niustaRepository);
        this.restNiustaMockMvc = MockMvcBuilders.standaloneSetup(niustaResource)
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
    public static Niusta createEntity(EntityManager em) {
        Niusta niusta = new Niusta()
            .ci(DEFAULT_CI)
            .nombreNiusta(DEFAULT_NOMBRE_NIUSTA)
            .ru(DEFAULT_RU)
            .item(DEFAULT_ITEM)
            .edad(DEFAULT_EDAD)
            .gustos(DEFAULT_GUSTOS)
            .estatura(DEFAULT_ESTATURA)
            .titulo(DEFAULT_TITULO);
        return niusta;
    }

    @BeforeEach
    public void initTest() {
        niusta = createEntity(em);
    }

    @Test
    @Transactional
    public void createNiusta() throws Exception {
        int databaseSizeBeforeCreate = niustaRepository.findAll().size();

        // Create the Niusta
        restNiustaMockMvc.perform(post("/api/niustas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niusta)))
            .andExpect(status().isCreated());

        // Validate the Niusta in the database
        List<Niusta> niustaList = niustaRepository.findAll();
        assertThat(niustaList).hasSize(databaseSizeBeforeCreate + 1);
        Niusta testNiusta = niustaList.get(niustaList.size() - 1);
        assertThat(testNiusta.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testNiusta.getNombreNiusta()).isEqualTo(DEFAULT_NOMBRE_NIUSTA);
        assertThat(testNiusta.getRu()).isEqualTo(DEFAULT_RU);
        assertThat(testNiusta.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testNiusta.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testNiusta.getGustos()).isEqualTo(DEFAULT_GUSTOS);
        assertThat(testNiusta.getEstatura()).isEqualTo(DEFAULT_ESTATURA);
        assertThat(testNiusta.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createNiustaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = niustaRepository.findAll().size();

        // Create the Niusta with an existing ID
        niusta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiustaMockMvc.perform(post("/api/niustas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niusta)))
            .andExpect(status().isBadRequest());

        // Validate the Niusta in the database
        List<Niusta> niustaList = niustaRepository.findAll();
        assertThat(niustaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNiustas() throws Exception {
        // Initialize the database
        niustaRepository.saveAndFlush(niusta);

        // Get all the niustaList
        restNiustaMockMvc.perform(get("/api/niustas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niusta.getId().intValue())))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].nombreNiusta").value(hasItem(DEFAULT_NOMBRE_NIUSTA.toString())))
            .andExpect(jsonPath("$.[*].ru").value(hasItem(DEFAULT_RU)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD)))
            .andExpect(jsonPath("$.[*].gustos").value(hasItem(DEFAULT_GUSTOS.toString())))
            .andExpect(jsonPath("$.[*].estatura").value(hasItem(DEFAULT_ESTATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())));
    }
    
    @Test
    @Transactional
    public void getNiusta() throws Exception {
        // Initialize the database
        niustaRepository.saveAndFlush(niusta);

        // Get the niusta
        restNiustaMockMvc.perform(get("/api/niustas/{id}", niusta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(niusta.getId().intValue()))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.nombreNiusta").value(DEFAULT_NOMBRE_NIUSTA.toString()))
            .andExpect(jsonPath("$.ru").value(DEFAULT_RU))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD))
            .andExpect(jsonPath("$.gustos").value(DEFAULT_GUSTOS.toString()))
            .andExpect(jsonPath("$.estatura").value(DEFAULT_ESTATURA.doubleValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNiusta() throws Exception {
        // Get the niusta
        restNiustaMockMvc.perform(get("/api/niustas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNiusta() throws Exception {
        // Initialize the database
        niustaRepository.saveAndFlush(niusta);

        int databaseSizeBeforeUpdate = niustaRepository.findAll().size();

        // Update the niusta
        Niusta updatedNiusta = niustaRepository.findById(niusta.getId()).get();
        // Disconnect from session so that the updates on updatedNiusta are not directly saved in db
        em.detach(updatedNiusta);
        updatedNiusta
            .ci(UPDATED_CI)
            .nombreNiusta(UPDATED_NOMBRE_NIUSTA)
            .ru(UPDATED_RU)
            .item(UPDATED_ITEM)
            .edad(UPDATED_EDAD)
            .gustos(UPDATED_GUSTOS)
            .estatura(UPDATED_ESTATURA)
            .titulo(UPDATED_TITULO);

        restNiustaMockMvc.perform(put("/api/niustas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNiusta)))
            .andExpect(status().isOk());

        // Validate the Niusta in the database
        List<Niusta> niustaList = niustaRepository.findAll();
        assertThat(niustaList).hasSize(databaseSizeBeforeUpdate);
        Niusta testNiusta = niustaList.get(niustaList.size() - 1);
        assertThat(testNiusta.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testNiusta.getNombreNiusta()).isEqualTo(UPDATED_NOMBRE_NIUSTA);
        assertThat(testNiusta.getRu()).isEqualTo(UPDATED_RU);
        assertThat(testNiusta.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testNiusta.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testNiusta.getGustos()).isEqualTo(UPDATED_GUSTOS);
        assertThat(testNiusta.getEstatura()).isEqualTo(UPDATED_ESTATURA);
        assertThat(testNiusta.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingNiusta() throws Exception {
        int databaseSizeBeforeUpdate = niustaRepository.findAll().size();

        // Create the Niusta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiustaMockMvc.perform(put("/api/niustas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(niusta)))
            .andExpect(status().isBadRequest());

        // Validate the Niusta in the database
        List<Niusta> niustaList = niustaRepository.findAll();
        assertThat(niustaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNiusta() throws Exception {
        // Initialize the database
        niustaRepository.saveAndFlush(niusta);

        int databaseSizeBeforeDelete = niustaRepository.findAll().size();

        // Delete the niusta
        restNiustaMockMvc.perform(delete("/api/niustas/{id}", niusta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Niusta> niustaList = niustaRepository.findAll();
        assertThat(niustaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Niusta.class);
        Niusta niusta1 = new Niusta();
        niusta1.setId(1L);
        Niusta niusta2 = new Niusta();
        niusta2.setId(niusta1.getId());
        assertThat(niusta1).isEqualTo(niusta2);
        niusta2.setId(2L);
        assertThat(niusta1).isNotEqualTo(niusta2);
        niusta1.setId(null);
        assertThat(niusta1).isNotEqualTo(niusta2);
    }
}
