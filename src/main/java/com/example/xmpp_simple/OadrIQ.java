package com.example.xmpp_simple;

import org.jivesoftware.smack.packet.IQ;

public class OadrIQ extends IQ {
	
    public static final String ELEMENT = "oadrPayload";
    public static final String NAMESPACE = "http://oadr";

    public OadrIQ() {
        super(ELEMENT, NAMESPACE);
    }

	@Override
	protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
		System.out.println("[Oadr IQ]");
		xml.append(">");
		xml.append("Test");
		return xml;
	}

}
