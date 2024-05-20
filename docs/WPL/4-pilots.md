![US Logo](images/logo_us.png)

Lista y gestión de usuarios pilotos
---


![Cohabify](images/Cohabify.png)

<table>
    <tbody>
        <tr>
            <td rowspan=2><p></p> González Castillero, Rafael (editor) <p></p>Morato Navarro, Juan Carlos (editor)<p></p> Morato Navarro, Olegario (editor/revisor)<p></p>
            </td>
        </tr>
    </tbody>
</table>

<table>
  <tr>
    <th>Grupo</th>
    <th>4</th>
    <th>Entregable</th>
    <th>WPL</th>
  </tr>
  <tr>
    <td>Repositorio</td>
    <td colspan="3"><a href="https://github.com/Cohabify/Cohabify">https://github.com/Cohabify/Cohabify</a></td>
  </tr>
</table>

## Tabla de versiones
| Versión | Cambios | Autores |
| --- | --- | --- |
| V1.0 | Creación del documento | - |
| V1.1 | Añadido versionado | Rafael González Castillero |
| V1.2 | Añadido gestión de grupos en 2.3 Comunicación | Olegario Morato Navarro |
| V1.3 | Añadida planificación de usuarios pilotos en 2.6 Planificación de usuarios pilotos | Olegario Morato Navarro / Juan Carlos Morato Navarro |


