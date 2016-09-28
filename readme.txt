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

