package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class VueloCarga {

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
	 * Tipo(nacional o internacional)
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * Precio por unidad de densidad
	 */
	@JsonProperty(value="precioDensidad")
	private int precioDensidad;
	
	/**
	 * Capacidad actual
	 */
	@JsonProperty(value="capacidadActual")
	private int capacidadActual;
	
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
	
	public VueloCarga(@JsonProperty(value="id")int id, @JsonProperty(value="horaLlegada")int horaLlegada, @JsonProperty(value="horaSalida")int horaSalida, @JsonProperty(value="frecuencia")int frecuencia, 
			@JsonProperty(value="distancia")int distancia,@JsonProperty(value="duracion") int duracion,
			@JsonProperty(value="precioDensidad")int precioDensidad, @JsonProperty(value="codAerolinea") String codAerolinea, @JsonProperty(value="idAeroOrigen")int idAeroOrigen,
			@JsonProperty(value="idAeroDestino")int idAeroDestino,@JsonProperty(value="numSerieAeronave") String numSerieAeronave,
			@JsonProperty(value="capacidadActual")int capacidadActual,@JsonProperty(value="tipo") String tipo) {
		super();
		this.id = id;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.frecuencia = frecuencia;
		this.distancia = distancia;
		this.duracion = duracion;
		this.precioDensidad = precioDensidad;
		this.codAerolinea = codAerolinea;
		this.idAeroOrigen = idAeroOrigen;
		this.idAeroDestino = idAeroDestino;
		this.numSerieAeronave=numSerieAeronave;
		this.capacidadActual=capacidadActual;
		this.tipo=tipo;
	}
	
	public VueloCarga(@JsonProperty(value="id")int id, @JsonProperty(value="horaLlegada")int horaLlegada, @JsonProperty(value="horaSalida")int horaSalida, @JsonProperty(value="frecuencia")int frecuencia, 
			@JsonProperty(value="distancia")int distancia,@JsonProperty(value="duracion") int duracion,
			@JsonProperty(value="precioDensidad")int precioDensidad, @JsonProperty(value="codAerolinea") String codAerolinea, @JsonProperty(value="idAeroOrigen")int idAeroOrigen,
			@JsonProperty(value="idAeroDestino")int idAeroDestino,@JsonProperty(value="numSerieAeronave") String numSerieAeronave,
			@JsonProperty(value="capacidadActual")int capacidadActual,@JsonProperty(value="tipo") String tipo,@JsonProperty(value="fechaLlegada") java.sql.Date fechaLlegada, @JsonProperty(value="fechaSalida") java.sql.Date fechaSalida) {
		super();
		this.id = id;
		this.horaLlegada = horaLlegada;
		this.horaSalida = horaSalida;
		this.frecuencia = frecuencia;
		this.distancia = distancia;
		this.duracion = duracion;
		this.precioDensidad = precioDensidad;
		this.codAerolinea = codAerolinea;
		this.idAeroOrigen = idAeroOrigen;
		this.idAeroDestino = idAeroDestino;
		this.numSerieAeronave=numSerieAeronave;
		this.capacidadActual=capacidadActual;
		this.tipo=tipo;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida= fechaSalida;
		
	}

	public int getId() {
		return id;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getPrecioDensidad() {
		return precioDensidad;
	}

	public void setPrecioDensidad(int precioDensidad) {
		this.precioDensidad = precioDensidad;
	}

	public int getCapacidadActual() {
		return capacidadActual;
	}

	public void setCapacidadActual(int capacidadActual) {
		this.capacidadActual = capacidadActual;
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
