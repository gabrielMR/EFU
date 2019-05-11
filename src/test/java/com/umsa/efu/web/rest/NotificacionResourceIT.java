package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Notificacion;
import com.umsa.efu.repository.NotificacionRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.umsa.efu.web.rest.TestUtil.sameInstant;
import static com.umsa.efu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link NotificacionResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class NotificacionResourceIT {

    private static final String DEFAULT_TITULO_NOTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_NOTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTADO = 1;
    private static final Integer UPDATED_ESTADO = 2;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HORA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_ID_FRATERNIDAD = 1;
    private static final Integer UPDATED_ID_FRATERNIDAD = 2;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Mock
    private NotificacionRepository notificacionRepositoryMock;

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

    private MockMvc restNotificacionMockMvc;

    private Notificacion notificacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotificacionResource notificacionResource = new NotificacionResource(notificacionRepository);
        this.restNotificacionMockMvc = MockMvcBuilders.standaloneSetup(notificacionResource)
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
    public static Notificacion createEntity(EntityManager em) {
        Notificacion notificacion = new Notificacion()
            .tituloNotificacion(DEFAULT_TITULO_NOTIFICACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .contenido(DEFAULT_CONTENIDO)
            .estado(DEFAULT_ESTADO)
            .fecha(DEFAULT_FECHA)
            .hora(DEFAULT_HORA)
            .idFraternidad(DEFAULT_ID_FRATERNIDAD);
        return notificacion;
    }

    @BeforeEach
    public void initTest() {
        notificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificacion() throws Exception {
        int databaseSizeBeforeCreate = notificacionRepository.findAll().size();

        // Create the Notificacion
        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isCreated());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeCreate + 1);
        Notificacion testNotificacion = notificacionList.get(notificacionList.size() - 1);
        assertThat(testNotificacion.getTituloNotificacion()).isEqualTo(DEFAULT_TITULO_NOTIFICACION);
        assertThat(testNotificacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNotificacion.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testNotificacion.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testNotificacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testNotificacion.getHora()).isEqualTo(DEFAULT_HORA);
        assertThat(testNotificacion.getIdFraternidad()).isEqualTo(DEFAULT_ID_FRATERNIDAD);
    }

    @Test
    @Transactional
    public void createNotificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificacionRepository.findAll().size();

        // Create the Notificacion with an existing ID
        notificacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificacionMockMvc.perform(post("/api/notificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotificacions() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get all the notificacionList
        restNotificacionMockMvc.perform(get("/api/notificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].tituloNotificacion").value(hasItem(DEFAULT_TITULO_NOTIFICACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].hora").value(hasItem(sameInstant(DEFAULT_HORA))))
            .andExpect(jsonPath("$.[*].idFraternidad").value(hasItem(DEFAULT_ID_FRATERNIDAD)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllNotificacionsWithEagerRelationshipsIsEnabled() throws Exception {
        NotificacionResource notificacionResource = new NotificacionResource(notificacionRepositoryMock);
        when(notificacionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restNotificacionMockMvc = MockMvcBuilders.standaloneSetup(notificacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNotificacionMockMvc.perform(get("/api/notificacions?eagerload=true"))
        .andExpect(status().isOk());

        verify(notificacionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllNotificacionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        NotificacionResource notificacionResource = new NotificacionResource(notificacionRepositoryMock);
            when(notificacionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restNotificacionMockMvc = MockMvcBuilders.standaloneSetup(notificacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNotificacionMockMvc.perform(get("/api/notificacions?eagerload=true"))
        .andExpect(status().isOk());

            verify(notificacionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        // Get the notificacion
        restNotificacionMockMvc.perform(get("/api/notificacions/{id}", notificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notificacion.getId().intValue()))
            .andExpect(jsonPath("$.tituloNotificacion").value(DEFAULT_TITULO_NOTIFICACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.hora").value(sameInstant(DEFAULT_HORA)))
            .andExpect(jsonPath("$.idFraternidad").value(DEFAULT_ID_FRATERNIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingNotificacion() throws Exception {
        // Get the notificacion
        restNotificacionMockMvc.perform(get("/api/notificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        int databaseSizeBeforeUpdate = notificacionRepository.findAll().size();

        // Update the notificacion
        Notificacion updatedNotificacion = notificacionRepository.findById(notificacion.getId()).get();
        // Disconnect from session so that the updates on updatedNotificacion are not directly saved in db
        em.detach(updatedNotificacion);
        updatedNotificacion
            .tituloNotificacion(UPDATED_TITULO_NOTIFICACION)
            .descripcion(UPDATED_DESCRIPCION)
            .contenido(UPDATED_CONTENIDO)
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA)
            .hora(UPDATED_HORA)
            .idFraternidad(UPDATED_ID_FRATERNIDAD);

        restNotificacionMockMvc.perform(put("/api/notificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotificacion)))
            .andExpect(status().isOk());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeUpdate);
        Notificacion testNotificacion = notificacionList.get(notificacionList.size() - 1);
        assertThat(testNotificacion.getTituloNotificacion()).isEqualTo(UPDATED_TITULO_NOTIFICACION);
        assertThat(testNotificacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNotificacion.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testNotificacion.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testNotificacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testNotificacion.getHora()).isEqualTo(UPDATED_HORA);
        assertThat(testNotificacion.getIdFraternidad()).isEqualTo(UPDATED_ID_FRATERNIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificacion() throws Exception {
        int databaseSizeBeforeUpdate = notificacionRepository.findAll().size();

        // Create the Notificacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificacionMockMvc.perform(put("/api/notificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notificacion)))
            .andExpect(status().isBadRequest());

        // Validate the Notificacion in the database
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificacion() throws Exception {
        // Initialize the database
        notificacionRepository.saveAndFlush(notificacion);

        int databaseSizeBeforeDelete = notificacionRepository.findAll().size();

        // Delete the notificacion
        restNotificacionMockMvc.perform(delete("/api/notificacions/{id}", notificacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        assertThat(notificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notificacion.class);
        Notificacion notificacion1 = new Notificacion();
        notificacion1.setId(1L);
        Notificacion notificacion2 = new Notificacion();
        notificacion2.setId(notificacion1.getId());
        assertThat(notificacion1).isEqualTo(notificacion2);
        notificacion2.setId(2L);
        assertThat(notificacion1).isNotEqualTo(notificacion2);
        notificacion1.setId(null);
        assertThat(notificacion1).isNotEqualTo(notificacion2);
    }
}
