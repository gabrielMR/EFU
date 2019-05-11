package com.umsa.efu.web.rest;

import com.umsa.efu.EfuApp;
import com.umsa.efu.domain.Comunicado;
import com.umsa.efu.repository.ComunicadoRepository;
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
 * Integration tests for the {@Link ComunicadoResource} REST controller.
 */
@SpringBootTest(classes = EfuApp.class)
public class ComunicadoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_GESTION = 1;
    private static final Integer UPDATED_GESTION = 2;

    @Autowired
    private ComunicadoRepository comunicadoRepository;

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

    private MockMvc restComunicadoMockMvc;

    private Comunicado comunicado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComunicadoResource comunicadoResource = new ComunicadoResource(comunicadoRepository);
        this.restComunicadoMockMvc = MockMvcBuilders.standaloneSetup(comunicadoResource)
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
    public static Comunicado createEntity(EntityManager em) {
        Comunicado comunicado = new Comunicado()
            .titulo(DEFAULT_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .contenido(DEFAULT_CONTENIDO)
            .gestion(DEFAULT_GESTION);
        return comunicado;
    }

    @BeforeEach
    public void initTest() {
        comunicado = createEntity(em);
    }

    @Test
    @Transactional
    public void createComunicado() throws Exception {
        int databaseSizeBeforeCreate = comunicadoRepository.findAll().size();

        // Create the Comunicado
        restComunicadoMockMvc.perform(post("/api/comunicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunicado)))
            .andExpect(status().isCreated());

        // Validate the Comunicado in the database
        List<Comunicado> comunicadoList = comunicadoRepository.findAll();
        assertThat(comunicadoList).hasSize(databaseSizeBeforeCreate + 1);
        Comunicado testComunicado = comunicadoList.get(comunicadoList.size() - 1);
        assertThat(testComunicado.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testComunicado.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testComunicado.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testComunicado.getGestion()).isEqualTo(DEFAULT_GESTION);
    }

    @Test
    @Transactional
    public void createComunicadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comunicadoRepository.findAll().size();

        // Create the Comunicado with an existing ID
        comunicado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComunicadoMockMvc.perform(post("/api/comunicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunicado)))
            .andExpect(status().isBadRequest());

        // Validate the Comunicado in the database
        List<Comunicado> comunicadoList = comunicadoRepository.findAll();
        assertThat(comunicadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComunicados() throws Exception {
        // Initialize the database
        comunicadoRepository.saveAndFlush(comunicado);

        // Get all the comunicadoList
        restComunicadoMockMvc.perform(get("/api/comunicados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comunicado.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].gestion").value(hasItem(DEFAULT_GESTION)));
    }
    
    @Test
    @Transactional
    public void getComunicado() throws Exception {
        // Initialize the database
        comunicadoRepository.saveAndFlush(comunicado);

        // Get the comunicado
        restComunicadoMockMvc.perform(get("/api/comunicados/{id}", comunicado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comunicado.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.gestion").value(DEFAULT_GESTION));
    }

    @Test
    @Transactional
    public void getNonExistingComunicado() throws Exception {
        // Get the comunicado
        restComunicadoMockMvc.perform(get("/api/comunicados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComunicado() throws Exception {
        // Initialize the database
        comunicadoRepository.saveAndFlush(comunicado);

        int databaseSizeBeforeUpdate = comunicadoRepository.findAll().size();

        // Update the comunicado
        Comunicado updatedComunicado = comunicadoRepository.findById(comunicado.getId()).get();
        // Disconnect from session so that the updates on updatedComunicado are not directly saved in db
        em.detach(updatedComunicado);
        updatedComunicado
            .titulo(UPDATED_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .contenido(UPDATED_CONTENIDO)
            .gestion(UPDATED_GESTION);

        restComunicadoMockMvc.perform(put("/api/comunicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComunicado)))
            .andExpect(status().isOk());

        // Validate the Comunicado in the database
        List<Comunicado> comunicadoList = comunicadoRepository.findAll();
        assertThat(comunicadoList).hasSize(databaseSizeBeforeUpdate);
        Comunicado testComunicado = comunicadoList.get(comunicadoList.size() - 1);
        assertThat(testComunicado.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testComunicado.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testComunicado.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testComunicado.getGestion()).isEqualTo(UPDATED_GESTION);
    }

    @Test
    @Transactional
    public void updateNonExistingComunicado() throws Exception {
        int databaseSizeBeforeUpdate = comunicadoRepository.findAll().size();

        // Create the Comunicado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComunicadoMockMvc.perform(put("/api/comunicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comunicado)))
            .andExpect(status().isBadRequest());

        // Validate the Comunicado in the database
        List<Comunicado> comunicadoList = comunicadoRepository.findAll();
        assertThat(comunicadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComunicado() throws Exception {
        // Initialize the database
        comunicadoRepository.saveAndFlush(comunicado);

        int databaseSizeBeforeDelete = comunicadoRepository.findAll().size();

        // Delete the comunicado
        restComunicadoMockMvc.perform(delete("/api/comunicados/{id}", comunicado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Comunicado> comunicadoList = comunicadoRepository.findAll();
        assertThat(comunicadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comunicado.class);
        Comunicado comunicado1 = new Comunicado();
        comunicado1.setId(1L);
        Comunicado comunicado2 = new Comunicado();
        comunicado2.setId(comunicado1.getId());
        assertThat(comunicado1).isEqualTo(comunicado2);
        comunicado2.setId(2L);
        assertThat(comunicado1).isNotEqualTo(comunicado2);
        comunicado1.setId(null);
        assertThat(comunicado1).isNotEqualTo(comunicado2);
    }
}
