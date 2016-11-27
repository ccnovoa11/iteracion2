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
package vos1;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Video
 * @author Juan
 */
public class Aerolinea {

	//// Atributos

	/**
	 * Codigo
	 */
	@JsonProperty(value="codigo")
	private String codigo;

	/**
	 * IATA
	 */
	@JsonProperty(value="iata")
	private String iata;

	/**
	 * Nombre
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Pais
	 */
	@JsonProperty(value="pais")
	private String pais;
	
	@JsonProperty(value="correo")
	private String correo;
	
	@JsonProperty(value="persona")
	private String persona;
	
	

	/**
	 * Método constructor de la clase aerolinea
	 * <b>post: </b> Crea la aerolinea con los valores que entran como parámetro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duración en minutos del video.
	 */
	public Aerolinea(@JsonProperty(value="codigo")String codigo, @JsonProperty(value="iata")String iata,@JsonProperty(value="nombre") String nombre, @JsonProperty(value="pais") String pais) {

		this.codigo = codigo;
		this.iata = iata;
		this.nombre = nombre;
		this.pais =pais;
	}
	
	public Aerolinea(@JsonProperty(value="codigo")String codigo, @JsonProperty(value="iata")String iata,@JsonProperty(value="nombre") String nombre, @JsonProperty(value="pais") String pais,
			@JsonProperty(value="correo") String correo,@JsonProperty(value="persona") String persona) {

		this.codigo = codigo;
		this.iata = iata;
		this.nombre = nombre;
		this.pais =pais;
		this.correo = correo;
		this.persona = persona;
		
	}

	
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	} 


}
