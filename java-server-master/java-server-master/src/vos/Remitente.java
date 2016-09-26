package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Remitente {

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
	 * Peso carga
	 */
	@JsonProperty(value="peso")
	private int peso;
	
	/**
	 * Volumen
	 */
	@JsonProperty(value="volumen")
	private int volumen;
	
	/**
	 * Contenido
	 */
	@JsonProperty(value="contenido")
	private String contenido;

	public Remitente(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="tipoIdent")String tipoIdent, 
			@JsonProperty(value="nacionalidad")String nacionalidad, @JsonProperty(value="peso")int peso, @JsonProperty(value="volumen")int volumen,
			@JsonProperty(value="contenido")String contenido) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipoIdent = tipoIdent;
		this.nacionalidad = nacionalidad;
		this.peso = peso;
		this.volumen = volumen;
		this.contenido = contenido;
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

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int getVolumen() {
		return volumen;
	}

	public void setVolumen(int volumen) {
		this.volumen = volumen;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
}

