package org.vaadin.easyapp.util;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.easyapp.event.SearchTrigger;

import com.vaadin.ui.Image;


public class ActionContainer {
	private List<ButtonWithCheck> listButtonWithCheck = new ArrayList<>();
	private List<Image> listImages = new ArrayList<>();
	private SearchTrigger searchTrigger;
	private String styleName;

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public SearchTrigger getSearchTrigger() {
		return searchTrigger;
	}

	public void addImageIcon(Image image) {
		listImages.add(image);
	}
	
	public void addButtonWithCheck(ButtonWithCheck buttonWithCheck) {
		listButtonWithCheck.add(buttonWithCheck);
	}

	public List<ButtonWithCheck> getListButtonWithCheck() {
		return listButtonWithCheck;
	}
	
	public List<Image> getListImages() {
		return listImages;
	}

	public void setSearch(SearchTrigger searchTrigger) {
		this.searchTrigger = searchTrigger;
	}
	
	
	
}
