package vos1;

import org.codehaus.jackson.annotate.JsonProperty;

public class RangoFechas {
	
	/**
	 * Fecha inicio
	 */
	@JsonProperty(value="fechaInicio")
	private java.sql.Date fechaInicio;

	/**
	 * Fecha fin
	 */
	@JsonProperty(value="fechaFin")
	private java.sql.Date fechaFin;

	public java.sql.Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(java.sql.Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public java.sql.Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(java.sql.Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
