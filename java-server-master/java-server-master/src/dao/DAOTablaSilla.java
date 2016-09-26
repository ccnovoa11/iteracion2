package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Silla;

public class DAOTablaSilla {

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
	public DAOTablaSilla() {
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
	public ArrayList<Silla> darSillas() throws SQLException, Exception {
		ArrayList<Silla> sillas = new ArrayList<Silla>();

		String sql = "SELECT * FROM ISIS2304B041620.SILLA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			String numero = rs.getString("NUMERO");
			String tipo = rs.getString("TIPO");
			String numAeronave = rs.getString("NUMSERIE_AERONAVE");
			int ocupada = rs.getInt("OCUPADA");
			sillas.add(new Silla(numero, tipo, numAeronave,ocupada));
		}
		return sillas;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Silla> buscarSillasPorTipo(String tipo) throws SQLException, Exception {
		ArrayList<Silla> sillas = new ArrayList<Silla>();

		String sql = "SELECT * FROM ISIS2304B041620.SILLA WHERE TIPO ='" + tipo + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String numero = rs.getString("NUMERO");
			String tipo2 = rs.getString("TIPO");
			String numAeronave = rs.getString("NUMSERIE_AERONAVE");
			int ocupada = rs.getInt("OCUPADA");
			sillas.add(new Silla(numero,tipo2,numAeronave,ocupada));
		}

		return sillas;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addSilla(Silla silla) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.SILLA VALUES (";
		sql += silla.getNumero() + ",'";
		sql += silla.getTipo() + "',";
		sql += silla.getNumSerieAeronave() + "',";
		sql += silla.getOcupada() + ")";

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
	public void updateSilla(Silla silla) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.SILLA SET ";
		sql += "TIPO='" + silla.getTipo() + "',";
		sql += "NUMSERIE_AERONAVE=" + silla.getNumSerieAeronave()+ "',";
		sql += "OCUPADA=" + silla.getOcupada();
		sql += " WHERE NUMERO = " + silla.getNumero();

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
	public void deleteSilla(Silla silla) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.SILLA";
		sql += " WHERE NUMERO = " + silla.getNumero();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
