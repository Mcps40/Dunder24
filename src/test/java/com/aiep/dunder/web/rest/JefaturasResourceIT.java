package com.aiep.dunder.web.rest;

import static com.aiep.dunder.domain.JefaturasAsserts.*;
import static com.aiep.dunder.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.dunder.IntegrationTest;
import com.aiep.dunder.domain.Jefaturas;
import com.aiep.dunder.repository.JefaturasRepository;
import com.aiep.dunder.service.JefaturasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JefaturasResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class JefaturasResourceIT {

    private static final String DEFAULT_NOMBRE_JEFE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_JEFE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jefaturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JefaturasRepository jefaturasRepository;

    @Mock
    private JefaturasRepository jefaturasRepositoryMock;

    @Mock
    private JefaturasService jefaturasServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJefaturasMockMvc;

    private Jefaturas jefaturas;

    private Jefaturas insertedJefaturas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jefaturas createEntity() {
        return new Jefaturas().nombre_jefe(DEFAULT_NOMBRE_JEFE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jefaturas createUpdatedEntity() {
        return new Jefaturas().nombre_jefe(UPDATED_NOMBRE_JEFE);
    }

    @BeforeEach
    public void initTest() {
        jefaturas = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedJefaturas != null) {
            jefaturasRepository.delete(insertedJefaturas);
            insertedJefaturas = null;
        }
    }

    @Test
    @Transactional
    void createJefaturas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Jefaturas
        var returnedJefaturas = om.readValue(
            restJefaturasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jefaturas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Jefaturas.class
        );

        // Validate the Jefaturas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertJefaturasUpdatableFieldsEquals(returnedJefaturas, getPersistedJefaturas(returnedJefaturas));

        insertedJefaturas = returnedJefaturas;
    }

    @Test
    @Transactional
    void createJefaturasWithExistingId() throws Exception {
        // Create the Jefaturas with an existing ID
        jefaturas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJefaturasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jefaturas)))
            .andExpect(status().isBadRequest());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJefaturas() throws Exception {
        // Initialize the database
        insertedJefaturas = jefaturasRepository.saveAndFlush(jefaturas);

        // Get all the jefaturasList
        restJefaturasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jefaturas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre_jefe").value(hasItem(DEFAULT_NOMBRE_JEFE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJefaturasWithEagerRelationshipsIsEnabled() throws Exception {
        when(jefaturasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJefaturasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(jefaturasServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllJefaturasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(jefaturasServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJefaturasMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(jefaturasRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getJefaturas() throws Exception {
        // Initialize the database
        insertedJefaturas = jefaturasRepository.saveAndFlush(jefaturas);

        // Get the jefaturas
        restJefaturasMockMvc
            .perform(get(ENTITY_API_URL_ID, jefaturas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jefaturas.getId().intValue()))
            .andExpect(jsonPath("$.nombre_jefe").value(DEFAULT_NOMBRE_JEFE));
    }

    @Test
    @Transactional
    void getNonExistingJefaturas() throws Exception {
        // Get the jefaturas
        restJefaturasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJefaturas() throws Exception {
        // Initialize the database
        insertedJefaturas = jefaturasRepository.saveAndFlush(jefaturas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jefaturas
        Jefaturas updatedJefaturas = jefaturasRepository.findById(jefaturas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJefaturas are not directly saved in db
        em.detach(updatedJefaturas);
        updatedJefaturas.nombre_jefe(UPDATED_NOMBRE_JEFE);

        restJefaturasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJefaturas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedJefaturas))
            )
            .andExpect(status().isOk());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJefaturasToMatchAllProperties(updatedJefaturas);
    }

    @Test
    @Transactional
    void putNonExistingJefaturas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jefaturas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJefaturasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jefaturas.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jefaturas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJefaturas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jefaturas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJefaturasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jefaturas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJefaturas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jefaturas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJefaturasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jefaturas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJefaturasWithPatch() throws Exception {
        // Initialize the database
        insertedJefaturas = jefaturasRepository.saveAndFlush(jefaturas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jefaturas using partial update
        Jefaturas partialUpdatedJefaturas = new Jefaturas();
        partialUpdatedJefaturas.setId(jefaturas.getId());

        partialUpdatedJefaturas.nombre_jefe(UPDATED_NOMBRE_JEFE);

        restJefaturasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJefaturas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJefaturas))
            )
            .andExpect(status().isOk());

        // Validate the Jefaturas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJefaturasUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedJefaturas, jefaturas),
            getPersistedJefaturas(jefaturas)
        );
    }

    @Test
    @Transactional
    void fullUpdateJefaturasWithPatch() throws Exception {
        // Initialize the database
        insertedJefaturas = jefaturasRepository.saveAndFlush(jefaturas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jefaturas using partial update
        Jefaturas partialUpdatedJefaturas = new Jefaturas();
        partialUpdatedJefaturas.setId(jefaturas.getId());

        partialUpdatedJefaturas.nombre_jefe(UPDATED_NOMBRE_JEFE);

        restJefaturasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJefaturas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJefaturas))
            )
            .andExpect(status().isOk());

        // Validate the Jefaturas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJefaturasUpdatableFieldsEquals(partialUpdatedJefaturas, getPersistedJefaturas(partialUpdatedJefaturas));
    }

    @Test
    @Transactional
    void patchNonExistingJefaturas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jefaturas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJefaturasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jefaturas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jefaturas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJefaturas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jefaturas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJefaturasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jefaturas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJefaturas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jefaturas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJefaturasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jefaturas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Jefaturas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJefaturas() throws Exception {
        // Initialize the database
        insertedJefaturas = jefaturasRepository.saveAndFlush(jefaturas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jefaturas
        restJefaturasMockMvc
            .perform(delete(ENTITY_API_URL_ID, jefaturas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jefaturasRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Jefaturas getPersistedJefaturas(Jefaturas jefaturas) {
        return jefaturasRepository.findById(jefaturas.getId()).orElseThrow();
    }

    protected void assertPersistedJefaturasToMatchAllProperties(Jefaturas expectedJefaturas) {
        assertJefaturasAllPropertiesEquals(expectedJefaturas, getPersistedJefaturas(expectedJefaturas));
    }

    protected void assertPersistedJefaturasToMatchUpdatableProperties(Jefaturas expectedJefaturas) {
        assertJefaturasAllUpdatablePropertiesEquals(expectedJefaturas, getPersistedJefaturas(expectedJefaturas));
    }
}
