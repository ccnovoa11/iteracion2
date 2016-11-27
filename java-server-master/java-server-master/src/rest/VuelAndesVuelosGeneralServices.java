package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.VuelAndesMaster;
import vos1.ListaVuelosGeneral;
import vos1.ListaVuelosPasajero;

@Path("vuelosGeneral")
public class VuelAndesVuelosGeneralServices {
	

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
	
	
	@GET
	@Path("/Pasajero/{origen}/{destino}/{fecha1}/{fecha2}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVueloGeneralOrigenDestino(@javax.ws.rs.PathParam("origen") String origen, @javax.ws.rs.PathParam("destino") String destino, @javax.ws.rs.PathParam("fecha1") String fecha1,@javax.ws.rs.PathParam("fecha2") String fecha2) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosGeneral vuelos;
		try {
			vuelos = tm.buscarVuelosPasajeroOrigenDestinoEnRango(origen, destino, fecha1, fecha2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	@GET
	@Path("/Carga/{origen}/{destino}/{fecha1}/{fecha2}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getVueloCargaGeneralOrigenDestino(@javax.ws.rs.PathParam("origen") String origen, @javax.ws.rs.PathParam("destino") String destino, @javax.ws.rs.PathParam("fecha1") String fecha1,@javax.ws.rs.PathParam("fecha2") String fecha2) {
		VuelAndesMaster tm = new VuelAndesMaster(getPath());
		ListaVuelosGeneral vuelos;
		try {
			vuelos = tm.buscarVuelosCargaOrigenDestinoEnRango(origen, destino, fecha1, fecha2);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(vuelos).build();
	}

}
