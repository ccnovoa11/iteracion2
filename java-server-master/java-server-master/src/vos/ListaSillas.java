package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSillas {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="sillas")
	private List<Silla> sillas;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaSillas( @JsonProperty(value="sillas")List<Silla> sillas){
		this.sillas = sillas;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Silla> getSillas() {
		return sillas;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setSilla(List<Silla> sillas) {
		this.sillas = sillas;
	}
}
