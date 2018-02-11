package com.example.xmpp_simple;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class OadrIQListener implements StanzaListener {

	private XMPPTCPConnection conn;
	
	public OadrIQListener(XMPPTCPConnection conn) {
		this.conn = conn;
	}
	
	public void processStanza(Stanza packet) throws NotConnectedException, InterruptedException {
		System.out.println("OadrIQProvider#processStanza(" + packet + ")");
		OadrIQ resultIQ = new OadrIQ();
		resultIQ.setType(IQ.Type.result);
		resultIQ.setStanzaId(packet.getStanzaId());
		resultIQ.setTo(packet.getFrom());
		resultIQ.setFrom(packet.getTo());
		
		System.out.println("OadrIQProvider#processStanza(" + packet + ") -> result送信。");
		conn.sendStanza(resultIQ);
	}

}
