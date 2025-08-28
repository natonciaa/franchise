Franquicias – Spring WebFlux

Este proyecto implementa un API reactivo en Spring Boot + WebFlux siguiendo arquitectura hexagonal.
Permite gestionar franquicias, sus sucursales y los productos en cada una.

Funcionalidades

* Crear una franquicia.
* Agregar sucursales a una franquicia.
* Agregar/eliminar productos a una sucursal.
* Modificar el stock de un producto.
* Consultar el producto con mayor stock por sucursal de una franquicia.

Tecnologías

* Java 21
* Spring Boot 3
* Spring WebFlux
* Reactor
* MongoDB
* JUnit 5 + StepVerifier 

Ejecución en local
1. Requisitos previos

* Java 21
* Gradle 8.14.3
* Docker (para MongoDB)

2. Levantar base de datos MongoDB con Docker
docker run -d --name mongo -p 27017:27017 mongo:6
3. Compilar y ejecutar
./gradlew build
./gradlew bootRun
4. Coleccion Postman 

Se incluye la colección para probar todos los endpoints del proyecto.

1. Descarga el archivo  franchises.postman_collection.json ubicado en la raiz del proyecto
2. Importa la colección en Postman.
3. Ajustar las variables `franchiseId`, `branchId` y `productId` según los objetos creados.
