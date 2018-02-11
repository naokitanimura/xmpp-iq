package com.example.xmpp_simple;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class VenApp 
{
	private static boolean received = false;

    public static void main( String[] args ) throws Exception
    {
        System.out.println("[VEN] アプリ開始。");
    		SmackConfiguration.DEBUG = true;
    	
    		ProviderManager.addIQProvider("oadrPayload", "http://oadr", new OadrIQProvider("VEN"));
    		
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
        		.setUsernameAndPassword("ven", "password")
        		.setXmppDomain("domain")
        		.setHost("host")
        		.setPort(5222)
        		.setResource("OpenADR")
        		.setSecurityMode(SecurityMode.disabled)
        		.build();
        
        final XMPPTCPConnection conn = new XMPPTCPConnection(config);
		conn.addAsyncStanzaListener(new StanzaListener() {
			public void processStanza(Stanza stanza) throws NotConnectedException, InterruptedException {
				System.out.println("[VEN] スタンザリスナ[" + stanza.toString() + "]");
				if (stanza instanceof IQ) {
					OadrIQ resultIQ = new OadrIQ();
					resultIQ.setType(IQ.Type.result);
					resultIQ.setStanzaId(stanza.getStanzaId());
					resultIQ.setTo(stanza.getFrom());
					resultIQ.setFrom(stanza.getTo());
					conn.sendStanza(resultIQ);
					received = true;
				}
			}
		}, new OadrIQFilter());
        conn.connect().login();
        System.out.println("[VEN] XMPPサーバーにログイン。");
		
        System.out.println("[VEN] リクエストリッスン開始。");
		while (received == false) {
			Thread.sleep(1000);
		}
        System.out.println("[VEN] リクエストリッスン終了。");

		Thread.sleep(5000);
        conn.disconnect();
        System.out.println("[VEN] XMPPサーバーにログアウト。");
        
        System.out.println("[VEN] アプリ終了。");
    }
}
