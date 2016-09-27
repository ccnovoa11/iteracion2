package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ReservaCarga;
import vos.ReservaPasajero;

public class DAOTablaReservas {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaReservas() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Método que, usando la conexión a la base de datos, saca todos las aerolineas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM AEROLINEA;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ReservaPasajero> darReservasPasajeros() throws SQLException, Exception {
		ArrayList<ReservaPasajero> reservas = new ArrayList<ReservaPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.RESERVA_PASAJERO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("ID");
			String numSilla = rs.getString("NUMSILLA");
			int idViajero = rs.getInt("ID_VIAJERO");
			int idVPasajero = rs.getInt("ID_VUELO_PASAJERO");
			reservas.add(new ReservaPasajero(id, numSilla, idViajero,idVPasajero));
		}
		return reservas;
	}
	
	public ArrayList<ReservaCarga> darReservasCarga() throws SQLException, Exception {
		ArrayList<ReservaCarga> reservas = new ArrayList<ReservaCarga>();

		String sql = "SELECT * FROM ISIS2304B041620.RESERVA_CARGA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("ID");
			int idViajero = rs.getInt("ID_REMITENTE");
			int idVPasajero = rs.getInt("ID_VUELO_CARGA");
			reservas.add(new ReservaCarga(id, idViajero,idVPasajero));
		}
		return reservas;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ReservaPasajero> buscarReservasPorVuelo(int vuelo) throws SQLException, Exception {
		ArrayList<ReservaPasajero> reservas = new ArrayList<ReservaPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.RESERVA_PASAJERO WHERE ID_VUELO_PASAJERO ='" + vuelo + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("ID");
			String numSilla = rs.getString("NUMSILLA");
			int idViajero = rs.getInt("ID_VIAJERO");
			int idVPasajero = rs.getInt("ID_VUELO_PASAJERO");
			reservas.add(new ReservaPasajero(id, numSilla, idViajero,idVPasajero));
		}

		return reservas;
	}
	
	public ArrayList<ReservaCarga> buscarReservasPorVueloCarga(int vuelo) throws SQLException, Exception {
		ArrayList<ReservaCarga> reservas = new ArrayList<ReservaCarga>();

		String sql = "SELECT * FROM ISIS2304B041620.RESERVA_CARGA WHERE ID_VUELO_CARGA ='" + vuelo + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("ID");
			int idViajero = rs.getInt("ID_REMITENTE");
			int idVPasajero = rs.getInt("ID_VUELO_PASAJERO");
			reservas.add(new ReservaCarga(id, idViajero,idVPasajero));
		}

		return reservas;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addReserva(ReservaPasajero reserva) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.RESERVA_PASAJERO VALUES (";
		sql += reserva.getId() + ",'";
		sql += reserva.getNumSilla() + "',";
		sql += reserva.getIdViajero() + ",";
		sql += reserva.getIdVueloPasajero() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public void addReservaCarga(ReservaCarga reserva) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.RESERVA_CARGA VALUES (";
		sql += reserva.getId() + ",'";
		sql += reserva.getIdRemi() + ",";
		sql += reserva.getIdVueloCarga() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatReserva(ReservaPasajero reserva) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.RESERVA_PASAJERO SET ";
		sql += "NUMSILLA='" +reserva.getNumSilla() + "',";
		sql += "ID_VIAJERO=" +reserva.getIdViajero() + ",";
		sql += "ID_VUELO_PASAJERO=" +reserva.getIdVueloPasajero();
		sql += " WHERE ID = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void updatReservaCarga(ReservaCarga reserva) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.RESERVA_CARGA SET ";
		sql += "ID_REMITENTE='" +reserva.getIdRemi()+ ",";
		sql += "ID_VUELO_CARGA=" +reserva.getIdVueloCarga();
		sql += " WHERE ID = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Método que elimina el video que entra como parámetro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteReserva(ReservaPasajero reserva) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.RESERVA_PASAJERO";
		sql += " WHERE ID = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteReservaCarga(ReservaCarga reserva) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.RESERVA_CARGA";
		sql += " WHERE ID = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
