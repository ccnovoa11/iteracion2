package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Remitente;
import vos.Viajero;
import vos.VueloPasajero;

public class DAOTablaCliente {

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
	public DAOTablaCliente() {
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
	public ArrayList<Remitente> darRemitentes() throws SQLException, Exception {
		ArrayList<Remitente> remitentes = new ArrayList<Remitente>();

		String sql = "SELECT * FROM ISIS2304B041620.REMITENTE";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO_IDENT");
			String nacionalidad = rs.getString("NACIONALIDAD");
			int peso = rs.getInt("PESOCARGA");
			int volumen = rs.getInt("VOLUMEN");
			String contenido = rs.getString("CONTENIDO");
			remitentes.add(new Remitente(id, nombre, tipo,nacionalidad,peso,volumen,contenido));
		}
		return remitentes;
	}

	public ArrayList<Viajero> darViajeros() throws SQLException, Exception {
		ArrayList<Viajero> viajeros = new ArrayList<Viajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VIAJERO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO_IDENT");
			String nacionalidad = rs.getString("NACIONALIDAD");
			int esFrecuente = rs.getInt("ESFRECUENTE");

			viajeros.add(new Viajero(id, nombre, tipo,nacionalidad,esFrecuente));
		}
		return viajeros;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Remitente> buscarRemitentesPorNombre(String name) throws SQLException, Exception {
		ArrayList<Remitente> remitentes = new ArrayList<Remitente>();

		String sql = "SELECT * FROM ISIS2304B041620.REMITENTE WHERE NOMBRE ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {
			int id = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO_IDENT");
			String nacionalidad = rs.getString("NACIONALIDAD");
			int peso = rs.getInt("PESOCARGA");
			int volumen = rs.getInt("VOLUMEN");
			String contenido = rs.getString("CONTENIDO");
			remitentes.add(new Remitente(id, nombre, tipo,nacionalidad,peso,volumen,contenido));
		}

