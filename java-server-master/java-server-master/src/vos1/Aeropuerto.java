package vos1;

import org.codehaus.jackson.annotate.JsonProperty;

public class Aeropuerto {

////Atributos

	/**
	 * Codigo
	 */
	@JsonProperty(value="codigo")
	private int codigo;

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
	
	/**
	 * Tipo
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * Capacidad
	 */
	@JsonProperty(value="capacidad")
	private String capacidad;
	
	/**
	 * Id del admin
	 */
	@JsonProperty(value="idAdmin")
	private int idAdmin;

	/**
	 * Método constructor de la clase aerolinea
	 * <b>post: </b> Crea la aerolinea con los valores que entran como parámetro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duración en minutos del video.
	 */
	public Aeropuerto(@JsonProperty(value="codigo")int codigo, @JsonProperty(value="nombre") String nombre, 
			@JsonProperty(value="pais") String pais, @JsonProperty(value="tipo") String tipo,
			@JsonProperty(value="capacidad") String capacidad, @JsonProperty(value="idAdmin") int idAdmin) {

		this.codigo = codigo;
		this.nombre = nombre;
		this.pais =pais;
		this.capacidad = capacidad;
		this.tipo=tipo;
		this.idAdmin=idAdmin;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	
	
}
