![US Logo](images/logo_us.png)

Políticas de desarrollo
---


![Cohabify](images/Cohabify.png)

<table>
    <tbody>
        <tr>
            <td rowspan=2>González Castillero, Rafael (editor) <p></p> Márquez Sierra, María (editor)<p></p> Morato Navarro, Olegario (revisor)
            </td>
        </tr>
    </tbody>
</table>

<table>
  <tr>
    <th>Grupo</th>
    <th>4</th>
    <th>Entregable</th>
    <th>S2</th>
  </tr>
  <tr>
    <td>Repositorio</td>
    <td colspan="3"><a href="https://github.com/Cohabify/Cohabify">https://github.com/Cohabify/Cohabify</a></td>
  </tr>
  <tr>
    <td>Base de conocimiento común</td>
    <td colspan="3"><a href="https://bgcc.vercel.app/">https://bgcc.vercel.app/</a></td>
  </tr>
</table>

### Versión Cambios Autores

| Versión | Cambios | Autores |
| --- | --- | --- |
| V1.0 | Creación del documento | - |
| V1.1 | Añadido versionado | Rafael González Castillero |
| V2.0 | Actualización para funcionalidad de Governify y BlueJ | María Márquez Sierra |

## Tabla de contenidos
- [Políticas de desarrollo](#políticas-de-desarrollo)
  - [Versión Cambios Autores](#versión-cambios-autores)
- [Tabla de contenidos](#tabla-de-contenidos)
- [Resumen ejecutivo](#resumen-ejecutivo)
- [1. Políticas de desarrollo](#1-políticas-de-desarrollo)
  - [1.1 Política de commits](#11-política-de-commits)
    - [1.1.1 Normas de commits](#111-normas-de-commits)
    - [1.1.2 Plantilla de commits](#112-plantilla-de-commits)
- [1.2 Política de branching](#12-política-de-branching)
- [1.3 Política de versionado](#13-política-de-versionado)
- [1.4 Políticas de código](#14-políticas-de-código)
- [1.4 Políticas de BlueJ](#15-políticas-de-bluej)

## Resumen ejecutivo
En el siguiente documento se detallan las políticas que serán utilizadas para mantener una homogeneidad en la gestión del repositorio y del código. Se estipula que los commits deben ser atómicos y siguiendo una plantilla, lo que mejora la legibilidad. En cuanto a la política de branching, se seguirá el estándar de gitflow, con ramas de feature, de develop o de release, entre otros. Las issues serán catalogadas por tipos, y se tomarán medidas según este. Las normas de código buscan reducir la complejidad y la deuda técnica de este, así como reducir el código spaghetti. Por último, el versionado de la aplicación será la definición de los estados estables de la aplicación, por lo que se utilizará el sistema vX.Y.Z.

## 1. Políticas de desarrollo

### 1.1 Política de commits
A continuación, se detallan una serie de características que debe tener cualquier commit de nuestro repositorio, buscando que estos sean homogéneos y facilitando la lectura y la rápida interpretación.

#### 1.1.1 Normas de commits
- Commits Atómicos: Cada commit debe representar un cambio atómico y significativo en el código. Esto facilita la revisión de código y la identificación de errores.
- Mensajes Descriptivos: Los mensajes de commit deben ser descriptivos y claros, explicando qué cambios se realizaron y por qué. Esto ayuda a entender el propósito de cada cambio sin necesidad de revisar el código.
- Frecuencia Regular: Se debe animar a realizar commits de forma regular y frecuente, en lugar de acumular muchos cambios en un solo commit. Esto facilita la colaboración y reduce el riesgo de conflictos.
- Revisión de Código: Antes de hacer un commit en el branch principal, se debe realizar una revisión de código por parte de al menos un compañero de equipo que no haya participado en la creación del commit. Esto ayuda a mantener la calidad del código y a identificar posibles problemas antes de fusionar los cambios.
- Integración Continua: Utilizar herramientas de integración continua para automatizar pruebas y despliegues. Los commits deben pasar estas pruebas antes de fusionarse en el branch principal.
- Resolución de Conflictos: En caso de conflictos durante la fusión de branches, se debe resolver de manera rápida y efectiva, comunicando cualquier cambio importante al equipo.

#### 1.1.2 Plantilla de commits
tipo: asunto 
<Línea en blanco> 
cuerpo
Donde:
- Tipos de commits
    - feat (nueva funcionalidad)
    - fix (corrección de bugs)
    - research (incorporación de código experimental, puede ser no funcional)
    - refactor (refactorización de código)
    - docs (actualización de documentación)
    - test (incorporación o modificación de tests)
    - conf (modificación de archivos de configuración)
- Asunto: Consiste en una breve descripción del problema que se ha tratado y que debe de comenzar con un verbo en participio.
- Cuerpo (opcional): Se utilizará en caso de que el asunto no sea suficientemente descriptivo.
Ejemplo: `conf : Actualizado docker-compose.yml`

## 1.2 Política de branching
A la hora de elegir una política de branching, se optará por utilizar Git Flow, debido a las ventajas que esta puede brindar a nuestra aplicación, además de nuestra familiaridad con el método. Dicha política se basa en la idea de tener distintos tipos de branches para gestionar las diferentes etapas del ciclo de vida del desarrollo de software. Los tipos de ramas de este estándar son:

- Branch Master (Main): En Gitflow, el branch master (o main como se le llama en algunas convenciones) es el branch principal y estable. Este branch debería contener sólo el código que está listo para ser desplegado en producción. Proporciona una línea base estable para la versión actual del producto, lo que garantiza que la versión desplegada en producción sea confiable y funcional.
- Branch Develop: El branch develop es donde se integran todas las características completadas y se realiza la mayoría del desarrollo. Ofrece un entorno centralizado para la integración continua y la colaboración del equipo. Permite realizar pruebas de integración tempranas y mantener un código base consistente y en evolución.
- Branches Feature: Cada nueva característica o historia de usuario se desarrolla en su propio branch de feature, que se deriva de develop. Permite un desarrollo paralelo de múltiples características sin interferencias entre sí. También facilita la revisión de código y la realización de pruebas específicas para cada característica antes de su integración en el branch develop.
- Branches Release: Cuando se está preparando una nueva versión para el lanzamiento, se crea un branch de release desde develop. En este branch se realizan las últimas pruebas y ajustes antes del lanzamiento. Proporciona un entorno controlado para la preparación del lanzamiento, lo que permite corregir errores de último minuto y realizar pruebas finales sin interferir con el desarrollo en curso en develop.
- Branches Hotfix: Si surge un problema crítico en producción que requiere una solución inmediata, se crea un branch de hotfix desde master. Una vez corregido el problema, los cambios se fusionan tanto en master como en develop. Permite abordar problemas urgentes en producción de manera rápida y controlada, mientras se mantiene la estabilidad del branch develop para el desarrollo continuo.

## 1.3 Política de versionado
Se ha decidido utilizar el esquema de versionado vX.Y.Z porque proporciona una forma clara y consistente de identificar y comunicar las versiones de nuestro software en desarrollo. En este esquema:
El número de versiones se divide en tres partes: X, Y y Z.
- El número X indica la versión principal o mayor, que cambia cuando se realizan cambios significativos que pueden afectar la compatibilidad hacia atrás o introducir nuevas características importantes.
- El número Y representa la versión secundaria o menor, que se incrementa con cada nueva funcionalidad agregada o mejora que no rompe la compatibilidad con versiones anteriores.
- El número Z corresponde a la versión de corrección o revisión, que se incrementa con cada corrección de errores o parches que no alteran la funcionalidad existente.

## 1.4 Políticas de código
Con respecto a la realización de código, seguiremos estas pautas con el objetivo de conseguir claridad y eficiencia a la hora de desarrollar nuestra aplicación:
- Nombres claros y descriptivos: Los nombres que usaremos para variables, funciones y clases serán significativos y describirán su uso. Evitaremos abreviaturas complejas y nombres poco explicativos que puedan crear confusión a la hora de la lectura.
- Funciones y métodos pequeños y específicos: A la hora de crear funciones, estas serán cortas y se centrarán en una tarea específica en la medida de lo posible para conseguir una mayor legibilidad del código, además de facilitar el trabajo de entendimiento del mismo por parte del resto de compañeros del equipo. En el caso de tener funciones demasiado largas, se valorará el dividirlas en otras más cortas.
- Comentarios útiles y concisos: Solo se añadirán comentarios en lugares en los que sea necesario aclarar el propósito del código comentado para evitar llenar líneas de texto que no aporta nada.
- Formato consistente: Para esta política se enumeran ciertas prácticas que ayudarán a mantener el código legible y homogéneo:
    - Usaremos la sangría de forma adecuada y homogénea a lo largo del proyecto.
    - Mantendremos el espaciado de forma consistente.
- Evita la duplicación de código (DRY): Trataremos de mantener el código repetido al mínimo posible. Encapsularemos la mayor parte lógica repetida en funciones, clases o módulos reutilizables para facilitar el trabajo de desarrollo.
- Refactorización constante: En el caso de encontrarnos con un posible elemento refactorizable en el código, este se valorará con el objetivo de analizar si su refactorización producirá un cambio significativo en la limpieza del código.

## 1.5 Políticas de BlueJ

- Estos procedimientos detallan las acciones necesarias para garantizar el correcto funcionamiento del repositorio en colaboración con las herramientas BlueJ y Governify. Sigue estas pautas para asegurar una integración fluida y eficiente en el desarrollo del proyecto.

### CREAR UNA RAMA:

1. Mover la tarea de 'TODO' a la columna 'In Progress'.
2. Crear una nueva rama desde DEVELOP con el siguiente formato: `Feat/XXX-Creacion-Anuncio-Piso` (siendo XXX el número que aparece como #XXX en la issue creada).

---

### MOVER DE 'In-Progress' A 'In Review':

1. Mover la tarea de 'In-Progress' a 'In-Review'.
2. Crear la pull request con el siguiente formato: `Creación anuncio piso /XXX` (siendo XXX el número que aparece como #XXX en la issue de dicha PR; es importante que el nombre sea el mismo que el de la issue).

Cabe destacar que la PR debe ser creada por la persona asignada a la tarea de dicha PR.

---

### MOVER DE 'In-Review' A 'DONE':

1. Aceptar la PR y hacer un merge.
2. Una vez aprobada la PR, moverla de la columna 'In-Review' a 'DONE'.

- Cabe destacar que la persona que aprueba y hace merge de la PR debe ser distinta a la persona asignada, y los commits de la pull request también deben ser realizados por una persona que no haya colaborado en ningún momento en la PR.


