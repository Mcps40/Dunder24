package com.aiep.dunder.domain;

import static com.aiep.dunder.domain.DepartamentoTestSamples.*;
import static com.aiep.dunder.domain.JefaturasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.dunder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JefaturasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jefaturas.class);
        Jefaturas jefaturas1 = getJefaturasSample1();
        Jefaturas jefaturas2 = new Jefaturas();
        assertThat(jefaturas1).isNotEqualTo(jefaturas2);

        jefaturas2.setId(jefaturas1.getId());
        assertThat(jefaturas1).isEqualTo(jefaturas2);

        jefaturas2 = getJefaturasSample2();
        assertThat(jefaturas1).isNotEqualTo(jefaturas2);
    }

    @Test
    void departamentoTest() {
        Jefaturas jefaturas = getJefaturasRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        jefaturas.setDepartamento(departamentoBack);
        assertThat(jefaturas.getDepartamento()).isEqualTo(departamentoBack);

        jefaturas.departamento(null);
        assertThat(jefaturas.getDepartamento()).isNull();
    }
}
