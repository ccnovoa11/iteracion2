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
import vos.Aerolinea;
import vos.Aeropuerto;
import vos.ListaAerolineas;
import vos.ListaAeropuertos;
import vos.ListaVuelosCarga;
import vos.ListaVuelosPasajero;
import vos.VueloPasajero;

/**
 * Clase que expone servicios REST con ruta base: http://"ip o nombre de host":8080/VideoAndes/rest/videos/...
 * @author Juan
 */
@Path("aeropuertos")
public class VuelAndesAeropuertosServices {

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
	public Response getAeropuertos() {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAeropuertos aeropuertos;
		try {
			aeropuertos = tm.darAeropuertos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuertos).build();
	}


	/**
	 * Método que expone servicio REST usando GET que busca el video con el nombre que entra como parámetro
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/name/"name para la busqueda"
	 * @param name - Nombre del video a buscar que entra en la URL como parámetro 
	 * @return Json con el/los videos encontrados con el nombre que entra como parámetro o json con 
	 * el error que se produjo
	 */
	@GET
	@Path("/name/{name}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAeropuertoName(@javax.ws.rs.PathParam("name") String name) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAeropuertos aeropuertos;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del aeropuerto no valido");
			aeropuertos = tm.buscarAeropuertosPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuertos).build();
	}

	@GET
	@Path("/id/{id}/vuelos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVuelosAeropuertoId(@javax.ws.rs.PathParam("id") int id) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (id<=0)
				throw new Exception("Nombre del aeropuerto no valido");
			vuelos = tm.buscarVueloPasajeroPorIdAeropuerto(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAeropuertoId(@javax.ws.rs.PathParam("id") int id) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		Aeropuerto aeropuertos;
		try {
			if (id<0)
				throw new Exception("Nombre del aeropuerto no valido");
			aeropuertos = tm.buscarAeropuertosPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuertos).build();
	}

//	@GET
//	@Path("/id/{id}/vuelos/{id1}")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getVuelosAeropuertoOrigenDestino(@javax.ws.rs.PathParam("id") int id, @javax.ws.rs.PathParam("id1") int id1){
//		VuelAndesMaster tm = new VuelAndesMaster(getPath());
//		VueloPasajero vuelos;
//		try {
//			if (id<=0)
//				throw new Exception("aeropuerto no valido");
//			vuelos = tm.buscarVueloPasajeroOrigenDestino(id, id1);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(vuelos).build();
//	}

	@GET
	@Path("/id/{id}/vuelosCarga")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVuelosCargaAeropuertoId(@javax.ws.rs.PathParam("id") int id) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosCarga vuelos;
		try {
			if (id<=0)
				throw new Exception("Nombre del aeropuerto no valido");
			vuelos = tm.buscarVueloCargaPorIdAeropuerto(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}

	@GET
	@Path("/id/{id}/aerolinea/{aerolinea}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVuelosAeropuertoIdAerolinea(@javax.ws.rs.PathParam("id") int id,@javax.ws.rs.PathParam("aerolinea") String aerolinea) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (id<=0)
				throw new Exception("Nombre del aeropuerto no valido");
			vuelos = tm.buscarVueloPasajeroPorIdAeropuertoAerolinea(id,aerolinea);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}

	@GET
	@Path("/id/{id}/aerolineaCarga/{aerolinea}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVuelosCargaAeropuertoIdAerolinea(@javax.ws.rs.PathParam("id") int id,@javax.ws.rs.PathParam("aerolinea") String aerolinea) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosCarga vuelos;
		try {
			if (id<=0)
				throw new Exception("Nombre del aeropuerto no valido");
			vuelos = tm.buscarVueloCargaPorIdAeropuertoAerolinea(id,aerolinea);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
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
	@POST
	@Path("/aeropuerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAeropuerto(Aeropuerto aeropuerto) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addAeropuerto(aeropuerto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuerto).build();
	}

	/**
	 * Método que expone servicio REST usando PUT que agrega los videos que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/videos
	 * @param videos - videos a agregar. 
	 * @return Json con el video que agrego o Json con el error que se produjo
	 */
	@PUT
	@Path("/aeropuertos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAeropuerto(ListaAeropuertos aeropuertos) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addAeropuertos(aeropuertos);;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuertos).build();
	}

	/**
	 * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param video - video a actualizar. 
	 * @return Json con el video que actualizo o Json con el error que se produjo
	 */
	@PUT
	@Path("/aeropuerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAeropuerto(Aeropuerto aeropuerto) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.updateAeropuerto(aeropuerto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuerto).build();
	}

	/**
	 * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
	 * @param video - video a aliminar. 
	 * @return Json con el video que elimino o Json con el error que se produjo
	 */
	@DELETE
	@Path("/aeropuerto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAeropuerto(Aeropuerto aeropuerto) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.deleteAeropuerto(aeropuerto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeropuerto).build();
	}


}
