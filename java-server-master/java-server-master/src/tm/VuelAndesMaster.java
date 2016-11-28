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
package tm;

import java.io.File;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import dao.DAOTablaAdmin;
import dao.DAOTablaAerolineas;
import dao.DAOTablaAeronaves;
import dao.DAOTablaAeropuertos;
import dao.DAOTablaCliente;
import dao.DAOTablaReservas;
import dao.DAOTablaSilla;
import dao.DAOTablaVueloCarga;
import dao.DAOTablaVueloGeneral;
import dao.DAOTablaVueloPasajero;
import dtm.VuelAndesDistributed;
import jms.NonReplyException;
import vos.AerolineaMsg;
import vos.ListaAerolineasMsg;
import vos.ListaReservasMsg;
import vos.ListaUsuariosMsg;
import vos.ReservaMsg;
import vos.UsuarioMsg;
import vos1.Admin;
import vos1.Aerolinea;
import vos1.Aeronave;
import vos1.Aeropuerto;
import vos1.ListaAdmins;
import vos1.ListaAerolineas;
import vos1.ListaAeronaves;
import vos1.ListaAeropuertos;
import vos1.ListaRemitentes;
import vos1.ListaReservasCarga;
import vos1.ListaReservasPasajero;
import vos1.ListaSillas;
import vos1.ListaViajeros;
import vos1.ListaVuelosCarga;
import vos1.ListaVuelosGeneral;
import vos1.ListaVuelosPasajero;
import vos1.RangoFechas;
import vos1.Remitente;
import vos1.ReservaCarga;
import vos1.ReservaPasajero;
import vos1.Silla;
import vos1.Viajero;
import vos1.Vuelo;
import vos1.VueloCarga;
import vos1.VueloGeneral;
import vos1.VueloPasajero;

/**
 * Fachada en patron singleton de la aplicación
 * @author Juan
 */
public class VuelAndesMaster {


	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;


