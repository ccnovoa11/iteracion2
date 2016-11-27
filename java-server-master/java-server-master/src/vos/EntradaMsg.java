package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class EntradaMsg {

	@JsonProperty(value="usuarios")
	private List<Integer> usuarios;

	@JsonProperty(value="origen")
	private String origen;

	@JsonProperty(value="destino")
	private String destino;

	public EntradaMsg(@JsonProperty(value="usuarios")List<Integer> usuarios, @JsonProperty(value="origen")String origen, @JsonProperty(value="destino")String destino) {
		super();
		this.usuarios = usuarios;
		this.origen = origen;
		this.destino = destino;
	}

	public List<Integer> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Integer> usuarios) {
		this.usuarios = usuarios;
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
	
	
	
}