		return remitentes;
	}

	
	public ArrayList<Remitente> buscarRemitentesPorId(int id) throws SQLException, Exception {
		ArrayList<Remitente> remitentes = new ArrayList<Remitente>();

		String sql = "SELECT * FROM ISIS2304B041620.REMITENTE WHERE IDENTIFICACION =" + id ;

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {
			int id2 = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO_IDENT");
			String nacionalidad = rs.getString("NACIONALIDAD");
			int peso = rs.getInt("PESOCARGA");
			int volumen = rs.getInt("VOLUMEN");
			String contenido = rs.getString("CONTENIDO");
			remitentes.add(new Remitente(id2, nombre, tipo,nacionalidad,peso,volumen,contenido));
		}

		return remitentes;
	}
	
	
	public ArrayList<Viajero> buscarViajerosPorNombre(String name) throws SQLException, Exception {
		ArrayList<Viajero> viajeros = new ArrayList<Viajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VIAJERO WHERE NOMBRE ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


		while (rs.next()) {
			int id = rs.getInt("IDENTIFICACION");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO_IDENT");
			String nacionalidad = rs.getString("NACIONALIDAD");
			int esFrecuente = rs.getInt("ESFRECUENTE");

			viajeros.add(new Viajero(id, nombre, tipo,nacionalidad,esFrecuente));
		}

		return viajeros;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRemitente(Remitente remitente) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.REMITENTE VALUES (";
		sql += remitente.getId() + ",'";
		sql += remitente.getNombre() + "','";
		sql += remitente.getTipoIdent() + "',";
		sql += remitente.getPeso() + ",";
		sql += remitente.getVolumen() + ",'";
		sql += remitente.getContenido() + "','";
		sql += remitente.getNacionalidad() + "')";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void addViajero(Viajero viajero) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B041620.VIAJERO VALUES (";
		sql += viajero.getId() + ",'";
		sql += viajero.getNombre() + "','";
		sql += viajero.getTipoIdent() + "','";
		sql += viajero.getNacionalidad() + "',";
		sql += viajero.getEsFrecuente() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	public ArrayList<VueloPasajero> buscarVuelosPorCodAerolienaViajero(int ide, String cod, String clase, int distancia) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT T2.* FROM ((SELECT * FROM ISIS2304B041620.RESERVA_PASAJERO INNER JOIN SILLA ON NUMSILLA=NUMERO )T1 INNER JOIN (SELECT* FROM VUELO_PASAJERO WHERE CODAEROLINEA = '"+cod+"' )T2 ON T1.ID_VUELO_PASAJERO = T2.ID ) WHERE T1.TIPO='"+clase+"' AND T2.DISTANCIA >= "+distancia+" AND T1.ID_VIAJERO="+ide+"";
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id2 = rs.getInt("ID");
			int horaLlegada = rs.getInt("HORALLEGADA");
			int horaSalida = rs.getInt("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia2 = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			String tipo = rs.getString("TIPO");
			int precioEj = rs.getInt("PRECIO_EJECUTIVO");
			int precioEc = rs.getInt("PRECIO_ECONOMICO");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			String numSerieAeronave = rs.getString("NUMSERIE_AERONAVE");
			java.sql.Date fechaLlegada = rs.getDate("FECHALLEGADA");
			java.sql.Date fechaSalida = rs.getDate("FECHASALIDA");
			vuelos.add(new VueloPasajero(id2, horaLlegada, horaSalida,frecuencia,distancia2,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino,numSerieAeronave,fechaLlegada,fechaSalida));
		}

		return vuelos;
	}
	
	public ArrayList<VueloPasajero> buscarVuelosPorCodAerolienaGerente(String cod, String clase, int distancia) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT T2.* FROM ((SELECT * FROM ISIS2304B041620.RESERVA_PASAJERO INNER JOIN SILLA ON NUMSILLA=NUMERO )T1 INNER JOIN (SELECT* FROM VUELO_PASAJERO WHERE CODAEROLINEA = '"+cod+"' )T2 ON T1.ID_VUELO_PASAJERO = T2.ID ) WHERE T1.TIPO='"+clase+"' AND T2.DISTANCIA >= "+distancia+"";
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id2 = rs.getInt("ID");
			int horaLlegada = rs.getInt("HORALLEGADA");
			int horaSalida = rs.getInt("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia2 = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			String tipo = rs.getString("TIPO");
			int precioEj = rs.getInt("PRECIO_EJECUTIVO");
			int precioEc = rs.getInt("PRECIO_ECONOMICO");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			String numSerieAeronave = rs.getString("NUMSERIE_AERONAVE");
			java.sql.Date fechaLlegada = rs.getDate("FECHALLEGADA");
			java.sql.Date fechaSalida = rs.getDate("FECHASALIDA");
			vuelos.add(new VueloPasajero(id2, horaLlegada, horaSalida,frecuencia,distancia2,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino,numSerieAeronave,fechaLlegada,fechaSalida));
		}

		return vuelos;
	}
	
	public ArrayList<VueloPasajero> buscarVuelosPorFechaViajero(int ide, String comienzo, String fin, String clase, int dist) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT T2.* FROM ((SELECT * FROM ISIS2304B041620.RESERVA_PASAJERO INNER JOIN SILLA ON NUMSILLA=NUMERO )T1 INNER JOIN (SELECT* FROM VUELO_PASAJERO WHERE FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY') )T2 ON T1.ID_VUELO_PASAJERO = T2.ID ) WHERE T1.TIPO='"+clase+"' AND T2.DISTANCIA >= "+dist+" AND T1.ID_VIAJERO="+ide+"";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id2 = rs.getInt("ID");
			int horaLlegada = rs.getInt("HORALLEGADA");
			int horaSalida = rs.getInt("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			String tipo = rs.getString("TIPO");
			int precioEj = rs.getInt("PRECIO_EJECUTIVO");
			int precioEc = rs.getInt("PRECIO_ECONOMICO");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			String numSerieAeronave = rs.getString("NUMSERIE_AERONAVE");
			java.sql.Date fechaLlegada = rs.getDate("FECHALLEGADA");
			java.sql.Date fechaSalida = rs.getDate("FECHASALIDA");
			vuelos.add(new VueloPasajero(id2, horaLlegada, horaSalida,frecuencia,distancia,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino,numSerieAeronave,fechaLlegada,fechaSalida));
		}

		return vuelos;
	}

	public ArrayList<VueloPasajero> buscarVuelosPorFechaGerente(String comienzo, String fin, String clase, int dist) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT T2.* FROM ((SELECT * FROM ISIS2304B041620.RESERVA_PASAJERO INNER JOIN SILLA ON NUMSILLA=NUMERO )T1 INNER JOIN (SELECT* FROM VUELO_PASAJERO WHERE FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY') )T2 ON T1.ID_VUELO_PASAJERO = T2.ID ) WHERE T1.TIPO='"+clase+"' AND T2.DISTANCIA >= "+dist+"";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id2 = rs.getInt("ID");
			int horaLlegada = rs.getInt("HORALLEGADA");
			int horaSalida = rs.getInt("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			String tipo = rs.getString("TIPO");
			int precioEj = rs.getInt("PRECIO_EJECUTIVO");
			int precioEc = rs.getInt("PRECIO_ECONOMICO");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			String numSerieAeronave = rs.getString("NUMSERIE_AERONAVE");
			java.sql.Date fechaLlegada = rs.getDate("FECHALLEGADA");
			java.sql.Date fechaSalida = rs.getDate("FECHASALIDA");
			vuelos.add(new VueloPasajero(id2, horaLlegada, horaSalida,frecuencia,distancia,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino,numSerieAeronave,fechaLlegada,fechaSalida));
		}

		return vuelos;
	}
	

	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRemitente(Remitente remitente) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.REMITENTE SET ";
		
		sql += "NOMBRE='" +remitente.getNombre() + "',";
		sql += "TIPO_IDENT='" +remitente.getTipoIdent() + "',";
		sql += "NACIONALIDAD='" +remitente.getNacionalidad() + "',";
		sql += "PESOCARGA=" +remitente.getPeso() + ",";
		sql += "VOLUMEN=" +remitente.getVolumen() + ",";
		sql += "CONTENIDO='" +remitente.getContenido();		
		sql += "' WHERE IDENTIFICACION = " + remitente.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void updateViajero(Viajero viajero) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B041620.VIAJERO SET ";
		
		sql += "NOMBRE='" +viajero.getNombre() + "',";
		sql += "TIPO_IDENT='" +viajero.getTipoIdent() + "',";
		sql += "NACIONALIDAD='" +viajero.getNacionalidad() + "',";
		sql += "ESFRECUENTE=" +viajero.getEsFrecuente();		
		sql += " WHERE IDENTIFICACION = " + viajero.getId();

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
	public void deleteRemitente(Remitente remitente) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.REMITENTE";
		sql += " WHERE IDENTIFICACION = " + remitente.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteViajero(Viajero viajero) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B041620.VIAJERO";
		sql += " WHERE IDENTIFICACION = " + viajero.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
