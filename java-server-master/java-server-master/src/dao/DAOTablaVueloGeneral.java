package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos1.VueloGeneral;

public class DAOTablaVueloGeneral {
	
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
	public DAOTablaVueloGeneral() {
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
	
	
	//
	//
	
	public int numeroPorVueloPasajero (int idVuelo) throws SQLException
	{
		int res =0;
		String sql = "SELECT count(ID_VUELO_PASAJERO) AS NUMERO FROM RESERVA_PASAJERO WHERE ID_VUELO_PASAJERO = "+idVuelo;
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		return rs.getInt("NUMERO");
		
	}
	
	public ArrayList<VueloGeneral> buscarVuelosCargaOrigenDestinoFecha (String Origen, String Destino, String fecha1, String fecha2) throws SQLException
	{
		ArrayList<VueloGeneral> vuelos = new ArrayList<VueloGeneral>();
		
		String sql = "SELECT ID2, HORALLEGADA, HORASALIDA, FRECUENCIA, DISTANCIA, DURACION, CAPACIDAD_ACTUAL,CODAEROLINEA, ID_AERO_ORIGEN, ID_AERO_DESTINO, NUMSERIE_AERONAVE, FECHASALIDA, FECHALLEGADA, CODIGO, PAIS_ORIGEN, PAIS_DESTINO FROM ((SELECT ID AS ID1, PAIS AS PAIS_ORIGEN FROM (VUELO_CARGA INNER JOIN AEROPUERTO ON VUELO_CARGA.ID_AERO_ORIGEN = AEROPUERTO.CODIGO)WHERE PAIS = '"+Origen+"' AND FECHASALIDA BETWEEN TO_DATE('"+fecha1+"','DD-MM-YY') AND TO_DATE('"+fecha2+"','DD-MM-YY'))T1 " 
				+ " INNER JOIN " 
				+ " (SELECT ID AS ID2, HORALLEGADA, HORASALIDA, FRECUENCIA, DISTANCIA, DURACION,CAPACIDAD_ACTUAL,CODAEROLINEA, ID_AERO_ORIGEN, ID_AERO_DESTINO, NUMSERIE_AERONAVE, FECHASALIDA, FECHALLEGADA, CODIGO, PAIS AS PAIS_DESTINO FROM (VUELO_CARGA INNER JOIN AEROPUERTO ON VUELO_CARGA.ID_AERO_DESTINO = AEROPUERTO.CODIGO)WHERE PAIS = '"+Destino+"' AND FECHASALIDA BETWEEN TO_DATE('"+fecha1+"','DD-MM-YY') AND TO_DATE('"+fecha2+"','DD-MM-YY'))T2 "
				+ " ON T1.ID1 = T2.ID2)";
				
				
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			int id2 = rs.getInt("ID2");
			int horaLlegada = rs.getInt("HORALLEGADA");
			int horaSalida = rs.getInt("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			int numeroPasajeros = 0;
			int capacidad = rs.getInt("CAPACIDAD_ACTUAL");
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			String numSerieAeronave = rs.getString("NUMSERIE_AERONAVE");
			java.sql.Date fechaLlegada = rs.getDate("FECHALLEGADA");
			java.sql.Date fechaSalida = rs.getDate("FECHASALIDA");
			String paisOrigen = rs.getString("PAIS_ORIGEN");
			String paisDestino = rs.getString("PAIS_DESTINO");
			
			vuelos.add(new VueloGeneral(id2, horaLlegada, horaSalida, frecuencia, distancia, duracion, numeroPasajeros, capacidad, codAerolinea, idOrigen, idDestino, numSerieAeronave, fechaLlegada, fechaSalida, paisOrigen, paisDestino));
		}

		return vuelos;
	}
	
	public ArrayList<VueloGeneral> buscarVuelosPasajeroOrigenDestinoFecha (String Origen, String Destino, String fecha1, String fecha2) throws SQLException
	{
		ArrayList<VueloGeneral> vuelos = new ArrayList<VueloGeneral>();
		
		String sql = "SELECT ID2, HORALLEGADA, HORASALIDA, FRECUENCIA, DISTANCIA, DURACION,(SELECT COUNT(ID_VUELO_PASAJERO) AS NUMERO FROM RESERVA_PASAJERO WHERE ID_VUELO_PASAJERO = ID2)AS NUMERO, CODAEROLINEA, ID_AERO_ORIGEN, ID_AERO_DESTINO, NUMSERIE_AERONAVE, FECHASALIDA, FECHALLEGADA, CODIGO, PAIS_ORIGEN, PAIS_DESTINO FROM ((SELECT ID AS ID1, PAIS AS PAIS_ORIGEN FROM (VUELO_PASAJERO INNER JOIN AEROPUERTO ON VUELO_PASAJERO.ID_AERO_ORIGEN = AEROPUERTO.CODIGO)WHERE PAIS = '"+Origen+"' AND FECHASALIDA BETWEEN TO_DATE('"+fecha1+"','DD-MM-YY') AND TO_DATE('"+fecha2+"','DD-MM-YY'))T1 " 
				+ " INNER JOIN " 
				+ " (SELECT ID AS ID2, HORALLEGADA, HORASALIDA, FRECUENCIA, DISTANCIA, DURACION,CODAEROLINEA, ID_AERO_ORIGEN, ID_AERO_DESTINO, NUMSERIE_AERONAVE, FECHASALIDA, FECHALLEGADA, CODIGO, PAIS AS PAIS_DESTINO FROM (VUELO_PASAJERO INNER JOIN AEROPUERTO ON VUELO_PASAJERO.ID_AERO_DESTINO = AEROPUERTO.CODIGO)WHERE PAIS = '"+Destino+"' AND FECHASALIDA BETWEEN TO_DATE('"+fecha1+"','DD-MM-YY') AND TO_DATE('"+fecha2+"','DD-MM-YY'))T2 "
				+ " ON T1.ID1 = T2.ID2)";
		
		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while (rs.next()) {
			int id2 = rs.getInt("ID2");
			int horaLlegada = rs.getInt("HORALLEGADA");
			int horaSalida = rs.getInt("HORASALIDA");
			int frecuencia = rs.getInt("FRECUENCIA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			int numeroPasajeros = rs.getInt("NUMERO");
			int capacidad = 0;
			String codAerolinea = rs.getString("CODAEROLINEA");
			int idOrigen = rs.getInt("ID_AERO_ORIGEN");
			int idDestino = rs.getInt("ID_AERO_DESTINO");
			String numSerieAeronave = rs.getString("NUMSERIE_AERONAVE");
			java.sql.Date fechaLlegada = rs.getDate("FECHALLEGADA");
			java.sql.Date fechaSalida = rs.getDate("FECHASALIDA");
			String paisOrigen = rs.getString("PAIS_ORIGEN");
			String paisDestino = rs.getString("PAIS_DESTINO");
			vuelos.add(new VueloGeneral(id2, horaLlegada, horaSalida, frecuencia, distancia, duracion, numeroPasajeros, capacidad, codAerolinea, idOrigen, idDestino, numSerieAeronave, fechaLlegada, fechaSalida, paisOrigen, paisDestino));
		}

		return vuelos;
	}

}
