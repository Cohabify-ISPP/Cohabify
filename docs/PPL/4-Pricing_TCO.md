![US Logo](images/logo_us.png)

TCO y Pricing
---


![Cohabify](images/Cohabify.png)

<table>
    <tbody>
        <tr>
            <td rowspan=2>Arriaza Arriaza, Daniel (editor)<p></p> González Castillero, Rafael (editor)<p></p>Morato Navarro, Olegario (editor)<p></p> 
            </td>
        </tr>
    </tbody>
</table>

<table>
  <tr>
    <th>Grupo</th>
    <th>4</th>
    <th>Entregable</th>
    <th>PPL</th>
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
| V1.2 | Estadísticas de alquiler y personas que comparten vivienda y estimaciones de usuarios. Dividir en CAPEX y OPEX. | Rafael González Castillero |
| V1.3 | Visión sobre el aumento/reducción de usuarios en nuestra aplicación. | Rafael González Castillero |
| V1.4 | Corregir errata en aumento de costes de operación | Daniel Arriaza Arriaza |
| V2.0 | Añadir cálculos de TCO para 2 años | Daniel Arriaza Arriaza y Olegario Morato Navarro |
| V3.0 | Añadir costes atención al cliente y actualizar costes totales y beneficios | Rafael González Castillero |
| V3.1 | Corregir errores de transcripción a markdown | Rafael González Castillero |
| V3.2 | Modificar formulas en latex y añadir gastos de nuevos perfiles actualizando costes y beneficios | Rafael González Castillero |
| V4.0| Añadir costes de marketing y SEO en desarrollo y operación (CAPEX y OPEX), actualizar estimación de usuarios debido al plan de marketing, añadir costes de mantenimiento, y recalcular recursos necesarios en costes de operación. Actualizar estimaciones de rendimiento y recortar/aumentar costes en estimaciones realistas y pesimistas actualizando los beneficios en los 3 casos que se describen | Rafael González Castillero |
| V4.1 | Corregir erratas | Daniel Arriaza Arriaza |


