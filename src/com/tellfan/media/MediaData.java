package com.tellfan.media;

import java.util.ArrayList;
import java.util.List;

import com.tellfan.shoutout.Fan;

public class MediaData {

	private String clientID = new String();
	private String userID = new String();
	private String  messageID = new String();
	private String messageTypeID = new String();
	private String message = new String();
	private String groupID = new String();
	private String videoID = new String();
	private String outputFileName = new String();
	private String galleryID = new String();
	private String coverPhotoID = new String();
	private String description = new String();
	private String ordernum = new String();
	private String URL = new String();
	private boolean IsMultiple = false;
	private List<Fan> comments = new ArrayList<Fan>();
	
	public MediaData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MediaData(String clientID, String userID, String messageID,
			String messageTypeID, String message, String groupID,
			String videoID, String outputFileName, String galleryID,
			String coverPhotoID, String description, String ordernum,
			String uRL, boolean isMultiple) {
		super();
		this.clientID = clientID;
		this.userID = userID;
		this.messageID = messageID;
		this.messageTypeID = messageTypeID;
		this.message = message;
		this.groupID = groupID;
		this.videoID = videoID;
		this.outputFileName = outputFileName;
		this.galleryID = galleryID;
		this.coverPhotoID = coverPhotoID;
		this.description = description;
		this.ordernum = ordernum;
		URL = uRL;
		IsMultiple = isMultiple;
	}
	
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public String getMessageTypeID() {
		return messageTypeID;
	}
	public void setMessageTypeID(String messageTypeID) {
		this.messageTypeID = messageTypeID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getVideoID() {
		return videoID;
	}
	public void setVideoID(String videoID) {
		this.videoID = videoID;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public String getGalleryID() {
		return galleryID;
	}
	public void setGalleryID(String galleryID) {
		this.galleryID = galleryID;
	}
	public String getCoverPhotoID() {
		return coverPhotoID;
	}
	public void setCoverPhotoID(String coverPhotoID) {
		this.coverPhotoID = coverPhotoID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public boolean isIsMultiple() {
		return IsMultiple;
	}
	public void setIsMultiple(boolean isMultiple) {
		IsMultiple = isMultiple;
	}
	
	public void setComments(List<Fan> result) {
		this.comments = result;
	}
	
	public void addComment(Fan result) {
		this.comments.add(result);
	}
	public List<Fan> getComments() {
		return comments;
	}
}
