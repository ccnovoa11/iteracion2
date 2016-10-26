package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.VuelAndesMaster;
import vos.ListaReservasPasajero;
import vos.ReservaPasajero;
import vos.Vuelo;

@Path("reservasPasajero")
public class VuelAndesReservasPasajeroServices {

	// Servicios REST tipo GET:


	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}


	/**
	 * Método que expone servicio REST usando GET que da todos los videos de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos O json con 
	 * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getReservasPasajero() {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaReservasPasajero reservasPasajero;
		try {
			reservasPasajero = tm.darReservasPasajero();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservasPasajero).build();
	}


	/**
	 * Método que expone servicio REST usando GET que busca el video con el nombre que entra como parámetro
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/name/"name para la busqueda"
	 * @param name - Nombre del video a buscar que entra en la URL como parámetro 
	 * @return Json con el/los videos encontrados con el nombre que entra como parámetro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path("/idVuelo/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getReservaPasajeroPorVuelo(@javax.ws.rs.PathParam("id") int id) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaReservasPasajero reservasPasajero;
		try {
			if (id <=-1 )
				throw new Exception("Id del vuelo no valido");
			reservasPasajero = tm.buscarReservaPorVuelo(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservasPasajero).build();
	}

	@GET
	@Path("/idViajero/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getReservaPasajeroPorViajero(@javax.ws.rs.PathParam("id") int id) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaReservasPasajero reservasPasajero;
		try {
			if (id <=-1 )
				throw new Exception("Id del vuelo no valido");
			reservasPasajero = tm.buscarReservaPorViajero(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservasPasajero).build();
	}

	//    /**
	//     * Método que expone servicio REST usando GET que busca el video mas alquilado
	//     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/MayorAlquilado
	//     * @return Json con el/los videos encontrados con el nombre que entra como parámetro o json con 
	//     * el error que se produjo
	//     */
	//	@GET
	//	@Path("/MayorAlquilado")
	//	@Produces({ MediaType.APPLICATION_JSON })
	//	public Response getVideoMayorAlquilado() {
	//		VuelAndesMaster tm = new VuelAndesMaster(getPath());
	//		ListaAerolineas videos;
	//		try {
	//			videos = tm.videosMasAlquilados();
	//		} catch (Exception e) {
	//			return Response.status(500).entity(doErrorMessage(e)).build();
	//		}
	//		return Response.status(200).entity(videos).build();
	//	}


	/**
	 * Método que expone servicio REST usando PUT que agrega el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param video - video a agregar
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@PUT
	@Path("/reservaPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateReservaPasajero(ReservaPasajero reservaPasajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.updateReservaPasajero(reservaPasajero);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservaPasajero).build();
	}

	/**
	 * Método que expone servicio REST usando PUT que agrega los videos que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/videos
	 * @param videos - videos a agregar. 
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@POST
	@Path("/reservasPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRemitente(ListaReservasPasajero reservasPasajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		Vuelo total;

		try {
			tm.addReservasPasajero(reservasPasajero);
			total = tm.addReservasVueloTotal(reservasPasajero);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(total).build();
	}

	/**
	 * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param video - video a actualizar. 
	 * @return Json con el video que actualizo o Json con el error que se produjo
	 */
	@POST
	@Path("/reservaPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReservaPasajero(ReservaPasajero reservaPasajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			if(tm.buscarSillaPorNumero(reservaPasajero.getNumSilla()).getOcupada()==0){
				tm.addReservaPasajero(reservaPasajero);
				tm.updateSilla(tm.buscarSillaPorNumero(reservaPasajero.getNumSilla()));
			}
			else{
				throw new Exception("La silla ya est� ocupada");
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservaPasajero).build();
	}

	/**
	 * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param video - video a aliminar. 
	 * @return Json con el video que elimino o Json con el error que se produjo
	 */
	@DELETE
	@Path("/reservaPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReservaPasajero(ReservaPasajero reservaPasajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.deleteReservaPasajero(reservaPasajero);
			tm.updateSillaE(tm.buscarSillaPorNumero(reservaPasajero.getNumSilla()));
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservaPasajero).build();
	}

	@DELETE
	@Path("/reservasPasajeros")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReservasPasajero(ListaReservasPasajero reservasPasajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {

			tm.deleteReservasPasajero(reservasPasajero);
//			for(ReservaPasajero reservaPasajero : reservasPasajero.getReservasPasajero()){
//				
//				tm.updateSillaE(tm.buscarSillaPorNumero(reservaPasajero.getNumSilla()));
//			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservasPasajero).build();
	}

}
