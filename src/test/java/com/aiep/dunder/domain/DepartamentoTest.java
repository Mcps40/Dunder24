package com.aiep.dunder.domain;

import static com.aiep.dunder.domain.DepartamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.dunder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departamento.class);
        Departamento departamento1 = getDepartamentoSample1();
        Departamento departamento2 = new Departamento();
        assertThat(departamento1).isNotEqualTo(departamento2);

        departamento2.setId(departamento1.getId());
        assertThat(departamento1).isEqualTo(departamento2);

        departamento2 = getDepartamentoSample2();
        assertThat(departamento1).isNotEqualTo(departamento2);
    }
}
