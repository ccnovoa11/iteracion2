package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Aeronave;
import vos.VueloPasajero;

public class DAOTablaVueloPasajero {

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
	public DAOTablaVueloPasajero() {
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
	public ArrayList<VueloPasajero> darVuelosPasajero() throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		
		while (rs.next()) {
			int id = rs.getInt("ID");
			String horaLlegada = rs.getString("HORALLEGADA");
			String horaSalida = rs.getString("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			String tipo = rs.getString("TIPO");
			int precioEj = rs.getInt("PRECIO_EJECUTIVO");
			int precioEc = rs.getInt("PRECIO_ECONOMICO");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");

			
			vuelos.add(new VueloPasajero(id, horaLlegada, horaSalida,frecuencia,distancia,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino));
		}
		return vuelos;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<VueloPasajero> buscarVuelosPorId(int id) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE ID ='" + id + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id2 = rs.getInt("ID");
			String horaLlegada = rs.getString("HORALLEGADA");
			String horaSalida = rs.getString("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			String tipo = rs.getString("TIPO");
			int precioEj = rs.getInt("PRECIO_EJECUTIVO");
			int precioEc = rs.getInt("PRECIO_ECONOMICO");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			vuelos.add(new VueloPasajero(id2, horaLlegada, horaSalida,frecuencia,distancia,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino));
		}

		return vuelos;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addVueloPasajero(VueloPasajero vuelo) throws SQLException, Exception {

		
		String sql = "INSERT INTO ISIS2304B041620.VUELO_PASAJERO VALUES (";
		sql += vuelo.getId() + ",'";
		sql += vuelo.getHoraLlegada() + "',";
		sql += vuelo.getHoraSalida() + "',";
		sql += vuelo.getFrecuencia() + "',";
		sql += vuelo.getDistancia() + "',";
		sql += vuelo.getDuracion() + "',";
		sql += vuelo.getTipo() + "',";
		sql += vuelo.getPrecioEjecutiva() + "',";
		sql += vuelo.getPrecioEconomica() + "',";
		sql += vuelo.getCodAerolinea() + "',";
		sql += vuelo.getIdAeroOrigen() + "',";
		sql += vuelo.getIdAeroDestino() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	
	public void asociarAeronaveViajePasajero(Aeronave aeronave, VueloPasajero vueloP) throws SQLException, Exception{
		
		String sql = "UPDATE ISIS2304B041620.VUELO_PASAJERO SET ";
		sql += "NUMSERIE_AERONAVE='" +aeronave.getNumSerie() + "'";
		sql += " WHERE ID = " + vueloP.getId();

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
	public void updatVueloPasajero(VueloPasajero vuelo) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.VUELO_PASAJERO SET ";
		sql += "HORALLEGADA='" +vuelo.getHoraLlegada() + "',";
		sql += "HORASALIDA" +vuelo.getHoraSalida() + "',";
		sql += "FRECUENCIA='" +vuelo.getFrecuencia() + "',";
		sql += "DISTANCIA='" +vuelo.getDistancia() + "',";
		sql += "DURACION='" +vuelo.getDuracion() + "',";
		sql += "TIPO='" +vuelo.getTipo() + "',";
		sql += "PRECIO_EJECUTIVO='" +vuelo.getPrecioEjecutiva() + "',";
		sql += "PRECIO_ECONOMICA='" +vuelo.getPrecioEconomica() + "',";
		sql += "CODAEROLINEA" +vuelo.getCodAerolinea() + "',";
		sql += "ID_AERO_ORIGEN='" +vuelo.getIdAeroOrigen() + "',";
		sql += "ID_AERO_DESTINO=" + vuelo.getIdAeroDestino();
		sql += " WHERE ID = " + vuelo.getId();

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
	public void deleteVueloPasajero(VueloPasajero vuelo) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.VUELO_PASAJERO";
		sql += " WHERE ID = " + vuelo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
