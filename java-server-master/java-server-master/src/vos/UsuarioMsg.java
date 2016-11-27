package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class UsuarioMsg 
{
	@JsonProperty(value="id")
	private int id;

	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="nacionalidad")
	private String nacionalidad;
	
	public UsuarioMsg(@JsonProperty(value="id") int id,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="nacionalidad") String nacionalidad)
	{
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
}
