package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VueloMsg {

	
////Atributos

	@JsonProperty(value="id")
	private String id;
	
	@JsonProperty(value="fecha")
	private String fecha;

	@JsonProperty(value="tipo")
	private String tipo;
	
	@JsonProperty(value="origen")
	private String origen;
	
	@JsonProperty(value="destino")
	private String destino;
	
	@JsonProperty(value="aerolinea")
	private String aerolinea;
	
	@JsonProperty(value="precio")
	private Double precio;

	/**
	 * Método constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parámetro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duración en minutos del video.
	 */
	public VueloMsg(@JsonProperty(value="id")String id, @JsonProperty(value="fecha")String fecha,@JsonProperty(value="tipo")String tipo, @JsonProperty(value="origen")String origen,
			@JsonProperty(value="destino")String destino, @JsonProperty(value="aerolinea")String aerolinea, @JsonProperty(value="precio") Double precio){
		super();
		this.id = id;
		this.fecha=fecha;
		this.tipo=tipo;
		this.origen=origen;
		this.destino=destino;
		this.aerolinea=aerolinea;
		this.precio=precio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getAerolinea() {
		return aerolinea;
	}

	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
		
}
