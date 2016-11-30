package jms;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rabbitmq.jms.admin.RMQDestination;

import dtm.VuelAndesDistributed;
import vos.ExchangeMsg;
import vos.ListaUsuariosMsg;
import vos.UsuarioMsg;

public class Promover2PCMDB implements MessageListener, ExceptionListener
{
	public final static int TIME_OUT = 5;
	private final static String APP = "D03";

	private final static String GLOBAL_TOPIC_NAME = "java:global/RMQTopicPromover2PC";
	private final static String LOCAL_TOPIC_NAME = "java:global/RMQPromover2PCLocal";

	private final static String REQUEST = "REQUEST";
	private final static String REQUEST_ANSWER = "REQUEST_ANSWER";
	private final static String WAITING = "WAITING";

	private TopicConnection topicConnection;
	private TopicSession topicSession;
	private Topic globalTopic;
	private Topic localTopic;
	private boolean waiting;
	private int contador;
	private int millas;

	private List<UsuarioMsg> answer = new ArrayList<UsuarioMsg>();

	public Promover2PCMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException 
	{	
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopic = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME);
		TopicSubscriber topicSubscriber =  topicSession.createSubscriber(globalTopic);
		topicSubscriber.setMessageListener(this);
		localTopic = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME);
		topicSubscriber =  topicSession.createSubscriber(localTopic);
		topicSubscriber.setMessageListener(this);
		topicConnection.setExceptionListener(this);
		waiting = false;
		contador = 0;
	}

	public void start() throws JMSException
	{
		topicConnection.start();
	}

	public void close() throws JMSException
	{
		topicSession.close();
		topicConnection.close();
	}

	public ListaUsuariosMsg getRemoteUsuarios(int millas) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		this.millas = millas;
		answer.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);
		//		id = new String(md.digest(id.getBytes()));

		sendMessage(""+millas, REQUEST, globalTopic, id);
		boolean waiting = true;

		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(this.answer.isEmpty()){
				waiting = false;
				throw new NonReplyException("Time Out - No Reply");
			}
		}
		waiting = false;

		if(answer.isEmpty())
			throw new NonReplyException("Non Response");
		ListaUsuariosMsg res = new ListaUsuariosMsg(answer);
		return res;
	}


	private void sendMessage(String payload, String status, Topic dest, String id) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(id);
		ExchangeMsg msg = new ExchangeMsg("usuarios.general.D03", APP, payload, status, id);
		TopicPublisher topicPublisher = topicSession.createPublisher(dest);
		topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage txtMsg = topicSession.createTextMessage();
		txtMsg.setJMSType("TextMessage");
		String envelope = mapper.writeValueAsString(msg);
		System.out.println(envelope);
		txtMsg.setText(envelope);
		topicPublisher.publish(txtMsg);
	}

	@Override
	public void onMessage(Message message) 
	{
		TextMessage txt = (TextMessage) message;
		try 
		{
			String body = txt.getText();
			System.out.println(body);
			ObjectMapper mapper = new ObjectMapper();
			ExchangeMsg ex = mapper.readValue(body, ExchangeMsg.class);
			String id = ex.getMsgId();
			System.out.println(ex.getSender());
			System.out.println(ex.getStatus());
			if(!ex.getSender().equals(APP))
			{
				if(ex.getStatus().equals(REQUEST) && !waiting)
				{
					VuelAndesDistributed dtm = VuelAndesDistributed.getInstance();
					millas = Integer.parseInt(ex.getPayload());
					ListaUsuariosMsg usuarios = dtm.getUsuariosPromovidos(millas);
					String payload = mapper.writeValueAsString(usuarios);
					Topic t = new RMQDestination("", "usuarios.test", ex.getRoutingKey(), "");
					sendMessage(payload, WAITING, t, id);
					waiting = true;
				}
				else if(ex.getStatus().equals(REQUEST) && waiting)
				{
					VuelAndesDistributed dtm = VuelAndesDistributed.getInstance();
					ListaUsuariosMsg promovidos = mapper.readValue(ex.getPayload(), ListaUsuariosMsg.class);
					dtm.promover(promovidos);
					waiting = false;
				}
				else if(ex.getStatus().equals(WAITING))
				{
					VuelAndesDistributed dtm = VuelAndesDistributed.getInstance();
					ListaUsuariosMsg v = mapper.readValue(ex.getPayload(), ListaUsuariosMsg.class);
					answer.addAll(v.getUsuarios());
					contador++;
					if(contador==2)
					{
						contador = 0;
						answer.addAll(dtm.getUsuariosPromovidos(millas).getUsuarios());
						ListaUsuariosMsg promovidosGlobal = new ListaUsuariosMsg(answer);
						String payload = mapper.writeValueAsString(promovidosGlobal);
						Topic t = new RMQDestination("", "usuarios.test", ex.getRoutingKey(), "");
						sendMessage(payload, REQUEST, t, id);
					}
				}
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onException(JMSException exception) 
	{
		System.out.println(exception);
	}
}
