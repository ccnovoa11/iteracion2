package vos1;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaVuelosPasajero {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="vuelosPasajero")
	private List<VueloPasajero> vuelosPasajero;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaVuelosPasajero( @JsonProperty(value="vuelosPasajero")List<VueloPasajero> vuelosPasajero){
		this.vuelosPasajero = vuelosPasajero;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<VueloPasajero> getVuelosPasajero() {
		return vuelosPasajero;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setVueloPasajero(List<VueloPasajero> vuelosPasajero) {
		this.vuelosPasajero = vuelosPasajero;
	}

}
