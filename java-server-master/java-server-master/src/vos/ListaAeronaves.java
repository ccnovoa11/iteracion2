package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAeronaves {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="aeronaves")
	private List<Aeronave> aeronaves;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaAeronaves( @JsonProperty(value="aeronaves")List<Aeronave> aeronaves){
		this.aeronaves = aeronaves;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Aeronave> getAeronaves() {
		return aeronaves;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setAeronave(List<Aeronave> aeronaves) {
		this.aeronaves = aeronaves;
	}

}
