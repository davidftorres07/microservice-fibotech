Para inicializar el servicio primero verificamos que no haya ninguna instancia del contenedor construida.
```console
docker compose down
```
Para iniciar la aplicación con las pruebas correspondientes executamos el siguiente comando:
 ```console
./mvnw clean package &&  docker-compose up --build
```
Para iniciar la aplicación con sin las pruebas executamos con la opción *-DskipTests*:
```console
./mvnw clean package -DskipTests &&  docker-compose up --build
```