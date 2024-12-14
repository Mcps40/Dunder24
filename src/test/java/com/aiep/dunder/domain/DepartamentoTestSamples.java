package com.aiep.dunder.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DepartamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Departamento getDepartamentoSample1() {
        return new Departamento().id(1L).nombre_depto("nombre_depto1").ubicacion_depto("ubicacion_depto1").presupuesto_depto(1);
    }

    public static Departamento getDepartamentoSample2() {
        return new Departamento().id(2L).nombre_depto("nombre_depto2").ubicacion_depto("ubicacion_depto2").presupuesto_depto(2);
    }

    public static Departamento getDepartamentoRandomSampleGenerator() {
        return new Departamento()
            .id(longCount.incrementAndGet())
            .nombre_depto(UUID.randomUUID().toString())
            .ubicacion_depto(UUID.randomUUID().toString())
            .presupuesto_depto(intCount.incrementAndGet());
    }
}
