package org.vaadin.easyapp.util;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.easyapp.event.SearchTrigger;


public class ActionContainer {
	private List<ButtonWithCheck> listButtonWithCheck = new ArrayList<>();
	private SearchTrigger searchTrigger;

	public SearchTrigger getSearchTrigger() {
		return searchTrigger;
	}

	public void addButtonWithCheck(ButtonWithCheck buttonWithCheck) {
		listButtonWithCheck.add(buttonWithCheck);
	}

	public List<ButtonWithCheck> getListButtonWithCheck() {
		return listButtonWithCheck;
	}
	
	public void setSearch(SearchTrigger searchTrigger) {
		this.searchTrigger = searchTrigger;
	}
	
}
