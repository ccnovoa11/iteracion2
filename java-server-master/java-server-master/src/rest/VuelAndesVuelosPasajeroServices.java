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
import vos.Aeronave;
import vos.Aeropuerto;
import vos.ListaAeropuertos;
import vos.ListaVuelosPasajero;
import vos.VueloPasajero;

@Path("vuelosPasajero")
public class VuelAndesVuelosPasajeroServices {

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
	public Response getVuelosPasajero() {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			vuelos = tm.darVuelosPasajero();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}


    /**
     * Método que expone servicio REST usando GET que busca el video con el nombre que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/name/"name para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parámetro 
     * @return Json con el/los videos encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo
     */
	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVueloPasajeroId(@javax.ws.rs.PathParam("id") int id) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (id <=-1 )
				throw new Exception("Id del vuelo no valido");
			vuelos = tm.buscarVueloPasajeroPorId(id);
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
	@Path("/vueloPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addVueloPasajero(VueloPasajero vuelo) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addVueloPasajero(vuelo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelo).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que agrega los videos que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/videos
     * @param videos - videos a agregar. 
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@PUT
	@Path("/vuelosPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addVueloPasajero(ListaVuelosPasajero vuelos) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addVuelosPasajero(vuelos);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	
	@PUT
	@Path("/id/{idVuelo}/idAeronave/{idAeronave}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response asociarVueloPasajeroAeronave(@javax.ws.rs.PathParam("idVuelo") int idVuelo, @javax.ws.rs.PathParam("idAeronave") String idAeronave) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		VueloPasajero vuelo;
		try {
			if (idVuelo <= 0)
				throw new Exception("Id del vuelo no valido");
			vuelo = tm.buscarVueloPasajeroPorId(idVuelo).getVuelosPasajero().get(0);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		Aeronave aeronave;
		try {
			if (idAeronave ==null )
				throw new Exception("numSerie de la aeronave no valido");
			aeronave = tm.buscarAeronavePasajeroPorNumSerie(idAeronave).getAeronaves().get(0);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		VueloPasajero respuesta = tm.asociarVueloPasajeroAeronave(vuelo, aeronave);
		
		return Response.status(200).entity(respuesta).build();
	}
	
	
    /**
     * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a actualizar. 
     * @return Json con el video que actualizo o Json con el error que se produjo
     */
	@PUT
	@Path("/vueloPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateVueloPasajero(VueloPasajero vuelo) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.updateVueloPasajero(vuelo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelo).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a aliminar. 
     * @return Json con el video que elimino o Json con el error que se produjo
     */
	@DELETE
	@Path("/vueloPasajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteVueloPasajero(VueloPasajero vuelo) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.deleteVueloPasajero(vuelo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelo).build();
	}
}
