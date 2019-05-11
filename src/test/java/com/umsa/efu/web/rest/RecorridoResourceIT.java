package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Recorrido;
import com.umsa.efu.repository.RecorridoRepository;
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
 * Integration tests for the {@Link RecorridoResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class RecorridoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_GESTION = 1;
    private static final Integer UPDATED_GESTION = 2;

    @Autowired
    private RecorridoRepository recorridoRepository;

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

    private MockMvc restRecorridoMockMvc;

    private Recorrido recorrido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecorridoResource recorridoResource = new RecorridoResource(recorridoRepository);
        this.restRecorridoMockMvc = MockMvcBuilders.standaloneSetup(recorridoResource)
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
    public static Recorrido createEntity(EntityManager em) {
        Recorrido recorrido = new Recorrido()
            .nombre(DEFAULT_NOMBRE)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .descripcion(DEFAULT_DESCRIPCION)
            .gestion(DEFAULT_GESTION);
        return recorrido;
    }

    @BeforeEach
    public void initTest() {
        recorrido = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecorrido() throws Exception {
        int databaseSizeBeforeCreate = recorridoRepository.findAll().size();

        // Create the Recorrido
        restRecorridoMockMvc.perform(post("/api/recorridos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recorrido)))
            .andExpect(status().isCreated());

        // Validate the Recorrido in the database
        List<Recorrido> recorridoList = recorridoRepository.findAll();
        assertThat(recorridoList).hasSize(databaseSizeBeforeCreate + 1);
        Recorrido testRecorrido = recorridoList.get(recorridoList.size() - 1);
        assertThat(testRecorrido.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRecorrido.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testRecorrido.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testRecorrido.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testRecorrido.getGestion()).isEqualTo(DEFAULT_GESTION);
    }

    @Test
    @Transactional
    public void createRecorridoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recorridoRepository.findAll().size();

        // Create the Recorrido with an existing ID
        recorrido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecorridoMockMvc.perform(post("/api/recorridos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recorrido)))
            .andExpect(status().isBadRequest());

        // Validate the Recorrido in the database
        List<Recorrido> recorridoList = recorridoRepository.findAll();
        assertThat(recorridoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRecorridos() throws Exception {
        // Initialize the database
        recorridoRepository.saveAndFlush(recorrido);

        // Get all the recorridoList
        restRecorridoMockMvc.perform(get("/api/recorridos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recorrido.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)));
    }
    
    @Test
    @Transactional
    public void getRecorrido() throws Exception {
        // Initialize the database
        recorridoRepository.saveAndFlush(recorrido);

        // Get the recorrido
        restRecorridoMockMvc.perform(get("/api/recorridos/{id}", recorrido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recorrido.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION));
    }

    @Test
    @Transactional
    public void getNonExistingRecorrido() throws Exception {
        // Get the recorrido
        restRecorridoMockMvc.perform(get("/api/recorridos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecorrido() throws Exception {
        // Initialize the database
        recorridoRepository.saveAndFlush(recorrido);

        int databaseSizeBeforeUpdate = recorridoRepository.findAll().size();

        // Update the recorrido
        Recorrido updatedRecorrido = recorridoRepository.findById(recorrido.getId()).get();
        // Disconnect from session so that the updates on updatedRecorrido are not directly saved in db
        em.detach(updatedRecorrido);
        updatedRecorrido
            .nombre(UPDATED_NOMBRE)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .descripcion(UPDATED_DESCRIPCION)
            .gestion(UPDATED_GESTION);

        restRecorridoMockMvc.perform(put("/api/recorridos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecorrido)))
            .andExpect(status().isOk());

        // Validate the Recorrido in the database
        List<Recorrido> recorridoList = recorridoRepository.findAll();
        assertThat(recorridoList).hasSize(databaseSizeBeforeUpdate);
        Recorrido testRecorrido = recorridoList.get(recorridoList.size() - 1);
        assertThat(testRecorrido.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRecorrido.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testRecorrido.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testRecorrido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testRecorrido.getGestion()).isEqualTo(UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRecorrido() throws Exception {
        int databaseSizeBeforeUpdate = recorridoRepository.findAll().size();

        // Create the Recorrido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecorridoMockMvc.perform(put("/api/recorridos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recorrido)))
            .andExpect(status().isBadRequest());

        // Validate the Recorrido in the database
        List<Recorrido> recorridoList = recorridoRepository.findAll();
        assertThat(recorridoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecorrido() throws Exception {
        // Initialize the database
        recorridoRepository.saveAndFlush(recorrido);

        int databaseSizeBeforeDelete = recorridoRepository.findAll().size();

        // Delete the recorrido
        restRecorridoMockMvc.perform(delete("/api/recorridos/{id}", recorrido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Recorrido> recorridoList = recorridoRepository.findAll();
        assertThat(recorridoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recorrido.class);
        Recorrido recorrido1 = new Recorrido();
        recorrido1.setId(1L);
        Recorrido recorrido2 = new Recorrido();
        recorrido2.setId(recorrido1.getId());
        assertThat(recorrido1).isEqualTo(recorrido2);
        recorrido2.setId(2L);
        assertThat(recorrido1).isNotEqualTo(recorrido2);
        recorrido1.setId(null);
        assertThat(recorrido1).isNotEqualTo(recorrido2);
    }
}
