package com.aiep.dunder.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class JefaturasAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJefaturasAllPropertiesEquals(Jefaturas expected, Jefaturas actual) {
        assertJefaturasAutoGeneratedPropertiesEquals(expected, actual);
        assertJefaturasAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJefaturasAllUpdatablePropertiesEquals(Jefaturas expected, Jefaturas actual) {
        assertJefaturasUpdatableFieldsEquals(expected, actual);
        assertJefaturasUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJefaturasAutoGeneratedPropertiesEquals(Jefaturas expected, Jefaturas actual) {
        assertThat(expected)
            .as("Verify Jefaturas auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJefaturasUpdatableFieldsEquals(Jefaturas expected, Jefaturas actual) {
        assertThat(expected)
            .as("Verify Jefaturas relevant properties")
            .satisfies(e -> assertThat(e.getNombre_jefe()).as("check nombre_jefe").isEqualTo(actual.getNombre_jefe()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertJefaturasUpdatableRelationshipsEquals(Jefaturas expected, Jefaturas actual) {
        assertThat(expected)
            .as("Verify Jefaturas relationships")
            .satisfies(e -> assertThat(e.getDepartamento()).as("check departamento").isEqualTo(actual.getDepartamento()));
    }
}
