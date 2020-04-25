**Información útil**:
- **Wiki**: Situada en el menú izquierdo. Contiene tutoriales y desgloses de trabajos realizados.
- **Issues**: Situada en el menú izquierdo, contiene problemas, bugs y resoluciones de los mismos.


**Requisitos proyecto**:
- **Java 8 en adelante**.
- **Maven**.

**Trabajos BASE (Práctica 4)**:
- Texto indentado.
- Nomenclatura respetada.
- Homogeneizados nombres.
- Separadas clases por grado de responsabilidad.
- Eliminados System.out.println innecesarios.
- Eliminados comentarios innecesarios.
- Eliminados printStackTrace innecesarios, dejando el mensaje de responsabilidad centralizado (BUM).
- Encapsulación con getters/setters.
- Eliminada duplicidad y redundancia de código.
- Obviamente, compila y corre los tests.
- Contiene herencia con pull-up.
- Tamaño de las clases reducido.
- Refactorizados los métodos de las clases.
- Creados singletons para los accesos de las bases de datos.
- Se ha usado Strategy para la encriptación de ficheros.
- Se han usado bloques try/catch para homogeneizar las excepciones salientes.
- Se han usado los bloques try/catch apartado por apartado, para prevenir excepciones no esperadas/innecesarias en otro contexto.
- Se han reducido todos los argumentos de las funciones a 3 o menos.
- Se ha homogeneizado el nivel de abstracción.
- Se han abstraido los métodos para que cumplan una función definida y no redundante.
- Se ha reducido el tamaño de los métodos.
- Nomenclatura y naming de clases/métodos ajustado.
- Se ha seguido la normativa de código.
- Subidos XML de ejecución del código de tests satisfactorios.
- Usado patrón Singleton en TokenManager (-> Renamed to TokenController), TokenRequestsStore (renamed to TokenRequestDatabase) y TokensStore (renamed to TokenDatabase)
                            
**Trabajos EXTRA (Práctica 4)**:
- Se ha refactorizado usando tipos genéricos.
- Se ha agregado la herramienta de checking SonarCloud mediante integración continua.
- Se ha generado la documentación completa "JavaDoc" sobre la carpeta "JavaDoc".
- Integrado el proyecto con Telegram y Slack, con manuales de integración (usando CI/CD).
- Los tests se corren automáticamente al subir un commit al repositorio (usando CI). Se incluye manual.
- Creadas interfaces para promover el buen uso y escalabilidad del proyecto.
- Eliminados ficheros de database del repositorio de git para promover la transparencia de datos y compartición de archivos / privacidad.
- Agregado JavaDoc a todos los archivos del proyecto.
- Patronización de diseño MVC**.
- Se ha adaptado para permitir el uso con Java 8 o superior.
- Se han usado operadores diamond/lambda.
- Se han usado argumentos de tamaño variable.
- Refactorizados y agregados toString, Hashing, etc a todos los objetos modelo.
- Código redundante en tiempo real con el siguiente repositorio: https://github.com/d3sd1/uc3m-pds-eg
- La calidad de código basada en SonarCloud puede verse mediante una exposición, que podemos tener durante cualquier meeting.
- Revisados errores de seguridad del proyecto.
- Se ha utilizado Jackson ya que Gson no ofrecía todas las posibilidades requeridas para nuestra casuística, pero se intentó implementar con ello.

** TESTS MODIFICADOS Y POR QUE **
TokenRequestTest -> WithSeparattorAtTheEnd.json -> Valor inicial: Error: invalid date data in JSON structure.,
 valor modificado a: Error: JSON object cannot be created due to incorrect representation
 motivo: el json está malformado, no tiene nada que ver con la fecha, así que el mensaje que debería devolver es el que se ha dado en la modificación.