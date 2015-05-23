package org.androidpn.client;

import org.jivesoftware.smack.packet.IQ;

public class NormalMesIQ extends IQ {

	private String from;
	private String to;
	private String mes;
	private String time;
	
	
	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getMes() {
		return mes;
	}


	public void setMes(String mes) {
		this.mes = mes;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public String getChildElementXML() {
		// TODO Auto-generated method stub
		 StringBuilder buf = new StringBuilder();
	        buf.append("<").append("normalmes").append(" xmlns=\"").append(
	                "androidpn:iq:normalmes").append("\">");
	        if (from!= null) {
	            buf.append("<from>").append(from).append("</from>");
	        }
	        if (to!= null) {
	        	buf.append("<to>").append(to).append("</to>");
	        }
	        if (mes!= null) {
	        	buf.append("<mes>").append(mes).append("</mes>");
	        }
	        if (time!= null) {
	        	buf.append("<time>").append(time).append("</time>");
	        }
	        buf.append("</").append("normalmes").append("> ");
	        return buf.toString();
		 
	}

}
