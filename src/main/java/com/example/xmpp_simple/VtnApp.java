package com.example.xmpp_simple;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;

public class VtnApp {

	private static boolean exit = false;

	public static void main(String[] args) throws Exception {
		SmackConfiguration.DEBUG = true;
	    	
		ProviderManager.addIQProvider("oadrPayload", "http://oadr", new OadrIQProvider("VTN"));

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword("vtn", "password")
				.setXmppDomain("domain")
				.setHost("host")
				.setPort(5222)
				.setResource("OpenADR")
				.setSecurityMode(SecurityMode.disabled)
				.build();

		AbstractXMPPConnection conn = new XMPPTCPConnection(config);
		conn.addAsyncStanzaListener(new StanzaListener() {
			public void processStanza(Stanza stanza) throws NotConnectedException, InterruptedException {
				System.out.println("[VTN] スタンザリスナ[" + stanza.toString() + "]");
				if (stanza instanceof IQ) {
					System.out.println("[VTN] 終了へ...");
					exit = true;
				}
			}
		}, new OadrIQFilter());
		conn.connect().login();

		IQ iqRequest = new OadrIQ();
		iqRequest.setType(IQ.Type.set);
		iqRequest.setTo(JidCreate.from("ven@domain/OpenADR"));
		conn.sendStanza(iqRequest);
		
		while (exit == false) {
			Thread.sleep(1000);
		}

		Thread.sleep(5000);
		conn.disconnect();
	}
}
