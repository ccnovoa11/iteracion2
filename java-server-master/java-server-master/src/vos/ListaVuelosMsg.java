package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import java.util.List;

public class ListaVuelosMsg {

	/**
	 * List con los videos
	 */
	@JsonProperty(value="vuelosMsg")
	private List<VueloMsg> vuelos;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaVuelosMsg( @JsonProperty(value="vuelosMsg")List<VueloMsg> vuelos){
		this.vuelos = vuelos;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<VueloMsg> getVuelos() {
		return vuelos;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setVuelo(List<VueloMsg> vuelos) {
		this.vuelos = vuelos
				;
	}
	
}
