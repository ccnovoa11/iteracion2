package vos1;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReservasPasajero {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="reservasPasajero")
	private List<ReservaPasajero> reservasPasajero;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaReservasPasajero( @JsonProperty(value="reservasPasajero")List<ReservaPasajero> reservasPasajero){
		this.reservasPasajero = reservasPasajero;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<ReservaPasajero> getReservasPasajero() {
		return reservasPasajero;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setReservaPasajero(List<ReservaPasajero> reservasPasajero) {
		this.reservasPasajero = reservasPasajero;
	}

}
