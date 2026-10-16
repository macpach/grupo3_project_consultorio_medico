# Retroalimentación — Laboratorio Lista Simple (Momento 2, implementación)

**Grupo:** Grupo3 · **Proyecto:** Centro Médico (Consultorio)

Buen trabajo: la lista simple quedó funcionando de verdad dentro del proyecto.

## Nota

| Criterio | Peso | Nota (0-5) |
|---|---|---|
| Relaciones uno-a-muchos | 20% | 5.0 |
| `ListaSimple<T>` integrada al `Service` | 30% | 4.0 |
| Menú en consola | 15% | 4.5 |
| Reemplazo sin código sobrante | 20% | 3.5 |
| Buenas prácticas (commits, nombres y estructura) | 15% | 3.0 |
| **Nota del laboratorio** | | **4.03** |

La nota se calcula así: 20% relaciones + 30% integración + 15% menú + 20% reemplazo + 15% buenas prácticas, cada criterio sobre 5.

## 1. Relaciones uno-a-muchos (5.0)
**Lo que hicieron bien:**
- Eligieron dos relaciones correctas y reales: un `Paciente` tiene muchas `Cita` y muchas `Consulta`.

## 2. `ListaSimple<T>` integrada al `Service` (4.0)
**Lo que hicieron bien:**
- `Paciente` guarda sus citas y su historial de consultas en atributos `ListaSimple<Cita>` y `ListaSimple<Consulta>`.
- `Consultorio` agrega con `insertarFinal` y elimina con `eliminarPorValor`; reutilizaron su propia `ListaSimple` y `Nodo` en `model/structures`.

**Lo que pueden mejorar:**
- La vista (`Menu`) recibe la lista completa y la recorre con `getTamano()` y `obtener(i)`. La vista debe hablar solo con el `Service`; que `Consultorio` entregue los datos ya listos.
- El `Service` no usa `buscarPorValor`, así que la parte de buscar no se ve en el uso de la lista.

## 3. Menú en consola (4.5)
**Lo que hicieron bien:**
- El menú tiene submenús para citas y consultas con agregar, listar, actualizar y eliminar, todo pasando por `Consultorio`.
- Además agregaron la opción de actualizar, que no se pedía.

**Lo que pueden mejorar:**
- No hay una opción para buscar un elemento puntual (solo listar).

## 4. Reemplazo sin código sobrante (3.5)
**Lo que hicieron bien:**
- No quedó ningún `ArrayList` ni arreglo viejo en las relaciones.

**Lo que pueden mejorar:**
- `Paciente` conserva `registrarCita` y `registrarConsulta`, que nadie usa porque `Consultorio` hace ese trabajo. Es código repetido y sobrante.
- Siguen subidos al repositorio archivos compilados viejos en `bin/` (`cita.class`, `paciente.class`...) que ya no corresponden al código actual.

## 5. Buenas prácticas (3.0)
**Lo que hicieron bien:**
- Hay varios commits repartidos en distintos días y varias personas aportando.
- Los nombres de clases, métodos y variables siguen las convenciones de Java.

**Lo que pueden mejorar:**
- No siguieron la estructura de carpetas acordada en clase: todo el proyecto está dentro de una carpeta `CentroMedico/` y el `Service` está en `model/service` en vez de una carpeta `service/` propia. Además subieron `bin/` y `.vscode/`, que no deben ir al repositorio.
- Casi todo el trabajo se hizo directo sobre `main`; la rama `desarrollo` quedó casi sin uso.
- Varios mensajes son poco claros ("organizar codigo, ver mas ordenado", "organizar el codigo"). Cuenten qué cambió y por qué.

## ¿El programa funciona?
Sí. Compila sin errores y el menú corre: se pueden agregar, listar, actualizar y eliminar citas y consultas sin fallos. Hay que registrar un paciente y un médico al iniciar.

## Para el próximo laboratorio
- Muevan el contenido de `CentroMedico/` a la raíz del repositorio y dejen `service/` como carpeta propia.
- Hagan que `Consultorio` devuelva los datos al `Menu` sin que este manipule la lista.
- Borren `registrarCita` y `registrarConsulta` de `Paciente` si no se usan, y quiten `bin/` del repositorio (con un `.gitignore`).
- Agreguen una opción de buscar en el menú usando `buscarPorValor`.
- Trabajen en una rama y únanla a `main` con mensajes de commit más descriptivos.
