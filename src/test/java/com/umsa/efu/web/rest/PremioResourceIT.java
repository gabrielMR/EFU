package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Premio;
import com.umsa.efu.repository.PremioRepository;
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
import java.util.ArrayList;
import java.util.List;

import static com.umsa.efu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PremioResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class PremioResourceIT {

    private static final String DEFAULT_TITULO_PREMIO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_PREMIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUESTO = 1;
    private static final Integer UPDATED_PUESTO = 2;

    private static final Integer DEFAULT_GESTION = 1;
    private static final Integer UPDATED_GESTION = 2;

    private static final Integer DEFAULT_ID_FRATERNIDAD = 1;
    private static final Integer UPDATED_ID_FRATERNIDAD = 2;

    @Autowired
    private PremioRepository premioRepository;

    @Mock
    private PremioRepository premioRepositoryMock;

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

    private MockMvc restPremioMockMvc;

    private Premio premio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PremioResource premioResource = new PremioResource(premioRepository);
        this.restPremioMockMvc = MockMvcBuilders.standaloneSetup(premioResource)
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
    public static Premio createEntity(EntityManager em) {
        Premio premio = new Premio()
            .tituloPremio(DEFAULT_TITULO_PREMIO)
            .descripcion(DEFAULT_DESCRIPCION)
            .categoria(DEFAULT_CATEGORIA)
            .puesto(DEFAULT_PUESTO)
            .gestion(DEFAULT_GESTION)
            .idFraternidad(DEFAULT_ID_FRATERNIDAD);
        return premio;
    }

    @BeforeEach
    public void initTest() {
        premio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPremio() throws Exception {
        int databaseSizeBeforeCreate = premioRepository.findAll().size();

        // Create the Premio
        restPremioMockMvc.perform(post("/api/premios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isCreated());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeCreate + 1);
        Premio testPremio = premioList.get(premioList.size() - 1);
        assertThat(testPremio.getTituloPremio()).isEqualTo(DEFAULT_TITULO_PREMIO);
        assertThat(testPremio.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPremio.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testPremio.getPuesto()).isEqualTo(DEFAULT_PUESTO);
        assertThat(testPremio.getGestion()).isEqualTo(DEFAULT_GESTION);
        assertThat(testPremio.getIdFraternidad()).isEqualTo(DEFAULT_ID_FRATERNIDAD);
    }

    @Test
    @Transactional
    public void createPremioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = premioRepository.findAll().size();

        // Create the Premio with an existing ID
        premio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPremioMockMvc.perform(post("/api/premios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPremios() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        // Get all the premioList
        restPremioMockMvc.perform(get("/api/premios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(premio.getId().intValue())))
            .andExpect(jsonPath("$.[*].tituloPremio").value(hasItem(DEFAULT_TITULO_PREMIO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO)))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)))
            .andExpect(jsonPath("$.[*].idFraternidad").value(hasItem(DEFAULT_ID_FRATERNIDAD)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPremiosWithEagerRelationshipsIsEnabled() throws Exception {
        PremioResource premioResource = new PremioResource(premioRepositoryMock);
        when(premioRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPremioMockMvc = MockMvcBuilders.standaloneSetup(premioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPremioMockMvc.perform(get("/api/premios?eagerload=true"))
        .andExpect(status().isOk());

        verify(premioRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPremiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        PremioResource premioResource = new PremioResource(premioRepositoryMock);
            when(premioRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPremioMockMvc = MockMvcBuilders.standaloneSetup(premioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPremioMockMvc.perform(get("/api/premios?eagerload=true"))
        .andExpect(status().isOk());

            verify(premioRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPremio() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        // Get the premio
        restPremioMockMvc.perform(get("/api/premios/{id}", premio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(premio.getId().intValue()))
            .andExpect(jsonPath("$.tituloPremio").value(DEFAULT_TITULO_PREMIO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.puesto").value(DEFAULT_PUESTO))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION))
            .andExpect(jsonPath("$.idFraternidad").value(DEFAULT_ID_FRATERNIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingPremio() throws Exception {
        // Get the premio
        restPremioMockMvc.perform(get("/api/premios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePremio() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        int databaseSizeBeforeUpdate = premioRepository.findAll().size();

        // Update the premio
        Premio updatedPremio = premioRepository.findById(premio.getId()).get();
        // Disconnect from session so that the updates on updatedPremio are not directly saved in db
        em.detach(updatedPremio);
        updatedPremio
            .tituloPremio(UPDATED_TITULO_PREMIO)
            .descripcion(UPDATED_DESCRIPCION)
            .categoria(UPDATED_CATEGORIA)
            .puesto(UPDATED_PUESTO)
            .gestion(UPDATED_GESTION)
            .idFraternidad(UPDATED_ID_FRATERNIDAD);

        restPremioMockMvc.perform(put("/api/premios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPremio)))
            .andExpect(status().isOk());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
        Premio testPremio = premioList.get(premioList.size() - 1);
        assertThat(testPremio.getTituloPremio()).isEqualTo(UPDATED_TITULO_PREMIO);
        assertThat(testPremio.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPremio.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testPremio.getPuesto()).isEqualTo(UPDATED_PUESTO);
        assertThat(testPremio.getGestion()).isEqualTo(UPDATED_GESTION);
        assertThat(testPremio.getIdFraternidad()).isEqualTo(UPDATED_ID_FRATERNIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingPremio() throws Exception {
        int databaseSizeBeforeUpdate = premioRepository.findAll().size();

        // Create the Premio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremioMockMvc.perform(put("/api/premios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(premio)))
            .andExpect(status().isBadRequest());

        // Validate the Premio in the database
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePremio() throws Exception {
        // Initialize the database
        premioRepository.saveAndFlush(premio);

        int databaseSizeBeforeDelete = premioRepository.findAll().size();

        // Delete the premio
        restPremioMockMvc.perform(delete("/api/premios/{id}", premio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Premio> premioList = premioRepository.findAll();
        assertThat(premioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Premio.class);
        Premio premio1 = new Premio();
        premio1.setId(1L);
        Premio premio2 = new Premio();
        premio2.setId(premio1.getId());
        assertThat(premio1).isEqualTo(premio2);
        premio2.setId(2L);
        assertThat(premio1).isNotEqualTo(premio2);
        premio1.setId(null);
        assertThat(premio1).isNotEqualTo(premio2);
    }
}