	private VuelAndesDistributed dtm;
	/**
	 * Método constructor de la clase VuelAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VuelAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public VuelAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		System.out.println("Instancing DTM...");
		dtm = VuelAndesDistributed.getInstance(this);
		System.out.println("Done!");
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAerolineas darAerolineas() throws Exception {
		ArrayList<Aerolinea> aerolineas;
		DAOTablaAerolineas daoAerolineas = new DAOTablaAerolineas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAerolineas.setConn(conn);
			aerolineas = daoAerolineas.darAerolineas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAerolineas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAerolineas(aerolineas);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAerolineas buscarAerolineasPorName(String name) throws Exception {
		ArrayList<Aerolinea> aerolineas;
		DAOTablaAerolineas daoAerolineas = new DAOTablaAerolineas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAerolineas.setConn(conn);
			aerolineas = daoAerolineas.buscarAerolineasPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAerolineas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAerolineas(aerolineas);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addAerolinea(Aerolinea aerolinea) throws Exception {
		DAOTablaAerolineas daoAerolineas = new DAOTablaAerolineas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAerolineas.setConn(conn);
			daoAerolineas.addAerolinea(aerolinea);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAerolineas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addAerolineas(ListaAerolineas aerolineas) throws Exception {
		DAOTablaAerolineas daoAerolineas = new DAOTablaAerolineas();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoAerolineas.setConn(conn);
			for(Aerolinea aerolinea : aerolineas.getAerolineas())
				daoAerolineas.addAerolinea(aerolinea);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoAerolineas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateAerolinea(Aerolinea aerolinea) throws Exception {
		DAOTablaAerolineas daoAerolineas = new DAOTablaAerolineas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAerolineas.setConn(conn);
			daoAerolineas.updatAerolinea(aerolinea)
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAerolineas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteAerolinea(Aerolinea aerolinea) throws Exception {
		DAOTablaAerolineas daoAerolineas = new DAOTablaAerolineas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAerolineas.setConn(conn);
			daoAerolineas.deleteAerolinea(aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAerolineas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//	/**
	//	 * Método que modela la transacción que retorna el/los videos mas alquilados
	//	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	//	 * @throws Exception -  cualquier error que se genere durante la transacción
	//	 */
	//	public ListaVideos videosMasAlquilados() throws Exception {
	//		ArrayList<Video> videos;
	//		DAOTablaVideos daoVideos = new DAOTablaVideos();
	//		try 
	//		{
	//			//////Transacción
	//			this.conn = darConexion();
	//			daoVideos.setConn(conn);
	//			videos = daoVideos.darVideoMasAlquilado();
	//
	//		} catch (SQLException e) {
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} catch (Exception e) {
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			throw e;
	//		} finally {
	//			try {
	//				daoVideos.cerrarRecursos();
	//				if(this.conn!=null)
	//					this.conn.close();
	//			} catch (SQLException exception) {
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return new ListaVideos(videos);
	//	}


	////Transacciones aeropuertos////

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAeropuertos darAeropuertos() throws Exception {
		ArrayList<Aeropuerto> aeropuertos;
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeropuertos.setConn(conn);
			aeropuertos = daoAeropuertos.darAeropuertos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAeropuertos(aeropuertos);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAeropuertos buscarAeropuertosPorName(String name) throws Exception {
		ArrayList<Aeropuerto> aeropuertos;
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeropuertos.setConn(conn);
			aeropuertos = daoAeropuertos.buscarAeropuertosPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAeropuertos(aeropuertos);
	}

	public Aeropuerto buscarAeropuertosPorId(int id) throws Exception {
		Aeropuerto aeropuertos;
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeropuertos.setConn(conn);
			aeropuertos = daoAeropuertos.buscarAeropuertosPorCodigo(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return aeropuertos;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addAeropuerto(Aeropuerto aeropuerto) throws Exception {
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeropuertos.setConn(conn);
			daoAeropuertos.addAeropuerto(aeropuerto);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addAeropuertos(ListaAeropuertos aeropuertos) throws Exception {
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoAeropuertos.setConn(conn);
			for(Aeropuerto aeropuerto : aeropuertos.getAeropuertos())
				daoAeropuertos.addAeropuerto(aeropuerto);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateAeropuerto(Aeropuerto aeropuerto) throws Exception {
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeropuertos.setConn(conn);
			daoAeropuertos.updateAeropuerto(aeropuerto)
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteAeropuerto(Aeropuerto aeropuerto) throws Exception {
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeropuertos.setConn(conn);
			daoAeropuertos.deleteAeropuerto(aeropuerto);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeropuertos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}





	////Transacciones remitentes////





	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaRemitentes darRemitentes() throws Exception {
		ArrayList<Remitente> remitentes;
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoRemitentes.setConn(conn);
			remitentes = daoRemitentes.darRemitentes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaRemitentes(remitentes);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaRemitentes buscarRemitentesPorName(String name) throws Exception {
		ArrayList<Remitente> remitentes;
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoRemitentes.setConn(conn);
			remitentes = daoRemitentes.buscarRemitentesPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaRemitentes(remitentes);
	}


	public ListaRemitentes buscarRemitentesPorId(int id) throws Exception {
		ArrayList<Remitente> remitentes;
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoRemitentes.setConn(conn);
			remitentes = daoRemitentes.buscarRemitentesPorId(id);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaRemitentes(remitentes);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addRemitente(Remitente remitente) throws Exception {
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoRemitentes.setConn(conn);
			daoRemitentes.addRemitente(remitente);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addRemitentes(ListaRemitentes remitentes) throws Exception {
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoRemitentes.setConn(conn);
			for(Remitente remitente : remitentes.getRemitentes())
				daoRemitentes.addRemitente(remitente);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateRemitente(Remitente remitente) throws Exception {
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoRemitentes.setConn(conn);
			daoRemitentes.updateRemitente(remitente);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteRemitente(Remitente remitente) throws Exception {
		DAOTablaCliente daoRemitentes = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoRemitentes.setConn(conn);
			daoRemitentes.deleteRemitente(remitente);;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRemitentes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}





	////Transacciones viajeros////





	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaViajeros darViajeros() throws Exception {
		ArrayList<Viajero> viajeros;
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajeros.setConn(conn);
			viajeros = daoViajeros.darViajeros();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajeros(viajeros);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaViajeros buscarViajerosPorName(String name) throws Exception {
		ArrayList<Viajero> viajeros;
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajeros.setConn(conn);
			viajeros = daoViajeros.buscarViajerosPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajeros(viajeros);
	}
	
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// MILLAS
	//
	//
	//
	//
	
	public ListaViajeros buscarViajerosPromovidos(int millas) throws Exception {
		ArrayList<Viajero> viajeros=new ArrayList<>();
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajeros.setConn(conn);
			List<Integer> idsViajeros= daoViajeros.darUsuariosMillas(millas);
			for (Integer idActual : idsViajeros) {
				Viajero viajero= daoViajeros.buscarViajeroPorId(idActual);
				viajeros.add(viajero);
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaViajeros(viajeros);
	}
	
	
	public ListaUsuariosMsg darUsuariosPromovidos(int millas) throws Exception{
		List<UsuarioMsg> respuesta = new ArrayList<>();
		ListaViajeros sinConvertir = buscarViajerosPromovidos(millas);
		for (int i = 0; i < sinConvertir.getViajeros().size(); i++) {
			Viajero actual = sinConvertir.getViajeros().get(i);
			UsuarioMsg usuario = new UsuarioMsg(actual.getId(), actual.getNombre(), actual.getNacionalidad());
			respuesta.add(usuario);
		}
		return new ListaUsuariosMsg(respuesta);
	}
	
	public ListaVuelosPasajero buscarVuelosPorCodAerolienaViajero(int ide, String cod, String clase, int distancia) throws Exception
	{
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();
		DAOTablaCliente daocliente = new DAOTablaCliente();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daocliente.setConn(conn);
			vuelos = daocliente.buscarVuelosPorCodAerolienaViajero(ide, cod,clase,distancia);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daocliente.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaVuelosPasajero(vuelos);

	}

	public ListaVuelosPasajero buscarVuelosPorCodAerolienaGerente(String cod, String clase, int distancia) throws Exception
	{
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();
		DAOTablaCliente daocliente = new DAOTablaCliente();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daocliente.setConn(conn);
			vuelos = daocliente.buscarVuelosPorCodAerolienaGerente(cod,clase,distancia);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daocliente.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaVuelosPasajero(vuelos);

	}

	public ListaVuelosPasajero buscarVuelosPorFechaViajero(int ide, String comienzo, String fin, String clase, int distancia) throws Exception
	{
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();
		DAOTablaCliente daocliente = new DAOTablaCliente();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daocliente.setConn(conn);
			vuelos = daocliente.buscarVuelosPorFechaViajero(ide, comienzo,fin,clase,distancia);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daocliente.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaVuelosPasajero(vuelos);

	}

	public ListaVuelosPasajero buscarVuelosPorFechaGerente(String comienzo, String fin, String clase, int distancia) throws Exception
	{
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();
		DAOTablaCliente daocliente = new DAOTablaCliente();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daocliente.setConn(conn);
			vuelos = daocliente.buscarVuelosPorFechaGerente(comienzo,fin,clase,distancia);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daocliente.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaVuelosPasajero(vuelos);

	}

	public ListaVuelosPasajero buscarVuelosPaisOrigenDestino(String origen, String destino) throws Exception
	{
		ArrayList<VueloPasajero> vuelos = new ArrayList<VueloPasajero>();
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPaisOrigenDestino(origen, destino);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daoVuelos.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaVuelosPasajero(vuelos);

	}


	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */

	public void addReservasVueloGrupal (ListaViajeros viajeros,int nSilla, int idVP,int idVC) throws Exception {
		DAOTablaReservas daoReserva = new DAOTablaReservas();
		DAOTablaVueloPasajero daoVueloPasajero = new DAOTablaVueloPasajero();
		DAOTablaVueloCarga daoVueloCarga = new DAOTablaVueloCarga();

		try{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReserva.setConn(conn);
			daoVueloPasajero.setConn(conn);
			daoVueloCarga.setConn(conn);
			for(Viajero viajero : viajeros.getViajeros()){
				daoReserva.addReservaCargaVuelo(viajero.getId(), idVC);
				daoReserva.addReservaVuelo(nSilla, viajero.getId(), idVP);
			}
			ReservaCarga reservaCarga = null;
			ReservaPasajero reservaPasajero = null;

			ArrayList<ReservaCarga> res = daoReserva.buscarReservasPorVueloCarga(idVC);
			for (int i=0; i<res.size();i++)
			{
				ReservaCarga resC = res.get(i);
				if (resC.getIdVueloCarga()==idVC)
				{
					reservaCarga = resC;
				}
			}

			ArrayList<ReservaPasajero> res2 = daoReserva.buscarReservasPorVuelo(idVP);
			for (int i=0; i<res2.size();i++)
			{
				ReservaPasajero resP = res2.get(i);
				if (resP.getIdVueloPasajero()==idVP)
				{
					reservaPasajero = resP;
				}
			}

			VueloCarga vueloCarga = null;
			VueloPasajero vueloPasajero = null;

			ArrayList<VueloCarga> vue = daoVueloCarga.buscarVuelosPorId(idVC);
			for (int i=0; i<vue.size();i++)
			{
				VueloCarga vueC = vue.get(i);
				if (vueC.getId()==idVC)
				{
					vueloCarga = vueC;
				}
			}

			ArrayList<VueloPasajero> vueP = daoVueloPasajero.buscarVuelosPorId(idVP);
			for (int i=0; i<vueP.size();i++)
			{
				VueloPasajero vuePa = vueP.get(i);
				if (vuePa.getId()==idVP)
				{
					vueloPasajero = vuePa;
				}
			}

			if (reservaPasajero != null && reservaCarga!=null && vueloCarga.getFechaSalida().getTime() == vueloPasajero.getFechaSalida().getTime())
			{
				conn.commit();
			}
			else {
				conn.rollback();
			}

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReserva.cerrarRecursos();
				daoVueloPasajero.cerrarRecursos();
				daoVueloCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}
	public void addViajero(Viajero viajero) throws Exception {
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajeros.setConn(conn);
			daoViajeros.addViajero(viajero);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addViajeros(ListaViajeros viajeros) throws Exception {
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoViajeros.setConn(conn);
			for(Viajero viajero : viajeros.getViajeros())
				daoViajeros.addViajero(viajero);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateViajero(Viajero viajero) throws Exception {
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajeros.setConn(conn);
			daoViajeros.updateViajero(viajero);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteViajero(Viajero viajero) throws Exception {
		DAOTablaCliente daoViajeros = new DAOTablaCliente();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoViajeros.setConn(conn);
			daoViajeros.deleteViajero(viajero);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoViajeros.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	////Transacciones aeronaves////





	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAeronaves darAeronavesCarga() throws Exception {
		ArrayList<Aeronave> aeronaves;
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			aeronaves = daoAeronaves.darAeronavesCarga();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAeronaves(aeronaves);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAeronaves buscarAeronavesCargaPorNumSerie(String num) throws Exception {
		ArrayList<Aeronave> aeronaves;
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			aeronaves = daoAeronaves.buscarAeronavesCargaPorNumeroSerie(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAeronaves(aeronaves);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addAeronaveCarga(Aeronave aeronave) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			daoAeronaves.addAeronaveCarga(aeronave);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addAeronavesCarga(ListaAeronaves aeronaves) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoAeronaves.setConn(conn);
			for(Aeronave aeronave : aeronaves.getAeronaves())
				daoAeronaves.addAeronaveCarga(aeronave);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateAeronaveCarga(Aeronave aeronave) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			daoAeronaves.updatAeronaveCarga(aeronave);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteAeronaveCarga(Aeronave aeronave) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			daoAeronaves.deleteAeronaveCarga(aeronave);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	////Transacciones aeronaves////





	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAeronaves darAeronavesPasajero() throws Exception {
		ArrayList<Aeronave> aeronaves;
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			aeronaves = daoAeronaves.darAeronavesPasajero();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAeronaves(aeronaves);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAeronaves buscarAeronavePasajeroPorNumSerie(String num) throws Exception {
		ArrayList<Aeronave> aeronaves;
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			aeronaves = daoAeronaves.buscarAeronavesPasajeroPorNumeroSerie(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAeronaves(aeronaves);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addAeronavePasajero(Aeronave aeronave) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			daoAeronaves.addAeronavePasajero(aeronave);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addAeronavesPasajero(ListaAeronaves aeronaves) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoAeronaves.setConn(conn);
			for(Aeronave aeronave : aeronaves.getAeronaves())
				daoAeronaves.addAeronavePasajero(aeronave);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateAeronavePasajerp(Aeronave aeronave) throws Exception {
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			daoAeronaves.updatAeronavePasajero(aeronave);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteAeronavePasajero(Aeronave aeronave) throws Exception 
	{
		DAOTablaAeronaves daoAeronaves = new DAOTablaAeronaves();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAeronaves.setConn(conn);
			daoAeronaves.deleteAeronavePasajero(aeronave);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAeronaves.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	////Transacciones vuelo pasajeros////





	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaVuelosPasajero darVuelosPasajero() throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.darVuelosPasajero();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaVuelosPasajero buscarVueloPasajeroPorId(int num) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}


	public ListaVuelosPasajero buscarVueloPasajeroPorAerolinea(String aerolinea) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorAerolinea(aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}



	//RF12/////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////

	public ArrayList buscarVueloEscala(int origen, int destino)throws Exception {
		ArrayList<VueloPasajero> vuelosOrigen = null;
		ArrayList<VueloPasajero> vuelosDestino = null;
		ArrayList<VueloPasajero> vuelos = new ArrayList<>();

		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();

		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);

			vuelosOrigen= daoVuelos.buscarVuelosPorIdAeropuertoOrigen(origen);
			vuelosDestino= daoVuelos.buscarVuelosPorIdAeropuertoDestino(destino);

			int total = vuelosOrigen.get(0).getPrecioEconomica();

			for (int i = 0; i < vuelosOrigen.size(); i++) {
				VueloPasajero ori = vuelosOrigen.get(i);
				for (int j = 0; j < vuelosDestino.size(); j++) {
					VueloPasajero dest = vuelosDestino.get(j);

					if(ori.getIdAeroDestino()==dest.getIdAeroOrigen()){
						int precio1=ori.getPrecioEconomica();
						int precio2=dest.getPrecioEconomica();
						if(precio1+precio2<total){
							total=precio1+precio2;
							vuelos.add(ori);
							vuelos.add(dest);			
						}
					}
				}
			}		

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vuelos;
	}


	//RF12/////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////

	public ListaVuelosPasajero buscarVueloPasajeroOrigenDestino(int origen, int destino) throws Exception {
		ArrayList<VueloPasajero> vuelos = null;

		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);

			if(daoVuelos.buscarVuelosOrigenDestino(origen, destino) != null && daoVuelos.buscarVuelosOrigenDestino(origen, destino).size()>=1 ){
				vuelos = daoVuelos.buscarVuelosOrigenDestino(origen, destino);
			}
			else{
				ArrayList escalas = buscarVueloEscala(origen, destino);
				vuelos = escalas;
			}


		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}



	public ListaVuelosPasajero buscarVueloPasajeroPorIdAeropuerto(int num) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorIdAeropuerto(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaPorIdAeropuerto(int num) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorIdAeropuerto(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaMedianaAeropuerto(int num) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosMedianaPorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaAAFT(int num,String comienzo,String fin,String aerolinea,String tipo) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosCargaAAFT(num, comienzo, fin, aerolinea, tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}


	public ListaVuelosPasajero buscarVueloPasajeroAAFT(int num,String comienzo,String fin,String aerolinea,String tipo) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroAAFT(num, comienzo, fin, aerolinea, tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaPequenaAeropuerto(int num) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPequenaPorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaGrandeAeropuerto(int num) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosGrandePorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroGrandeAeropuerto(int num) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroGrandePorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroMedianaAeropuerto(int num) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroMedianaPorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroPequenaAeropuerto(int num) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroPequenaPorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaAeropuertoFecha(int num, String comienzo, String fin) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorIdAeropuertoFecha(num, comienzo, fin);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaAeropuertoFechaNoTipo(int num, String comienzo, String fin,String tamano) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosNoTipoPorId(num, comienzo, fin, tamano);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroAeropuertoFechaNoTipo(int num, String comienzo, String fin,String tamano) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroNoTipoPorId(num, comienzo, fin, tamano);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}


	public ListaVuelosCarga buscarVueloCargaAeropuertoFechaNoTipoNoAerolinea(int num, String comienzo, String fin,String tamano,String aerolinea) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosNoTipoNoAerolineaPorId(num, comienzo, fin, tamano,aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroAeropuertoFechaNoTipoNoAerolinea(int num, String comienzo, String fin,String tamano,String aerolinea) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroNoTipoNoAerolineaPorId(num, comienzo, fin, tamano,aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaAeropuertoFechaNoAerolinea(int num, String comienzo, String fin,String aerolinea) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorIdAeropuertoFechaNoAerolinea(num, comienzo, fin,aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroAeropuertoFechaNoAerolinea(int num, String comienzo, String fin,String aerolinea) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroPorIdAeropuertoFechaNoAerolinea(num, comienzo, fin, aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroAeropuertoFecha(int num, String comienzo, String fin) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroPorIdAeropuertoFecha(num, comienzo, fin);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroPorIdAeropuertoAerolinea(int num,String aerolinea) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorIdAeropuertoAerolinea(num,aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaPorIdAeropuertoAerolinea(int num,String aerolinea) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorIdAeropuertoAerolinea(num,aerolinea);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosCarga buscarVueloCargaPorAeropuertoAerolineaFecha(int num,String aerolinea,String comienzo, String fin) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosCargaPorAeropuertoAerolineaFecha(num, aerolinea, comienzo, fin);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	public ListaVuelosPasajero buscarVueloPasajeroPorAeropuertoAerolineaFecha(int num,String aerolinea,String comienzo, String fin) throws Exception {
		ArrayList<VueloPasajero> vuelos;
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPasajeroPorAeropuertoAerolineaFecha(num, aerolinea, comienzo, fin);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosPasajero(vuelos);
	}


	public void asociarVueloPasajeroAeronave(String aeronave,int vuelo) throws Exception
	{
		DAOTablaVueloPasajero daovuelo = new DAOTablaVueloPasajero();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daovuelo.setConn(conn);
			daovuelo.asociarAeronaveViajePasajero(aeronave, vuelo);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daovuelo.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addVueloPasajero(VueloPasajero vuelo) throws Exception {
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.addVueloPasajero(vuelo);;
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addVuelosPasajero(ListaVuelosPasajero vuelos) throws Exception {
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoVuelos.setConn(conn);
			for(VueloPasajero vuelo : vuelos.getVuelosPasajero())
				daoVuelos.addVueloPasajero(vuelo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateVueloPasajero(VueloPasajero vuelo) throws Exception {
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.updatVueloPasajero(vuelo);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVueloPasajero(VueloPasajero vuelo) throws Exception {
		DAOTablaVueloPasajero daoVuelos = new DAOTablaVueloPasajero();
		DAOTablaReservas dao = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.deleteVueloPasajero(vuelo);
			//dao.createSavepoint(String.valueOf(vuelo.getId()));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	//Transaccion vueloCarga

	public ListaVuelosCarga darVuelosCarga() throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.darVuelosCarga();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaVuelosCarga buscarVueloCargaPorId(int num) throws Exception {
		ArrayList<VueloCarga> vuelos;
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.buscarVuelosPorId(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVuelosCarga(vuelos);
	}

	//	public VueloPasajero asociarVueloPasajeroAeronave(VueloPasajero vuelo, Aeronave aeronave) throws Exception
	//	{
	//		DAOTablaVueloPasajero daovuelo = new DAOTablaVueloPasajero();
	//		VueloPasajero rta;
	//		
	//		try
	//		{
	//			//////Transacción
	//			this.conn = darConexion();
	//			conn.setAutoCommit(false);
	//			daovuelo.setConn(conn);
	//			daovuelo.asociarAeronaveViajePasajero(aeronave, vuelo);
	//			rta = buscarVueloPasajeroPorId(vuelo.getId()).getVuelosPasajero().get(0);
	//			conn.commit();
	//
	//		}
	//		catch (SQLException e)
	//		{
	//			System.err.println("SQLException:" + e.getMessage());
	//			e.printStackTrace();
	//			conn.rollback();
	//			throw e;
	//		}
	//		catch (Exception e)
	//		{
	//			System.err.println("GeneralException:" + e.getMessage());
	//			e.printStackTrace();
	//			conn.rollback();
	//			throw e;
	//		}
	//		finally
	//		{
	//			try
	//			{
	//				daovuelo.cerrarRecursos();
	//				if (this.conn != null)
	//					this.conn.close();
	//			}
	//			catch (SQLException exception)
	//			{
	//				System.err.println("SQLException closing resources:" + exception.getMessage());
	//				exception.printStackTrace();
	//				throw exception;
	//			}
	//		}
	//		return rta;
	//		
	//	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addVueloCarga(VueloCarga vuelo) throws Exception {
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.addVueloCarga(vuelo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addVuelosCarga(ListaVuelosCarga vuelos) throws Exception {
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoVuelos.setConn(conn);
			for(VueloCarga vuelo : vuelos.getVuelosCarga())
				daoVuelos.addVueloCarga(vuelo);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateVueloCarga(VueloCarga vuelo) throws Exception {
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.updatVueloCarga(vuelo);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVueloCarga(VueloCarga vuelo) throws Exception {
		DAOTablaVueloCarga daoVuelos = new DAOTablaVueloCarga();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.deleteVueloCarga(vuelo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//Transaccion Vuelo General

	public ListaVuelosGeneral buscarVuelosPasajeroOrigenDestinoEnRango(String origen, String destino, String fecha1, String fecha2) throws SQLException
	{
		ArrayList<VueloGeneral> vuelosP;
		DAOTablaVueloGeneral daoVuelos = new DAOTablaVueloGeneral();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelosP = daoVuelos.buscarVuelosPasajeroOrigenDestinoFecha(origen, destino, fecha1, fecha2);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
		return new ListaVuelosGeneral(vuelosP);

	}

	public ListaVuelosGeneral buscarVuelosCargaOrigenDestinoEnRango(String origen, String destino, String fecha1, String fecha2) throws SQLException
	{
		ArrayList<VueloGeneral> vuelosC;
		DAOTablaVueloGeneral daoVuelos = new DAOTablaVueloGeneral();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelosC = daoVuelos.buscarVuelosCargaOrigenDestinoFecha(origen, destino, fecha1, fecha2);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
		return new ListaVuelosGeneral(vuelosC);

	}


	//Transaccion Admin

	public ListaAeronaves buscarAeronaveNumSerie(int ide, String num) throws Exception
	{
		ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();
		DAOTablaAdmin daoadmin = new DAOTablaAdmin();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoadmin.setConn(conn);
			aeronaves = daoadmin.buscarAeronaveNumSerie(ide, num);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daoadmin.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaAeronaves(aeronaves);

	}

	public ListaAeronaves buscarAeronaveCapacidad(int ide, int cap) throws Exception
	{
		ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();
		DAOTablaAdmin daoadmin = new DAOTablaAdmin();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoadmin.setConn(conn);
			aeronaves = daoadmin.buscarAeronaveCapacidad(ide, cap);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daoadmin.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaAeronaves(aeronaves);

	}

	public ListaAeronaves buscarAeronaveTamano(int ide, String tam) throws Exception
	{
		ArrayList<Aeronave> aeronaves = new ArrayList<Aeronave>();
		DAOTablaAdmin daoadmin = new DAOTablaAdmin();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoadmin.setConn(conn);
			aeronaves = daoadmin.buscarAeronaveTamano(ide, tam);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daoadmin.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaAeronaves(aeronaves);

	}

	public ListaAdmins darAdmins() throws Exception {
		ArrayList<Admin> admins;
		DAOTablaAdmin daoAdmins = new DAOTablaAdmin();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAdmins.setConn(conn);
			admins = daoAdmins.darAdmins();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAdmins.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAdmins(admins);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaAdmins buscarAdminsPorName(String name) throws Exception {
		ArrayList<Admin> admins;
		DAOTablaAdmin daoAdmins = new DAOTablaAdmin();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAdmins.setConn(conn);
			admins = daoAdmins.buscarAdmisPorNombre(name);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAdmins.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaAdmins(admins);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addAdmin(Admin admin) throws Exception {
		DAOTablaAdmin daoAdmins = new DAOTablaAdmin();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAdmins.setConn(conn);
			daoAdmins.addAdmin(admin);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAdmins.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addAdmins(ListaAdmins admins) throws Exception {
		DAOTablaAdmin daoAdmins = new DAOTablaAdmin();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoAdmins.setConn(conn);
			for(Admin admin : admins.getAdmins())
				daoAdmins.addAdmin(admin);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoAdmins.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateAdmin(Admin admin) throws Exception {
		DAOTablaAdmin daoAdmins = new DAOTablaAdmin();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAdmins.setConn(conn);
			daoAdmins.updatAdmin(admin);
			;

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAdmins.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteAdmin(Admin admin) throws Exception {
		DAOTablaAdmin daoAdmins = new DAOTablaAdmin();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoAdmins.setConn(conn);
			daoAdmins.deleteAdmin(admin);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAdmins.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}



	// Transaccion Silla

	public ListaSillas darSillas() throws Exception {
		ArrayList<Silla> sillas;
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			sillas = daoSillas.darSillas();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSillas(sillas);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaSillas buscarSillaPorTipo(String tipo) throws Exception {
		ArrayList<Silla> sillas;
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			sillas = daoSillas.buscarSillasPorTipo(tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSillas(sillas);
	}

	public ListaSillas buscarSillaPorAeronave(String tipo) throws Exception {
		ArrayList<Silla> sillas;
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			sillas = daoSillas.buscarSillasPorAeronave(tipo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaSillas(sillas);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addSilla(Silla silla) throws Exception {
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			daoSillas.addSilla(silla);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addSillas(ListaSillas sillas) throws Exception {
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoSillas.setConn(conn);
			for(Silla silla : sillas.getSillas())
				daoSillas.addSilla(silla);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateSilla(Silla silla) throws Exception {
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			daoSillas.updateSilla(silla);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public void updateSillaE(Silla silla) throws Exception {
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			daoSillas.updateSillaE(silla);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteSilla(Silla silla) throws Exception {
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			daoSillas.deleteSilla(silla);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//Transaccion reserva pasajero


	public ListaReservasPasajero darReservasPasajero() throws Exception {
		ArrayList<ReservaPasajero> reservasPasajero;
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			reservasPasajero = daoReservasPasajero.darReservasPasajeros();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReservasPasajero(reservasPasajero);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaReservasPasajero buscarReservaPorVuelo(int num) throws Exception {
		ArrayList<ReservaPasajero> reservasPasajero;
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			reservasPasajero = daoReservasPasajero.buscarReservasPorVuelo(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReservasPasajero(reservasPasajero);
	}

	public ListaReservasPasajero buscarReservaPorViajero(int num) throws Exception {
		ArrayList<ReservaPasajero> reservasPasajero;
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			reservasPasajero = daoReservasPasajero.buscarReservasPorViajero(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReservasPasajero(reservasPasajero);
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addReservaPasajero(ReservaPasajero reservaPasajero) throws Exception {
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			daoReservasPasajero.addReserva(reservaPasajero);
			//daoReservasPasajero.createSavepoint(String.valueOf(reservaPasajero.getId()));
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws SQLException 
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */


	public ListaReservasPasajero lista(List<Integer> ids, String origen, String destino) throws SQLException, Exception{
		DAOTablaVueloPasajero daoVuelosPasajero = new DAOTablaVueloPasajero();
		
		try{
		this.conn = darConexion();
		daoVuelosPasajero.setConn(conn);
		ArrayList<VueloPasajero> vuelos = daoVuelosPasajero.buscarVuelosPaisOrigenDestino(origen, destino);
		List<ReservaPasajero> reservasPasajero = new ArrayList<>();

		int numeroReserva = (int) (Math.random()*30000) + 20000;
		int numeroSilla = (int) (Math.random()*5000) + 1;

		String silla = String.valueOf(numeroSilla);

		if(vuelos.size()!= 0){
			VueloPasajero actual = vuelos.get(0);
			for (int i = 0; i < ids.size(); i++) {
				int idActual = ids.get(i);
				ReservaPasajero reserva = new ReservaPasajero(numeroReserva, silla, idActual, actual.getId());
				reservasPasajero.add(reserva);
			}
		}
		
		conn.commit();
		return new ListaReservasPasajero(reservasPasajero);
		}catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoVuelosPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}		
	}

	//A�adir una lista de reservas!!!!
	//
	//
	//
	//
	//
	//


	public ListaReservasPasajero addReservasPasajero(ListaReservasPasajero reservasPasajero) throws Exception {
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		List<ReservaPasajero> reservasAnadidas = new ArrayList<>();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReservasPasajero.setConn(conn);
			for(ReservaPasajero reservaPasajero : reservasPasajero.getReservasPasajero()){

				daoReservasPasajero.addReserva(reservaPasajero);
				ReservaPasajero anadir = daoReservasPasajero.buscarReservaPorId(reservaPasajero.getId());
				reservasAnadidas.add(anadir);
				//daoReservasPasajero.createSavepoint(String.valueOf(reservasPasajero.getReservasPasajero().get(0).getId()));
			}
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReservasPasajero(reservasAnadidas);
	}


	public ListaReservasMsg darRegistrarReservas(List<Integer> ids, String origen, String destino) throws SQLException, Exception{
		
		ListaReservasPasajero antesDeAnadir = lista(ids,origen,destino);
		ListaReservasPasajero despuesDe = addReservasPasajero(antesDeAnadir);
		List<ReservaMsg> rta = new ArrayList<>();
		for (int i = 0; i < despuesDe.getReservasPasajero().size(); i++) {
			ReservaPasajero actual = despuesDe.getReservasPasajero().get(i);
			ReservaMsg nuevaReserva = new ReservaMsg(actual.getId()+"-D03", actual.getIdViajero(), actual.getNumSilla(), 0.0, actual.getIdVueloPasajero()+"-D03");
			rta.add(nuevaReserva);
		}
		return new ListaReservasMsg(rta);
	}
	
	public ListaReservasMsg darReservasGlobal(List<Integer> ids, String origen, String destino) throws Exception {
		ListaReservasMsg remL = darRegistrarReservas(ids, origen, destino);
		try
		{
			ListaReservasMsg resp = dtm.getRemoteReservas(ids, origen, destino);
			System.out.println(resp.getReservas().size());
			remL.getReservas().addAll(resp.getReservas());
			return remL;
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}


	
	public Vuelo addReservasVueloTotal(ListaReservasPasajero reservasPasajero) throws Exception {
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		Vuelo vuelo = new Vuelo(0, 0);
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReservasPasajero.setConn(conn);
			Random randomGenerator = new Random();
			for(ReservaPasajero reservaPasajero : reservasPasajero.getReservasPasajero()){
				daoReservasPasajero.addReserva(reservaPasajero);
			}
			vuelo.setId(randomGenerator.nextInt(100));
			vuelo.setId1(reservasPasajero.getReservasPasajero().get(0).getId());
			if (reservasPasajero.getReservasPasajero().size()>=2) {
				vuelo.setId2(reservasPasajero.getReservasPasajero().get(1).getId());
			}

			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return vuelo;
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateReservaPasajero(ReservaPasajero reservaPasajero) throws Exception {
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			daoReservasPasajero.updatReserva(reservaPasajero);
			//daoReservasPasajero.createSavepoint(String.valueOf(reservaPasajero.getId()));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteReservaPasajero(ReservaPasajero reservaPasajero) throws Exception {
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			daoReservasPasajero.deleteReserva(reservaPasajero);
			//daoReservasPasajero.createSavepoint(String.valueOf(reservaPasajero.getId()));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void deleteReservasPasajero(ListaReservasPasajero reservasPasajero) throws Exception {
		DAOTablaReservas daoReservasPasajero = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasPasajero.setConn(conn);
			for(ReservaPasajero reservaPasajero : reservasPasajero.getReservasPasajero())			
				daoReservasPasajero.deleteReserva(reservaPasajero);

			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasPasajero.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	//Transaccion Reserva Carga


	public ListaReservasCarga darReservasCarga() throws Exception {
		ArrayList<ReservaCarga> reservasCarga;
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasCarga.setConn(conn);
			reservasCarga = daoReservasCarga.darReservasCarga();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReservasCarga(reservasCarga);
	}

	/**
	 * Método que modela la transacción que busca el/los videos en la base de datos con el nombre entra como parámetro.
	 * @param name - Nombre del video a buscar. name != null
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaReservasCarga buscarReservaPorVueloCarga(int num) throws Exception {
		ArrayList<ReservaCarga> reservasCarga;
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasCarga.setConn(conn);
			reservasCarga = daoReservasCarga.buscarReservasPorVueloCarga(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaReservasCarga(reservasCarga);
	}


	public Silla buscarSillaPorNumero(String num) throws Exception {
		Silla silla;
		DAOTablaSilla daoSillas = new DAOTablaSilla();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoSillas.setConn(conn);
			silla = daoSillas.buscarSillasPorNumero(num);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return silla;
	}

	/**
	 * Método que modela la transacción que agrega un solo video a la base de datos.
	 * <b> post: </b> se ha agregado el video que entra como parámetro
	 * @param video - el video a agregar. video != null
	 * @throws Exception - cualquier error que se genera agregando el video
	 */
	public void addReservaCarga(ReservaCarga reservaCarga) throws Exception {
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasCarga.setConn(conn);
			daoReservasCarga.addReservaCarga(reservaCarga);
			//daoReservasCarga.createSavepoint(String.valueOf(reservaCarga.getId()));
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que agrega los videos que entran como parámetro a la base de datos.
	 * <b> post: </b> se han agregado los videos que entran como parámetro
	 * @param videos - objeto que modela una lista de videos y se estos se pretenden agregar. videos != null
	 * @throws Exception - cualquier error que se genera agregando los videos
	 */
	public void addReservasCarga(ListaReservasCarga reservasCarga) throws Exception {
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacción - ACID Example
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoReservasCarga.setConn(conn);
			for(ReservaCarga reservaCarga : reservasCarga.getReservasCarga())
				daoReservasCarga.addReservaCarga(reservaCarga);			//daoReservasCarga.createSavepoint(String.valueOf(reservasCarga.getReservasCarga().get(0).getId()));
			conn.setSavepoint();
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que actualiza el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha actualizado el video que entra como parámetro
	 * @param video - Video a actualizar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void updateReservaCarga(ReservaCarga reservaCarga) throws Exception {
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasCarga.setConn(conn);
			daoReservasCarga.updatReservaCarga(reservaCarga);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteReservaCarga(ReservaCarga reservaCarga) throws Exception {
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoReservasCarga.setConn(conn);
			daoReservasCarga.deleteReservaCarga(reservaCarga);
			//daoReservasCarga.createSavepoint(String.valueOf(reservaCarga.getId()));

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	//ITERA5 
	
	public ListaAerolineasMsg ingresosRFC12 (RangoFechas rango) throws SQLException
	{
		ArrayList<AerolineaMsg> aerolineaP = new ArrayList<AerolineaMsg>();
		ArrayList<AerolineaMsg> aerolineaIngresos = new ArrayList<AerolineaMsg>();
		
		DAOTablaAerolineas daoAero = new DAOTablaAerolineas();

		try
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			daoAero.setConn(conn);
			aerolineaIngresos = daoAero.ingresoCarga(rango);
			aerolineaP = daoAero.ingresoPasajeros(rango);
			aerolineaIngresos.addAll(aerolineaP);
			conn.commit();

		}
		catch (SQLException e)
		{
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e)
		{
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		finally
		{
			try
			{
				daoAero.cerrarRecursos();
				if (this.conn != null)
					this.conn.close();
			}
			catch (SQLException exception)
			{
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return new ListaAerolineasMsg(aerolineaIngresos);
		
	}
}
