package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAeropuertos {


	/**
	 * List con los videos
	 */
	@JsonProperty(value="aeropuertos")
	private List<Aeropuerto> aeropuertos;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaAeropuertos( @JsonProperty(value="aeropuertos")List<Aeropuerto> aeropuertos){
		this.aeropuertos = aeropuertos;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Aeropuerto> getAeropuertos() {
		return aeropuertos;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setAeropuerto(List<Aeropuerto> aeropuertos) {
		this.aeropuertos = aeropuertos;
	}
}
