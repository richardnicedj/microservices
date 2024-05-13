# Proyecto de Microservicios RESTfull

## Descripción

Este proyecto es una aplicación desarrollada en Java con Spring Boot el manejo de cliente, cuentas, movimientos
y para la generación de reportes de estado de cuenta bancarios. 
La aplicación utiliza una base de datos MySQL para almacenar la información.

## Tecnologías Utilizadas

- Java
- Spring Boot
- MySQL
- Maven
- Lombok
- Gson

## Estructura del Proyecto

El proyecto sigue una estructura estándar de Spring Boot, con los siguientes paquetes principales:

- `clientes-service`: Microservicio para la gestión de clientes.
    - `controller`: Contiene los controladores RESTful para la gestión de clientes.
    - `service`: Contiene la lógica de negocio relacionada con los clientes.
    - `repository`: Contiene las interfaces de repositorio para interactuar con la base de datos de clientes.
    - `model`: Contiene las clases de modelo que representan las entidades de cliente.

- `cuentas-service`: Microservicio para la gestión de cuentas y movimientos bancarios.
    - `controller`: Contiene los controladores RESTful para la gestión de cuentas y movimientos.
    - `service`: Contiene la lógica de negocio relacionada con las cuentas y movimientos.
    - `repository`: Contiene las interfaces de repositorio para interactuar con la base de datos de cuentas y movimientos.
    - `model`: Contiene las clases de modelo que representan las entidades de cuentas y movimientos.

## Configuración y Ejecución

1. Clonar el repositorio del proyecto: `git clone https://github.com/richardnicedj/microservices.git`
2. Importar cada microservicio en tu IDE preferido.
3. Configurar la base de datos MySQL y las credenciales de conexión en el archivo `application.properties` de cada microservicio.
4. Ejecutar cada microservicio como una aplicación Spring Boot.

## Endpoints de la API REST

### Microservicio de Clientes

1. `GET /clientes`: Obtiene la lista de todos los clientes.
2. `GET /clientes/{id}`: Obtiene un cliente por su ID.
3. `POST /clientes`: Crea un nuevo cliente.
4. `PUT /clientes/{id}`: Actualiza la información de un cliente existente.
5. `DELETE /clientes/{id}`: Elimina un cliente por su ID.

### Microservicio de Cuentas y Movimientos

1. `GET /cuentas`: Obtiene la lista de todas las cuentas.
2. `GET /cuentas/{id}`: Obtiene una cuenta por su ID.
3. `POST /cuentas`: Crea una nueva cuenta.
4. `PUT /cuentas/{id}`: Actualiza la información de una cuenta existente.
5. `DELETE /cuentas/{id}`: Elimina una cuenta por su ID.
6. `GET /movimientos`: Obtiene la lista de todos los movimientos.
7. `GET /movimientos/{id}`: Obtiene un movimiento por su ID.
8. `POST /movimientos`: Crea un nuevo movimiento.
9. `PUT /movimientos/{id}`: Actualiza la información de un movimiento existente.
10. `DELETE /movimientos/{id}`: Elimina un movimiento por su ID.
## Contribución

Las contribuciones son bienvenidas. Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz commit de ellos (`git commit -am 'Añadir nueva funcionalidad'`).
4. Sube tus cambios a tu repositorio fork (`git push origin feature/nueva-funcionalidad`).
5. Crea un nuevo Pull Request.

## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).
