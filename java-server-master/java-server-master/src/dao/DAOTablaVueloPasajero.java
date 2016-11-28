package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos1.Aeronave;
import vos1.Aeropuerto;
import vos1.VueloCarga;
import vos1.VueloPasajero;

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


			vuelos.add(new VueloPasajero(id, horaLlegada, horaSalida,frecuencia,distancia,duracion,precioEj,precioEc,codAerolinea,
					idOrigen,idDestino,numSerieAeronave,fechaLlegada,fechaSalida));
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
					idOrigen,idDestino,numSerieAeronave, fechaLlegada,fechaSalida));
		}

		return vuelos;
	}

	public ArrayList<VueloPasajero> buscarVuelosPorIdAeropuerto(int id) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+")";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroPequenaPorId(int id) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT u.* FROM (SELECT * FROM AERONAVE_PASAJERO WHERE TAMANO='Pequena') INNER JOIN (SELECT * FROM VUELO_PASAJERO)u ON NUMSERIE=NUMSERIE_AERONAVE WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+")";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroMedianaPorId(int id) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT u.* FROM (SELECT * FROM AERONAVE_PASAJERO WHERE TAMANO='Mediana') INNER JOIN (SELECT * FROM VUELO_PASAJERO)u ON NUMSERIE=NUMSERIE_AERONAVE WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+")";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroGrandePorId(int id) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT u.* FROM (SELECT * FROM AERONAVE_PASAJERO WHERE TAMANO='Grande') INNER JOIN (SELECT * FROM VUELO_PASAJERO)u ON NUMSERIE=NUMSERIE_AERONAVE WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+")";

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

	public ArrayList<VueloPasajero> buscarVuelosPasajeroPorIdAeropuertoFecha(int id,String comienzo, String fin) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY')";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroPorAeropuertoAerolineaFecha(int id,String aerolinea,String comienzo, String fin) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND CODAEROLINEA='"+aerolinea+"' AND FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY')";

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
	
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroAAFT(int id, String comienzo, String fin,String aerolinea, String tipo) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT u.* FROM (SELECT * FROM AERONAVE_PASAJERO WHERE TAMANO='"+tipo+"') INNER JOIN (SELECT * FROM VUELO_PASAJERO)u ON NUMSERIE=NUMSERIE_AERONAVE WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY') AND CODAEROLINEA='"+aerolinea+"'";

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
			String tipo2 = rs.getString("TIPO");
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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroPorIdAeropuertoFechaNoAerolinea(int id,String comienzo, String fin,String aerolinea) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY') AND CODAEROLINEA !='"+aerolinea+"'";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroNoTipoPorId(int id,String comienzo, String fin, String tamano) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT u.* FROM (SELECT * FROM AERONAVE_PASAJERO WHERE TAMANO !='"+tamano+"') INNER JOIN (SELECT * FROM VUELO_PASAJERO)u ON NUMSERIE=NUMSERIE_AERONAVE WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY')";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPasajeroNoTipoNoAerolineaPorId(int id,String comienzo, String fin, String tamano,String aerolinea) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT u.* FROM (SELECT * FROM AERONAVE_PASAJERO WHERE TAMANO !='"+tamano+"') INNER JOIN (SELECT * FROM VUELO_PASAJERO)u ON NUMSERIE=NUMSERIE_AERONAVE WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND FECHASALIDA BETWEEN TO_DATE('"+comienzo+"','DD-MM-YYYY') AND TO_DATE('"+fin+"','DD-MM-YYYY')AND CODAEROLINEA!='"+aerolinea+"'";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPorIdAeropuertoAerolinea(int id,String aerolinea) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE (ID_AERO_ORIGEN =" + id+ " OR ID_AERO_DESTINO="+id+") AND CODAEROLINEA='"+aerolinea+"'";

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
	
	public ArrayList<VueloPasajero> buscarVuelosPorAerolinea(String aerolinea) throws SQLException, Exception {
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE CODAEROLINEA='"+aerolinea+"'";

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

	public ArrayList<VueloPasajero> buscarVuelosPorIdAeropuertoOrigen(int id) throws SQLException, Exception {

		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE ID_AERO_ORIGEN =" + id;

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


	public ArrayList<VueloPasajero> buscarVuelosPorIdAeropuertoDestino(int id) throws SQLException, Exception {


		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE ID_AERO_DESTINO =" + id;

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


	
	

	
		public ArrayList<VueloPasajero> buscarVuelosPaisOrigenDestino(String origen, String destino) throws SQLException, Exception {

		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT vp.* FROM (ISIS2304B041620.VUELO_PASAJERO vp INNER JOIN ISIS2304B041620.AEROPUERTO aer ON vp.ID_AERO_ORIGEN=aer.CODIGO) INNER JOIN ISIS2304B041620.AEROPUERTO des on vp.ID_AERO_DESTINO=des.CODIGO WHERE aer.PAIS ='"+ origen + "' and des.PAIS='"+destino+"'";

		System.out.println("SQL stmt:" + sql);
		System.out.println("conn:" + conn);

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
	
	
	
	//RF12/////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	
	
	public ArrayList<VueloPasajero> buscarVuelosOrigenDestino(int origen, int destino) throws SQLException, Exception {

		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();

		String sql = "SELECT * FROM ISIS2304B041620.VUELO_PASAJERO WHERE ID_AERO_ORIGEN =" + origen + " AND ID_AERO_DESTINO="+destino;

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
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addVueloPasajero(VueloPasajero vuelo) throws SQLException, Exception {


		String sql = "INSERT INTO ISIS2304B041620.VUELO_PASAJERO VALUES (";
		sql += vuelo.getId() + ",";
		sql += vuelo.getHoraLlegada() + ",";
		sql += vuelo.getHoraSalida() + ",";
		sql += vuelo.getFrecuencia() + ",";
		sql += vuelo.getDistancia() + ",";
		sql += vuelo.getDuracion() + ",'";
		sql += vuelo.getTipo() + "',";
		sql += vuelo.getPrecioEjecutiva() + ",";
		sql += vuelo.getPrecioEconomica() + ",'";
		sql += vuelo.getCodAerolinea() + "',";
		sql += vuelo.getIdAeroOrigen() + ",";
		sql += vuelo.getIdAeroDestino() + ",'";
		sql += vuelo.getNumSerieAeronave() + "',";
		sql += vuelo.getFechaLlegada() + ",";
		sql += vuelo.getFechaSalida() +")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	public void asociarAeronaveViajePasajero(String aeronave, int vueloP) throws SQLException, Exception{

		//		String sql = "UPDATE ISIS2304B041620.VUELO_PASAJERO SET ";
		//		sql += "NUMSERIE_AERONAVE='" +aeronave.getNumSerie() + "'";
		//		sql += " WHERE ID = " + vueloP.getId();

		String sql = "UPDATE ISIS2304B041620.VUELO_PASAJERO SET ";
		sql += "NUMSERIE_AERONAVE= (SELECT NUMSERIE FROM (SELECT COUNT(*)AS CUENTA FROM ISIS2304B041620.RESERVA_PASAJERO WHERE ID_VUELO_PASAJERO ='" + vueloP;
		sql +=	"')T1, ISIS2304B041620.AERONAVE_PASAJERO aerP WHERE aerP.NUMSERIE = '"+aeronave+"' AND T1.CUENTA <= aerP.CAPACIDAD_CARGA)WHERE ID ='"+vueloP+ "'" ;

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
		sql += "HORALLEGADA=" +vuelo.getHoraLlegada() + ",";
		sql += "HORASALIDA=" +vuelo.getHoraSalida() + ",";
		sql += "FRECUENCIA=" +vuelo.getFrecuencia() + ",";
		sql += "DISTANCIA=" +vuelo.getDistancia() + ",";
		sql += "DURACION=" +vuelo.getDuracion() + ",";
		sql += "TIPO='" +vuelo.getTipo() + "',";
		sql += "PRECIO_EJECUTIVO=" +vuelo.getPrecioEjecutiva() + ",";
		sql += "PRECIO_ECONOMICO=" +vuelo.getPrecioEconomica() + ",";
		sql += "CODAEROLINEA='" +vuelo.getCodAerolinea() + "',";
		sql += "NUMSERIE_AERONAVE='" +vuelo.getNumSerieAeronave() + "',";
		sql += "ID_AERO_ORIGEN=" +vuelo.getIdAeroOrigen() + ",";
		sql += "ID_AERO_DESTINO=" + vuelo.getIdAeroDestino()+ ",";
		sql += "FECHALLEGADA=" + vuelo.getFechaLlegada()+ ",";
		sql += "FECHASALIDA=" + vuelo.getFechaSalida();
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
