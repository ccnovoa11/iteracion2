package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaPasajero {

////Atributos

	/**
	 * Identificacion
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Numero de silla
	 */
	@JsonProperty(value="numSilla")
	private String numSilla;
	
	/**
	 * Identificacion del viajero
	 */
	@JsonProperty(value="idViajero")
	private int idViajero;
	
	/**
	 * Identificacion del vuelo de pasajeros
	 */
	@JsonProperty(value="idVueloPasajero")
	private int idVueloPasajero;

	public ReservaPasajero(@JsonProperty(value="id")int id, @JsonProperty(value="numSilla")String numSilla, 
			@JsonProperty(value="idViajero")int idViajero, @JsonProperty(value="idVueloPasajero")int idVueloPasajero) {
		this.id = id;
		this.numSilla = numSilla;
		this.idViajero = idViajero;
		this.idVueloPasajero = idVueloPasajero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumSilla() {
		return numSilla;
	}

	public void setNumSilla(String numSilla) {
		this.numSilla = numSilla;
	}

	public int getIdViajero() {
		return idViajero;
	}

	public void setIdViajero(int idViajero) {
		this.idViajero = idViajero;
	}

	public int getIdVueloPasajero() {
		return idVueloPasajero;
	}

	public void setIdVueloPasajero(int idVueloPasajero) {
		this.idVueloPasajero = idVueloPasajero;
	}
	

	
}
