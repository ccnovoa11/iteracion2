package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VueloGeneral {
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
	private int horaLlegada;
	
	/**
	 * Hora salida
	 */
	@JsonProperty(value="horaSalida")
	private int horaSalida;
	
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
	 * Precio por silla ejecutiva
	 */
	@JsonProperty(value="precioEjecutiva")
	private int numeroPasajeros;
	
	/**
	 * Precio por silla economica
	 */
	@JsonProperty(value="precioEconomica")
	private int tamanoCarga;
	
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

	@JsonProperty(value="fechaLlegada")
	private java.sql.Date fechaLlegada;

	@JsonProperty(value="fechaSalida")
	private java.sql.Date fechaSalida;
	
	@JsonProperty(value="paisOrigen")
	private String paisOrigen;
	
	@JsonProperty(value="paisDestino")
	private String paisDestino;
	
	
	public VueloGeneral(@JsonProperty(value="id")int id, @JsonProperty(value="horaLlegada")int horaLlegada, @JsonProperty(value="horaSalida")int horaSalida, @JsonProperty(value="frecuencia")int frecuencia, 
			@JsonProperty(value="distancia")int distancia,@JsonProperty(value="duracion") int duracion,
			@JsonProperty(value="numeroPasajeros")int numeroPasajeros,@JsonProperty(value="tamanoCarga")int tamanoCarga, @JsonProperty(value="codAerolinea") String codAerolinea, @JsonProperty(value="idAeroOrigen")int idAeroOrigen,
			@JsonProperty(value="idAeroDestino")int idAeroDestino, @JsonProperty(value="numSerieAeronave") String numSerieAeronave, @JsonProperty(value="fechaLlegada") java.sql.Date fechaLlegada, @JsonProperty(value="fechaSalida") java.sql.Date fechaSalida
			,	@JsonProperty(value="paisOrigen") String paisOrigen, @JsonProperty(value="paisDestino") String paisDestino) 
	{
		
		this.id = id;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.frecuencia = frecuencia;
		this.distancia = distancia;
		this.duracion = duracion;
		this.numeroPasajeros = numeroPasajeros;
		this.tamanoCarga = tamanoCarga;
		this.codAerolinea = codAerolinea;
		this.idAeroOrigen = idAeroOrigen;
		this.idAeroDestino = idAeroDestino;
		this.numSerieAeronave = numSerieAeronave;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.paisOrigen = paisOrigen;
		this.paisDestino = paisDestino;
		
	}

	public java.sql.Date getFechaLlegada() {
		return fechaLlegada;
	}


	public void setFechaLlegada(java.sql.Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}


	public java.sql.Date getFechaSalida() {
		return fechaSalida;
	}


	public void setFechaSalida(java.sql.Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(int horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public int getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(int horaSalida) {
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

	public int getNumeroPasajeros() {
		return numeroPasajeros;
	}

	public void setNumeroPasajeros(int numeroPasajeros) {
		this.numeroPasajeros = numeroPasajeros;
	}

	public int getTamanoCarga() {
		return tamanoCarga;
	}

	public void setTamanoCarga(int tamanoCarga) {
		this.tamanoCarga = tamanoCarga;
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
	
	public String getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(String paisOrigen) {
		this.paisOrigen = paisOrigen;
	}
	
	public String getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}

}
