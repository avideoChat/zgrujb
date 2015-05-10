package org.androidpn.server.xmpp.handler;

 

import org.androidpn.server.console.controller.NotificationController;
import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;

public class IQSetAliasHandler extends IQHandler{

	private static final String NAMESPACE = "androidpn:iq:setalias";
 
	private SessionManager sessionManager;
	public IQSetAliasHandler(){
		 sessionManager = SessionManager.getInstance();
	}
	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		// TODO Auto-generated method stub

	        ClientSession session = sessionManager.getSession(packet.getFrom());
		IQ reply = null;
	        if (session == null) {
	            log.error("Session not found for key " + packet.getFrom());
	            reply = IQ.createResultIQ(packet);
	            reply.setChildElement(packet.getChildElement().createCopy());
	            reply.setError(PacketError.Condition.internal_server_error);
	            return reply;
	        }
	        if(session.getStatus() == Session.STATUS_AUTHENTICATED){
	        	if(IQ.Type.set.equals(packet.getType())){
	        		Element element = packet.getChildElement();
	        		String username = element.elementText("username");
	        		String alias = element.elementText("alias");
	        
	        		if(username!=null&&!username.equals("")&&alias!=null&&!alias.equals("")){
	        			System.out.println("alias success");
	        			sessionManager.setUserAlias(username, alias);
	        		}
	        	}
	        }
		return null;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

}
