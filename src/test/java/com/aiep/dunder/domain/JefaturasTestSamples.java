package com.aiep.dunder.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class JefaturasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Jefaturas getJefaturasSample1() {
        return new Jefaturas().id(1L).nombre_jefe("nombre_jefe1");
    }

    public static Jefaturas getJefaturasSample2() {
        return new Jefaturas().id(2L).nombre_jefe("nombre_jefe2");
    }

    public static Jefaturas getJefaturasRandomSampleGenerator() {
        return new Jefaturas().id(longCount.incrementAndGet()).nombre_jefe(UUID.randomUUID().toString());
    }
}
