package com.tellfan.media;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tellfan.shoutout.Fan;

public class MediaDatasHandler extends DefaultHandler{
	private List<MediaData> mediaDatas;
	private MediaData currentMediaData;
	
	private Fan currentFan;

	private StringBuilder builder;
	
	public List<MediaData> getMediaData(){
		return this.mediaDatas;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
		if (this.mediaDatas != null) {			

			if (localName.equalsIgnoreCase("URL")) {
				this.currentMediaData.setURL(builder.toString());				
			}
			if (localName.equalsIgnoreCase("messageID")){
				this.currentMediaData.setMessageID(builder.toString());
			}
			if (localName.equalsIgnoreCase("outputFileName")){
				this.currentMediaData.setOutputFileName(builder.toString());
			}
			
			if (this.currentFan != null){
				if (localName.equalsIgnoreCase("message")) {
					this.currentFan.setMessage(builder.toString());
				}
				this.currentMediaData.addComment(currentFan);	
			}
			
			if (localName.equalsIgnoreCase("mediaData")) 
				mediaDatas.add(this.currentMediaData);
			builder.setLength(0);
		}

	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		mediaDatas = new ArrayList<MediaData>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase("mediaData")){		
			this.currentMediaData = new MediaData();
			
		}	
		
		if (localName.equalsIgnoreCase("fan")){				
				this.currentFan = new Fan();
				this.currentFan.setCfirsname(attributes.getValue("cfirstname"));
				this.currentFan.setClastname(attributes.getValue("clastname"));
				this.currentFan.setClientid(attributes.getValue("clientid"));			
				this.currentFan.setCname(attributes.getValue("cname"));
				this.currentFan.setCshortname(attributes.getValue("cshortname"));
				this.currentFan.setUsernlb(attributes.getValue("usernlb"));
				this.currentFan.setDateadded(attributes.getValue("dateadded"));			
				//this.currentFan.setMessage(attributes.getValue("message"));
				this.currentFan.setMessagetypeid(attributes.getValue("messagetypeid"));	
				if (attributes.getValue("ThumbURL") !=null){
					this.currentFan.setThumburl(attributes.getValue("ThumbURL"));
				}
				else {
					this.currentFan.setThumburl(attributes.getValue("thumburl"));
				}
				//this.currentMediaData.addComment(currentFan);												
			
		}	
		
	}
}