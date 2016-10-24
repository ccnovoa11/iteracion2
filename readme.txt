RF8-Registrar una reserva de pasajero

1) Consultar el listado de todos los vuelos que hay de pasajeros
GET http://localhost:8080/VideoAndes/rest/vuelosPasajero/ 

2) Elegir el vuelo que sea conveniente
GET http://localhost:8080/VideoAndes/rest/vuelosPasajero/id/10

3) Consultar las sillas asociadas a ese vuelo. Ese listado contiene todas las sillas sin importar el tipo ni si están ocupadas.
Se da toda la información de las sillas para que el viajero pueda elegir que tipo de silla quiere(economica, ejecutiva)
GET http://localhost:8080/VideoAndes/rest/vuelosPasajero/id/10/sillas

4) Una vez el viajero haya decidido que silla quiere, se dirige a crear una nueva reserva de pasajero
POST http://localhost:8080/VideoAndes/rest/reservasPasajero/reservaPasajero
Allí debe indicar su identificación de viajero, la identificacion del vuelo que eligió y el número de silla que quiere
Si la silla está disponible, se crea la reserva, de lo contrario, no se crea.

5)Para ver el identificador unico de la reserva que acaba de crear debe seguir la siguiente ruta cambiando "" por su id
GET http://localhost:8080/VideoAndes/rest/reservasPasajero/idViajero/""

RF9-Registrar una reserva de carga

1) Consultar el listador de todos los vuelos que hay de carga
GET http://localhost:8080/VideoAndes/rest/vuelosCarga

2) Elegir el vuelo que sea conveniente
GET http://localhost:8080/VideoAndes/rest/vuelosCarga/id/1

3) Una vez el remitente haya decidido que vuelo quiere, crea una nueva reserva de carga
POST http://localhost:8080/VideoAndes/rest/reservasCarga/reservaCarga
Allí se debe indicar la identificacion del remitente y la identificacion del vuelo que eligió.
Si el peso de lo que quiere enviar no supera la capacidad actual del avión, se crea la reserva. No se crea en caso contrario.

RFC1

1) Vuelos totales de ese aeropuerto
GET http://localhost:8080/VideoAndes/rest/aeropuertos/id/100/vuelos
GET http://localhost:8080/VideoAndes/rest/aeropuertos/id/100/vuelosCarga

2)Vuelos de ese aeropuerto segun aerolinea
GET http://localhost:8080/VideoAndes/rest/aeropuertos/id/100/aerolinea/JK
GET http://localhost:8080/VideoAndes/rest/aeropuertos/id/100/aerolineaCarga/JK

RF12
Se aseguran las propiedades ACID porque se va agregando de a una reserva, todo en una transacción de forma atómica.
De igual forma, la consistencia se asegura ya que se envían mensajes de error cada vez que se intenta agregar un dato que tiene por ejemplo,
una llave foranea inexistente.
Tambien la durabilidad se asegura porque el proyecto tiene unos web services que se conectan con la base da datos, por lo tanto los
datos son persistences.

GET http://localhost:8080/VideoAndes/rest/vuelosPasajero/92/100

RF13

Para asegurar las ACID, se hace todo el proceso dentro de una transaccion, se ejecutan todos los pasos completos o no se ejecuta
ninguno, si llega a haber algun error durante el proceso, no se elimina la reserva. Esto por lo tanto, asegura la consistencia
porque no deja reservas a medio borrar. Además, la durabilidad se asegura porque se conecta a la base de datos y se recuperan todas
las reservas pero cuando se elimina, se elimina de forma permanente.
DELETE http://localhost:8080/VideoAndes/rest/reservasPasajero/reservaPasajero

RF14

La atomicidad se asegura porque esta transaccion no permite que se borre una sola reserva, sino que se borran todas o ninguna.
Tambien, los datos quedan consistentes, porque al borrarse de forma completa, no quedan reservas a medio borrar.
DELETE http://localhost:8080/VideoAndes/rest/reservasPasajero/reservasPasajero

RF15
Para cancelar un viaje se asegura la atomicidad porque no se permite que se borre solo el vuelo, sino que se tienen que borrar
las reservas asociadas. No se puede borrar solo una cosa, sino que tiene que ser todo o no se borra.
La consistencia se asegura tambien porque no quedan reservas asociadas a un vuelo inexistente sino que se asocian a otro vuelo.
La durabilidad se asegura por lo web services que permiten las persistencia de los datos.
DELETE http://localhost:8080/VideoAndes/rest/vuelosPasajero/vueloPasajero

RF16

