package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Admin {

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
	 * Correo electronico
	 */
	@JsonProperty(value="correo")
	private String correo;
	
	/**
	 * Rol
	 */
	@JsonProperty(value="rol")
	private String rol;

	public Admin(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre, @JsonProperty(value="correo")String correo, @JsonProperty(value="rol")String rol) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.rol = rol;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
}
