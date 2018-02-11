package com.example.xmpp_simple;

import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Stanza;

public class OadrIQFilter implements StanzaFilter {

	public boolean accept(Stanza stanza) {
		System.out.println("OadrIQFilter#accept(" + stanza + ")");
		if (stanza instanceof OadrIQ) {
			System.out.println("OadrIQFilter#accept -> true");
			return true;
		}
		System.out.println("OadrIQFilter#accept -> false");
		return false;
	}

}
