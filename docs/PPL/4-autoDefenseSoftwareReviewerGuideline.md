![US Logo](images/logo_us.png)

AutoDefensa Software Reviewer Guidelines
---


![Cohabify](images/Cohabify.png)

<table>
    <tbody>
        <tr>
            <td rowspan=2><p>González Castillero, Rafael (editor)</p> 
            </td>
        </tr>
        <tr>
            <td rowspan=2><p>Roldán García, Miguel Ángel (editor)</p> 
            </td>
        </tr>
        <tr>
            <td rowspan=2><p>Sierra Márquez, María (editor)</p> 
            </td>
        </tr>
        <tr>
            <td rowspan=2><p>Urquijo Martínez, Álvaro (editor)</p> 
            </td>
        </tr>
        <tr>
            <td rowspan=2><p>Barrera García, Ismael (revisor)</p> 
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
| V0.1 | Redacción de las FC de Reviewer Guidelines del S3 | Rafael González Castillero |
| V1.0 | Creación del documento en md | Miguel Ángel Roldán García y María Márquez Sierra |
| V2.0 | Añadida autodefensa S2 | Álvaro Urquijo Martínez |
| V3.0 | Añadido Autodefensa PPL | Álvaro Urquijo Martínez |


## Tabla de contenidos
- [Tabla de contenidos](#tabla-de-contenidos)
- [Autodefensa s2](#autodefensa-s2)
- [Autodefensa S3](#autodefensa-s3)
- [Autodefensa PPL](#autodefensa-ppl)

## Autodefensa S2
A continuación se muestra una tabla con el listado de Failure Conditions encontradas en el documento de Software Reviewer Guidelines, un porcentaje que representa el nivel al que hemos seguido la condición indicada (100% significará que no hemos incumplido la condición, a medida que disminuya el porcentaje indicará que la failure condition se ha empezado a cumplir y por tanto, hay cierto nivel de fallo).


| Failure Condition | Porcentaje de completado | Razonamiento |
| --- | --- | --- |
|(RG-13) A legal interaction with your system results in an HTTP error perceived by user. | 90% | Se ha probado el sistema por nuestra parte y usuarios pilotos, pero no podemos garantizar con el nivel de pruebas realizadas que ciertas interacciones no resulten en lo esperado dando errores percibidos por el usuario. |
|(RG-14) A legal interaction with your system results in a panic (crash/...) perceived by user. | 95% | Es poco probable que el sistema deje de responder debido a fallos en el código. |
|(RG-15) A legal interaction with your system doesn’t have the expected behavior | 50% | Una interacción normal de un usuario no debería resultar en comportamientos inesperados, sin embargo, hemos sido capaces de localizar en varias zonas de la aplicación con ayuda de usuarios pilotos problemas como mensajes de error vacíos o el chat, que en el despliegue no funciona correctamente, los mensajes tardan en reflejarse o se pierden. Ponemos 50% porque no son problemas complejos de solucionar y para la siguiente entrega se pulirán estos y otros aspectos. |
| (RG-16) Submitting a form with wrong data is not detected (form validation) | 95% | Todos los formularios tienen validaciones tanto en frontend como en backend. |
| (RG-17) An actor can list, edit, or delete data that belongs to another actor. | 100% | La seguridad del Sistema es un punto clave y somos conscientes de ello, en nuestra aplicación no se han encontrado errores de seguridad del estilo. |
| (RG-18) The system is not deployed to the cloud, or it is not available any time during the subject(until July) | 100% | Los despliegues se han llevado a cabo los días especificados sin falta. |
| (RG-19) The system deployment is modified /updated after the delivery deadline. | 100% | No se modifica el despliegue tras la fecha de entrega del entregable. |

La siguiente tabla contiene un análisis sobre los comentarios recibidos por los usuarios piloto. Para cada comentario, se va a evaluar si es una Failure Condition y en qué grado se cumpliría, similar a la tabla anterior. Además se proporcionará una explicación.

| Mensaje | ¿Es Failure Condition? | Porcentaje de completado | Razonamiento |
| --- | --- | --- | --- |
| (C-1) Al hacer un nuevo registro manualmente, da un error pero solo se visualiza el recuadro rojo, no aparece ningún texto que te informe del tipo de error (faltaba por subir la imagen). | Sí | 20% | En este despliegue se ha comprobado que algunos campos no expresan el error cometido y simplemente indican que existe un error, se corregirá con brevedad. |
| (C-2) Con el usuario y contraseña que ofrecían no he podido iniciar sesión | No | - | No hemos podido recrear este supuesto fallo así que quizás ha sido un fallo puntual con la sincronización de la Base de Datos. No podemos interpretarlo como error. |
| (C-3) No se puede editar un anuncio de compañero.| Si | 100% | El error existía, pero cuando se ha revisado este comentario y se ha comprobado en la aplicación, estaba ya arreglado. |
| (C-4) Puedo comentar en cualquier vivienda. Puedo comentar en cualquier compañero.| No | 100% | Consideramos que esto no es ningún error, ya que no hay nada que evite que una persona pueda comentar otras publicaciones. | 
| (C-5) La ubicación de la vivienda y la superficie solo se puede añadir al editar.  | No | 100% | La ubicación y superficie se añaden con el numero del catastro, así que en cualquier caso es un error de interfaz al no entenderse. |
| (C-6) Al buscar casas, el dueño de la misma no se puede ver. Solo si invitas inquilinos  | No | - | Al recrear el error se ha podido hacer sin problemas. |
| (C-7) Botón de "ver planes" en la parte inferior de la pantalla no hace nada | No | - | Al recrear el error se ha podido hacer sin problemas. |
| (C-8) El mapa no funciona. | No | 100% | La funcionalidad de los mapas es algo que se implementará en futuras versiones asi que no consideramos esto como una failure condition. |
| (C-9) Los chats no funcionan. | No | 100% | Mismo razonamiento que (C-8) |
| (C-10) Si cambio de plan a explorador sale un icono al lado del inicio de sesión, solo sale con explorador. | No | 80% | Lo que dice el comentario es cierto, no hemos asignado ningún icono al plan propietario o Básico. Es un fallo de diseño, pero se corregirá. |
| (C-11) El botón para iniciar sesión con Google redirige al registro en bucle, da igual si ya habías rellenado el registro. Este problema impide efectivamente que los usuarios puedan acceder a su cuenta a través de Google.  | Sí | 20% | Es cierto que se han tenido ciertos errores con el inicio social, pero es un problema que se arreglará rapidamente en una nueva versión. |
| (C-12) Los filtros de búsqueda no funcionan. Al entrar en buscar viviendas, si filtras por cualquier campo, y luego cambias el filtro a uno menor (por ejemplo max inquilinos), sale lo mismo y no se actualiza. Además si le das a borrar el filtro de la fecha, se recarga la página y no tiene nada, como si fuese un bug.También pasa con el filtro de búsqueda para viviendas, si bajas un filtro una vez puesto, o lo subes, sigue sin actualizarse.   | Si | 80% | El error que se comenta aquí es cierto, si un filtro se cambia a uno menor da error, este fallo se corregirá con rapidez pero existe.  |
| (C-13) Crear anuncio de vivienda: Me deja crearlo sin calle ni teléfono por alguna razón | No | - | Al intentar recrear este error han salido los mensajes de fallo correspondientes por lo que no podemos confirmar que el error sea real. |
| (C-14) El html de listado de usuarios muestra las etiquetas parcialmente. | Sí | 100% | Este es un error que se ha comprobado que sucede cuando el tamaño de pantalla varía, en una versión posterior se cambiará para que las etiquetas se adapten al tamaño de pantalla. |
| (C-15) Al registrar usuario solo acepta como carácter especial los que se especifican entre paréntesis, ni signo de cerrar interrogación, ni guiones, etc. | No | 20% | Este fallo sucede, pero en la validation solo se comprueban los carácteres marcados, por lo que no es un error en sí, podria considerarse un error de diseño al no poder elegir más carácteres.  |
| (C-16) Al registrar usuario si copio y pego la contraseña me dice que no coinciden, pero si la escribo a mano no da problema. | No | - | Al intentar recrear el error se ha podido copiar y pegar sin problemas, consideramos que el error habrá sido un fallo puntual. |
| (C-17) Como usuario básico no me deja publicar vivienda | No | 100% | Un usuario básico no tiene permitido publicar vivienda, solo anuncio de compañero, por lo que esto no es un error. |
| (C-18) No deja seleccionar ubicación en la creación/edición de anuncio de compañero | No | - | Al intentar recrear el error se ha podido indicar la ubicación en ambos casos por lo que consideramos que no es un error. |
| (C-19) Cuando selecciono ver publicado en "anuncios de compañero" me lleva a la pantalla de crear/Editar y no a la lista de anuncios de compañero. | Si | 50% | Esto sucede y es debido a que "Ver publidado" redirige al anuncio que haya publicado el usuario, por lo que es un fallo pero no de programación, en todo caso sería un fallo de diseño ya que los botones no son intuitivos. |
| (C-20) Cuando he intentado acceder a la lista de viviendas (de nuevo, desde la vista de usuario) me ha salido un mensaje de error que no me permite acceder a la vista de viviendas. Intento meter directamente la URL y sigue sin mostrarse nada.| No | - | Al intentar recrear este error, se ha podido acceder correctamente a la lista de viviendas tanto con url como en el botón. |



## Autodefensa S3
A continuación se muestra una tabla con el listado de Failure Conditions encontradas en el documento de Software Reviewer Guidelines, un porcentaje que representa el nivel al que hemos seguido la condición indicada (100% significará que no hemos incumplido la condición, a medida que disminuya el porcentaje indicará que la failure condition se ha empezado a cumplir y por tanto, hay cierto nivel de fallo).


| Failure Condition | Porcentaje de completado | Razonamiento |
| --- | --- | --- |
|(RG-13) A legal interaction with your system results in an HTTP error perceived by user. | 90% | Se ha probado el sistema por nuestra parte y usuarios pilotos, pero no podemos garantizar con el nivel de pruebas realizadas que ciertas interacciones no resulten en lo esperado dando errores percibidos por el usuario. |
|(RG-14) A legal interaction with your system results in a panic (crash/...) perceived by user. | 95% | Es poco probable que el sistema deje de responder debido a fallos en el código. |
|(RG-15) A legal interaction with your system doesn’t have the expected behavior | 70% | Una interacción normal de un usuario no debería resultar en comportamientos inesperados, sin embargo, hemos sido capaces de localizar en varias zonas de la aplicación con ayuda de usuarios pilotos problemas como mensajes de error vacíos o el chat, que en el despliegue no funciona correctamente, los mensajes tardan en reflejarse o se pierden. Ponemos 70% porque no son problemas complejos de solucionar y para la siguiente entrega se pulirán estos y otros aspectos. |
| (RG-16) Submitting a form with wrong data is not detected (form validation) | 95% | Todos los formularios tienen validaciones tanto en frontend como en backend. |
| (RG-17) An actor can list, edit, or delete data that belongs to another actor. | 100% | La seguridad del Sistema es un punto clave y somos conscientes de ello, en nuestra aplicación no se han encontrado errores de seguridad del estilo. |
| (RG-18) The system is not deployed to the cloud, or it is not available any time during the subject(until July) | 100% | Los despliegues se han llevado a cabo los días especificados sin falta. |
| (RG-19) The system deployment is modified /updated after the delivery deadline. | 100% | No se modifica el despliegue tras la fecha de entrega del entregable. |

La siguiente tabla contiene un análisis sobre los comentarios recibidos por los usuarios piloto. Para cada comentario, se va a evaluar si es una Failure Condition y en qué grado se cumpliría, similar a la tabla anterior. Además se proporcionará una explicación.

| Mensaje | ¿Es Failure Condition? | Porcentaje de completado | Razonamiento |
| --- | --- | --- | --- |
| (C-1) Según la guía de usuario piloto el número de teléfono solo tiene como condición ser de 9 cifras; al introducir 343423423 me dice que el formato del teléfono es incorrecto. El correo también estaba mal, y no salia ningún mensaje. Al corregir el correo me ha dejado hacer el registro, por tanto el mensaje sale en el parámetro que no es. | No | - | Este usuario ha intentado introducir un teléfono no válido, por lo que el validador funciona correctamente. En España, todos los números de teléfono empiezan por uno de los siguientes dígitos (6, 7, 8, 9) y 343423423 empieza por '3'. Además el error que comenta de no recibir un mensaje de error de validar el correo, se debe a que primero se valida el teléfono. Probablemente ha corregido los dos a la vez y por eso no ha recibido error en el correo. No podemos dar otra explicación ya que no hemos podido reproducirlo. |
| (C-2) Al registrarte si no subes imagen, te aparece un recuadro rojo, pero no muestra mensaje de error alguno (asumo que es por no seleccionar imagen). | Sí | 20% | Es cierto que en la última versión han desaparecido algunos mensajes de error y este es uno de ellos. Actualmente, varias de las interacciones erróneas del registro muestran el recuadro de error vacío. Es algo que se arreglará para la próxima versión con rapidez. |
| (C-3) Al editar contraseña, se puede cortar y pegar la contraseña del usuario, es una vulnerabilidad bastante grande de seguridad. La aplicación no deberia sacar la contraseña de la base de datos, incluso si no la muestra en el html, es extremadamente facil para un atacante obtener los datos del usuario si este error no es subsanado (recomiendo que antes de enviar al cliente el usuario se copie el objeto y se cambie el campo de contraseña a nulo). | No | - | No sabemos dónde ha conseguido ver la contraseña, ya que, aparte de estar guardada en la base de datos con un hash `BCrypt A2`, nuestro usuario posee en la contraseña una anotación `@JsonProperty(access = Access.WRITE_ONLY)` que bloquea e impide la lectura de la contraseña para cualquier conversor usado (Como Jackson) para transmitir entidades al frontend mediante formato JSON. |
| (C-4) En anuncios de vivienda puedes poner un gif en vez de una imagen | No | 100% | Se nos ha hecho llegar este feedback como error. Sin embargo, un gif es un tipo de imagen y consideramos que si un usuario desea usar un gif para enseñar su vivienda en un anuncio, debería tener esa libertad. | 
| (C-5) Los filtros de vivienda pueden tener valores negativos (de 3 a -1 baños). Este error lleva estando presente desde el sprint 1 | No | - | Es una acción legal que debería estar validada, comprobando que el extremo superior es mayor que el extremo inferior. No lo consideramos una Failure Condition ya que no provoca ningún error en el sistema. Tampoco consideramos que se deba tratar como un formulario usado para introducir datos en la aplicación, ya que estos filtros solo filtran una lista en frontend. |
| (C-6) El botón de "ver anuncios de compañeros publicados" no está bien implementado, lleva a la pantalla de crear anuncio de búsqueda de piso | Sí | 80% | Este usuario está confundiendo el botón de ver su propio anuncio de búsqueda de compañeros. Está intentando acceder a ello sin poseer uno, por lo que el sistema le pide que lo cree. Esto nos lleva a pensar que hay una falta de claridad en el uso de la aplicación, lo que es un grave problema para nuestros usuarios. Se cambiará el texto de estos botones para hacerlo más claro. |
| (C-7) Si desactivas un plan de pago, para volverlo a activar hay que pagar de nuevo (no pasa siempre, pero me ha pasado un par de veces testeando) | No | - | Lo que este usuario describe es el uso típico de una aplicación con subscripciones. Si desactivas tu suscripción (Cambias a otro plan), esta deja de estar en efecto y si en algún momento quieres recuperarla deberás suscribirte de nuevo. |
| (C-8) Creo que cuando he publicado un anuncio de vivienda se han borrado todos los existentes. Cuando inicio sesión solo aparece el mio, cuando no, aparecen todos menos el mio. Es muy poco intuitivo los botones de la aplicación, no se si es comportamiento esperado o no. Si solo se ven las publicaciones en el botón debería poner "Ver MIS publicaciones", no "Ver publicaciones". | Sí | 80% | El error del usuario es precisamente el mismo que menciona. El botón al que le ha hecho click es el de "Mis anuncios de vivienda publicados", sin embargo, el texto del botón es "Anuncios de vivienda". Como nos comenta, no está clara la función del botón y se cambiará el texto para la siguiente versión. |
| (C-9) Al cerrar sesión, cuando la vuelves a abrir pierdes los roles de pago | No | - | No hemos podido comprobar que es un error, ya que no se ha podido reproducir.  |
| (C-10) La pantalla de mensajes se congela y al usuario al que se los he mandado no ha recibido nada | Sí | 50% | Es un error conocido que es culpa de la conexión entre el backend en AppEngine y el frontend en Render. En local el chat funciona, pero en la versión desplegada, cada mensaje tarda en llegar una cantidad aparentemente aleatoria de tiempo. No hemos detectado patrón alguno pero sí que el problema está en la latencia al recibir los nuevos mensajes. |
| (C-11) He probado la aplicación con un compañero y su vivienda promocionada no me aparece en la lista aunque a el si. | Sí | 80% | La razón por la que no aparece es que el segundo usuario tenía el plan básico. Al crear un anuncio, los usuarios básicos lo verán un día más tarde. El resto de usuarios posee "Acceso anticipado" y puede ver los anuncios recien creados antes que los usuarios con el plan básico. Esta funcionalidad no puede evitarse mediante una promoción. Cuando se promociona un anuncio, lo único que provoca esto es que salga más arriba en el listado, pero no desactiva el acceso anticipado. Esto nos indica nuevamente una falta de claridad en el uso de la aplicación. |
| (C-12) Al entrar como usuario anónimo no me ha mostrado la viviendas publicadas que no son las iniciales de demo. Después al registrarme si aparecen las dos viviendas que publicamos un compañero y yo. | Si | 80% | Véase la explicación del C-13. Esto es causa de un fallo de entendimiento del funcionamiento del "Acceso anticipado". Al iniciar sesión con un usuario con un plan que no es el básico se pueden ver los anuncios recién publicados.  |
| (C-13) No deja poner anuncios de vivienda | No | - | No hemos podido comprobar que es un error, ya que no se ha podido reproducir con tan poco detalle. |
| (C-14) Al acceder a viviendas para alquilar e intentar chatear me sale "Ya posee un chat con esa persona" y no me da oportunidad de seguir chateando, porque ya le envie un comentario para compañero. | No | - | El botón de iniciar chat es sólo para iniciar chat. Para entrar al chat se deberá hacer mediante el botón de chats de la barra de navegación. Creemos que junto con el mensaje de error, este hecho queda lo suficientemente claro. |
| (C-15) Publicación de anuncio de vivienda: Parece que el campo de ubicación no va. Si es así, mejor no ponerlo | Sí |80% | El campo de ubicación, junto con el de superficie son de solo lectura, ya que se rellenan al poner el catastro y presionar el botón de buscar. Se hará una llamada a una api y se rellenan con el resultado. Además mediante esta acción se rellena campo del catastro que se va a enviar (Que no es el mismo que el de buscar para evitar cambios despues de buscar). Es un fallo de claridad en el uso de la aplicación. |
| (C-16) Anuncio de vivienda (dar de alta una vivienda): No queda claro para qué sirve el botón de buscar debajo de catastro. | Si | 80% | Véase explicación del C-15. |
| (C-17) Anuncio de vivienda (dar de alta una vivienda): La superficie no deja editarla al darla de alta, una vez dada de alta si. | Si | 80% | Véase explicación del C-15. |
| (C-18) Anuncios de vivienda: Las opciones del sistema de calefacción son mejorables | No | - | Consideramos que esto es una sugerencia de mejora que, aunque seguiremos para mejorar la aplicación, no debería ser suficiente para considerarse Failure Condition. |
| (C-19) Anuncios de vivienda (dar de alta una vivienda): No se entiende el significado de los campos asociados a inquilinos al dar de alta el anuncio. | Si | 80% | Estos campos son para añadir inquilinos al anuncio de vivienda. Estos serán los usuarios que residirán en la vivienda anunciada. Es un fallo de claridad de la aplicación. |
| (C-20) Búsqueda de viviendas: El estado actual de los filtros es poco útil. Es excesivamente restrictivo. Sugiero que se parta de una opción que cumplan todas las viviendas y a partir de ahí, se van restringiendo con los filtros.| No | - | Véase explicación del C-18. |

## Autodefensa PPL
A continuación se muestra una tabla con el listado de Failure Conditions encontradas en el documento de Software Reviewer Guidelines, un porcentaje que representa el nivel al que hemos seguido la condición indicada (100% significará que no hemos incumplido la condición, a medida que disminuya el porcentaje indicará que la failure condition se ha empezado a cumplir y por tanto, hay cierto nivel de fallo).


| Failure Condition | Porcentaje de completado | Razonamiento |
| --- | --- | --- |
|(RG-13) A legal interaction with your system results in an HTTP error perceived by user. | 90% | Se ha probado el sistema por nuestra parte y usuarios pilotos, pero no podemos garantizar con el nivel de pruebas realizadas que ciertas interacciones no resulten en lo esperado dando errores percibidos por el usuario. |
|(RG-14) A legal interaction with your system results in a panic (crash/...) perceived by user. | 95% | Es poco probable que el sistema deje de responder debido a fallos en el código. |
|(RG-15) A legal interaction with your system doesn’t have the expected behavior | 100% | Todas las interacciones que el usuario pueda realizar se han comprobado y funcionan correctamente. |
| (RG-16) Submitting a form with wrong data is not detected (form validation) | 95% | Todos los formularios tienen validaciones tanto en frontend como en backend. |
| (RG-17) An actor can list, edit, or delete data that belongs to another actor. | 100% | La seguridad del Sistema es un punto clave y somos conscientes de ello, en nuestra aplicación no se han encontrado errores de seguridad del estilo. |
| (RG-18) The system is not deployed to the cloud, or it is not available any time during the subject(until July) | 90% | El despliegue ha sido realizado un día después del que se tenía planeado en un principio. |
| (RG-19) The system deployment is modified /updated after the delivery deadline. | 100% | No se modifica el despliegue tras la fecha de entrega del entregable. |

La siguiente tabla contiene un análisis sobre los comentarios recibidos por los usuarios piloto. Para cada comentario, se va a evaluar si es una Failure Condition y en qué grado se cumpliría, similar a la tabla anterior. Además se proporcionará una explicación.

| Mensaje | ¿Es Failure Condition? | Porcentaje de completado | Razonamiento |
| --- | --- | --- | --- |
| (C-1) En el registro, si falta por rellenar algun dato, aparece debajo del recuadro de imagen de prefil un error vacio. | Sí | 50% | Se va a arreglar a la mayor brevedad. |
| (C-2) El correo no se activa correctamente. | Sí | 80% | Es provocado por un error de despliegue en el que se enviaba en el correo de verificación el enlace al despliegue del S3. Se corrigió en menos de 24H pero aun así afectó a los usuarios. |
| (C-3) Las viviendas promocionadas no aparecen arriba del todo. | No | - | No se ha podido reproducir. |
| (C-4) En la segunda pantalla de registro, si no introduces una imagen aparece un marco rojo pero no muestra cual es el error (presupongo que se ha puesto imagen obligatoria aunque no esté marcado) | Sí | 50% | Se va a arreglar a la mayor brevedad. | 
| (C-5) Al registrarme con el mismo nombre de usuario de la vez anterior me ha salido un error indicando que ya existe el usuario, al pulsar una segunda vez justo debajo de ese mensaje de error me ha aparecido un mensaje en verde diciendo que el registro se ha completado exitosamente. | No | - | No se ha podido reproducir este error. |
| (C-6) Al intentar cambiar la contraseña de la cuenta antigua no me deja ya que dice que la cuenta está desactivada, pero no hay ninguna opción para reactivar una cuenta desactivada. | No | - | Una cuenta desactivada significa que no se ha activado. No es posible debido al problema del correo. Véase C-2 |
| (C-7) Al intentar registrarme de nuevo, en la segunda pantalla me sale un aviso de que el número de teléfono ya lo tiene otro usuario, en lugar de en la primera pantalla que es donde lo he introducido. | No | - | Las repeticiones de datos han de comprobarse en backend y se comprueba en la segunda pantalla, la del botón de enviar. Por lo tanto, esos mensajes de error deberán aparecer ahí. |
| (C-8) El registro me ha dicho que las contraseñas no coinciden, cuando literalmente ha sido copiar y pegar.  | No | - | Algunos sistemas/navegadores impiden el copia pega de los campos marcados como contraseña. |
| (C-9) En viviendas: Al aplicar primero un filtro pulsar aplicar, cambiarlo y volver a pulsar el botón no muestra ninguna vivienda. (Hay que borrar primero y luego volver a aplicar, pero la aplicación debería entender que al volver a aplicar debería salir la nueva selección del filtro y no mostrarse vacío) Al seguir probando, pasa solo con las barras, con los ticks y números parece que funciona bien. En cambio en los filtros de Usuario todo parece funcionar correctamente. | No | - | No se ha podido reproducir. |
| (C-10) Al intentar generar un chat como usuario no registrado me sale un mensaje de error genérico diciendo que ha habido un problema en vez de informar de que no se puede acceder al chat como usuario no registrado. | No | - | Es un error de interfaz, no deberían aparecer los botones. Aún así, la validación del backend recoge esta acción ilegal y se muestra un error al usuario. |
| (C-11) No me dejaba finalizar el proceso de registro, diciendo que el genero no estaba seleccionado cuando sí, por eso he probado el no registrarme después. | Sí | 60% |  Es un error que aparece siempre aunque el campo esté correcto. Se solucionará junto con otros errores de registro. | 
| (C-12) El filtro de precios de viviendas funciona al reves de como debería. | Sí | 50% | Es un error que se arreglará en breve. |
| (C-13) En la creacion de anuncio de vivienda, cuando se rellena el catastro, se rellenan direccion y superficie automáticamente, esto está bien, pero si quito el catástro, ahora no puedo borrar la direccion ni superficie. Ponte, que me equivocado, ahora tengo que refrescar y rellenar todo de nuevo. | No | - | Se puede introducir otra vez el catastro y buscando de nuevo se sobreescribirá. |
| (C-14) Si introduzco un catastro inventado, me deja darle a publicar sin ubicacion ni superficie. | Sí | 20% | Es un error de validación importante que no debería ocurrir. |
