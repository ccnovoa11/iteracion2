package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAerolineasMsg {

	/**
	 * List con los videos
	 */
	@JsonProperty(value="aerolineasMsg")
	private List<AerolineaMsg> aerolineas;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaAerolineasMsg( @JsonProperty(value="aerolineasMsg")List<AerolineaMsg> aerolineas){
		this.aerolineas = aerolineas;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<AerolineaMsg> getAerolineas() {
		return aerolineas;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setAerolinea(List<AerolineaMsg> aerolineas) {
		this.aerolineas = aerolineas;
	}
	
}
