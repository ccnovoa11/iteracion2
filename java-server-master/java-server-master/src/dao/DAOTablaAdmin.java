package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Admin;
import vos.Aeronave;

public class DAOTablaAdmin {

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
	public DAOTablaAdmin() {
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
	public ArrayList<Admin> darAdmins() throws SQLException, Exception {
		ArrayList<Admin> admins = new ArrayList<Admin>();

		String sql = "SELECT * FROM ISIS2304B041620.ADMIN";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		

		while (rs.next()) {
			int id = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			admins.add(new Admin(id, nombre, correo,rol));
		}
		return admins;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Admin> buscarAdmisPorNombre(String name) throws SQLException, Exception {
		ArrayList<Admin> admins = new ArrayList<Admin>();

		String sql = "SELECT * FROM ISIS2304B041620.ADMIN WHERE NOMBRE ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			admins.add(new Admin(id, nombre, correo,rol));
		}

		return admins;
	}
	
	public ArrayList<Aeronave> buscarAeronaveNumSerie(int ide, String num) throws SQLException, Exception {
		ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();

		String sql = "SELECT * FROM AERONAVE_PASAJERO WHERE ID_ADMIN =" +ide +"AND NUMSERIE = '"+num+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String numSerie = rs.getString("NUMSERIE");
			String marca = rs.getString("MARCA");
			String modelo = rs.getString("MODELO");
			int anoFabricacion = rs.getInt("ANOFABRICACION");
			String tamano = rs.getString("TAMANO");
			int capacidadCarga = rs.getInt("CAPACIDAD_CARGA");
			int enArriendo = rs.getInt("EN_ARRIENDO");
			String codAerolinea = rs.getString("COD_AEROLINEA");
			int idAdmin = rs.getInt("ID_ADMIN");
			aeronaves.add(new Aeronave(numSerie, marca, modelo,anoFabricacion,tamano,capacidadCarga,enArriendo,codAerolinea,idAdmin));
			
		}

		return aeronaves;
	}
	
	public ArrayList<Aeronave> buscarAeronaveTamano(int ide, String cap) throws SQLException, Exception {
		ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();

		String sql = "SELECT * FROM AERONAVE_PASAJERO WHERE ID_ADMIN =" +ide +"AND TAMANO = '"+cap+"'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String numSerie = rs.getString("NUMSERIE");
			String marca = rs.getString("MARCA");
			String modelo = rs.getString("MODELO");
			int anoFabricacion = rs.getInt("ANOFABRICACION");
			String tamano = rs.getString("TAMANO");
			int capacidadCarga = rs.getInt("CAPACIDAD_CARGA");
			int enArriendo = rs.getInt("EN_ARRIENDO");
			String codAerolinea = rs.getString("COD_AEROLINEA");
			int idAdmin = rs.getInt("ID_ADMIN");
			aeronaves.add(new Aeronave(numSerie, marca, modelo,anoFabricacion,tamano,capacidadCarga,enArriendo,codAerolinea,idAdmin));
			
		}

		return aeronaves;
	}
	
	public ArrayList<Aeronave> buscarAeronaveCapacidad(int ide, int num) throws SQLException, Exception {
		ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();

		String sql = "SELECT * FROM AERONAVE_PASAJERO WHERE ID_ADMIN =" +ide +"CAPACIDAD_CARGA >="+num;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String numSerie = rs.getString("NUMSERIE");
			String marca = rs.getString("MARCA");
			String modelo = rs.getString("MODELO");
			int anoFabricacion = rs.getInt("ANOFABRICACION");
			String tamano = rs.getString("TAMANO");
			int capacidadCarga = rs.getInt("CAPACIDAD_CARGA");
			int enArriendo = rs.getInt("EN_ARRIENDO");
			String codAerolinea = rs.getString("COD_AEROLINEA");
			int idAdmin = rs.getInt("ID_ADMIN");
			aeronaves.add(new Aeronave(numSerie, marca, modelo,anoFabricacion,tamano,capacidadCarga,enArriendo,codAerolinea,idAdmin));
			
		}

		return aeronaves;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addAdmin(Admin admin) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.ADMIN VALUES (";
		sql += admin.getId() + ",'";
		sql += admin.getNombre() + "','";
		sql += admin.getCorreo() + "','";
		sql += admin.getRol() + "')";

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
	public void updatAdmin(Admin admin) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.ADMIN SET ";
		sql += "NOMBRE='" + admin.getNombre() + "',";
		sql += "CORREO='" + admin.getCorreo()+ "',";
		sql += "ROL='" + admin.getRol();
		sql += "' WHERE IDENTIFICACION = " + admin.getId();

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
	public void deleteAdmin(Admin admin) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.ADMIN";
		sql += " WHERE IDENTIFICACION = " + admin.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
