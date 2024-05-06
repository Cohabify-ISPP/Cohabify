![US Logo](images/logo_us.png)

AutoDefensa
---


![Cohabify](images/Cohabify.png)

<table>
    <tbody>
        <tr>
            <td rowspan=2><p>Álvaro Urquijo Martínez (editor)</p> 
            </td>
        </tr>
        <tr>
          <td rowspan=2><p>Ismael Barrera García (editor)</p> 
            </td>
        </tr>
	<tr>
          <td rowspan=2><p>María Márquez Sierra (editora)</p> 
            </td>
        </tr>
                <tr>
            <td rowspan=2><p>María Márquez Soldán (revisora)</p> 
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
| V1.0 | Creación del documento y añadido S3 | Álvaro Urquijo Martínez |
| V1.1 | Añadidas autodefensas S1 y S2 | Ismael Barrera García |
| V1.2 | Añadida autodefensa PPL | María Márquez Sierra |


## Tabla de contenidos
- [Tabla de contenidos](#tabla-de-contenidos)
- [Autodefensa S1](#autodefensa-s1)
- [Autodefensa S2](#autodefensa-s2)
- [Autodefensa S3](#autodefensa-s3)
- [Autodefensa PPL](#autodefensa-ppl)
- [Docs](#docs)

## Autodefensa S1

Durante el sprint 1 se trabajó en tener la funcionalidad core junto al login y registro. Asimismo, realizamos toda la documentación que se nos pidió y colaboramos con el grupo 10 como usuarios piloto. Después de desplegar, detectamos que las funcionalidades del login y el registro no funcionaban en la versión desplegada, aunque sí funcionaban en la versión en local. El motivo fue un problema con las cookies debido a que el despliegue de la aplicación se tuvo que hacer separando el backend, que se encuentra en App Engine, y el frontend, que se encuentra en Render. Al hacer esto, las cookies de sesión comenzaron a ser detectadas por el navegador como cookies de terceros, por lo que las bloqueaba e impedía obtener la información de sesión. Una vez detectado el error, se intentó solucionar sin éxito, ya que gran parte de la funcionalidad se estuvo probando hasta el último momento y se acabó desplegando el día antes de la entrega.

El motivo del suspenso de S1 fue “A legal interaction with your system doesn’t have the expected behaviour.”.Consideramos que solicitar un despliegue de toda la funcionalidad core con feedback en la primera entrega puede ser algo mal planteado, ya que, además, es el sprint más corto de los 3. Al tener que gestionar la creación de un proyecto y los bloqueos que hay cuando se inicia, la puesta en marcha de los grupos de trabajo y la configuración y los tutoriales de los entornos, gran parte de este tiempo no puede ser usado en desarrollo, por lo que se vuelve insostenible tener todo el core funcional y testeado. 

En resumen, la carga de trabajo que había que realizar para conseguir la primera versión del proyecto con toda la funcionalidad core y la cantidad de documentos, sumado al hecho de solicitar la versión desplegada junto al feedback de los usuarios piloto, hizo que fuera físicamente imposible entregar un producto perfecto dada la alta exigencia de las FC. De hecho, en el sprint 2 el pilotaje ha mejorado en gran medida, ya que se ha podido ofrecer la aplicación con tiempo a los usuarios piloto para encontrar los errores y que sean arreglados antes de la entrega. Esta mejora, sin embargo, ha sucedido porque en el S2 sí teníamos una base sobre la que trabajar, y no se tuvo que implementar funcionalidad de forma apresurada.

Como grupo hemos intentado dar la mejor versión de nuestro producto y estamos orgullosos del trabajo que hemos realizado, pero no creemos que sea realista tener en dos semanas un producto de estas dimensiones con toda la funcionalidad core perfecta y con toda la documentación correcta. Como conclusión, creemos que todos los puntos que se nos pedían se han intentado cumplir de la mejor manera posible, y que el esfuerzo del grupo ha sido suficiente para considerar que el entregable es apto aún con algunos errores en el despliegue. Por tanto, consideramos que el entregable debería considerarse como apto.


## Autodefensa S2
Durante el sprint 2 se ha trabajado en continuar con el desarrollo planificado y además responder a la mayor cantidad de feedback posible tanto de profesores como de usuarios pilotos. El equipo considera que pese a las dificultades encontradas se ha trabajado bien y se han solventado problemas graves como la pérdida de un importante miembro del equipo de manera temporal. Pese a ello, sabemos que no todo lo que se especificaba en el documento de failure conditions se cumplía del todo, pues detectamos pequeños errores como puede ser no poseer una carpeta “demos” en el repositorio, pese a que siempre nos hemos preocupado por mantener disponibles las demos desde nuestro canal de Youtube, accesible desde la landing page. Además, hubo un problema con el login social, ya que se detectó que google tiene varias claves públicas para firmar los JWT de sesión del OAuth, y puede usar cualquiera de ellas. Aún habiendo solucionado esto antes del redespliegue, en el S3 hemos detectado que google, cada cierto tiempo, cambia las claves públicas, por lo que en el momento en el que fueron cambiadas dejó de funcionar también en el redespliegue. 

Como mejora ante el S1, hemos considerado, además del feedback de los profesores en clase, el feedback de los usuarios piloto, que fue recogido con varios días de antelación y se desplegó una nueva versión de la aplicación solucionando algunos problemas encontrados por estos. 

El equipo considera que pese a los pequeños errores detectados tras la entrega, cumplimos los suficientes requisitos y se ha esforzado lo bastante como para aprobar el entregable, teniendo en cuenta la carga de trabajo e intentando siempre cumplir con la mayor calidad posible. Todos los miembros del equipo aspiran a obtener la máxima nota posible, y por ello estamos realizando un gran esfuerzo que creemos que no debe ser empañado por ciertos aspectos de la entrega que consideramos no son tan importantes. Por tanto, consideramos que el entregable debería considerarse como apto.

A continuación se muestra una tabla con el listado de Failure Conditions, un porcentaje que representa el nivel al que hemos seguido la condición indicada (100% Significará que no hemos inclumplido la condición, a medida que disminuya el porcentaje indicará que la failure condition se ha incumplido en mayor o menor medida, siendo 0% un fallo total al cumplir la misma):

| Failure Condition | Porcentaje de completado | Razonamiento en caso de no estar completado al 100% |
| --- | --- | --- |
|T-1) Failure to give notice (at the beginning of an evaluation class) that a member is absent.| ✔️ ||
|T-2) Taking longer than the stipulated time for the presentation (even if it is only a few seconds).| ✔️ ||
|T-3) Finishing the presentation before the last minute.| ✔️ ||
|T-4) Use a different presentation file than the one included in the deliverable.| ✔️ ||
|T-5) Not respecting or not reacting to the feedback given in class without an explicit justification explainedin the presentation.| ✔️ ||
|T-6) Failure to explicitly include any of the expected aspects in the presentation document as they wereexplained in the previous class discussion.| ✔️ ||
|T-7) Including a document in the repository as part of a deliverable without an appropriate cover page that, at least, should include: |100%||
|&nbsp; &nbsp; • EV Group number. |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Group name. |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Name of the deliverable (e.g. Devising a Project, Sprint 1, Sprint 2, etc). |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • A descriptive title of the document (e.g. “Presentation” or “Reviewer’s Guide”). |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Name(s) and surname(s) of the team members contributing to the document, indicating the typeof contribution made. Only the names of those who have contributed should be entered, with aclear indication of the type of contribution (writing, revision, etc.) which must match thecorresponding clockify entry. |&nbsp; &nbsp; &nbsp; ✔️ ||
|T-8) Not including the following issues in your working planning representing a review performed by anymember of the group.  All of them must be tracked with Clockify and the name must be as follows: | 100% | |
|&nbsp; &nbsp; • FC terms review. |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Expected aspects review |&nbsp; &nbsp; &nbsp; ✔️ ||
|T-9) Not including the following content in the document of contributions to the shared Knowledge Base (X-report.pdf): | 100% | |
|&nbsp; &nbsp; • The link to the shared Knowledge Base. |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • The specific content that the team has contributed, which should include, at least, the specific feedback received and the annotations on the general feedback. |&nbsp; &nbsp; &nbsp; ✔️ || 
|&nbsp; &nbsp; • The consolidation actions that have been carried out. If none have been carried out, specify "No consolidation actions have been carried out".|&nbsp; &nbsp; &nbsp; ✔️ ||
| T-10) Failure to make a correct delivery considering the following instructions concerning the GitHub repository: | ✔️ ||
|&nbsp; &nbsp; • Provide the GitHub repository URL.|&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Failure to make a mistake in the format or file name in any of the files that conform to the deliverable to be included in a “docs” folder of the GitHub repository: |&nbsp; &nbsp; &nbsp; 100% ||
|&nbsp; &nbsp; &nbsp; &nbsp; 01. Presentation. \(presentacion.md) |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 02. Report of contributions to the common KB. \(report.md) |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 03. Report of user pilots (at least considering status, plan, feedback management, etc.) \(pilots.md) |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 04. Individual performance evaluation \(PerformanceEvaluation.md) following the performance evaluation template provided in EV. |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 05. Time-effort report for each member and for the group \(Time-Effort-report.md). |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 06. Report detailing the AI usage \(AI-usage.md). |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 07. Commitment Agreement (at least considering version, status, etc.) \(commitmentAgreement.md). |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 08. Pilot Users Commitment Agreement or Agreements (at least considering version, status, etc.) \(pilotUsersCommitmentAgreement.md or several of them). |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; &nbsp; &nbsp; 09. Any other document or video you consider should be in md, pdf, ppt, pptx, or mp4 format. |&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Include a proper Tag for the delivered version of the code and documentation (e.g. 0.1 for #DPdeliverable, 1.0 for #S1 deliverable, ...). |&nbsp; &nbsp; &nbsp; ✔️ ||
|&nbsp; &nbsp; • Perform a GitHub release linking the proper tag, which must include a zip file with all repository content. |&nbsp; &nbsp; &nbsp; ✔️ ||
|T-11) Not following the instructions of the software reviewer's guidelines; or incurring in any of the failure conditions presented in those guidelines.| ✔️ ||
|T-12) Not including in the docs repository folder the pilot users performance evaluation \(PilotUsersPerformanceEvaluation.md) following the pilot users performance evaluation template provided in EV.| ✔️ ||
|T-13) Not including in the docs repository folder all information received from pilot users \(InformationProvidedByPilotUsers.md) considering the MinimumInformationFromPilotUsers_Gathered-ByForm document.| ✔️ ||
|T-14) Not including the following issues in your working planning representing a review performed by anymember of the group. All of them must be tracked with Clockify and the name must be as follows: | ❌ | Se puso en el último tramo del sprint y no se hizo correctamente en ese tramo. Se aplica a partir del S3.|
|&nbsp; &nbsp; • Deployed Software review |&nbsp; &nbsp; &nbsp; ❌ ||
|T-15) Having text in the slides that is not readable from the back of the class.| ✔️ ||
|T-16) Software Demo not showing realistic data or information.|❓| Hay fotos pertenecientes a famosos, pero no consideramos que sean poco realistas.|

