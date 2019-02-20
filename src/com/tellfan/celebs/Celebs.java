package com.tellfan.celebs;

public class Celebs {

	private String clientID;
	private String clientName;
	private String mediaSource;
	private String mediaLink;
	
	public Celebs(String clientID, String clientName, String mediaSource,
			String mediaLink) {
		super();
		this.clientID = clientID;
		this.clientName = clientName;
		this.mediaSource = mediaSource;
		this.mediaLink = mediaLink;
	}
	public Celebs() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getMediaSource() {
		return mediaSource;
	}
	public void setMediaSource(String mediaSource) {
		this.mediaSource = mediaSource;
	}
	public String getMediaLink() {
		return mediaLink;
	}
	public void setMediaLink(String mediaLink) {
		this.mediaLink = mediaLink;
	}
}
