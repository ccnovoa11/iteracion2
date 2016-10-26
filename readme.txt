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

En este requerimiento se desea hacer una reserva de un vuelo que puede ser directo o tener escalas. Para esto, primero se busca un vuelo con los
aeropuertos de origen y destino y en caso de que haya un vuelo directo, se procede a hacer la reserva de ese vuelo.
En caso contrario, se busca otro vuelo que tenga de origen, el origen que nos dan por parámetro y como destino cualquier aeropuerto, por el momento,
y se busca otro vuelo que tenga como origen el aeropuerto cualquiera anterior y como destino el destino que nos dieron por parámetro.
Ahí se crea un listado de vuelos que permiten llegar del origen al destino indirectamente. Si en esa lista hay más de un vuelo se comparan los costos y se
eligen los vuelos que generen menor costo. O si solo hay un par de vuelos, se hace reserva de ellos.
Se aseguran las propiedades ACID porque se va agregando de a una reserva, todo en una transacción de forma atómica. Se agrega todo o no se agrega nada.
De igual forma, la consistencia se asegura ya que se envían mensajes de error cada vez que se intenta agregar un dato que tiene por ejemplo,
una llave foranea inexistente. Si se intenta reservar un vuelo que no existe, lanza error.
Tambien la durabilidad se asegura porque el proyecto tiene unos web services que se conectan con la base da datos, por lo tanto los
datos son persistences.

1) El viajero busca un vuelo por los codigo de los aeropuertos de origen y de destino:

GET http://localhost:8080/VideoAndes/rest/vuelosPasajero/92/100

2) Consultar las sillas asociadas a ese vuelo. Ese listado contiene todas las sillas sin importar el tipo ni si están ocupadas.
Se da toda la información de las sillas para que el viajero pueda elegir que tipo de silla quiere(economica, ejecutiva)
GET http://localhost:8080/VideoAndes/rest/vuelosPasajero/id/2/sillas

4) Una vez el viajero haya decidido que silla quiere, se dirige a crear una nueva reserva de pasajero

POST http://localhost:8080/VideoAndes/rest/reservasPasajero/reservaPasajero
Allí debe indicar su identificación de viajero, la identificacion del vuelo que eligió y el número de silla que quiere
Si la silla está disponible, se crea la reserva, de lo contrario, no se crea.


{
      "id": 1,
      "numSilla": "87",
      "idViajero": 443,
      "idVueloPasajero": 2
    }


5)Para ver el identificador unico de la reserva que acaba de crear debe seguir la siguiente ruta cambiando "" por su id
GET http://localhost:8080/VideoAndes/rest/reservasPasajero/idViajero/""

RF13

En este requerimiento se quiere cancelar una reserva dado su identificador, para esto el viajero simplemente tiene que indicar que
quiere borrar una reserva y luego dar el id, después el sistema se encarga de liberar el cupo.
Para asegurar las ACID, se hace todo el proceso dentro de una transaccion, se ejecutan todos los pasos completos o no se ejecuta
ninguno, si llega a haber algun error durante el proceso, no se elimina la reserva. Esto por lo tanto, asegura la consistencia
porque no deja reservas a medio borrar. Además, la durabilidad se asegura porque se conecta a la base de datos y se recuperan todas
las reservas pero cuando se elimina, se elimina de forma permanente.

1) Para borrar la reserva:

DELETE http://localhost:8080/VideoAndes/rest/reservasPasajero/reservaPasajero


{
      "id": 1,
      "numSilla": "87",
      "idViajero": 443,
      "idVueloPasajero": 2
    }

RF14

En este requerimiento se quiere borrar un listado de reservas, por eso el usuario debe saber cuales son las reservas que quiere
borrar e indicar que las quiere borrar. Luego el sistema las borra y libera todos los cupos.

La atomicidad se asegura porque esta transaccion no permite que se borre una sola reserva, sino que se borran todas o ninguna.
En caso de que no se pueda borrar una reserva, hace rollback para que no quede inconsistente la información.
Tambien, los datos quedan consistentes, porque al borrarse de forma completa, no quedan reservas a medio borrar.
La durabilidad se asegura porque tiene unos web services que se conectan a la base de datos y se puede recuperar datos pero 
también borrarlos de forma permanente.

DELETE http://localhost:8080/VideoAndes/rest/reservasPasajero/reservasPasajeros


{
      "id": 1062228011,
      "numSilla": "87",
      "idViajero": 443,
      "idVueloPasajero": 2
    },
    {
      "id": 1549263461,
      "numSilla": "87",
      "idViajero": 443,
      "idVueloPasajero": 2
    }

RF15

En este requerimiento se quiere cancelar un viaje. Cuando se cancela un viaje también se tienen que cancelar todas sus reservas
asociadas y poner a los viajeros en otros vuelos.
Primero se tiene que decir que vuelo se quiere cancelar, luego se buscan otros vuelos de la misma aerolinea. Después, se obtienen
todas las reservas que tenian ese vuelo y a esos viajeros se les asigna otro vuelo de la misma aerolinea.
Luego se eliminan las reservas del vuelo a eliminar y por ultimo se elimina el vuelo.

Para cancelar un viaje se asegura la atomicidad porque no se permite que se borre solo el vuelo, sino que se tienen que borrar
las reservas asociadas. No se puede borrar solo una cosa, sino que tiene que ser todo o no se borra.
La consistencia se asegura tambien porque no quedan reservas asociadas a un vuelo inexistente sino que se borran y se crean nuevas
reservas asociadas a otro vuelo.
La durabilidad se asegura por los web services que permiten las persistencia de los datos.
En caso de que haya fallos, hay savepoints cuando se crean reservas, cuando se eliminan y cuando se elimina un vuelo.
En addReservaPasajero en el tm.

DELETE http://localhost:8080/VideoAndes/rest/vuelosPasajero/vueloPasajero

RF16

En este requerimiento se debe, primero, buscar un vuelo tanto de pasajero como de carga que estén proximos y que partan del mismo
origen y lleguen al mismo destino.
Luego se hace un listado de todas las reservas de pasajero que se quieren hacer, las cuales se pueden reservar al tiempo.
Después se hace un listado de reservas de carga para los paquetes de los pasajeros.

La atomicidad se asegura porque el sistema no permite que se hagan reservas incompletas, se tienen que poder reservar todas o sino
se devuelve y no reserva ninguna.
La consistencia se asegura porque no se permite hacer reservas de vuelos que no existan
La durabilidad se asegura por la conexión con la base de datos y también se asegura la recuperación ante fallas por los savepoints
que hay luego de hacer cada reserva.

RC5
Consultar los vuelos del usuario dado un codigo de aerolínea, esto con el fin por ver los vuelos que ha realizado con la respectiva aerolínea. En este caso, dado que el usuario conoce el número de identificación, sólo aquellos con acceso a su número pueden consultar los vuelos que él ha realizado.

GET http://localhost:8080/VideoAndes/rest/viajeros/ide/443/cod/NE

*Las demas consultas por parámetros cuentan con la fecha en la que fue realizado un vuelo.

RC6
Consultar las aeronaves del administrador de un código propio dado, y el número de serie dado.

GET http://localhost:8080/VideoAndes/rest/admins/ide/88/num/Y9M5

*Las demas consultas cuentas con parámetros como lo son el tamaño o la capacidad de la aeronave. 