## Autodefensa S3
A continuación se muestra una tabla con el listado de Failure Conditions, un porcentaje que representa el nivel al que hemos seguido la condición indicada (100% Significará que no hemos incumplido la condición, a medida que disminuya el porcentaje indicará que la failure condition se ha incumplido en mayor o menor medida, siendo 0% un fallo total al cumplir la misma).


| Failure Condition | Porcentaje de completado | Razonamiento |
| --- | --- | --- |
|(I-1) Not being at both, the beginning and the end of an evaluation class; with the appropriate justification | - | Se comprobará el día de evaluación. |
|(I-2) Failure to respond to random feedback questions regarding a task that is part of his/her responsibility| - | Se comprobará el día de evaluación. |
|(I-3) Not including (and performing) an issue in your working planning for watching each theory pill shared inthe EV content. Such an issue must also be tracked with Clockify, and the name must be“watch_theory_pill_X” where X is the name of the theory pill name| 100% | Todos los miembros del equipo han visto las píldoras teóricas y tienen la tarea indicada en clockify. |
| (T-1) Failure to give notice (at the beginning of an evaluation class) that a member is absent. | - | Se comprobará el día de evaluación. |
| (T-2) Taking longer than the stipulated time for the presentation (even if it is only a few seconds). | - | Se comprobará el día de evaluación |
| (T-3) Finishing the presentation before the last minute. | - | Se comprobará el día de evaluación. |
| (T-4) Use a different presentation file than the one included in the deliverable. | - | Se comprobará el día de evaluación. |
| (T-5) Not respecting or not reacting to the feedback given in class without an explicit justification explainedin the presentation. | - | Se comprobará el día de evaluación. |
| (T-6) Failure to explicitly include any of the expected aspects in the presentation document as they wereexplained in the previous class discussion. | 100% | Tras la realización de la presentación se han realizado x pruebas con varios compañeros que aprueban que el contenido sea el esperado y evitar errores en el documento. |
| (T-7) Including a document in the repository as part of a deliverable without an appropriate cover page that,at least, should include: | 100% | Todos los documentos entregados siguen una plantilla clara de la organización creada con el fin de que se cumpla este requisito. Por tanto todos los documentos han sido revisados y contienen exactamente lo especificado en la failure condition. |
  • EV Group number. | ✔️ |
  • Group name. | ✔️ |
  • Name of the deliverable (e.g. Devising a Project, Sprint 1, Sprint 2, etc). | ✔️ |
  • A descriptive title of the document (e.g. “Presentation” or “Reviewer’s Guide”). | ✔️ |
  • Name(s) and surname(s) of the team members contributing to the document, indicating the typeof contribution made. Only the names of those who have contributed should be entered, with aclear indication of the type of contribution (writing, revision, etc.) which must match thecorresponding clockify entry. | ✔️ |
