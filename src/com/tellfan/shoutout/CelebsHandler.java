package com.tellfan.shoutout;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CelebsHandler extends DefaultHandler{
	private List<Celeb> celebs;
	private List<Fan> fans;
	private Celeb currentCeleb;
	private Fan currentFan;
	private StringBuilder builder;
	
	private boolean moreDisplayed = false;
	
	public List<Celeb> getCelebs(){
		return this.celebs;
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
		if (localName.equalsIgnoreCase("moredisplayed")) {
			String more = builder.toString();
			if (more != null && (more.trim().length()>0))
				moreDisplayed = Boolean.parseBoolean(more);
		}
		
		if (this.currentCeleb != null) {			
			if (localName.equalsIgnoreCase("message")) {
				if (this.currentCeleb.getMessage() == null)
					this.currentCeleb.setMessage(builder.toString());	
				else
					if (this.currentFan != null)
						this.currentFan.setMessage(builder.toString());	
			} else 
				if (localName.equalsIgnoreCase("celeb")) {
				celebs.add(this.currentCeleb);
			}
			builder.setLength(0);
		}

	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		celebs = new ArrayList<Celeb>();
		fans = new ArrayList<Fan>();
		builder = new StringBuilder();
		//this.currentCeleb = new Celeb();
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (localName.equalsIgnoreCase("celeb")){		
			this.currentCeleb = new Celeb();
			this.currentCeleb.setCfirsname(attributes.getValue("cfirstname"));
			this.currentCeleb.setClastname(attributes.getValue("clastname"));
			this.currentCeleb.setClientid(attributes.getValue("clientid"));
			this.currentCeleb.setClientlb(attributes.getValue("clientnlb"));
			this.currentCeleb.setCname(attributes.getValue("cname"));
			this.currentCeleb.setCshortname(attributes.getValue("cshortname"));
			this.currentCeleb.setDateadded(attributes.getValue("dateadded"));
			this.currentCeleb.setDevicetype(attributes.getValue("devicetype"));
			this.currentCeleb.setGalleryname(attributes.getValue("galleryname"));
			this.currentCeleb.setIscomment(attributes.getValue("iscomment"));			
			this.currentCeleb.setMessagetypeid(attributes.getValue("messagetypeid"));
			this.currentCeleb.setRootmessageid(attributes.getValue("rootmessageid"));
			this.currentCeleb.setShoutouttype(attributes.getValue("shoutouttype"));
			if (attributes.getValue("ThumbURL") !=null){
				this.currentCeleb.setThumburl(attributes.getValue("ThumbURL"));
			}
			else {
				this.currentCeleb.setThumburl(attributes.getValue("thumburl"));
			}
			
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
			this.currentFan.setMessage(attributes.getValue("message"));
			this.currentFan.setMessagetypeid(attributes.getValue("messagetypeid"));	
			if (attributes.getValue("ThumbURL") !=null){
				this.currentFan.setThumburl(attributes.getValue("ThumbURL"));
			}
			else {
				this.currentFan.setThumburl(attributes.getValue("thumburl"));
			}
			this.currentCeleb.addFan(currentFan);	
									
		}		

	}
	public boolean isMoreDisplayed() {
		return moreDisplayed;
	}
	public void setMoreDisplayed(boolean moreDisplayed) {
		this.moreDisplayed = moreDisplayed;
	}
}