# Tabla de contenidos
- [Tabla de contenidos](#tabla-de-contenidos)
- [Resumen ejecutivo](#resumen-ejecutivo)
- [1. Usuarios piloto](#1-usuarios-piloto)
  - [1.1 Estudiantes Universitarios](#11-estudiantes-universitarios)
  - [1.2 Propietarios](#12-propietarios)
  - [1.3 Grupo asignado ISPP](#13-grupo-asignado-ispp)
  - [1.4 Otros](#14-otros)
- [2. Gestión de usuarios pilotos](#2-gestión-de-usuarios-pilotos)
  - [2.1 División por roles](#21-división-por-roles)
    - [2.1.1 Rol no registrado](#211-rol-no-registrado)
    - [2.1.2 Rol Básico](#212-rol-básico)
    - [2.1.3 Rol Explorador](#213-rol-explorador)
    - [2.1.4 Rol Propietario](#214-rol-propietario)
  - [2.2 Asignación de roles](#22-asignación-de-roles)
  - [2.3 Comunicación](#23-comunicación)
  - [2.4 Formación](#24-formación)
  - [2.5 Recogida del feedback](#25-recogida-del-feedback)
  - [2.6 Planificación de usuarios pilotos](#26-planificación-de-usuarios-pilotos)
  - [2.7 Recompensas](#27-recompensas)

# Resumen ejecutivo
En el siguiente documento se recoge el listado de los usuarios pilotos que se tienen previstos para probar el funcionamiento de la aplicación de Cohabify. Principalmente encontramos estudiantes universitarios ya que componen la población más predominante en el ámbito de compartir piso, aunque también se prevé integrar a la lista propietarios y usuarios más generales. Además se expone el modo en el que se van a gestionar dichos usuarios pilotos para asegurar un buen balance entre los diferentes perfiles que interactúan en nuestra aplicación.

# 1. Usuarios piloto

## 1.1 Estudiantes Universitarios
Componen el grueso de nuestros usuarios pilotos ya que son la parte de la población más afectada por la búsqueda de compañeros de piso y por lo tanto es el foco que puede estar más interesado en lo que se ofrece con nuestra aplicación. Es por eso que nos parece muy importante obtener feedback de personas con este tipo de perfiles pues será más sencillo adaptar la aplicación a sus necesidades y al mismo tiempo la hacemos más atractiva con el objetivo de atraer al mayor público posible.

## 1.2 Propietarios
Con el fin de obtener feedback de mayor calidad en la parte de usuarios propietarios en el sistema hemos decidido tratar de contactar con propietarios de viviendas reales priorizando aquellos con experiencia en el ámbito de pisos compartidos. Estos usuarios pilotos serán de gran ayuda ya que estando fuera del ámbito estudiantil podrán darnos una opinión de más valor con respecto al uso de nuestra herramienta, facilidad, utilidad... y beneficios o desventajas de otros competidores que hayan usado.

## 1.3 Grupo asignado ISPP
El grupo de la asignatura ISPP asignado para probar nuestra aplicación está formado en el desarrollo y diseño de aplicaciones web por lo que consecuentemente su feedback será más enfocado en aspectos técnicos de la aplicación, aportando mucho valor en fallos que no hayamos podido detectar en otras fases de desarrollo, ideas de mejora o cosas innecesarias dentro del proyecto etc.

## 1.4 Otros
En este grupo encontramos a personas que no se encuentren en los perfiles anteriormente mencionados pero que de igual manera quieren colaborar con nosotros para probar la aplicación. Esto incluye a familiares, amigos y conocidos que de alguna forma quieran colaborar en las pruebas de la versión en desarrollo de Cohabify.

# 2. Gestión de usuarios pilotos

## 2.1 División por roles
Para poder obtener feedback de todas las partes de nuestro proyecto es necesario dividir el total de usuarios en distintos grupos experimentales conforme se vayan creando los planes de usuarios. Cada grupo tendrá un rol y unos privilegios en nuestra aplicación, y deberá centrarse en probar todo lo relacionado con el rol asignado. Los roles que hemos identificado son los siguientes:

### 2.1.1 Rol no registrado
Estimamos que un 15% de usuarios deben hacer de usuario no registrado. Un usuario no registrado es aquel que accede a la aplicación para obtener información rápida sobre publicaciones ya sean de viviendas o personales en una zona concreta, probando las distintas posibilidades que ofrecerá nuestro servicio, como filtros, búsquedas...

### 2.1.2 Rol Básico
El 40% de los usuarios pilotos serán usuarios básicos, usuarios registrados que a parte de lo comentado anteriormente deberán centrarse en realizar y gestionar publicaciones, iniciar y gestionar chats, gestionar su perfil de usuario y validar las restricciones impuestas por el pricing.

### 2.1.3 Rol Explorador
Un 25% serán usuarios con el plan Explorador, que a parte de todo lo mencionado anteriormente, deberán verificar que las ventajas del plan se aplican correctamente.

### 2.1.4 Rol Propietario
El 20% restante se encargará de probar el plan de Propietario. Los usuarios con este rol deberán realizar varias publicaciones de viviendas, gestionar estas publicaciones, gestionar los chats entrantes, verificar que las estadísticas se muestran correctamente y validar que los beneficios y restricciones del plan se aplican correctamente.

## 2.2 Asignación de roles
Cada uno de los usuarios piloto podrá tener uno de los roles mencionados, asignado por los miembros del equipo de desarrollo de forma arbitraria sin importar de qué grupo de usuarios piloto provenga, aunque teniendo siempre en cuenta dos cosas:
- Al menos dos integrantes del grupo de ISPP asignado para probar nuestra aplicación debe estar incluido en cada uno de los roles. Esto asegura que al menos dos personas con conocimientos más técnicos en desarrollo puedan probar todas las partes de nuestro proyecto.
- Las personas propietarias de viviendas deben tener con máxima prioridad el rol de usuario Propietario.
- Los porcentajes son una estimación final y variarán según avance el desarrollo de la aplicación.

## 2.3 Comunicación
Para la comunicación con nuestros usuarios pilotos se usará el correo electrónico. Para ello se han creado 3 grupos de difusión:
- Usuarios pilotos: engloba a todos los usuarios pilotos de la aplicación.
- Usuarios pilotos [USUARIOS]: usuarios que probarán las funciones de la aplicación propias de los usuarios incluyendo los diferentes planes de precio.
- Usuarios pilotos [PROPIETARIOS]: incluye a los usuarios propietarios, los cuales se encargarán de probar las funcionalidades pensadas para los futuros propietarios de pisos en la aplicación.

## 2.4 Formación
Para la formación de usuarios pilotos se realizará documentación y videos tutoriales explicando el papel que desempeñan y el uso de la aplicación ya que es un medio que bien trabajado puede hacer más sencillo el entendimiento de la aplicación y las tareas que se deben realizar. En ningún caso serán vídeos largos pues no se pretende hacer perder el tiempo a nuestros usuarios pilotos.

## 2.5 Recogida del feedback
El feedback se recogerá mediante encuestas de Microsoft con preguntas que permitan a los usuarios describir su experiencia y reportar errores, ya sean técnicos o de diseño, con el objetivo de tener presente la opinión sincera de nuestros usuarios. Las respuestas de los usuarios serán estudiadas y las peticiones de cambios o solución de errores serán priorizadas según el impacto que tengan sobre la aplicación. Posteriormente se crearán issues con el objetivo de solucionar los errores detectados por nuestros usuarios. Cabe destacar que tendrán mayor prioridad los reportes de errores sobre las peticiones de cambio visual dada la cantidad de tiempo limitada de la que dispone el equipo.

## 2.6 Planificación de usuarios pilotos
Los usuarios recibirán una nueva versión de la aplicación siempre cerca del final de cada sprint junto con un formulario para evaluar su feedback sobre las funcionalidades añadidas durante el desarrollo. Además, se les proporcionará el material necesario para comprender estas nuevas funcionalidades. La planificación para gestionar a nuestros usuarios pilotos durante las diferentes etapas de desarrollo son las siguientes:

| Etapa | Descripción | Fecha inicio | Fecha final |
|-------|-------------|--------------|-------------|
| S1    | Lanzamiento de la primera versión de Cohabify | 17:00-03/03/2024 | 19:00-04/03/2024 |
| S2    | Despliegue de Cohabify con las funcionalidades añadidas durante el sprint junto con las posibles correcciones sobre errores técnicos y de diseño detectados | 22:00-29/03/2024 | 19:00-01/04/2024 |
| S3    | Despliegue de Cohabify con las funcionalidades añadidas durante el sprint junto con las posibles correcciones sobre errores técnicos y de diseño detectados | 22:00-15/04/2024 | 19:00-17/04/2024 |
| PPL   | Comprobar funcionalidades añadidas durante los sprints 1,2 y 3 junto con las revisiones realizadas a partir del feedback de anteriores iteraciones | 22:00-03/05/2024 | 19:00-06/05/2024 |
| WPL   | Probar versión pre-lanzamiento de Cohabify | 22:00-15/05/2024 | 19:00-19/05/2024 |

## 2.7 Recompensas
Los usuarios pilotos recibirán 3 meses de suscripción gratuita para el plan que deseen, además de mostrar un icono distintivo permanente en sus perfiles que los identifique como fundadores de la aplicación.

