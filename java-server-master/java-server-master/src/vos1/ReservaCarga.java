package vos1;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaCarga {
	
////Atributos

	/**
	 * Identificacion
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Identificacion del remitente
	 */
	@JsonProperty(value="idRemi")
	private int idRemi;
	
	/**
	 * Identificacion del vuelo de carga
	 */
	@JsonProperty(value="idVueloCarga")
	private int idVueloCarga;

	public ReservaCarga(@JsonProperty(value="id")int id, @JsonProperty(value="idRemi")int idRemi, @JsonProperty(value="idVueloCarga")int idVueloCarga) {

		this.id = id;
		this.idRemi = idRemi;
		this.idVueloCarga = idVueloCarga;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdRemi() {
		return idRemi;
	}

	public void setIdRemi(int idRemi) {
		this.idRemi = idRemi;
	}

	public int getIdVueloCarga() {
		return idVueloCarga;
	}

	public void setIdVueloCarga(int idVueloCarga) {
		this.idVueloCarga = idVueloCarga;
	}
	
	
}
