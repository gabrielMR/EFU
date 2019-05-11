package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Actividad;
import com.umsa.efu.repository.ActividadRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.umsa.efu.web.rest.TestUtil.sameInstant;
import static com.umsa.efu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ActividadResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class ActividadResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHAINI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHAINI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHAFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHAFIN = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HORAINI = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORAINI = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_HORAFIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORAFIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTADO = 1;
    private static final Integer UPDATED_ESTADO = 2;

    private static final Integer DEFAULT_GESTION = 1;
    private static final Integer UPDATED_GESTION = 2;

    @Autowired
    private ActividadRepository actividadRepository;

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

    private MockMvc restActividadMockMvc;

    private Actividad actividad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActividadResource actividadResource = new ActividadResource(actividadRepository);
        this.restActividadMockMvc = MockMvcBuilders.standaloneSetup(actividadResource)
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
    public static Actividad createEntity(EntityManager em) {
        Actividad actividad = new Actividad()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaini(DEFAULT_FECHAINI)
            .fechafin(DEFAULT_FECHAFIN)
            .horaini(DEFAULT_HORAINI)
            .horafin(DEFAULT_HORAFIN)
            .contenido(DEFAULT_CONTENIDO)
            .estado(DEFAULT_ESTADO)
            .gestion(DEFAULT_GESTION);
        return actividad;
    }

    @BeforeEach
    public void initTest() {
        actividad = createEntity(em);
    }

    @Test
    @Transactional
    public void createActividad() throws Exception {
        int databaseSizeBeforeCreate = actividadRepository.findAll().size();

        // Create the Actividad
        restActividadMockMvc.perform(post("/api/actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividad)))
            .andExpect(status().isCreated());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeCreate + 1);
        Actividad testActividad = actividadList.get(actividadList.size() - 1);
        assertThat(testActividad.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testActividad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testActividad.getFechaini()).isEqualTo(DEFAULT_FECHAINI);
        assertThat(testActividad.getFechafin()).isEqualTo(DEFAULT_FECHAFIN);
        assertThat(testActividad.getHoraini()).isEqualTo(DEFAULT_HORAINI);
        assertThat(testActividad.getHorafin()).isEqualTo(DEFAULT_HORAFIN);
        assertThat(testActividad.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testActividad.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testActividad.getGestion()).isEqualTo(DEFAULT_GESTION);
    }

    @Test
    @Transactional
    public void createActividadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = actividadRepository.findAll().size();

        // Create the Actividad with an existing ID
        actividad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadMockMvc.perform(post("/api/actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividad)))
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActividads() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        // Get all the actividadList
        restActividadMockMvc.perform(get("/api/actividads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividad.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaini").value(hasItem(DEFAULT_FECHAINI.toString())))
            .andExpect(jsonPath("$.[*].fechafin").value(hasItem(DEFAULT_FECHAFIN.toString())))
            .andExpect(jsonPath("$.[*].horaini").value(hasItem(sameInstant(DEFAULT_HORAINI))))
            .andExpect(jsonPath("$.[*].horafin").value(hasItem(sameInstant(DEFAULT_HORAFIN))))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)));
    }
    
    @Test
    @Transactional
    public void getActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        // Get the actividad
        restActividadMockMvc.perform(get("/api/actividads/{id}", actividad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(actividad.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fechaini").value(DEFAULT_FECHAINI.toString()))
            .andExpect(jsonPath("$.fechafin").value(DEFAULT_FECHAFIN.toString()))
            .andExpect(jsonPath("$.horaini").value(sameInstant(DEFAULT_HORAINI)))
            .andExpect(jsonPath("$.horafin").value(sameInstant(DEFAULT_HORAFIN)))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION));
    }

    @Test
    @Transactional
    public void getNonExistingActividad() throws Exception {
        // Get the actividad
        restActividadMockMvc.perform(get("/api/actividads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();

        // Update the actividad
        Actividad updatedActividad = actividadRepository.findById(actividad.getId()).get();
        // Disconnect from session so that the updates on updatedActividad are not directly saved in db
        em.detach(updatedActividad);
        updatedActividad
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaini(UPDATED_FECHAINI)
            .fechafin(UPDATED_FECHAFIN)
            .horaini(UPDATED_HORAINI)
            .horafin(UPDATED_HORAFIN)
            .contenido(UPDATED_CONTENIDO)
            .estado(UPDATED_ESTADO)
            .gestion(UPDATED_GESTION);

        restActividadMockMvc.perform(put("/api/actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActividad)))
            .andExpect(status().isOk());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
        Actividad testActividad = actividadList.get(actividadList.size() - 1);
        assertThat(testActividad.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testActividad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testActividad.getFechaini()).isEqualTo(UPDATED_FECHAINI);
        assertThat(testActividad.getFechafin()).isEqualTo(UPDATED_FECHAFIN);
        assertThat(testActividad.getHoraini()).isEqualTo(UPDATED_HORAINI);
        assertThat(testActividad.getHorafin()).isEqualTo(UPDATED_HORAFIN);
        assertThat(testActividad.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testActividad.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testActividad.getGestion()).isEqualTo(UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void updateNonExistingActividad() throws Exception {
        int databaseSizeBeforeUpdate = actividadRepository.findAll().size();

        // Create the Actividad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadMockMvc.perform(put("/api/actividads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(actividad)))
            .andExpect(status().isBadRequest());

        // Validate the Actividad in the database
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActividad() throws Exception {
        // Initialize the database
        actividadRepository.saveAndFlush(actividad);

        int databaseSizeBeforeDelete = actividadRepository.findAll().size();

        // Delete the actividad
        restActividadMockMvc.perform(delete("/api/actividads/{id}", actividad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Actividad> actividadList = actividadRepository.findAll();
        assertThat(actividadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actividad.class);
        Actividad actividad1 = new Actividad();
        actividad1.setId(1L);
        Actividad actividad2 = new Actividad();
        actividad2.setId(actividad1.getId());
        assertThat(actividad1).isEqualTo(actividad2);
        actividad2.setId(2L);
        assertThat(actividad1).isNotEqualTo(actividad2);
        actividad1.setId(null);
        assertThat(actividad1).isNotEqualTo(actividad2);
    }
}
