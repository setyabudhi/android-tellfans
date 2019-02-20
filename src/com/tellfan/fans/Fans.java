package com.tellfan.fans;

public class Fans {   

	private String fullname;
	private String link;
	private String thumbnail;
	private boolean ispaidsubscription;
	
	public Fans() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    public Fans(String fullname, String link, String thumbnail,
			boolean ispaidsubscription) {
		super();
		this.fullname = fullname;
		this.link = link;
		this.thumbnail = thumbnail;
		this.ispaidsubscription = ispaidsubscription;
	}

	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public boolean isIspaidsubscription() {
		return ispaidsubscription;
	}
	public void setIspaidsubscription(boolean ispaidsubscription) {
		this.ispaidsubscription = ispaidsubscription;
	}
}
