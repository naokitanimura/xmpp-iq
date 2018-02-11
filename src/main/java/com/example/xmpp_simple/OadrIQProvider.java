package com.example.xmpp_simple;

import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

public class OadrIQProvider extends IQProvider<OadrIQ> {
	
	private final String tag;
	
	public OadrIQProvider(String tag) {
		this.tag = tag;
	}

	@Override
	public OadrIQ parse(XmlPullParser parser, int initialDepth) throws Exception {
		System.out.println("[" + tag + "] OadrIQProvider#parse[" + parser.getName() + "]");
		OadrIQ iq = new OadrIQ();

		if (parser.getName().equals("oadrPayload")) {
			int eventType = parser.next();
			if (eventType == XmlPullParser.TEXT) {
				System.out.println("[" + tag + "] oadrPayload: " + parser.getText());
			}
		}

		return iq;
	}

}
