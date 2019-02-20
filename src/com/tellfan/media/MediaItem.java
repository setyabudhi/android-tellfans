package com.tellfan.media;

public class MediaItem {
 
	private String mediaLabel = new String();
    private String mediaSource = new String();
    private String mediaLink = new String();
    private String albumId = new String();
    private String messageTypeid = new String();
    private String galleryName = new String();
    private boolean isMultiple;
    
    
    public MediaItem() {
		super();
	
	}
    
    public MediaItem(String mediaLabel, String mediaSource, String mediaLink,
			String albumId, String messageTypeid, String galleryName,
			boolean isMultiple) {
		super();
		this.mediaLabel = mediaLabel;
		this.mediaSource = mediaSource;
		this.mediaLink = mediaLink;
		this.albumId = albumId;
		this.messageTypeid = messageTypeid;
		this.galleryName = galleryName;
		this.isMultiple = isMultiple;
	}
    
	public String getMediaLabel() {
		return mediaLabel;
	}
	public void setMediaLabel(String mediaLabel) {
		this.mediaLabel = mediaLabel;
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
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getMessageTypeid() {
		return messageTypeid;
	}
	public void setMessageTypeid(String messageTypeid) {
		this.messageTypeid = messageTypeid;
	}
	public String getGalleryName() {
		return galleryName;
	}
	public void setGalleryName(String galleryName) {
		this.galleryName = galleryName;
	}
	public boolean isMultiple() {
		return isMultiple;
	}
	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}
}
