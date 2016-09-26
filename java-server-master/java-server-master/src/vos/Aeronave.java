package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Aeronave {

////Atributos

	/**
	 * Numero de serie
	 */
	@JsonProperty(value="numSerie")
	private String numSerie;

	/**
	 * Marca
	 */
	@JsonProperty(value="marca")
	private String marca;

	/**
	 * Modelo
	 */
	@JsonProperty(value="modelo")
	private String modelo;
	
	/**
	 * Ano de fabricacion
	 */
	@JsonProperty(value="anoFabricacion")
	private int anoFabricacion;
	
	/**
	 * Tamano
	 */
	@JsonProperty(value="tamano")
	private String tamano;
	
	/**
	 * Capacidad
	 */
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	/**
	 * Esta en arriendo. 0=No, 1=Si
	 */
	@JsonProperty(value="enArriendo")
	private int enArriendo;

	/**
	 * Codigo de la aerolinea
	 */
	@JsonProperty(value="codAerolinea")
	private String codAerolinea;
	
	/**
	 * Id del administrador
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
	public Aeronave(@JsonProperty(value="numSerie")String numSerie, @JsonProperty(value="marca")String marca, @JsonProperty(value="modelo")String modelo, @JsonProperty(value="anoFabricacion")int anoFabricacion, @JsonProperty(value="tamano")String tamano,@JsonProperty(value="capacidad") int capacidad,
			@JsonProperty(value="enArriendo")int enArriendo, @JsonProperty(value="codAerolinea")String codAerolinea,@JsonProperty(value="idAdmin")int idAdmin ) {

		this.numSerie = numSerie;
		this.marca = marca;
		this.modelo = modelo;
		this.anoFabricacion = anoFabricacion;
		this.tamano = tamano;
		this.capacidad = capacidad;
		this.enArriendo = enArriendo;
		this.codAerolinea=codAerolinea;
		this.idAdmin=idAdmin;
	}


	public String getNumSerie() {
		return numSerie;
	}


	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public int getAnoFabricacion() {
		return anoFabricacion;
	}


	public void setAnoFabricacion(int anoFabricacion) {
		this.anoFabricacion = anoFabricacion;
	}


	public String getTamano() {
		return tamano;
	}


	public void setTamano(String tamano) {
		this.tamano = tamano;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}


	public int getEnArriendo() {
		return enArriendo;
	}


	public void setEnArriendo(int enArriendo) {
		this.enArriendo = enArriendo;
	}


	public String getCodAerolinea() {
		return codAerolinea;
	}


	public void setCodAerolinea(String codAerolinea) {
		this.codAerolinea = codAerolinea;
	}


	public int getIdAdmin() {
		return idAdmin;
	}


	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	
	
	
}
