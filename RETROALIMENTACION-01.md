# Retroalimentación — Laboratorio Evaluativo — Codificación de Diseño OO (Momento 1)

**Grupo:** Grupo3 · **Proyecto:** Consultorio Médico (CentroMedico)
**Fecha límite:** 2026-09-08 23:59 -0500 · **Commit evaluado:** `c114840` (2026-09-07 12:54:31 -0500)

## Calificación

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| Codificación correcta del UML | 60% | 4.5 |
| Pruebas en el App — creación de objetos | 20% | 3.0 |
| Buenas prácticas de programación | 20% | 3.5 |
| **Nota del laboratorio** | | **4.00** |

```
nota_laboratorio = 0.60 × 4.5 + 0.20 × 3.0 + 0.20 × 3.5
                 = 2.70 + 0.60 + 0.70 = 4.00
nota_final_curso = (4.00 / 5) × 5% = 4.00%
```

## Detalle por criterio

### Codificación correcta del UML (4.5/5)
**Lo que está bien:**
- `Persona` (`src/model/domain/Persona.java`) es abstracta, implementa `RolClinico`, encapsula sus tres atributos y valida `identificacion` no nula/no vacía en el constructor exactamente como pide el enunciado, lanzando `IllegalArgumentException`.
- `datosResumen()` queda implementado en `Persona` combinando `identificacion`, `nombre` y `telefono`; `rolEnConsulta()` queda declarado `abstract` en `Persona` y correctamente resuelto en cada subtipo (`Paciente.java:21`, `Medico.java:15`), cada uno con lógica distinta (EPS/edad vs. especialidad/registro).
- `Paciente extends Persona` y `Medico extends Persona` invocan `super(...)`, conservan solo sus atributos propios (`edad`/`eps` y `especialidad`/`numeroRegistro` respectivamente) sin duplicar los heredados.
- La composición `Paciente "1" *-- "0..*" Consulta` está bien resuelta: `Paciente` mantiene `List<Consulta> historialConsultas` y solo se agregan `Consulta` a través de `registrarConsulta(...)` (`Paciente.java:37-39`), sin exponer un setter de la lista completa — coherente con que una `Consulta` no exista fuera de un `Paciente`.
- `Cita` (`Cita.java`) referencia a `Paciente` y `Medico` por asociación, tal como exige el diagrama (no composición).
- `Consulta` y `Cita` quedan correctamente encapsuladas con getters/setters, aunque ya existían de la práctica.

**Por mejorar:**
- Estructura de carpetas: el entregable esperado es `src/model/domain/...` en la raíz del repositorio, pero el código real está en `CentroMedico/src/model/domain/...` (carpeta extra con el nombre del proyecto envolviendo `src/`). El paquete Java (`model.domain`) sí es correcto, pero la ruta física incumple la convención acordada en clase — ver observación de convención más abajo.
- Los setters de `Consulta`, `Cita`, `Paciente` y `Medico` no validan antes de asignar (p. ej. `Cita.setFecha`, `Consulta.setDiagnostico`), aunque el diagrama no detalla validaciones específicas para estas clases.

### Pruebas en el App — creación de objetos (3.0/5)
**Lo que está bien:**
- El archivo `CentroMedico/src/App.java` compila y se ejecuta sin errores.
- Instancia un `Paciente` y un `Medico` (los dos subtipos concretos) y los integra en una `Cita`; además ejercita la composición real vía `paciente.registrarConsulta(...)`.
- Ejercita `rolEnConsulta()` sobre ambas instancias sin usar `instanceof`, e imprime el resultado por consola, mostrando comportamiento distinto entre `Paciente` y `Medico`.
- Prueba adicional válida: captura la `IllegalArgumentException` del constructor de `Paciente` con identificación vacía.

**Por mejorar:**
- La clase no se llama `PruebaCreacionObjetos` como exige explícitamente la rúbrica (`App.java`), ni respeta el paquete/ubicación esperada junto al `Main.java` del proyecto (el proyecto no tiene `Main.java`; `App.java` cumple ese rol pero con otro nombre).
- El polimorfismo se ejercita llamando `rolEnConsulta()` directamente sobre las variables `paciente` y `medico` (de tipo concreto), no a través de una colección o referencia de tipo `Persona`/`RolClinico` recorrida en un ciclo — no se ve una demostración explícita de despacho polimórfico sobre el tipo abstracto/interfaz, aunque el resultado impreso sí evidencia comportamiento distinto.

### Buenas prácticas de programación (3.5/5)
**Lo que está bien:**
- 16 commits antes de la fecha límite, con trabajo repartido en varias sesiones (no un único commit final).
- Nombres de clases en `PascalCase` (`Paciente`, `Medico`, `RolClinico`) y de métodos/variables en `camelCase` (`rolEnConsulta`, `historialConsultas`), siguiendo la convención Java.
- Uso de una rama (`desarrollo`) y un Pull Request (`#1`, commit `8b45165`) al inicio del proyecto.

**Por mejorar:**
- Todo el trabajo específico de este laboratorio (11 de los 16 commits, del 2026-09-07) se hizo directamente sobre `main`, sin volver a usar una rama de trabajo ni PR — el flujo de ramas solo se aplicó a la configuración inicial del repo.
- Algunos mensajes de commit son poco descriptivos o reflejan iteración desordenada: `"poner datos en rol clinico y quitar domain. porque no dio"` (`b9fc25a`), dos commits consecutivos de renombrado (`45410bc`, `68d9931`).
- Uno de los commits (`c114840`, el de corte) aparece con autor `unknown`, indicando que ese integrante no configuró `user.name`/`user.email` en git.

## Compilación y ejecución
Compila sin errores:
```
javac -d out $(find src -name "*.java")   # exit 0
```
Ejecución de `App` (equivalente a `PruebaCreacionObjetos`) con stdin vacío, salida correcta:
```
ID: 1017123456 | Nombre: Juan Pérez | Teléfono: 3001234567
Rol: Paciente | EPS: Sura | Edad: 20 años
ID: 43210987 | Nombre: Dra. María | Teléfono: 3109876543
Rol: Médico | Especialidad: General | Registro: RM-123
Cita asignada a Juan Pérez con Dra. María
Excepción capturada: La identificación no puede ser nula ni estar vacía.
```

## Recomendaciones para el siguiente corte
- Ubicar el código directamente en `<repo>/src/model/domain/` sin envolverlo en una carpeta con el nombre del proyecto (`CentroMedico/`).
- Nombrar la clase de prueba exactamente `PruebaCreacionObjetos` cuando la rúbrica lo exija por nombre.
- Demostrar el polimorfismo recorriendo una colección o arreglo de referencias del tipo abstracto/interfaz (`Persona[]` o `List<RolClinico>`) en un ciclo, en vez de invocar el método directamente sobre variables de tipo concreto.
- Configurar `git config user.name`/`user.email` en todos los equipos de desarrollo para que la autoría de los commits quede identificada.
- Mantener el uso de ramas de feature + PR durante todo el desarrollo del laboratorio, no solo en la configuración inicial.
