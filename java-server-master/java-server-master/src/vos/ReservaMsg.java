package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaMsg {
	
	@JsonProperty(value="id")
	private String id;
	
	@JsonProperty(value="idUsuario")
	private int idUsuario;

	@JsonProperty(value="silla")
	private String silla;
	
	@JsonProperty(value="peso")
	private Double peso;
	
	@JsonProperty(value="idVuelo")
	private String idVuelo;
	
	public ReservaMsg(@JsonProperty(value="id")String id, @JsonProperty(value="idUsuario")int idUsuario,@JsonProperty(value="silla")String silla,
			@JsonProperty(value="peso") Double peso,@JsonProperty(value="idVuelo")String idVuelo){
		super();
		this.id = id;
		this.idUsuario=idUsuario;
		this.silla=silla;
		this.peso=peso;
		this.idVuelo=idVuelo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getSilla() {
		return silla;
	}

	public void setSilla(String silla) {
		this.silla = silla;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo(String idVuelo) {
		this.idVuelo = idVuelo;
	}
	
	

}
