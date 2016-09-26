package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Aeropuerto;

public class DAOTablaAeropuertos {

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
	public DAOTablaAeropuertos() {
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
	public ArrayList<Aeropuerto> darAeropuertos() throws SQLException, Exception {
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>();

		String sql = "SELECT * FROM ISIS2304B041620.AEROPUERTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			int codigo = rs.getInt("CODIGO");
			String nombre = rs.getString("NOMBRE");
			String pais = rs.getString("PAIS");
			String tipo = rs.getString("TIPO");
			String capacidad = rs.getString("CAPACIDAD");
			int idAdmin = rs.getInt("IDADMIN");
			aeropuertos.add(new Aeropuerto(codigo,nombre,pais,tipo,capacidad,idAdmin));
		}
		return aeropuertos;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Aeropuerto> buscarAeropuertosPorNombre(String name) throws SQLException, Exception {
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>();

		String sql = "SELECT * FROM ISIS2304B041620.AEROPUERTO WHERE NOMBRE ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int codigo = rs.getInt("CODIGO");
			String nombre = rs.getString("NOMBRE");
			String pais = rs.getString("PAIS");
			String tipo = rs.getString("TIPO");
			String capacidad = rs.getString("CAPACIDAD");
			int idAdmin = rs.getInt("IDADMIN");
			aeropuertos.add(new Aeropuerto(codigo,nombre,pais,tipo,capacidad,idAdmin));
		}

		return aeropuertos;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addAeropuerto(Aeropuerto aeropuerto) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.AEROPUERTO VALUES (";
		sql += aeropuerto.getCodigo() + ",'";
		sql += aeropuerto.getNombre() + "','";
		sql += aeropuerto.getPais() + "','";
		sql += aeropuerto.getTipo() + "','";
		sql += aeropuerto.getCapacidad() + "',";
		sql += aeropuerto.getIdAdmin() + ")";

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
	public void updateAeropuerto(Aeropuerto aeropuerto) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.AEROPUERTO SET ";
		
		sql += "NOMBRE='" + aeropuerto.getNombre() + "',";
		sql += "PAIS= '" + aeropuerto.getPais() + "',";
		sql += "TIPO= '" +aeropuerto.getTipo() + "',";
		sql += "CAPACIDAD= '" +aeropuerto.getCapacidad() + "',";
		sql += "IDADMIN=" + aeropuerto.getIdAdmin();
		sql += " WHERE CODIGO = " + aeropuerto.getCodigo();


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
	public void deleteAeropuerto(Aeropuerto aeropuerto) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.AEROPUERTO";
		sql += " WHERE CODIGO = " + aeropuerto.getCodigo();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
