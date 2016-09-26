/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una arreglo de Video
 * @author Juan
 */
public class ListaAerolineas {
	
	/**
	 * List con los videos
	 */
	@JsonProperty(value="aerolineas")
	private List<Aerolinea> aerolineas;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaAerolineas( @JsonProperty(value="aerolineas")List<Aerolinea> aerolineas){
		this.aerolineas = aerolineas;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Aerolinea> getAerolineas() {
		return aerolineas;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setAerolinea(List<Aerolinea> aerolineas) {
		this.aerolineas = aerolineas;
	}
	
}
