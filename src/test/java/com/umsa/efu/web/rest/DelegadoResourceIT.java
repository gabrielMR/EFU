package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Delegado;
import com.umsa.efu.repository.DelegadoRepository;
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
 * Integration tests for the {@Link DelegadoResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class DelegadoResourceIT {

    private static final Integer DEFAULT_ID_DELEGADO = 1;
    private static final Integer UPDATED_ID_DELEGADO = 2;

    private static final Integer DEFAULT_CI = 1;
    private static final Integer UPDATED_CI = 2;

    private static final String DEFAULT_NOMBRE_DELEGADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DELEGADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_RU = 1;
    private static final Integer UPDATED_RU = 2;

    private static final Integer DEFAULT_ITEM = 1;
    private static final Integer UPDATED_ITEM = 2;

    private static final Long DEFAULT_TELEFONO = 1L;
    private static final Long UPDATED_TELEFONO = 2L;

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    @Autowired
    private DelegadoRepository delegadoRepository;

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

    private MockMvc restDelegadoMockMvc;

    private Delegado delegado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DelegadoResource delegadoResource = new DelegadoResource(delegadoRepository);
        this.restDelegadoMockMvc = MockMvcBuilders.standaloneSetup(delegadoResource)
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
    public static Delegado createEntity(EntityManager em) {
        Delegado delegado = new Delegado()
            .idDelegado(DEFAULT_ID_DELEGADO)
            .ci(DEFAULT_CI)
            .nombreDelegado(DEFAULT_NOMBRE_DELEGADO)
            .ru(DEFAULT_RU)
            .item(DEFAULT_ITEM)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO);
        return delegado;
    }

    @BeforeEach
    public void initTest() {
        delegado = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelegado() throws Exception {
        int databaseSizeBeforeCreate = delegadoRepository.findAll().size();

        // Create the Delegado
        restDelegadoMockMvc.perform(post("/api/delegados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegado)))
            .andExpect(status().isCreated());

        // Validate the Delegado in the database
        List<Delegado> delegadoList = delegadoRepository.findAll();
        assertThat(delegadoList).hasSize(databaseSizeBeforeCreate + 1);
        Delegado testDelegado = delegadoList.get(delegadoList.size() - 1);
        assertThat(testDelegado.getIdDelegado()).isEqualTo(DEFAULT_ID_DELEGADO);
        assertThat(testDelegado.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testDelegado.getNombreDelegado()).isEqualTo(DEFAULT_NOMBRE_DELEGADO);
        assertThat(testDelegado.getRu()).isEqualTo(DEFAULT_RU);
        assertThat(testDelegado.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testDelegado.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testDelegado.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    public void createDelegadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = delegadoRepository.findAll().size();

        // Create the Delegado with an existing ID
        delegado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDelegadoMockMvc.perform(post("/api/delegados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegado)))
            .andExpect(status().isBadRequest());

        // Validate the Delegado in the database
        List<Delegado> delegadoList = delegadoRepository.findAll();
        assertThat(delegadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDelegados() throws Exception {
        // Initialize the database
        delegadoRepository.saveAndFlush(delegado);

        // Get all the delegadoList
        restDelegadoMockMvc.perform(get("/api/delegados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delegado.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDelegado").value(hasItem(DEFAULT_ID_DELEGADO)))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].nombreDelegado").value(hasItem(DEFAULT_NOMBRE_DELEGADO.toString())))
            .andExpect(jsonPath("$.[*].ru").value(hasItem(DEFAULT_RU)))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.intValue())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())));
    }
    
    @Test
    @Transactional
    public void getDelegado() throws Exception {
        // Initialize the database
        delegadoRepository.saveAndFlush(delegado);

        // Get the delegado
        restDelegadoMockMvc.perform(get("/api/delegados/{id}", delegado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delegado.getId().intValue()))
            .andExpect(jsonPath("$.idDelegado").value(DEFAULT_ID_DELEGADO))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.nombreDelegado").value(DEFAULT_NOMBRE_DELEGADO.toString()))
            .andExpect(jsonPath("$.ru").value(DEFAULT_RU))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.intValue()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDelegado() throws Exception {
        // Get the delegado
        restDelegadoMockMvc.perform(get("/api/delegados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelegado() throws Exception {
        // Initialize the database
        delegadoRepository.saveAndFlush(delegado);

        int databaseSizeBeforeUpdate = delegadoRepository.findAll().size();

        // Update the delegado
        Delegado updatedDelegado = delegadoRepository.findById(delegado.getId()).get();
        // Disconnect from session so that the updates on updatedDelegado are not directly saved in db
        em.detach(updatedDelegado);
        updatedDelegado
            .idDelegado(UPDATED_ID_DELEGADO)
            .ci(UPDATED_CI)
            .nombreDelegado(UPDATED_NOMBRE_DELEGADO)
            .ru(UPDATED_RU)
            .item(UPDATED_ITEM)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);

        restDelegadoMockMvc.perform(put("/api/delegados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDelegado)))
            .andExpect(status().isOk());

        // Validate the Delegado in the database
        List<Delegado> delegadoList = delegadoRepository.findAll();
        assertThat(delegadoList).hasSize(databaseSizeBeforeUpdate);
        Delegado testDelegado = delegadoList.get(delegadoList.size() - 1);
        assertThat(testDelegado.getIdDelegado()).isEqualTo(UPDATED_ID_DELEGADO);
        assertThat(testDelegado.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testDelegado.getNombreDelegado()).isEqualTo(UPDATED_NOMBRE_DELEGADO);
        assertThat(testDelegado.getRu()).isEqualTo(UPDATED_RU);
        assertThat(testDelegado.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testDelegado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testDelegado.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void updateNonExistingDelegado() throws Exception {
        int databaseSizeBeforeUpdate = delegadoRepository.findAll().size();

        // Create the Delegado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDelegadoMockMvc.perform(put("/api/delegados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delegado)))
            .andExpect(status().isBadRequest());

        // Validate the Delegado in the database
        List<Delegado> delegadoList = delegadoRepository.findAll();
        assertThat(delegadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDelegado() throws Exception {
        // Initialize the database
        delegadoRepository.saveAndFlush(delegado);

        int databaseSizeBeforeDelete = delegadoRepository.findAll().size();

        // Delete the delegado
        restDelegadoMockMvc.perform(delete("/api/delegados/{id}", delegado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Delegado> delegadoList = delegadoRepository.findAll();
        assertThat(delegadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delegado.class);
        Delegado delegado1 = new Delegado();
        delegado1.setId(1L);
        Delegado delegado2 = new Delegado();
        delegado2.setId(delegado1.getId());
        assertThat(delegado1).isEqualTo(delegado2);
        delegado2.setId(2L);
        assertThat(delegado1).isNotEqualTo(delegado2);
        delegado1.setId(null);
        assertThat(delegado1).isNotEqualTo(delegado2);
    }
}
