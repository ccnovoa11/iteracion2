package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRemitentes {

	/**
	 * List con los videos
	 */
	@JsonProperty(value="remitentes")
	private List<Remitente> remitentes;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaRemitentes( @JsonProperty(value="remitentes")List<Remitente> remitentes){
		this.remitentes = remitentes;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Remitente> getRemitentes() {
		return remitentes;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setRemitente(List<Remitente> remitentes) {
		this.remitentes = remitentes;
	}
	
}
