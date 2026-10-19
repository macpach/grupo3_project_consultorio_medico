# Retroalimentación — Laboratorio Pilas y Colas

**Grupo:** Grupo3 · **Proyecto:** Consultorio Médico

Buen trabajo: ambas estructuras funcionan y el programa corre completo.

## Nota

| Pilas | Puntos | Colas | Puntos |
|---|---|---|---|
| `Pila<T>` correcta | 28/30 | `Cola<T>` correcta | 26/30 |
| Casos límite | 10/10 | Casos límite | 2/10 |
| Integración en entidad y `Service` | 28/30 | UML y entidad dueña | 13/15 |
| Menú en `view/` | 14/15 | Integración en el `Service` | 18/25 |
| Buenas prácticas | 8/15 | Menú en `view/` | 9/10 |
| | | Buenas prácticas | 4/10 |
| **Total Pilas** | **88/100** | **Total Colas** | **72/100** |

**Nota del laboratorio: 4.00**

Se calcula así: (88 + 72) / 2 = 80 sobre 100, y 80 / 20 = 4.00.

## Pilas
**Lo que hicieron bien:**
- `Pila<T>` funciona: sale primero lo último que entró, no usa colecciones de Java y cada operación es directa.
- `pop`, `peek` y `push(null)` lanzan el error correcto con mensaje.
- La pila de consultas vive en `Paciente`, y el listado no destruye la pila (probamos listar dos veces y el conteo siguió igual).
- El menú de pilas tiene las seis opciones, usa solo `ConsoleUtils` y avisa cuando no hay consultas sin cerrarse.

**Lo que pueden mejorar:**
- A `Pila<T>` le agregaron `getTope()`, que deja ver los nodos por dentro. Una pila solo debe dar acceso al tope.
- Dejaron una clase `main` de prueba dentro de `view/` (con nombre en minúscula). Bórrenla o muévanla fuera del proyecto.
- Hay dos menús de consultas (el principal y `MenuPilaView`) repitiendo lo mismo.
- Crearon `ConsultorioPila` aparte en vez de ampliar el `Service` que ya tenían.
- Buenas prácticas: no usaron la rama `feature/pila-en-el-proyecto`; trabajaron en `devolpment_miguel` (con error de escritura), aunque sí la fusionaron a `main`. Hay un commit repetido.

## Colas
**Lo que hicieron bien:**
- `Cola<T>` mantiene bien `frente` y `fin`; al vaciarla y encolar de nuevo, el nuevo elemento queda de primero.
- `SalaDeEspera` coincide con el diagrama: nombre, la fila privada creada en el constructor y su getter.
- Listar la fila gira los elementos sin cambiar el orden, y atender no borra al paciente de ninguna otra lista.
- El menú de colas tiene las seis opciones y no se cierra con la fila vacía.

**Lo que pueden mejorar:**
- Con la cola vacía, `desencolar` y `frente` devuelven `null` en vez de lanzar un error con mensaje. Así un fallo pasa en silencio.
- `encolar(null)` se acepta y mete un elemento vacío en la fila.
- `SalaDeEspera` no valida que el nombre no sea vacío y tiene un `setNombre` que no se pedía.
- `registrarLlegadaPaciente` recibe un paciente nuevo creado en el menú; debería buscar un paciente que ya exista.
- Los mensajes dicen "clientes" y ustedes atienden pacientes.
- Buenas prácticas: no crearon la rama `feature/cola-en-el-proyecto`; los cinco commits fueron directo a `main`, sin fusión.
- No siguieron la estructura de carpetas acordada: todo el código quedó dentro de una carpeta `CentroMedico/`.
