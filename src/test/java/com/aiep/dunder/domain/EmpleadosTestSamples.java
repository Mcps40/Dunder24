package com.aiep.dunder.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpleadosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Empleados getEmpleadosSample1() {
        return new Empleados()
            .id(1L)
            .nombre_empleado("nombre_empleado1")
            .apellido_empleado("apellido_empleado1")
            .telefono_empleado("telefono_empleado1")
            .correo_empleado("correo_empleado1");
    }

    public static Empleados getEmpleadosSample2() {
        return new Empleados()
            .id(2L)
            .nombre_empleado("nombre_empleado2")
            .apellido_empleado("apellido_empleado2")
            .telefono_empleado("telefono_empleado2")
            .correo_empleado("correo_empleado2");
    }

    public static Empleados getEmpleadosRandomSampleGenerator() {
        return new Empleados()
            .id(longCount.incrementAndGet())
            .nombre_empleado(UUID.randomUUID().toString())
            .apellido_empleado(UUID.randomUUID().toString())
            .telefono_empleado(UUID.randomUUID().toString())
            .correo_empleado(UUID.randomUUID().toString());
    }
}
