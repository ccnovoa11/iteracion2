package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Viajero {

	
////Atributos

	/**
	 * Identificacion
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Tipo de identificacion
	 */
	@JsonProperty(value="tipoIdent")
	private String tipoIdent;
	
	/**
	 * Nacionalidad
	 */
	@JsonProperty(value="nacionalidad")
	private String nacionalidad;
	
	/**
	 * Es frecuente. 0=no 1=si
	 */
	@JsonProperty(value="esFrecuente")
	private int esFrecuente;
	

	public Viajero(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="tipoIdent")String tipoIdent, 
			@JsonProperty(value="nacionalidad")String nacionalidad, @JsonProperty(value="esFrecuente")int esFrecuente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipoIdent = tipoIdent;
		this.nacionalidad = nacionalidad;
		this.esFrecuente=esFrecuente;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTipoIdent() {
		return tipoIdent;
	}


	public void setTipoIdent(String tipoIdent) {
		this.tipoIdent = tipoIdent;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}


	public int getEsFrecuente() {
		return esFrecuente;
	}


	public void setEsFrecuente(int esFrecuente) {
		this.esFrecuente = esFrecuente;
	}
	
}
