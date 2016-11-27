package vos1;

import org.codehaus.jackson.annotate.JsonProperty;

public class Silla {

	/**
	 * Numero
	 */
	@JsonProperty(value="numero")
	private String numero;
	
	/**
	 * Tipo 
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	/**
	 * Nacionalidad
	 */
	@JsonProperty(value="numSerieAeronave")
	private String numSerieAeronave;
	

	/**
	 * Está ocupada 0=no 1=si
	 */
	@JsonProperty(value="ocupada")
	private int ocupada;


	public Silla(@JsonProperty(value="numero")String numero,@JsonProperty(value="tipo") String tipo, 
			@JsonProperty(value="numSerieAeronave")String numSerieAeronave, @JsonProperty(value="ocupada")int ocupada) {
		this.numero = numero;
		this.tipo = tipo;
		this.numSerieAeronave = numSerieAeronave;
		this.ocupada = ocupada;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getNumSerieAeronave() {
		return numSerieAeronave;
	}


	public void setNumSerieAeronave(String numSerieAeronave) {
		this.numSerieAeronave = numSerieAeronave;
	}


	public int getOcupada() {
		return ocupada;
	}


	public void setOcupada(int ocupada) {
		this.ocupada = ocupada;
	}
	
	
}
