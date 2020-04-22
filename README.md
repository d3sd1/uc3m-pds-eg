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