package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VueloPasajero {

////Atributos

	/**
	 * Identificacion
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Hora llegada
	 */
	@JsonProperty(value="horaLlegada")
	private String horaLlegada;
	
	/**
	 * Hora salida
	 */
	@JsonProperty(value="horaSalida")
	private String horaSalida;
	
	/**
	 * Frecuencia
	 */
	@JsonProperty(value="frecuencia")
	private int frecuencia;
	
	/**
	 * Distancia
	 */
	@JsonProperty(value="distancia")
	private int distancia;
	
	/**
	 * Duracion
	 */
	@JsonProperty(value="duracion")
	private int duracion;
	
	/**
	 * Tipo(nacional o internacional)
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * Precio por silla ejecutiva
	 */
	@JsonProperty(value="precioEjecutiva")
	private int precioEjecutiva;
	
	/**
	 * Precio por silla economica
	 */
	@JsonProperty(value="precioEconomica")
	private int precioEconomica;
	
	/**
	 * Codigo de la aerolinea
	 */
	@JsonProperty(value="codAerolinea")
	private String codAerolinea;
	
	/**
	 * Id del aeropuerto de origen
	 */
	@JsonProperty(value="idAeroOrigen")
	private int idAeroOrigen;
	
	/**
	 * Id del aeropuerto de destino
	 */
	@JsonProperty(value="idAeroDestino")
	private int idAeroDestino;
	
	/**
	 * Numero de serie de la aeronave
	 */
	@JsonProperty(value="numSerieAeronave")
	private String numSerieAeronave;

	public VueloPasajero(@JsonProperty(value="id")int id, @JsonProperty(value="horaLlegada")String horaLlegada, @JsonProperty(value="horaSalida")String horaSalida, @JsonProperty(value="frecuencia")int frecuencia, 
			@JsonProperty(value="distancia")int distancia,@JsonProperty(value="duracion") int duracion,
			@JsonProperty(value="precioEjecutiva")int precioEjecutiva,@JsonProperty(value="precioEconomica")int precioEconomica, @JsonProperty(value="codAerolinea") String codAerolinea, @JsonProperty(value="idAeroOrigen")int idAeroOrigen,
			@JsonProperty(value="idAeroDestino")int idAeroDestino, @JsonProperty(value="numSerieAeronave") String numSerieAeronave) {
		
		this.id = id;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.frecuencia = frecuencia;
		this.distancia = distancia;
		this.duracion = duracion;
		this.precioEjecutiva = precioEjecutiva;
		this.precioEconomica = precioEconomica;
		this.codAerolinea = codAerolinea;
		this.idAeroOrigen = idAeroOrigen;
		this.idAeroDestino = idAeroDestino;
		this.numSerieAeronave = numSerieAeronave;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(String horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPrecioEjecutiva() {
		return precioEjecutiva;
	}

	public void setPrecioEjecutiva(int precioEjecutiva) {
		this.precioEjecutiva = precioEjecutiva;
	}

	public int getPrecioEconomica() {
		return precioEconomica;
	}

	public void setPrecioEconomica(int precioEconomica) {
		this.precioEconomica = precioEconomica;
	}

	public String getCodAerolinea() {
		return codAerolinea;
	}

	public void setCodAerolinea(String codAerolinea) {
		this.codAerolinea = codAerolinea;
	}

	public int getIdAeroOrigen() {
		return idAeroOrigen;
	}

	public void setIdAeroOrigen(int idAeroOrigen) {
		this.idAeroOrigen = idAeroOrigen;
	}

	public int getIdAeroDestino() {
		return idAeroDestino;
	}

	public void setIdAeroDestino(int idAeroDestino) {
		this.idAeroDestino = idAeroDestino;
	}

	public String getNumSerieAeronave() {
		return numSerieAeronave;
	}

	public void setNumSerieAeronave(String numSerieAeronave) {
		this.numSerieAeronave = numSerieAeronave;
	}

}
