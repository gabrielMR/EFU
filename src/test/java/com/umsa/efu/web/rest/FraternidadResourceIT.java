package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Fraternidad;
import com.umsa.efu.repository.FraternidadRepository;
import com.umsa.efu.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.umsa.efu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FraternidadResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class FraternidadResourceIT {

    private static final Integer DEFAULT_ID_FRATERNIDAD = 1;
    private static final Integer UPDATED_ID_FRATERNIDAD = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DANZA = "AAAAAAAAAA";
    private static final String UPDATED_DANZA = "BBBBBBBBBB";

    private static final String DEFAULT_INSTANCIA = "AAAAAAAAAA";
    private static final String UPDATED_INSTANCIA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FUNDACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FUNDACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTADO = 1;
    private static final Integer UPDATED_ESTADO = 2;

    @Autowired
    private FraternidadRepository fraternidadRepository;

    @Mock
    private FraternidadRepository fraternidadRepositoryMock;

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

    private MockMvc restFraternidadMockMvc;

    private Fraternidad fraternidad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FraternidadResource fraternidadResource = new FraternidadResource(fraternidadRepository);
        this.restFraternidadMockMvc = MockMvcBuilders.standaloneSetup(fraternidadResource)
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
    public static Fraternidad createEntity(EntityManager em) {
        Fraternidad fraternidad = new Fraternidad()
            .idFraternidad(DEFAULT_ID_FRATERNIDAD)
            .nombre(DEFAULT_NOMBRE)
            .danza(DEFAULT_DANZA)
            .instancia(DEFAULT_INSTANCIA)
            .fundacion(DEFAULT_FUNDACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .estado(DEFAULT_ESTADO);
        return fraternidad;
    }

    @BeforeEach
    public void initTest() {
        fraternidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createFraternidad() throws Exception {
        int databaseSizeBeforeCreate = fraternidadRepository.findAll().size();

        // Create the Fraternidad
        restFraternidadMockMvc.perform(post("/api/fraternidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraternidad)))
            .andExpect(status().isCreated());

        // Validate the Fraternidad in the database
        List<Fraternidad> fraternidadList = fraternidadRepository.findAll();
        assertThat(fraternidadList).hasSize(databaseSizeBeforeCreate + 1);
        Fraternidad testFraternidad = fraternidadList.get(fraternidadList.size() - 1);
        assertThat(testFraternidad.getIdFraternidad()).isEqualTo(DEFAULT_ID_FRATERNIDAD);
        assertThat(testFraternidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testFraternidad.getDanza()).isEqualTo(DEFAULT_DANZA);
        assertThat(testFraternidad.getInstancia()).isEqualTo(DEFAULT_INSTANCIA);
        assertThat(testFraternidad.getFundacion()).isEqualTo(DEFAULT_FUNDACION);
        assertThat(testFraternidad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testFraternidad.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createFraternidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fraternidadRepository.findAll().size();

        // Create the Fraternidad with an existing ID
        fraternidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFraternidadMockMvc.perform(post("/api/fraternidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraternidad)))
            .andExpect(status().isBadRequest());

        // Validate the Fraternidad in the database
        List<Fraternidad> fraternidadList = fraternidadRepository.findAll();
        assertThat(fraternidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFraternidads() throws Exception {
        // Initialize the database
        fraternidadRepository.saveAndFlush(fraternidad);

        // Get all the fraternidadList
        restFraternidadMockMvc.perform(get("/api/fraternidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fraternidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFraternidad").value(hasItem(DEFAULT_ID_FRATERNIDAD)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].danza").value(hasItem(DEFAULT_DANZA.toString())))
            .andExpect(jsonPath("$.[*].instancia").value(hasItem(DEFAULT_INSTANCIA.toString())))
            .andExpect(jsonPath("$.[*].fundacion").value(hasItem(DEFAULT_FUNDACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFraternidadsWithEagerRelationshipsIsEnabled() throws Exception {
        FraternidadResource fraternidadResource = new FraternidadResource(fraternidadRepositoryMock);
        when(fraternidadRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFraternidadMockMvc = MockMvcBuilders.standaloneSetup(fraternidadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFraternidadMockMvc.perform(get("/api/fraternidads?eagerload=true"))
        .andExpect(status().isOk());

        verify(fraternidadRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFraternidadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        FraternidadResource fraternidadResource = new FraternidadResource(fraternidadRepositoryMock);
            when(fraternidadRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFraternidadMockMvc = MockMvcBuilders.standaloneSetup(fraternidadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFraternidadMockMvc.perform(get("/api/fraternidads?eagerload=true"))
        .andExpect(status().isOk());

            verify(fraternidadRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFraternidad() throws Exception {
        // Initialize the database
        fraternidadRepository.saveAndFlush(fraternidad);

        // Get the fraternidad
        restFraternidadMockMvc.perform(get("/api/fraternidads/{id}", fraternidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fraternidad.getId().intValue()))
            .andExpect(jsonPath("$.idFraternidad").value(DEFAULT_ID_FRATERNIDAD))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.danza").value(DEFAULT_DANZA.toString()))
            .andExpect(jsonPath("$.instancia").value(DEFAULT_INSTANCIA.toString()))
            .andExpect(jsonPath("$.fundacion").value(DEFAULT_FUNDACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    public void getNonExistingFraternidad() throws Exception {
        // Get the fraternidad
        restFraternidadMockMvc.perform(get("/api/fraternidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFraternidad() throws Exception {
        // Initialize the database
        fraternidadRepository.saveAndFlush(fraternidad);

        int databaseSizeBeforeUpdate = fraternidadRepository.findAll().size();

        // Update the fraternidad
        Fraternidad updatedFraternidad = fraternidadRepository.findById(fraternidad.getId()).get();
        // Disconnect from session so that the updates on updatedFraternidad are not directly saved in db
        em.detach(updatedFraternidad);
        updatedFraternidad
            .idFraternidad(UPDATED_ID_FRATERNIDAD)
            .nombre(UPDATED_NOMBRE)
            .danza(UPDATED_DANZA)
            .instancia(UPDATED_INSTANCIA)
            .fundacion(UPDATED_FUNDACION)
            .descripcion(UPDATED_DESCRIPCION)
            .estado(UPDATED_ESTADO);

        restFraternidadMockMvc.perform(put("/api/fraternidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFraternidad)))
            .andExpect(status().isOk());

        // Validate the Fraternidad in the database
        List<Fraternidad> fraternidadList = fraternidadRepository.findAll();
        assertThat(fraternidadList).hasSize(databaseSizeBeforeUpdate);
        Fraternidad testFraternidad = fraternidadList.get(fraternidadList.size() - 1);
        assertThat(testFraternidad.getIdFraternidad()).isEqualTo(UPDATED_ID_FRATERNIDAD);
        assertThat(testFraternidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testFraternidad.getDanza()).isEqualTo(UPDATED_DANZA);
        assertThat(testFraternidad.getInstancia()).isEqualTo(UPDATED_INSTANCIA);
        assertThat(testFraternidad.getFundacion()).isEqualTo(UPDATED_FUNDACION);
        assertThat(testFraternidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testFraternidad.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingFraternidad() throws Exception {
        int databaseSizeBeforeUpdate = fraternidadRepository.findAll().size();

        // Create the Fraternidad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFraternidadMockMvc.perform(put("/api/fraternidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fraternidad)))
            .andExpect(status().isBadRequest());

        // Validate the Fraternidad in the database
        List<Fraternidad> fraternidadList = fraternidadRepository.findAll();
        assertThat(fraternidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFraternidad() throws Exception {
        // Initialize the database
        fraternidadRepository.saveAndFlush(fraternidad);

        int databaseSizeBeforeDelete = fraternidadRepository.findAll().size();

        // Delete the fraternidad
        restFraternidadMockMvc.perform(delete("/api/fraternidads/{id}", fraternidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Fraternidad> fraternidadList = fraternidadRepository.findAll();
        assertThat(fraternidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fraternidad.class);
        Fraternidad fraternidad1 = new Fraternidad();
        fraternidad1.setId(1L);
        Fraternidad fraternidad2 = new Fraternidad();
        fraternidad2.setId(fraternidad1.getId());
        assertThat(fraternidad1).isEqualTo(fraternidad2);
        fraternidad2.setId(2L);
        assertThat(fraternidad1).isNotEqualTo(fraternidad2);
        fraternidad1.setId(null);
        assertThat(fraternidad1).isNotEqualTo(fraternidad2);
    }
}