| (T-8) Not including the following issues in your working planning representing a review performed by anymember of the group. All of them must be tracked with Clockify and the name must be as follows:| 100% | Se ha revisado que se incluyan las tareas y que se hayan realizado al igual que se refleje en clockify. |
  • FC terms review. (referring terms of current document) | ✔️ |
  • Expected aspects review. (referring to T-6 aspects) | ✔️ |
| (T-9) Failure to make a correct delivery considering the following instructions concerning the Gitub repository: | 100% | Se ha reflejado el enlace al repositorio y se ha revisado que se cumpla con los documentos requeridos además de que su nombre sea correcto. |
  • Provide the GitHub repository URL.1 See the “Justification Guidelines for Evaluation Days” (JGED) document in the EV. | ✔️ |
  • Failure to make a mistake in the format or file name in any of the files that conform to the deliverableto be included in a [docs](#docs) folder of the GitHub repository: | ✔️ |
  • Include a proper Tag for the delivered version of the code and documentation (e.g. 0.1 for #DPdeliverable, 1.0 for #S1 deliverable, ...). | ✔️ |
  • Perform a GitHub release linking the proper tag, which must include a zip file with all repositorycontent. | ✔️ |
| (T-10) Not following the instructions of the software reviewer's guidelines; or incurring in any of the failureconditions presented in those guidelines. | 100% | Se han seguido las instrucciones que se especifican en el documento indicado. |
| (T-11) Not including in the docs repository folder the pilot users performance evaluation, evaluatingreviewer ISPP group as pilot user (PilotUsersPerformanceEvaluation.md) following the pilot usersperformance evaluation template provided in EV. Each and every group must be pilot user. (e.g. Group 1must include a PilotUsersPerformanceEvaluation.md evaluating the performance of Group 7 as pilot users). | 100% | El documento está incluido. |
| (T-12) Not including in the docs repository folder all information received from pilot users (reviewer ISPPgroup) (InformationProvidedByPilotUsers.md) considering the MinimumInformationFromPilot-Users_Gathered-ByForm document. (e.g. Group 1 must include a MinimumInformationFromPilotUsers-_GatheredByForm.md with the information obtained from Group 7 as pilot users) | 100% | Se incluye el documento y se sigue la guía como se indica. |
| (T-14) Not including the following issues in your working planning representing a review performed by anymember of the group. All of them must be tracked with Clockify and the name must be as follows: | 100% | Se incluye la tarea y ha sido realizada y se refleja en clockify |
• Deployed Software review | ✔️ |
| (T-15) Having text in the slides that is not readable from the back of the class. | 100% | - |
| (T-16) Software Demo not showing realistic data or information. | 100% | Se han modificado datos que podían llevar a fallar esta condición y se ha verificado que la cumpla con certeza. |

## Autodefensa PPL
A continuación se muestra una tabla con el listado de Failure Conditions, un porcentaje que representa el nivel al que hemos seguido la condición indicada (100% Significará que no hemos incumplido la condición, a medida que disminuya el porcentaje indicará que la failure condition se ha incumplido en mayor o menor medida, siendo 0% un fallo total al cumplir la misma).


| Failure Condition | Porcentaje de completado | Razonamiento |
| --- | --- | --- |
|(I-1) Not being at both, the beginning and the end of an evaluation class; with the appropriate justification | - | Se comprobará el día de evaluación. |
|(I-2) Failure to respond to random feedback questions regarding a task that is part of his/her responsibility| - | Se comprobará el día de evaluación. |
|(I-3) Not including (and performing) an issue in your working planning for watching each theory pill shared inthe EV content. Such an issue must also be tracked with Clockify, and the name must be“watch_theory_pill_X” where X is the name of the theory pill name| 100% | Todos los miembros del equipo han visto las píldoras teóricas y tienen la tarea indicada en clockify. |
| (T-1) Failure to give notice (at the beginning of an evaluation class) that a member is absent. | - | Se comprobará el día de evaluación. |
| (T-2) Taking longer than the stipulated time for the presentation (even if it is only a few seconds). | - | Se comprobará el día de evaluación |
| (T-3) Finishing the presentation before the last minute. | - | Se comprobará el día de evaluación. |
| (T-4) Use a different presentation file than the one included in the deliverable. | - | Se comprobará el día de evaluación. |
| (T-5) Not respecting or not reacting to the feedback given in class without an explicit justification explained in the presentation. | - | Se comprobará el día de evaluación. |
| (T-6) Failure to explicitly include any of the expected aspects in the presentation document as they were explained in the previous class discussion. | 100% | Tras la realización de la presentación se han realizado pruebas con varios compañeros que aprueban que el contenido sea el esperado y evitar errores en el documento. |
| (T-7) Including a document in the repository as part of a deliverable without an appropriate cover page that, at least, should include: | 100% | Todos los documentos entregados siguen una plantilla clara de la organización creada con el fin de que se cumpla este requisito. Por tanto todos los documentos han sido revisados y contienen exactamente lo especificado en la failure condition. |
  • EV Group number. | ✔️ |
  • Group name. | ✔️ |
  • Name of the deliverable (e.g. Devising a Project, Sprint 1, Sprint 2, etc). | ✔️ |
  • A descriptive title of the document (e.g. “Presentation” or “Reviewer’s Guide”). | ✔️ |
  • Name(s) and surname(s) of the team members contributing to the document, indicating the type of contribution made. Only the names of those who have contributed should be entered, with a clear indication of the type of contribution (writing, revision, etc.) which must match the corresponding clockify entry. | ✔️ |
| (T-8) Not including the following issues in your working planning representing a review performed by any member of the group. All of them must be tracked with Clockify and the name must be as follows:| 100% | Se ha revisado que se incluyan las tareas y que se hayan realizado al igual que se refleje en clockify. |
  • FC terms review. (referring terms of current document) | ✔️ |
  • Expected aspects review. (referring to T-6 aspects) | ✔️ |
| (T-9) Failure to make a correct delivery considering the following instructions concerning the Gitub repository: | 100% | Se ha reflejado el enlace al repositorio y se ha revisado que se cumpla con los documentos requeridos además de que su nombre sea correcto. |
  • Provide the GitHub repository URL.1 See the “Justification Guidelines for Evaluation Days” (JGED) document in the EV. | ✔️ |
  • Failure to make a mistake in the format or file name in any of the files that conform to the deliverableto be included in a [docs](#docs) folder of the GitHub repository: | ✔️ |
  • Include a proper Tag for the delivered version of the code and documentation (e.g. 0.1 for #DPdeliverable, 1.0 for #S1 deliverable, ...). | ✔️ |
  • Perform a GitHub release linking the proper tag, which must include a zip file with all repositorycontent. | ✔️ |
| (T-10) Not following the instructions of the software reviewer's guidelines; or incurring in any of the failure conditions presented in those guidelines. | 100% | Se han seguido las instrucciones que se especifican en el documento indicado. |
| (T-11) Not including in the docs repository folder the pilot users performance evaluation, evaluating reviewer ISPP group as pilot user (PilotUsersPerformanceEvaluation.md) following the pilot usersperformance evaluation template provided in EV. Each and every group must be pilot user. (e.g. Group 1 must include a PilotUsersPerformanceEvaluation.md evaluating the performance of Group 7 as pilot users). | 100% | El documento está incluido. |
| (T-12) Not including in the docs repository folder all information received from pilot users (reviewer ISPP group) (InformationProvidedByPilotUsers.md) considering the MinimumInformationFromPilot-Users_Gathered-ByForm document. (e.g. Group 1 must include a MinimumInformationFromPilotUsers-_GatheredByForm.md with the information obtained from Group 7 as pilot users) | 100% | Se incluye el documento y se sigue la guía como se indica. |
| (T-14) Not including the following issues in your working planning representing a review performed by any member of the group. All of them must be tracked with Clockify and the name must be as follows: | 100% | Se incluye la tarea, ha sido realizada y se refleja en clockify |
• Deployed Software review | ✔️ |
| (T-15) Having text in the slides that is not readable from the back of the class. | 100% | - |
| (T-16) Software Demo not showing realistic data or information. | 100% | Se han modificado datos que podían llevar a fallar esta condición y se ha verificado que la cumpla con certeza. |


## Docs
1. Presentation (presentacion.md)
2. Report of contributions to the common KB (report.md), including at least:o The specific content that the team has contributed, which should include, at least, thespecific feedback received and the annotations on the general feedback.o The consolidation actions that have been carried out. If none have been carried out,specify "No consolidation actions have been carried out".
3. Report of user pilots (at least considering status, plan, feedback management, etc.) (pilots.md)
4. Individual performance evaluation (PerformanceEvaluation.md) following the performanceevaluation template provided in EV.
5. Time-effort report for each member and for the group (Time-Effort-report.md).
6. Report detailing the AI usage (AI-usage.md).
7. Commitment Agreement (at least considering version, status, etc.) (commitmentAgreement.md).
8. Pilot Users Commitment Agreement or Agreements (at least considering version, status, etc.)(pilotUsersCommitmentAgreement.md or several of them).
9. Any other document or video you consider should be in md, pdf, ppt, pptx or mp4 format.
