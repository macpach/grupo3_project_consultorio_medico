# Retroalimentación — Laboratorio 1: Codificación del diseño OO

**Grupo:** Grupo3 · **Proyecto:** Consultorio Médico
**Fecha límite:** 2026-09-08 23:59 · **Versión revisada:** commit `c114840`

¡Muy buen trabajo! El diseño quedó muy bien pasado a código.

## Nota

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| El código sigue el diagrama UML | 60% | 5.0 |
| Pruebas: creación de objetos en el programa | 20% | 4.0 |
| Buenas prácticas de programación | 20% | 3.5 |
| **Nota del laboratorio** | | **4.50** |

La nota se calcula así: 60% diseño UML + 20% pruebas + 20% buenas prácticas.

## 1. El código sigue el diagrama UML (5.0)
**Lo que hicieron bien:**
- `Persona` es abstracta, implementa `RolClinico` y tiene sus atributos privados. El constructor revisa que la identificación no venga vacía y, si viene vacía, lanza `IllegalArgumentException`, tal como se pedía.
- `datosResumen()` está escrito una sola vez en `Persona`. `rolEnConsulta()` queda pendiente en `Persona` y cada hija (`Paciente` y `Medico`) lo resuelve a su manera.
- `Paciente` y `Medico` heredan de `Persona`, llaman a `super(...)` y solo agregan sus datos propios, sin repetir los que ya vienen del padre.
- Un `Paciente` tiene su propio historial de consultas. Las consultas solo se agregan con `registrarConsulta(...)`, así que ninguna `Consulta` existe por fuera de un paciente. Eso es justo lo que pide el diagrama.
- `Cita` guarda una referencia a un `Paciente` y a un `Medico`, sin ser dueña de ellos, como indica el diagrama.
- `Consulta` y `Cita` tienen sus atributos privados con getters y setters.

## 2. Pruebas: creación de objetos (4.0)
**Lo que hicieron bien:**
- `App` compila y corre sin errores.
- Crean un `Paciente` y un `Medico` y los usan para armar una `Cita`. También registran una consulta en el paciente.
- Llaman a `rolEnConsulta()` en los dos objetos sin usar `instanceof`, y se ve en consola que cada uno responde distinto.
- Prueban además que el constructor rechaza una identificación vacía y atrapan la excepción. ¡Muy bien!

## 3. Buenas prácticas (3.5)
**Lo que hicieron bien:**
- Hicieron varios commits en distintos momentos, no uno solo al final.
- Los nombres siguen las reglas de Java: clases como `Paciente` y métodos/variables como `rolEnConsulta`.
- Al inicio del proyecto usaron una rama (`desarrollo`) y un Pull Request.

**Lo que pueden mejorar:**
- No siguieron la estructura de carpetas acordada en clase: el código quedó dentro de una carpeta extra `CentroMedico/` en vez de ir directamente en `src/model/domain/`.
- Casi todo el trabajo de este laboratorio lo subieron directo a `main`. Las ramas solo las usaron al comienzo.
- Algunos mensajes de commit no explican bien el cambio, por ejemplo "poner datos en rol clinico y quitar domain. porque no dio". Un buen mensaje dice qué se hizo y para qué.
- Un commit aparece con autor `unknown`: ese integrante no tiene configurado su nombre y correo en git, así que no se sabe quién lo hizo.

## ¿El programa funciona?
Sí. Compila sin errores y al ejecutarlo muestra correctamente los datos del paciente, del médico, la cita y el mensaje de la excepción.

## Para el próximo laboratorio
- Pongan el código directamente en `src/model/domain/`, sin la carpeta `CentroMedico/`.
- Cada integrante debe configurar su nombre y correo en git (`git config user.name` y `git config user.email`) en el equipo donde trabaje.
- Sigan usando ramas durante todo el laboratorio, no solo al inicio.
- Escriban mensajes de commit claros que digan qué cambió.
