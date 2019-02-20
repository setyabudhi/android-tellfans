package com.tellfan.shoutout;

import java.util.ArrayList;
import java.util.List;

/** Contains getter and setter method for varialbles  */
public class Celeb {

	/** Variables */
	private String devicetype = new String();
	private String clientlb = new String();
	private String clientid = new String();
	private String shoutouttype = new String();
	private String cname = new String();
	private String cfirsname = new String();
	private String clastname = new String();
	private String cshortname = new String();
	private String rootmessageid = new String();
	private String dateadded = new String();
	private String messagetypeid = new String();
	private String iscomment = new String();
	private String galleryname = new String();
	private String message = null;
	private List<Fan> fan = new ArrayList<Fan>();
	private boolean isComment = false;
	private String thumburl = new String();
	
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getClientlb() {
		return clientlb;
	}
	public void setClientlb(String clientlb) {
		this.clientlb = clientlb;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getShoutouttype() {
		return shoutouttype;
	}
	public void setShoutouttype(String shoutouttype) {
		this.shoutouttype = shoutouttype;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCfirsname() {
		return cfirsname;
	}
	public void setCfirsname(String cfirsname) {
		this.cfirsname = cfirsname;
	}
	public String getClastname() {
		return clastname;
	}
	public void setClastname(String clastname) {
		this.clastname = clastname;
	}
	public String getCshortname() {
		return cshortname;
	}
	public void setCshortname(String cshortname) {
		this.cshortname = cshortname;
	}
	public String getRootmessageid() {
		return rootmessageid;
	}
	public void setRootmessageid(String rootmessageid) {
		this.rootmessageid = rootmessageid;
	}
	public String getDateadded() {
		return dateadded;
	}
	public void setDateadded(String dateadded) {
		this.dateadded = dateadded;
	}
	public String getMessagetypeid() {
		return messagetypeid;
	}
	public void setMessagetypeid(String messagetypeid) {
		this.messagetypeid = messagetypeid;
	}
	public String getIscomment() {
		return iscomment;
	}
	public void setIscomment(String iscomment) {
		this.iscomment = iscomment;
	}
	public String getGalleryname() {
		return galleryname;
	}
	public void setGalleryname(String galleryname) {
		this.galleryname = galleryname;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Fan> getFan() {
		return fan;
	}
	public void addFan(Fan fan) {
		this.fan.add(fan);
	}
	public boolean isComment() {
		return isComment;
	}
	public void setComment(boolean isComment) {
		this.isComment = isComment;
	}
	public void setFan(List<Fan> fan) {
		this.fan = fan;
	}
	public String getThumburl() {
		return thumburl;
	}
	public void setThumburl(String thumburl) {
		this.thumburl = thumburl;
	}
	
	


}
