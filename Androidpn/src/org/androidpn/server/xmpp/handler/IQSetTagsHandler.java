package org.androidpn.server.xmpp.handler;

 

import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.androidpn.server.xmpp.session.SessionManager;
import org.dom4j.Element;
import org.xmpp.packet.IQ;
import org.xmpp.packet.PacketError;

public class IQSetTagsHandler extends IQHandler{

	private static final String NAMESPACE = "androidpn:iq:settags";
 
	private SessionManager sessionManager;
	public IQSetTagsHandler(){
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
	        		String tagStr = element.elementText("tags");
	        		 String tagArray[] = tagStr.split(",");
	        		 if(tagArray!=null&&tagArray.length>0){
	        			 for(String tag:tagArray){
	        				sessionManager.setUserTag(username, tag); 
	        			 }
	        			 System.out.println("tag success");
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
