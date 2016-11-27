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
import vos1.Admin;
import vos1.ListaAdmins;
import vos1.ListaAeronaves;
import vos1.ListaVuelosPasajero;

@Path("admins")
public class VuelAndesAdminsServices {

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
	public Response getAdmins() {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAdmins admins;
		try {
			admins = tm.darAdmins();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(admins).build();
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
	public Response getAdminsName(@javax.ws.rs.PathParam("name") String name) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAdmins admins;
		try {
			if (name == null || name.length() == 0)
				throw new Exception("Nombre del admin no valido");
			admins = tm.buscarAdminsPorName(name);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(admins).build();
	}
	
	@GET
	@Path("/ide/{ide}/num/{num}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarAeronavesNumSerie(@javax.ws.rs.PathParam("ide")int ide,@javax.ws.rs.PathParam("num")String num) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAeronaves aeronaves;
		try {
			if (ide <=-1 )
				throw new Exception("Id del usuario no valido");
			aeronaves = tm.buscarAeronaveNumSerie(ide, num);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeronaves).build();
	}
	
	@GET
	@Path("/ide/{ide}/tam/{tam}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarAeronavesTamano(@javax.ws.rs.PathParam("ide")int ide,@javax.ws.rs.PathParam("tam")String tam) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAeronaves aeronaves;
		try {
			if (ide <=-1 )
				throw new Exception("Id del usuario no valido");
			aeronaves = tm.buscarAeronaveTamano(ide, tam);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeronaves).build();
	}
	
	@GET
	@Path("/ide/{ide}/cap/{cap}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarAeronavesCapacidad(@javax.ws.rs.PathParam("ide")int ide,@javax.ws.rs.PathParam("cap")int cap) throws Exception {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaAeronaves aeronaves;
		try {
			if (ide <=-1 )
				throw new Exception("Id del usuario no valido");
			aeronaves = tm.buscarAeronaveCapacidad(ide, cap);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(aeronaves).build();
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
	@Path("/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAdmin(Admin admin) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.updateAdmin(admin);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(admin).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que agrega los videos que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/videos
     * @param videos - videos a agregar. 
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path("/admins")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAdmins(ListaAdmins admins) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addAdmins(admins);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(admins).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a actualizar. 
     * @return Json con el video que actualizo o Json con el error que se produjo
     */
	@POST
	@Path("/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAdmin(Admin admin) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.addAdmin(admin);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(admin).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a aliminar. 
     * @return Json con el video que elimino o Json con el error que se produjo
     */
	@DELETE
	@Path("/admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAdmin(Admin admin) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		try {
			tm.deleteAdmin(admin);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(admin).build();
	}


}
