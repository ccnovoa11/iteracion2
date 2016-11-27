package dtm;

import tm.VuelAndesMaster;
import vos.ListaReservasMsg;
import vos.ListaUsuariosMsg;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.NonReplyException;

public class VuelAndesDistributed {

	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static VuelAndesDistributed instance;
	
	private VuelAndesMaster tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private AllVideosMDB allVideosMQ;
	
	private static String path;


	private VuelAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allVideosMQ = new AllVideosMDB(factory, ctx);
		
		allVideosMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		allVideosMQ.close();
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
	
	public ListaReservasMsg getRegistrarReservas(List<Integer> usuarios,String origen, String destino) throws Exception
	{
		return tm.darVideosLocal();
	}
	
	public ListaUsuariosMsg darUsuariosPromovidos(int millas){
		return tm.darUsuariosPromovidos(millas);
	}
	
	public ListaVideos getRemoteVideos() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allVideosMQ.getRemoteVideos();
	}
}
