/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos1.Aerolinea;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaAerolineas {


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
	public DAOTablaAerolineas() {
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
	public ArrayList<Aerolinea> darAerolineas() throws SQLException, Exception {
		ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>();

		String sql = "SELECT * FROM ISIS2304B041620.AEROLINEA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String codigo = rs.getString("CODIGO");
			String iata = rs.getString("IATA");
			String nombre = rs.getString("NOMBRE");
			String pais = rs.getString("PAIS");
			String correo = rs.getString("CORREO");
			String persona = rs.getString("PERSONA");
			aerolineas.add(new Aerolinea(codigo, iata, nombre,pais,correo,persona));
		}
		return aerolineas;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Aerolinea> buscarAerolineasPorNombre(String name) throws SQLException, Exception {
		ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>();

		String sql = "SELECT * FROM ISIS2304B041620.AEROLINEA WHERE NOMBRE ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String codigo = rs.getString("CODIGO");
			String iata = rs.getString("IATA");
			String pais = rs.getString("PAIS");
			String correo = rs.getString("CORREO");
			String persona = rs.getString("PERSONA");
			aerolineas.add(new Aerolinea(codigo,iata,nombre,pais,correo,persona));
		}

		return aerolineas;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addAerolinea(Aerolinea aerolinea) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.AEROLINEA VALUES (";
		sql += "'" + aerolinea.getCodigo() + "','";
		sql += aerolinea.getIata() + "','";
		sql += aerolinea.getNombre() + "','";
		sql += aerolinea.getPais()  + "','";
		sql += aerolinea.getCorreo() + "','";
		sql += aerolinea.getPersona() + "')";

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
	public void updatAerolinea(Aerolinea aerolinea) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.AEROLINEA SET ";
		sql += "NOMBRE='" + aerolinea.getNombre() + "',";
		sql += "IATA='" + aerolinea.getIata()+ "',";
		sql += "PAIS='" + aerolinea.getPais()+ "',";
		sql += "CORREO='" + aerolinea.getCorreo()+ "',";
		sql += "PERSONA='" + aerolinea.getPersona()+ "'";
		sql += " WHERE CODIGO = '" + aerolinea.getCodigo()+ "'";

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
	public void deleteAerolinea(Aerolinea aerolinea) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.AEROLINEA";
		sql += " WHERE CODIGO = '" + aerolinea.getCodigo()+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

//	/**
//	 * Método que busca el/los videos mas alquilados.
//	 * @return Arraylist con los videos encontrados
//	 * @throws SQLException - Cualquier error que la base de datos arroje.
//	 * @throws Exception - Cualquier error que no corresponda a la base de datos
//	 */
//	public ArrayList<Video> darVideoMasAlquilado()  throws SQLException, Exception {
//		ArrayList<Video> videos = new ArrayList<Video>();
//
//		String sql = "SELECT * " +
//					 "FROM ISIS2304MO11620.VIDEOS " +
//				     "WHERE ISIS2304MO11620.VIDEOS.ID IN (SELECT VIDEO_ID " +
//				                         "FROM ALQUILERES " +
//				                         "GROUP BY VIDEO_ID " +
//				                         "HAVING COUNT(*) = (SELECT MAX(COUNT(*)) " +
//				                                            "FROM ALQUILERES " +
//				                                            "GROUP BY VIDEO_ID)) ";
//
//		System.out.println("SQL stmt:" + sql);
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name = rs.getString("NAME");
//			int id = Integer.parseInt(rs.getString("ID"));
//			int duration = Integer.parseInt(rs.getString("DURATION"));
//			videos.add(new Video(id, name, duration));
//		}
//
//		return videos;
//	}


}