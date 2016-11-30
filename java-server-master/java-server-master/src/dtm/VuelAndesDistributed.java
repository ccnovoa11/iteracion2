package dtm;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.jms.*;
import javax.naming.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.*;

import jms.AerolineasMDB;
import jms.NonReplyException;
import jms.PromoverUsuarioMDB;
import jms.RegistrarReservaMDB;
import jms.VuelosMDB;
import tm.VuelAndesMaster;
import vos.ListaAerolineasMsg;
import vos.ListaReservasMsg;
import vos.ListaUsuariosMsg;
import vos.ListaVuelosMsg;
import vos1.RangoFechas;

public class VuelAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";

	private static VuelAndesDistributed instance;

	private VuelAndesMaster tm;

	private QueueConnectionFactory queueFactory;

	private TopicConnectionFactory factory;

	private AerolineasMDB aerolineasMQ;
	
	private VuelosMDB vuelosMQ;
	
	private RegistrarReservaMDB reservasMQ;
	
	private PromoverUsuarioMDB usuariosMQ;

	private static String path;


	private VuelAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		aerolineasMQ = new AerolineasMDB(factory, ctx);
		vuelosMQ = new VuelosMDB(factory, ctx);
		reservasMQ = new RegistrarReservaMDB(factory, ctx);
		usuariosMQ = new PromoverUsuarioMDB(factory, ctx);

		aerolineasMQ.start();
		vuelosMQ.start();
		reservasMQ.start();
		usuariosMQ.start();

	}

	public void stop() throws JMSException
	{
		aerolineasMQ.close();
		vuelosMQ.close();
		reservasMQ.close();
		usuariosMQ.close();
	}

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}

	public void setUpTransactionManager(VuelAndesMaster tm)
	{
		this.tm = tm;
	}

	private static VuelAndesDistributed getInst()
	{
		return instance;
	}

	public static VuelAndesDistributed getInstance(VuelAndesMaster tm)
	{
		if(instance == null)
		{
			try {
				instance = new VuelAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}

	public static VuelAndesDistributed getInstance()
	{
		if(instance == null)
		{
			VuelAndesMaster tm = new VuelAndesMaster(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		VuelAndesMaster tm = new VuelAndesMaster(path);
		return getInstance(tm);
	}

	public ListaReservasMsg getRegistrarReservas(List<Integer> ids, String origen, String destino) throws Exception
	{
		//TODO
		return tm.darRegistrarReservas(ids, origen, destino);
	}
	
	public ListaUsuariosMsg getUsuariosPromovidos(int millas) throws Exception
	{
		//TODO
		return tm.darUsuariosPromovidos(millas);
	}

	public ListaVuelosMsg getVuelosAeropuerto(int aeropuerto) throws Exception
	{
		//TODO
		return tm.darVuelosGlobal(aeropuerto);
	}
	
	public ListaAerolineasMsg getIngresoAerolineas() throws Exception
	{
		//TODO
		return tm.ingresosRFC12();
	}
	
	public void promover(ListaUsuariosMsg promovidos) throws Exception{
		tm.promoverUsuarios(promovidos);
	}

	public ListaReservasMsg getRemoteReservas(List<Integer> ids, String origen, String destino) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return reservasMQ.getRemoteReservas(ids, origen, destino);
	}
	
	public ListaUsuariosMsg getRemoteUsuarios(int millas) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return usuariosMQ.getRemoteUsuarios(millas);
	}
	
	public ListaVuelosMsg getRemotVuelos(int aeropuerto) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return vuelosMQ.getRemoteVuelos(aeropuerto);
	}
	
	public ListaAerolineasMsg getRemoteAerolineas() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return aerolineasMQ.getRemoteAerolineas();
	}
}
