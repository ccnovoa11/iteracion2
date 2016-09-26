package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReservasCarga {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="reservasCarga")
	private List<ReservaCarga> reservasCarga;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaReservasCarga( @JsonProperty(value="reservasCarga")List<ReservaCarga> reservasCarga){
		this.reservasCarga = reservasCarga;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<ReservaCarga> getReservasCarga() {
		return reservasCarga;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setReservaCarga(List<ReservaCarga> reservasCarga) {
		this.reservasCarga = reservasCarga;
	}

}
