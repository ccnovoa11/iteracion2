package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class AerolineaMsg 
{
	@JsonProperty(value="iata")
	private String iata;

	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="noVuelosCarga")
	private int noVuelosCarga;
	
	@JsonProperty(value="noElementos")
	private int noElementos;
	
	@JsonProperty(value="ingresosVuelosCarga")
	private int ingresosVuelosCarga;
	
	@JsonProperty(value="noVuelosPasajeros")
	private int noVuelosPasajeros;
	
	@JsonProperty(value="noPasajeros")
	private int noPasajeros;
	
	@JsonProperty(value="ingresosVuelosPasajeros")
	private int ingresosVuelosPasajeros;

	
	
	public AerolineaMsg(@JsonProperty(value="iata")String iata,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="noVuelosCarga") int noVuelosCarga,@JsonProperty(value="noElementos") int noElementos,@JsonProperty(value="ingresosVuelosCarga") int ingresosVuelosCarga,
			@JsonProperty(value="noVuelosPasajeros") int noVuelosPasajeros,@JsonProperty(value="noPasajeros") int noPasajeros,@JsonProperty(value="ingresosVuelosPasajeros") int ingresosVuelosPasajeros)
	{
		super();
		this.iata = iata;
		this.nombre = nombre;
		this.noVuelosCarga = noVuelosCarga;
		this.noElementos = noElementos;
		this.ingresosVuelosCarga = ingresosVuelosCarga;
		this.noVuelosPasajeros = noVuelosPasajeros;
		this.noPasajeros = noPasajeros;
		this.ingresosVuelosPasajeros = ingresosVuelosPasajeros;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNoVuelosCarga() {
		return noVuelosCarga;
	}

	public void setNoVuelosCarga(int noVuelosCarga) {
		this.noVuelosCarga = noVuelosCarga;
	}

	public int getNoElementos() {
		return noElementos;
	}

	public void setNoElementos(int noElementos) {
		this.noElementos = noElementos;
	}

	public int getIngresosVuelosCarga() {
		return ingresosVuelosCarga;
	}

	public void setIngresosVuelosCarga(int ingresosVuelosCarga) {
		this.ingresosVuelosCarga = ingresosVuelosCarga;
	}

	public int getNoVuelosPasajeros() {
		return noVuelosPasajeros;
	}

	public void setNoVuelosPasajeros(int noVuelosPasajeros) {
		this.noVuelosPasajeros = noVuelosPasajeros;
	}

	public int getNoPasajeros() {
		return noPasajeros;
	}

	public void setNoPasajeros(int noPasajeros) {
		this.noPasajeros = noPasajeros;
	}

	public int getIngresosVuelosPasajeros() {
		return ingresosVuelosPasajeros;
	}

	public void setIngresosVuelosPasajeros(int ingresosVuelosPasajeros) {
		this.ingresosVuelosPasajeros = ingresosVuelosPasajeros;
	}
	
}
