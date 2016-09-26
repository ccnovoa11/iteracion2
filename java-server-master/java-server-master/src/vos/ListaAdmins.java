package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAdmins {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="admins")
	private List<Admin> admins;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaAdmins( @JsonProperty(value="admins")List<Admin> admins){
		this.admins = admins;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Admin> getAdmins() {
		return admins;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setAdmin(List<Admin> admins) {
		this.admins = admins;
	}

}
