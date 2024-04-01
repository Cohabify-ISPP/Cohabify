![US Logo](images/logo_us.png)

Guía de Revision
---


![Cohabify](images/Cohabify.png)

<table>
    <tbody>
        <tr>
            <td rowspan=2> Galeano de Paz, Guillermo (editor)<p></p> Morato Navarro, Olegario (revisor)<p></p> Robles Russo, Eduardo (editor)
            </td>
        </tr>
    </tbody>
</table>
<br></br> 
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

<br></br>
<br></br>
<br></br>
<br></br>
<br></br>
<br></br>


# Tabla de contenidos
- [Tabla de contenidos](#tabla-de-contenidos)
- [Resumen ejecutivo](#resumen-ejecutivo)
- [Guía de Revisión de Software](#guía-de-revisión-de-software)

# Resumen ejecutivo

Para todos los entregables donde se debe presentar el Software, se requiere incluir en la carpeta de documentos del repositorio de Github un documento separado llamado “Revision.md”. Este documento debe incluir:
- Un mapeo explícito de los casos de uso a las interacciones en el software que hacen explícito cómo realizar tu caso de uso principal.
- Datos necesarios para realizar la revisión, como: usuarios, contraseñas o ejemplos de conjuntos de datos de entrada si son complejos, requisitos potenciales para usar el sistema (por ejemplo, “activar la ubicación”) y un enlace a la demostración mostrada en la clase de evaluación y almacenada en una carpeta de demostración dentro de tu repositorio de GitHub.

# Guía de Revisión de Software

  - Un mapeo explícito de los casos de uso a las interacciones en el software que hacen explícito cómo realizar su caso de uso principal:
     -  **Registro**: Desde la pantalla de inicio de sesión aparecerá un link que pone "¿No tienes cuenta?, regístrate." Al pulsar en este se mostrará por pantalla el registro. En este tenemos seis campos, cada uno de ellos con su propia validación. Al modificarlos podremos observar que si lo que hemos metido no es válido se mostrará en rojo. Una vez se han rellenado correctamente los seis campos, marcado el aceptar términos y condiciones y pulsado en siguiente se nos moverá a una segunda pantalla de registro, en la que podremos seleccionar nuestro género, los tags que nos interesen y una foto de perfil. En este punto podremos volver a la pantalla anterior o registrarnos.
    <br></br>
    ![Cohabify](images/ReviewGuideless/output_0009.png)
    <br></br>
    - **Inicio de sesión**: Al iniciar sesión tenemos dos opciones, el inicio de sesión tradicional y mediante google. En la primera opción con introducir los campos de un usuario registrado en el sistema se iniciaría sesión correctamente, apareciendo una pequeña ventana indicando el éxito de la operación.
    - **Inicio de sesión mediante Google**: Es un poco diferente. Al seleccionar el inicio de sesión con la cuenta deseada, se moverá al usuario a una pantalla de registro modificada en la que se requerirán campos no aportados por google o que podrían interesar al usuario cambiar. Estos campos son el nombre completo, el nombre de usuario, el teléfono y el correro, este último campo está deshabilitado y se muestra para que el usuario sepa con qué correo se está haciendo el proceso.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0004.png)
        ![Cohabify](images/ReviewGuideless/output_0012.png)
    <br></br>
    - **Editar perfil**: Una vez que un usuario ha iniciado sesión, puede ver su perfil. Aquí, tienen la opción de editar su perfil. Una vez que han realizado los cambios deseados, pueden guardarlos y su perfil se actualizará.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0017.png)
        ![Cohabify](images/ReviewGuideless/output_0019.png)
        ![Cohabify](images/ReviewGuideless/output_0020.png)
    <br></br>
    - **Publicar anuncios**: Los usuarios tienen la capacidad de publicar anuncios. Estos anuncios son visibles para otros usuarios en la plataforma. Además de publicar anuncios, los usuarios también pueden editarlos, verlos, marcarlos con “me gusta” y eliminarlos si así lo desean.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0026.png)
        ![Cohabify](images/ReviewGuideless/output_0029.png)
        ![Cohabify](images/ReviewGuideless/output_0036.png)
        ![Cohabify](images/ReviewGuideless/output_0040.png)
        ![Cohabify](images/ReviewGuideless/output_0041.png)
    <br></br>
    - **Publicar anuncios de vivienda**:  Los usuarios también pueden publicar anuncios de vivienda. Para hacer esto, deben completar los campos requeridos. Si la vivienda que están anunciando ya está registrada en la base de datos de Cohabify, la información de la vivienda se carga automáticamente, lo que ahorra tiempo al usuario. Para publciar un anuncio de vivienda tienes que estar con el plan de propietario.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0044.png)
        ![Cohabify](images/ReviewGuideless/output_0047.png)
        ![Cohabify](images/ReviewGuideless/output_0049.png)
        ![Cohabify](images/ReviewGuideless/output_0060.png)
        ![Cohabify](images/ReviewGuideless/output_0062.png)
    <br></br>
    - **Buscar anuncios de vivienda**: Los usuarios pueden buscar anuncios de vivienda en la plataforma. Para ayudarles a encontrar la vivienda que mejor se adapte a sus necesidades, pueden aplicar varios filtros a su búsqueda.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0072.png)
        ![Cohabify](images/ReviewGuideless/output_0078.png)
    <br></br>
    - **Comentar en anuncios de vivienda**: Los usuarios pueden dejar comentarios en los anuncios de vivienda. Estos comentarios son visibles para otros usuarios. Una vez que un usuario ha dejado un comentario, este aparecerá en el anuncio.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0095.png)
        ![Cohabify](images/ReviewGuideless/output_0097.png)
    <br></br>
    - **Ver listado de anuncios de compañeros de piso**: Los usuarios pueden ver un listado de anuncios de compañeros de piso. Al igual que con los anuncios de vivienda, pueden aplicar filtros a su búsqueda para ayudarles a encontrar a la persona adecuada.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0099.png)
        ![Cohabify](images/ReviewGuideless/output_0102.png)
    <br></br>
    - **Comentar y dar “me gusta” en perfiles de usuarios**: Los usuarios pueden dejar comentarios y dar “me gusta” en los perfiles de otros usuarios. Estas interacciones son visibles para otros usuarios. También podemos eliminar el comentario que hemos puesto.
    <br></br>
        ![Cohabify](images/ReviewGuideless/output_0109.png)
        ![Cohabify](images/ReviewGuideless/output_0115.png)
        ![Cohabify](images/ReviewGuideless/output_0118.png)
        ![Cohabify](images/ReviewGuideless/output_0110.png)
  
    <br></br> 
    <br></br> 
    <br></br> 
    <br></br>  
    <br></br>
    <br></br>
    <br></br>

- Datos necesarios para realizar la revisión, tales como: usuarios, contraseñas, o ejemplos de conjuntos de datos de entrada si son complejos; en particular:

| Información | Detalles |
|-------------|----------|
| URL de la página de inicio | https://cohabify.github.io/ |
| Credenciales de usuarios | Usuarios: **javgaragu90**, **manlovper90**<br>Contraseña: igual al nombre de usuario |
| URL de la plataforma de implementación | https://s2-cohabify.onrender.com/ |
| URL del repositorio de Github | https://github.com/Cohabify/Cohabify |
| URL y credenciales de la herramienta de seguimiento | https://dashboard.bluejay.governify.io/login<br>Usuario: **user**<br>Contraseña: bluejay |
| Requisitos potenciales para usar el sistema | No tenemos requisitos potenciales para usar la aplicación. |
| Enlace a la demostración | https://www.youtube.com/watch?v=r9J_1j0QP_A&list=PLdQqgrEneE1kv-LZOBGT_prwQPARLPeYY&index=3&t=11s |
