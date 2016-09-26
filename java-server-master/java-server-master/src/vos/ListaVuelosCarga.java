package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaVuelosCarga {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="vuelosCarga")
	private List<VueloCarga> vuelosCarga;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaVuelosCarga( @JsonProperty(value="vuelosCarga")List<VueloCarga> vuelosCarga){
		this.vuelosCarga = vuelosCarga;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<VueloCarga> getVuelosCarga() {
		return vuelosCarga;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setVueloCarga(List<VueloCarga> vuelosCarga) {
		this.vuelosCarga = vuelosCarga;
	}

}
