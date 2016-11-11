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
import vos.ListaViajeros;
import vos.ListaVuelosPasajero;
import vos.Viajero;

@Path("viajeros")
public class VuelAndesViajerosServices {

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
	public Response getViajeros() {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaViajeros viajeros;
		try {
			viajeros = tm.darViajeros();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(viajeros).build();
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
	public Response getViajeroName(@javax.ws.rs.PathParam("name") String name) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaViajeros viajeros;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del remitente no valido");
			viajeros = tm.buscarViajerosPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(viajeros).build();
	}
	
	@GET
	@Path("/ide/{ide}/cod/{cod}/clase/{clase}/distancia/{distancia}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarVuelosPorCodAerolienaViajero(@javax.ws.rs.PathParam("ide")int ide,@javax.ws.rs.PathParam("cod")String cod,
			@javax.ws.rs.PathParam("distancia")int distancia,@javax.ws.rs.PathParam("clase")String clase) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (ide <=-1 )
				throw new Exception("Id del usuario no valido");
			vuelos = tm.buscarVuelosPorCodAerolienaViajero(ide, cod, clase, distancia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	@GET
	@Path("/idg/{idg}/cod/{cod}/clase/{clase}/distancia/{distancia}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarVuelosPorCodAerolienaGerente(@javax.ws.rs.PathParam("idg")int idg,@javax.ws.rs.PathParam("cod")String cod,
			@javax.ws.rs.PathParam("distancia")int distancia,@javax.ws.rs.PathParam("clase")String clase) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (idg <=-1 )
				throw new Exception("Id del usuario no valido");
			vuelos = tm.buscarVuelosPorCodAerolienaGerente(cod, clase, distancia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	@GET
	@Path("/ide/{ide}/fecha/{comienzo}/{fin}/clase/{clase}/distancia/{distancia}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarVuelosPorFechaViajero(@javax.ws.rs.PathParam("ide")int ide,@javax.ws.rs.PathParam("comienzo")String comienzo,
			@javax.ws.rs.PathParam("fin")String fin,@javax.ws.rs.PathParam("clase")String clase,@javax.ws.rs.PathParam("distancia")int distancia) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (ide <=-1 )
				throw new Exception("Id del usuario no valido");
			vuelos = tm.buscarVuelosPorFechaViajero(ide, comienzo,fin,clase,distancia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	@GET
	@Path("/idg/{idg}/fecha/{comienzo}/{fin}/clase/{clase}/distancia/{distancia}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarVuelosPorFechGerente(@javax.ws.rs.PathParam("idg")int idg,@javax.ws.rs.PathParam("comienzo")String comienzo,
			@javax.ws.rs.PathParam("fin")String fin,@javax.ws.rs.PathParam("clase")String clase,@javax.ws.rs.PathParam("distancia")int distancia) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosPasajero vuelos;
		try {
			if (idg <=-1 )
				throw new Exception("Id del usuario no valido");
			vuelos = tm.buscarVuelosPorFechaGerente(comienzo,fin,clase,distancia);
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
	@PUT
	@Path("/viajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateViajero(Viajero viajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.updateViajero(viajero);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(viajero).build();
	}

	
    /**
     * Método que expone servicio REST usando PUT que agrega los videos que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/videos
     * @param videos - videos a agregar. 
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path("/viajeros")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addViajero(ListaViajeros viajeros) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addViajeros(viajeros);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(viajeros).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a actualizar. 
     * @return Json con el video que actualizo o Json con el error que se produjo
     */
	@POST
	@Path("/viajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addViajero(Viajero viajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addViajero(viajero);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(viajero).build();
	}
	
	
	@POST
	@Path("/idVP/{vueloPasajero}/idVP/{vueloPasajero}/nS/{numeroSilla}/idVC/{vueloCarga}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReservaGrupal(ListaViajeros viajeros, @javax.ws.rs.PathParam("vueloPasajero")int vueloPasajero,@javax.ws.rs.PathParam("numeroSilla")int numeroSilla,@javax.ws.rs.PathParam("vueloCarga")int vueloCarga) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaReservasPasajero reservas;
		try {
			tm.addReservasVueloGrupal(viajeros, numeroSilla, vueloPasajero, vueloCarga);
			reservas = tm.darReservasPasajero();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservas).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a aliminar. 
     * @return Json con el video que elimino o Json con el error que se produjo
     */
	@DELETE
	@Path("/viajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteViajero(Viajero viajero) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.deleteViajero(viajero);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(viajero).build();
	}


}
