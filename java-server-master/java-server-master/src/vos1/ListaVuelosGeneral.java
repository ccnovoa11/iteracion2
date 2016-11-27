package vos1;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaVuelosGeneral {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="vuelosGeneral")
	private List<VueloGeneral> vuelosGeneral;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaVuelosGeneral( @JsonProperty(value="vuelosGeneral")List<VueloGeneral> vuelosGeneral){
		this.vuelosGeneral = vuelosGeneral;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<VueloGeneral> getVuelosGeneral() {
		return vuelosGeneral;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setVueloCarga(List<VueloGeneral> vuelosGeneral) {
		this.vuelosGeneral = vuelosGeneral;
	}


}
