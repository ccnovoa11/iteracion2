package vos1;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaViajeros {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="viajeros")
	private List<Viajero> viajeros;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaViajeros( @JsonProperty(value="viajeros")List<Viajero> viajeros){
		this.viajeros = viajeros;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Viajero> getViajeros() {
		return viajeros;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setViajero(List<Viajero> viajeros) {
		this.viajeros = viajeros;
	}

}