### Tabla de contenidos
- [TCO y Pricing](#tco-y-pricing)
      - [Versión Cambios Autores](#versión-cambios-autores)
      - [Tabla de contenidos](#tabla-de-versiones)
      - [Resumen ejecutivo](#resumen-ejecutivo)
      - [Introducción](#introducción)
- [1. Total Cost of Ownership (TCO)](#1-total-cost-of-ownership-tco)
  - [1.1 Costes de desarrollo](#11-costes-de-desarrollo)
    - [1.1.1 CAPEX desarrollo](#111-capex-desarrollo)
      - [1.1.1.1 Perfiles](#1111-perfiles)
      - [1.1.1.2 Adquisición de productos](#1112-adquisición-de-productos)
      - [1.1.1.3 Total CAPEX desarrollo](#1113-total-capex-desarrollo)
    - [1.1.2 OPEX desarrollo](#112-opex-desarrollo)
      - [1.1.2.1 Costes GitHub](#1121-costes-github)
      - [1.1.2.2 Costes Google App Engine](#1122-costes-google-app-engine)
      - [1.1.2.3 Costes Marketing](#1123-costes-marketing)
      - [1.1.2.4 Total OPEX desarrollo](#1124-total-opex-desarrollo)
    - [1.1.3 Gastos totales desarrollo](#113-gastos-totales-desarrollo)
  - [1.2 Costes de operación](#12-costes-de-operación)
    - [1.2.1 Estimación de usuarios en Cohabify](#121-estimación-de-usuarios-en-cohabify)
      - [1.2.1.1 Estudio del INE](#1211-estudio-del-ine)
      - [1.2.1.2 Estudio de Fotocasa Research](#1212-estudio-de-fotocasa-research)
      - [1.2.1.3 Estimación optimista del porcentaje de posibles usuarios](#1213-estimación-optimista-del-porcentaje-de-posibles-usuarios)
      - [1.2.1.4 Estimación pesimista del porcentaje de posibles usuarios](#1214-estimación-pesimista-del-porcentaje-de-posibles-usuarios)
      - [1.2.1.5 Estimación realista del porcentaje de posibles usuarios](#1215-estimación-realista-del-porcentaje-de-posibles-usuarios)
      - [1.2.1.6 Estimación de usuarios usando el método PERT](#1216-estimación-de-usuarios-usando-el-método-pert)
    - [1.2.2 OPEX operación](#122-opex-operación)
      - [1.2.2.1 Costes backend](#1221-costes-backend)
      - [1.2.2.2 Costes frontend](#1222-costes-frontend)
      - [1.2.2.3 Costes de base de datos](#1223-costes-de-base-de-datos)
      - [1.2.2.4 Costes de verificación SMS](#1224-costes-de-verificación-sms)
      - [1.2.2.5 Costes de verificación de viviendas](#1225-costes-de-verificación-de-viviendas)
      - [1.2.2.6 Costes GitHub](#1226-costes-github)
      - [1.2.2.7 Costes Google Maps](#1227-costes-google-maps)
      - [1.2.2.8 Gastos servicio externo atención al cliente Partnerhero](#1228-gastos-servicio-externo-atención-al-cliente-partnerhero)
      - [1.2.2.9 Mantenimiento de la aplicación](#1229-mantenimiento-de-la-aplicación)
      - [1.2.2.10 Marketing y SEO](#12210-marketing-y-seo)
      - [1.2.2.11 Tabla de costes de operación](#12211-tabla-de-costes-de-operación)
- [2. Pricing de Cohabify](#2-pricing-de-cohabify)
  - [2.1 Estimación de usuarios](#21-estimación-de-usuarios)
  - [2.2 Pricing de Cohabify](#22-pricing-de-cohabify)
    - [2.2.1 Promoción de publicaciones - 3€](#221-promoción-de-publicaciones---3)
    - [2.2.2 Plan Propietario - 15€ (base) + 5€ (por vivienda adicional)](#222-plan-propietario---15-base--5-por-vivienda-adicional)
    - [2.2.3 Plan Explorador - 5€](#223-plan-explorador---5)
    - [2.2.4 Análisis de Rentabilidad y Recuperación de la Inversión](#224-análisis-de-rentabilidad-y-recuperación-de-la-inversión)
      - [2.2.4.1 Ingresos Mensuales](#2241-ingresos-mensuales)
      - [2.2.4.2 Beneficio Mensual](#2242-beneficio-mensual)
      - [2.2.4.3 Recuperación de la Inversión](#2243-recuperación-de-la-inversión)
- [3. Desvíos del número de usuarios estimados.](#3-desvíos-del-número-de-usuarios-estimados)
  - [3.1 Disminución de usuarios](#31-disminución-de-usuarios)
    - [3.1.1 Cálculo de ingreso pesimista](#311-cálculo-de-ingreso-pesimista)
    - [3.1.2 Reducción de costes de operación](#312-reducción-de-costes-de-operación)
    - [3.1.3 Cálculo de beneficio pesimista](#313-cálculo-de-beneficio-pesimista)
  - [3.2 Aumento de usuarios](#32-aumento-de-usuarios)
    - [3.2.1 Cálculo de ingreso optimista](#321-cálculo-de-ingreso-optimista)
    - [3.2.2 Aumento de costes de operación](#322-aumento-de-costes-de-operación)
    - [3.2.3 Cálculo de beneficio optimista](#323-cálculo-de-beneficio-optimista)

## Resumen ejecutivo
En este documento se realiza un análisis del TCO en el primer año de vida del proyecto con su respectivo coste de desarrollo divididos en CAPEX y OPEX. Se realiza una estimación de los usuarios que se esperan en Cohabify y se calcula el coste de operación mensual en base a estos datos.

Se seleccionan distintos PaaS, API’s y servicios externos según los requisitos de nuestro producto y las tecnologías que usaremos, y se calcula que el coste de operación expresado mensualmente según la estimación realista de usuarios de nuestra aplicación. Un 3% de los españoles en 2022 han tenido la necesidad de alquilar una vivienda compartida, nuestro objetivo y estimación es obtener un tráfico del 2.4% mensual del total, es decir, alrededor de 20.600 usuarios/mes.
En base a estos datos se crea un plan de precios para Cohabify con el fin de recuperar la inversión del desarrollo en un plazo de entre un año a año y medio como tarde, siendo nuestra estimación la recuperación total en 9 meses. Dentro de este plan existirán pagos por promoción de publicaciones, un plan para propietarios y un plan de usuarios normales.

## Introducción
La intención de este documento es describir el método de cálculo del Total Cost of Ownership (TCO) donde se desglosan los costos asociados con un activo a lo largo de su vida útil. En este contexto, se detallará exhaustivamente el TCO para el desarrollo y operación del proyecto, incluyendo horas de trabajo y gastos mensuales de plataformas como Heroku, Netlify, MongoDB Atlas, Twilio... La inversión se complementará con una estrategia de ingresos mediante servicios de pago. El análisis financiero proporcionará una visión clara de la rentabilidad mensual y proyecta un período de recuperación de la inversión. Este enfoque integral guía la toma de decisiones estratégicas y asegura una gestión financiera eficaz a lo largo del ciclo de vida del proyecto.

## 1. Total Cost of Ownership (TCO)

### 1.1 Costes de desarrollo

#### 1.1.1 CAPEX desarrollo

##### 1.1.1.1 Perfiles
Un total de trece personas formarán parte del proyecto de Cohabify durante la etapa de desarrollo. Se estima que serán necesarias unas 1.950 horas de trabajo distribuidas entre los distintos perfiles de trabajo identificados para completar con éxito el proyecto y finalizar con su respectiva puesta en producción.

El coste por hora de trabajo base será de 23,85€/h. En la siguiente tabla se detallarán los perfiles y presupuestos para el desarrollo de la aplicación.

| Perfil              | Horas (h) | Diferencia base | Total       |
|---------------------|-----------|-----------------|-------------|
| Group manager       | 150       | 56,18 %         | 5.587,50 €  |
| Programador full stack | 1105    | -               | 26.354,25 € |
| Tester              | 300       | -0,71 %         | 7.104,00 €  |
| Diseñador gráfico   | 150       | 12,91 %         | 4.039,50 €  |
| Administrador de DB | 20        | 48,97 %         | 710,60 €    |
| Analista de requisitos | 50     | 29,43 %         | 1.543,50 €  |
| Secretario          | 75        | -58,23 %        | 747,00 €    |
| Encargado GDPR | 30          | 47 %               | 1.151,50 €    |
| Community Manager   | 70        | -16,14        | 1.400 €  |
| **Cálculo total**   |           |                 | **48.637,85 €** |
| **Contingencia (+5%)** |         |                 | **51.069,74 €** |

Para el plan de marketing que se realizará anterior al lanzamiento de la aplicación se necesita hacer adquisición de varios productos descritos en la siguiente tabla.

##### 1.1.1.2 Adquisición de productos

| Producto | Proveedor | Cantidad | Total (€) |
|----------|------|--------|-------------|
| Pegatinas QR | publikea.es | 2.000 | 68 € |
| Pegatinas Logo | publikea.es | 5.000 | 137,50 € |
| Pegatinas rectangulares | publikea.es | 1.000 | 71,30 € |
| Flyers | publikea.es | 20.000 | 275 € |
| Micrófono | amazon.com | 1 | 50 € |
| Trípode | amazon.com | 1 | 20 € |
| **Total** | - | - | **621,80 €** |

##### 1.1.1.3 Total CAPEX desarrollo
El coste total de desarrollo del proyecto Cohabify se puede calcular de la siguiente forma:

```math
\text{CAPEX desarrollo} = 51.069,74 € + 621,80 € = 51.691,54 €
```

#### 1.1.2 OPEX desarrollo

##### 1.1.2.1 Costes GitHub
Analizando nuestras necesidades en cuanto a servicios externos durante la etapa de desarrollo se obtiene que necesitamos pagar las licencias de uso de github además de requerir servicios de github Actions. El plan seleccionado para las licencias es “Teams” el cual cumple con todos nuestros requisitos. El plan de Actions seleccionado nos permite correr 3 jobs diarios de 2 minutos cada uno, suficiente para cubrir las tareas automatizadas de despliegue y revisión de código durante el desarrollo dado el tamaño del proyecto.

##### 1.1.2.2 Costes Google App Engine
Otro servicio a tener en cuenta es la plataforma de despliegue de las versiones de prueba de nuestra aplicación, para ello haremos uso de Google App Engine. Necesitaremos dos instancias (backend y frontend) lo suficientemente potentes para soportar la cantidad de usuarios pilotos (entre 30 y 40).

##### 1.1.2.3 Costes Marketing

Para el plan de marketing que se realizará anterior al lanzamiento de la aplicación se necesita hacer adquisición de varios servicios como el seo, anuncios en redes sociales y uso de google adds. Se describe en la siguiente tabla.

| Servicio            | Proveedor  | Total   |
|---------------------|--------------|-------|
| SEO                 | seotech.es   |450€   |
| Instagram ads    | instagram.com | 750€ |
| Google ads        | google.com    | 1.000€ |
| Tiktok ads        | tiktok.com    | 500€  |
| Twitter ads       | twitter.com   | 300€  |
| **Suma**           |              | **3.000€** |


##### 1.1.2.4 Total OPEX desarrollo
Podemos calcular el gasto mensual total y el total en los 4 meses de desarrollo de la siguiente forma:

| Servicio            | Mensual  | Total   |
|---------------------|----------|---------|
| GitHub              | ≈50€     | ≈200€  |
| Google App Engine   | ≈34€     | ≈136€  |
| Marketing           |  750€  | 3.000€ |
| **Suma**            | **834€** | **≈3.336€** |

#### 1.1.3 Gastos totales desarrollo
La inversión total necesaria para el proyecto durante el proceso de desarrollo se puede por tanto calcular de la siguiente forma:

```math
\text{CAPEX desarrollo} + \text{OPEX desarrollo} = 51.691.54 € + 3.336 € = 55.027,54 €
```

### 1.2 Costes de operación

Antes de adentrarnos en realizar las estimaciones de costes en la etapa de producción de nuestro proyecto es necesario realizar un análisis sobre el flujo de usuarios que podríamos llegar a adquirir, para así poder tener en mente los recursos que se necesitarán contratar.

#### 1.2.1 Estimación de usuarios en Cohabify

Para realizar esta estimación nos hemos basado en 2 estudios principalmente, uno realizado por el INE (Instituto Nacional de Estadística) y el más relevante en nuestro caso, ya que acota de una mejor forma el nicho de la búsqueda de pisos compartidos, el estudio realizado por Fotocasa Research, el grupo de investigación de la empresa líder en el sector inmobiliario Fotocasa.

##### 1.2.1.1 Estudio del INE

El estudio realizado por el INE “Hogares/personas en viviendas alquiladas según cuota” muestra la cantidad de personas/viviendas que están en situación de alquiler, agrupados por la cuota o precio del alquiler. En nuestro caso es interesante obtener el total de personas que están alquilando una vivienda sin importar el precio del alquiler. Tras realizar los cálculos se obtiene que el total de personas en esta situación es de 4.941.734, lo que equivale a algo más del 10% de la población de España.

##### 1.2.1.2 Estudio de Fotocasa Research

El estudio de la empresa Fotocasa se centra en el número de personas que han estado buscando una vivienda compartida de alquiler a lo largo del año 2022. En este post se resumen los resultados obtenidos por el estudio. Lo más destacable que se obtiene es que un 3% del total de la población española, es decir alrededor de 1.440.000 personas han estado en situación de búsqueda de una habitación para alquilar.

##### 1.2.1.3 Estimación optimista del porcentaje de posibles usuarios

Teniendo en cuenta los estudios analizados deducimos que una estimación optimista para el flujo de usuarios de nuestra aplicación puede acercarse al 2.8% de las 1.440.000 personas, es decir alrededor de 40.000 usuarios mensuales.

##### 1.2.1.4 Estimación pesimista del porcentaje de posibles usuarios

Teniendo en cuenta los estudios analizados deducimos que una estimación pesimista para el flujo de usuarios de nuestra aplicación puede acercarse al 0.25% del 3% las 1.440.000, es decir alrededor de 3.600 usuarios mensuales.

##### 1.2.1.5 Estimación realista del porcentaje de posibles usuarios

Teniendo en cuenta los estudios analizados deducimos que una estimación realista para el flujo de usuarios de nuestra aplicación puede acercarse al 1.4% de las 1.440.000 personas, es decir alrededor de 20.000 usuarios mensuales.

##### 1.2.1.6 Estimación de usuarios usando el método PERT

Haciendo uso del método PERT para realizar la estimación de usuarios tras el análisis de los estudios anteriores podemos obtener que una buena estimación de los usuarios mensuales de Cohabify ronda el siguiente número:

```math
PERT = \frac{(3.600 + 4*20.000 + 40.000)}{6} = 20.600 \text{ usuarios mensuales}
```

#### 1.2.2 OPEX operación

Todas las estimaciones se han realizado teniendo en cuenta la cantidad de 20.600 usuarios mensuales de flujo en nuestra aplicación.

##### 1.2.2.1 Costes backend

Para desplegar el backend de nuestro servicio se ha decidido hacer uso de la plataforma Heroku. Teniendo en cuenta que en el primer año de vida del proyecto el tráfico no será lo suficientemente grande hemos optado por hacer uso del plan Standard-2X que carga 0,6 dólares/h con un coste máximo de 50 dólares. Este plan nos ofrece un Dyno (conjunto de componentes del servidor) lo suficientemente potente para suplir las horas de menor concurrencia. Este Dyno estará activo en todo momento, lo que nos supondrá un gasto de 50 dólares mensuales.

Sabemos que en horas de máxima concurrencia no será suficiente con un Dyno, por lo que se activarán 2 Dynos más para abastecer la demanda. Las horas de máxima concurrencia se acuerdan que serán entre las 10:00 y las 14:00 horas. Esto supondrá un gasto extra de 14,4 dólares mensuales.

En total, el gasto de operación del backend será de 64,4 dólares lo que equivale unos 60€ al mes. Se debe tener en cuenta que Heroku nos permite escalar nuestro servicio de forma rápida y dinámica, por lo que en cualquier momento se puede volver a regular el plan según las necesidades en cada momento.

##### 1.2.2.2 Costes frontend

Para el despliegue del frontend se ha decidido usar la plataforma Netlify, la cual está orientada a esta tarea. Su plan de precio no se basa en recursos usados sino en tamaño total del tráfico mensual y en el número de gestores de nuestro proyecto en la plataforma. Según las estimaciones, nos bastaría con pagar el plan Starter de Netlify para que nuestro proyecto sea capaz de abastecer la carga suficiente. Este plan cuesta un total de 19 dólares por miembro gestor y nos permite un tráfico de 1 TB total al mes, si pensamos tener 2 miembros encargados de la gestión de Netlify estaremos pagando 38 dólares mensuales que al cambio son unos 35€. Con 1TB de tráfico mensual y estimando que cada petición tiene de media un tamaño de 250KB significa que se pueden realizar 2.000.000 solicitudes, esto equivale a unas 190 peticiones por usuario, que teniendo en cuenta que un alto porcentaje de ellos serán visitantes que no volverán, nos da un margen de maniobra bastante amplio.


##### 1.2.2.3 Costes de base de datos

La plataforma que hemos decidido usar para mantener nuestra base de datos es MongoDB Atlas con el plan de soporte de desarrollador con un precio de 29 dólares por mes. Contrataremos un espacio inicial de 1TB de almacenamiento que supone un gasto adicional de 23,56 dólares mensuales. Además por cada petición Post y Put se realiza un cargo adicional. Teniendo en cuenta nuestros usuarios mensuales y las características de nuestro servicio (chats, publicaciones...) estimamos que se realicen un total de 2.000.000 peticiones mensuales de este tipo, lo cual supone un extra de 10,6 dólares. Finalmente el gasto mensual por el resto de peticiones sería de 4 dólares. En total el coste mensual por usar MongoDB Atlas asciende a 67,16 dólares que ronda los 62€.

##### 1.2.2.4 Costes de verificación SMS

Para la verificación de usuarios usaremos el número de teléfono móvil como método de seguridad, para ello necesitamos consumir la API de Twilio. El servicio de Twilio carga 0,05 dólares por cada verificación SMS. Estimamos que se necesiten verificar unos 75.000 usuarios totales el primer año por lo que se nos cargará 3.750 dólares que son 3.504€. Esto mensualmente supone un gasto de 292€.

##### 1.2.2.5 Costes de verificación de viviendas

Para la verificación de los inmuebles usaremos la API que ofrece la sede electrónica del catastro, cuyo uso es totalmente gratuito.

##### 1.2.2.6 Costes GitHub

Todos los integrantes del grupo necesitarán hacer uso de un plan de precios de GitHub, en este caso dada las dimensiones del proyecto hemos decidido hacer uso del plan “Team”, además necesitaremos hacer uso de GitHub Actions como se comentó al inicio. El coste ya ha sido calculado en el punto 1.1.2.1 y es de unos 50€.

##### 1.2.2.7 Costes Google Maps

Para obtener las coordenadas cartesianas a partir de la dirección introducida por el usuario necesitaremos hacer uso de la API de GeoCoding de Google Maps. Esperamos un total de 30.000 llamadas a la API mensualmente (cada vivienda necesita una llamada al publicarse y filtros por dirección) y su coste por llamada es de 0,005 dólares, lo que hace un total de 150 dólares mensuales, esto equivale a alrededor de 140€ por mes.

##### 1.2.2.8 Gastos servicio externo atención al cliente Partnerhero

Para la atención al cliente debido a que conocemos que la demanda de este será baja y en las horas de concurrencia máxima que se encuentran entre las 10:00 y las 14:00 (horas de disponibilidad del servicio de atención al cliente) hemos decicido contratar un servicio de atención al cliente externo. La prioridad es que el servicio sea de calidad y que se pueda atender a los usuarios en el tiempo especificado en el acuerdo a nivel de servicio. En cuanto al coste, el servicio contratado, Partnerhero, nos ofrece un precio de 10 dólares por hora de atención al cliente. Usando como referencia la cantidad de usuarios calculada como más probable, estimamos que unos 100 usuarios realicen consultas al mes, cada consulta necesitará de media 12 minutos para ser resuelta, lo que supone 1200 minutos al mes, es decir, 20 horas. Por lo tanto, el coste mensual de este servicio será de 200 dólares que al cambio son 188€.

Usando la externalización de este servicio, nos aseguramos de que la atención al cliente sea de calidad y que se pueda atender a los usuarios en el tiempo especificado en el acuerdo a nivel de servicio. Además, este servicio es autoescalable, por lo que en caso de que la demanda aumente, se nos garantiza que se podrá atender a todos los usuarios sin problemas, aumentando el coste según el tiempo de atención. Nos será más rentable que contratar a un empleado a tiempo completo para esta tarea siempre y cuando el número de usuarios de la aplicación no se dispare, en ese caso sería más rentable contratar a un empleado a tiempo completo.

##### 1.2.2.9 Mantenimiento de la aplicación

Para el mantenimiento de la aplicación se dispondrá de recursos humanos de nuestro proyecto, dos desarrolladores full stack que se encarguen de la resolución de bugs y de la implementación de nuevas funcionalidades. Estos programadores trabajarán 10 horas a la semana cada uno y cobrarán 20€/h. Esto supone un gasto mensual de 1.600€.

##### 1.2.2.10 Marketing y SEO

Para el marketing se invertirá un total de 750€ mensuales en anuncios en redes sociales (Instagram, Tiktok y Twitter) y en Google Ads, además de 400€ en SEO, servicio que será cedido por la empresa seotech.es. En total, el gasto mensual en marketing será de 1.150€.

##### 1.2.2.11 Tabla de costes de operación

| Servicio        | Coste mensual | Estimación primer año | Estimación segundo año |
|-----------------|---------------|-----------------------|------------------------|
| Heroku          | 60€           | 720€                  |1.440€                   |
| Netlify         | 35€           | 420€                  |840€                    |
| MongoDB Atlas   | 62€           | 744€                  |1.488€                   |
| Twilio          | 292€          | 3.504€                 |7.008€                   |
| GitHub          | 50€           | 600€                  |1.200€                   |
| Google Maps     | 140€          | 1.680€                 |3.360€                   |
| Partnerhero     | 188€          | 2.256€                 |4.512€                   |
| Mantenimiento   | 1.600€        |   38.400€            | 19.200€           |
| Marketing       | 1.150€        | 13.800€                | 27.600€                 |
| **Total**       | **3.577€**    | **42.924€**           | **85.848€**              |

## 2. Pricing de Cohabify

### 2.1 Estimación de usuarios

Anteriormente se estimó una media de 20.600 usuarios únicos mensuales, incluyendo tanto a los registrados como los no registrados. De este total, proyectamos que el 7.3% (equivalente a 1.500 usuarios aproximadamente) optará por utilizar alguno de nuestros servicios de pago. Dentro de este grupo de usuarios, la distribución se desglosa de la siguiente manera:

- 300 usuarios serán propietarios de viviendas que optarán por adquirir las ventajas de propietario de Cohabify. Del total, 75 tendrán más de una vivienda registrada.
- Otros 500 usuarios optarán por nuestro plan de pago para usuarios exploradores.
- Finalmente, se promocionarán 700 publicaciones, proporcionando visibilidad adicional a los anuncios de los usuarios.

Estas cifras representan nuestras expectativas actuales y pueden variar en función de las tendencias del mercado y el comportamiento del usuario.

### 2.2 Pricing de Cohabify

#### 2.2.1 Promoción de publicaciones - 3€

Cualquier usuario registrado tiene la opción de destacar cualquier tipo de publicación durante 24 horas por un precio de 3 euros. Esto hará que las publicaciones que entren dentro del espacio de búsqueda de cualquier usuario aparezcan con prioridad respecto a las no promocionadas.

#### 2.2.2 Plan Propietario - 15€ (base) + 5€ (por vivienda adicional)

Como propietario, tendrás la capacidad de gestionar las viviendas publicadas, obteniendo información valiosa sobre tus viviendas e inquilinos facilitando la comunicación y aumentando la visibilidad de tus publicaciones, además de recibir estadísticas detalladas sobre tus publicaciones. Este plan tiene un coste mensual de 15€, que te permitirá gestionar una vivienda. Si necesitas publicar y gestionar más viviendas, se aplicará una tarifa adicional de 5€ por vivienda al mes. Finalmente, los usuarios propietarios podrán promocionar gratuitamente una publicación por cada vivienda que gestionen al mes (durante 24 horas) y tendrán acceso a chats ilimitados.

#### 2.2.3 Plan Explorador - 5€

El Plan Explorador está diseñado para aquellos usuarios que buscan encontrar su vivienda ideal. Por una tarifa mensual de 5€, este plan ofrece chats ilimitados (inicialmente limitado a abrir un máximo de 3 chats en paralelo) para poder contactar con más propietarios o inquilinos simultáneamente. También incluye acceso anticipado (“Early bird access”), permitiendo a los usuarios visualizar todas las publicaciones con un día de antelación, facilitando así la búsqueda de las mejores ofertas. Finalmente, los usuarios del Plan Explorador se distinguirán de los usuarios normales mediante un identificador visual de verificado, lo que aumentará la relevancia psicológica de sus publicaciones y su perfil. Tanto el plan Básico como el plan Explorador están limitados a una única publicación por usuario. Esto es debido a que según el análisis realizado, sólo es posible estar en situación de búsqueda de piso (lo que puede provocar la necesidad de crear una publicación de tu perfil) o estar en la situación de búsqueda de compañeros para un piso del cual eres inquilino (lo que resulta en la necesidad de publicar la vivienda para que contacten posibles nuevos compañeros).

#### 2.2.4 Análisis de Rentabilidad y Recuperación de la Inversión

Este análisis tiene como objetivo proporcionar una visión clara de la rentabilidad de nuestro negocio y el tiempo estimado que se tardará en recuperar la inversión inicial.

##### 2.2.4.1 Ingresos Mensuales

Basándonos en los datos estimados podemos obtener la siguiente tabla:

| Pricing                   | Número de Ventas | Precio (€) | Ingresos Mensuales (€) |
|---------------------------|------------------|------------|-------------------------|
| Promoción de Publicaciones| 700              | 3€         | 2.100€                   |
| Plan Propietario (una vivienda) | 225       | 15€        | 3.375€                   |
| Plan Propietario (viviendas adicionales) | 75 | 20€  | 1.500€                    |
| Plan Explorador           | 500              | 5€         | 2.500€                    |
| **Ingresos Totales Mensuales**   |                |            | **9.475€**               |

##### 2.2.4.2 Beneficio Mensual

Para calcular el beneficio mensual, restamos el Coste Total de Propiedad (TCO) mensual a los ingresos mensuales. En este caso, el TCO mensual es de 3.577€, por lo que el beneficio mensual sería de:

```math
9.475€ - 3.577€ = 5.898€
```

##### 2.2.4.3 Recuperación de la Inversión

Finalmente, calculamos el tiempo que tardaremos en recuperar la inversión inicial del desarrollo. Para ello, dividimos la inversión inicial del desarrollo (55.027,54€) entre el beneficio mensual:

```math
\frac{55.027,54€}{5.898€/mes} = 9,32 \text{ meses}
```

Es decir, en poco menos de un año tendríamos la posibilidad de recuperar la totalidad de la inversión inicial. Este análisis nos proporciona una visión clara de la viabilidad financiera de nuestro proyecto y nos permite tomar decisiones informadas sobre la gestión de nuestro negocio.

## 3. Desvíos del número de usuarios estimados.

### 3.1 Disminución de usuarios

Suponiendo que una vez la aplicación esté en producción no alcancemos el número de usuarios esperado, es decir, lleguemos a la estimación pesimista de los mismos (3.600 usuarios mensuales), sería necesario una reducción de los costes operativos. Para ello antes vamos a calcular qué ganancia obtendremos en base a este número de usuarios según nuestros planes de precio.

#### 3.1.1 Cálculo de ingreso pesimista

Teniendo una media de 3.600 usuarios únicos mensuales, incluyendo tanto a los registrados como los no registrados y deduciendo que una menor cuota de mercado haría la aplicación menos atractiva a los usuarios, disminuyendo el porcentaje de usuarios de pago, en torno al 5% (equivalente a 180 usuarios) optará por utilizar alguno de nuestros servicios de pago. Dentro de este grupo de usuarios, la distribución se desglosa de la siguiente manera:

| Pricing                   | Número de Ventas | Precio (€) | Ingresos Mensuales (€) |
|---------------------------|------------------|------------|-------------------------|
| Promoción de Publicaciones| 80               | 3€         | 240€                    |
| Plan Propietario (una vivienda) | 40         | 15€        | 600€                    |
| Plan Propietario (viviendas adicionales) | 10 | 20€  | 200€                     |
| Plan Explorador           | 50               | 5€         | 250€                    |
| **Ingresos Totales Mensuales**   |                |            | **1.290€**               |

#### 3.1.2 Reducción de costes de operación

Los costes de operación que se describen en el punto 1.2 están estimados para una base de 10.600 usuarios mensuales. En el caso de tener 3.600 usuarios reales estaríamos desperdiciando recursos por lo que es necesario un recorte en ciertos servicios, que no influyan en el rendimiento final de la aplicación.

| Servicio      | Plan anterior   | Plan recortado | Coste anterior | Coste recortado | Ahorro |
|---------------|-----------------|----------------|----------------|-----------------|--------|
| Heroku        | 1 dyno + 2 extra| 1 dyno         | 60€            | 46€             | 14€    |
| Google Maps   | 30k peticiones  | 7k peticiones  | 140€            | 32€             | 108€   |
| Twilio        | 6.2k usuarios/mes| 1.3k usuarios/mes | 292€         | 61€             | 231€   |
| Netlify       | 2 gestores      | 1 gestor       | 35€            | 17,5€           | 17,5€  |
| Partnerhero   | 20 horas        | 5 horas        | 188€           | 47€             | 141€   |
| Marketing     | SEO + Anuncios | Anuncios reducidos  | 1.150€         | 25€            | 1.125€   |
| Mantenimiento  | 2 Desarrolladores  | No contrato fijo, pago por horas  | 1.600€         | 50€            | 1.500€   |
| **Total reducido** | -          | -              |     | -               | **3.186,5€** |

Con estos cambios, nos ahorraríamos 3.186,5€/mes. Por lo tanto, el gasto de operación en este supuesto se puede calcular de la siguiente forma:

```math
\text{Coste mensual operación} - \text{Ahorro} = 3.577€ - 3.186,5€ = 391,5€
```

#### 3.1.3 Cálculo de beneficio pesimista

```math
\text{Ingresos pesimistas} - \text{Costes operación pesimistas} = 1.290€ - 391,5€ = 898,5€
```

### 3.2 Aumento de usuarios

Suponiendo que una vez la aplicación esté en producción dupliquemos el número de usuarios esperado, es decir, lleguemos a la estimación optimista (40.000 usuarios mensuales), los costes operativos aumentarían, al igual que los ingresos y beneficios.

Suponiendo que una vez la aplicación esté en producción dupliquemos el número de usuarios esperado, es decir, lleguemos a la estimación optimista (40.000 usuarios mensuales), los costes operativos aumentarían, al igual que los ingresos y beneficios.

#### 3.2.1 Cálculo de ingreso optimista

Teniendo una media de 40.000 usuarios únicos mensuales, incluyendo tanto a los registrados como los no registrados y deduciendo que una mayor cuota de mercado haría la aplicación más atractiva a los usuarios, aumentando el porcentaje de usuarios de pago, en torno al 8% (equivalente a 3.200 usuarios) optará por utilizar alguno de nuestros servicios de pago. Dentro de este grupo de usuarios, la distribución se desglosa de la siguiente manera:

| Pricing                   | Número de Ventas | Precio (€) | Ingresos Mensuales (€) |
|---------------------------|------------------|------------|-------------------------|
| Promoción de Publicaciones| 1600              | 3€         | 3800€                   |
| Plan Propietario (una vivienda) | 600       | 15€        | 9000€                   |
| Plan Propietario (viviendas adicionales) | 150 | 20€  | 3000€                    |
| Plan Explorador           | 850              | 5€         | 4250€                    |
| **Ingresos Totales Mensuales**   |                |            | **20.050€**               |

#### 3.2.2 Aumento de costes de operación

Los costes de operación que se describen en el punto 1.2 están estimados para una base de 20.600 usuarios mensuales. En el caso de tener 40.000 usuarios reales necesitamos aumentar la capacidad recursos por lo que es necesario mejorar los planes de servicios para poder abastecer la demanda.

| Servicio      | Plan anterior   | Plan mejorado  | Coste anterior | Coste aumentado | Aumento de gastos |
|---------------|-----------------|----------------|----------------|-----------------|-------------------|
| Heroku        | 1 dyno + 2 extra| 3 dyno + 2 extra| 60€            | 160€            | 100€               |
| Google Maps   | 30k peticiones  | 60k peticiones | 140€            | 280€            | 140€               |
| Twilio        | 6.2k usuarios/mes| 8.4k usuarios/mes | 292€         | 388€            |    96€             |
| Netlify       | 2 gestores      | 3 gestor       | 35€            | 52,5€           | 17,5€             |
| Mongo         | 2M peticiones   | 5M peticiones  | 10€ + 4€       | 25€ + 10€       | 21€               |
| Partnerhero   | 20 horas        | 40 horas       | 188€           | 376€            | 188€              |
| Marketing     | SEO + Anuncios | SEO + Anuncios aumentados  | 1.150€         | 1.500€          | 350€              |
| Mantenimiento  | 2 Desarrolladores (10h)  | 2 Desarrolladores (20h)  | 1.600€         | 3.200€          | 1.600€              |
| **Total aumentado** | -          | -              |     | -               | **2.512,5€** |

Con estos cambios, aumentaríamos el coste mensual en 2.512,5€. Por lo tanto, el gasto de operación en este supuesto se puede calcular de la siguiente forma:

```math
\text{Coste mensual operación} + \text{Aumento} = 3.577€ + 2.512,5€ = 6.089,5€
```

#### 3.2.3 Cálculo de beneficio optimista

```math
\text{Ingresos optimistas} - \text{Costes operación optimistas} = 20.050€ - 6.089,5€ = 13.960,5€
```
