package com.aiep.dunder.domain;

import static com.aiep.dunder.domain.DepartamentoTestSamples.*;
import static com.aiep.dunder.domain.EmpleadosTestSamples.*;
import static com.aiep.dunder.domain.JefaturasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.dunder.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpleadosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empleados.class);
        Empleados empleados1 = getEmpleadosSample1();
        Empleados empleados2 = new Empleados();
        assertThat(empleados1).isNotEqualTo(empleados2);

        empleados2.setId(empleados1.getId());
        assertThat(empleados1).isEqualTo(empleados2);

        empleados2 = getEmpleadosSample2();
        assertThat(empleados1).isNotEqualTo(empleados2);
    }

    @Test
    void departamentoTest() {
        Empleados empleados = getEmpleadosRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        empleados.setDepartamento(departamentoBack);
        assertThat(empleados.getDepartamento()).isEqualTo(departamentoBack);

        empleados.departamento(null);
        assertThat(empleados.getDepartamento()).isNull();
    }

    @Test
    void jefaturaTest() {
        Empleados empleados = getEmpleadosRandomSampleGenerator();
        Jefaturas jefaturasBack = getJefaturasRandomSampleGenerator();

        empleados.setJefatura(jefaturasBack);
        assertThat(empleados.getJefatura()).isEqualTo(jefaturasBack);

        empleados.jefatura(null);
        assertThat(empleados.getJefatura()).isNull();
    }
}
