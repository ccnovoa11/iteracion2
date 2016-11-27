package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReservasMsg {

	@JsonProperty(value="reservasMsg")
	private List<ReservaMsg> reservas;
	

	public ListaReservasMsg( @JsonProperty(value="reservasMsg")List<ReservaMsg> reservas){
		this.reservas = reservas;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<ReservaMsg> getReservas() {
		return reservas;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setReserva(List<ReservaMsg> reservas) {
		this.reservas = reservas;
	}
	
}
