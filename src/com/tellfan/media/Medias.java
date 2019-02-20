package com.tellfan.media;

import java.util.ArrayList;
import java.util.List;

public class Medias {


	private int totalResultsAvailable;
    private int totalPages;
    private int nextButton;
    private int prevButton;
    private String message = new String();
    private List<MediaItem> result = new ArrayList<MediaItem>();
    
	public Medias() {
		super();	
	}
    
	public Medias(int totalResultsAvailable, int totalPages, int nextButton,
			int prevButton, String message, List<MediaItem> result) {
		super();
		this.totalResultsAvailable = totalResultsAvailable;
		this.totalPages = totalPages;
		this.nextButton = nextButton;
		this.prevButton = prevButton;
		this.message = message;
		this.result = result;
	}
	
	public int getTotalResultsAvailable() {
		return totalResultsAvailable;
	}
	public void setTotalResultsAvailable(int totalResultsAvailable) {
		this.totalResultsAvailable = totalResultsAvailable;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getNextButton() {
		return nextButton;
	}
	public void setNextButton(int nextButton) {
		this.nextButton = nextButton;
	}
	public int getPrevButton() {
		return prevButton;
	}
	public void setPrevButton(int prevButton) {
		this.prevButton = prevButton;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<MediaItem> getResult() {
		return result;
	}
	public void setResult(List<MediaItem> result) {
		this.result = result;
	}
	
	public void addResult(List<MediaItem> result) {
		this.result.addAll(result);
	}
}
