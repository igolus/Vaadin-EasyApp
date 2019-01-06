package org.vaadin.easyapp.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.vaadin.easyapp.event.SearchTrigger;

import com.vaadin.ui.Component;
import com.vaadin.ui.Image;


public class ActionContainer {
	
	public enum Position {
		  LEFT,
		  RIGHT
	}
	
	public enum InsertPosition {
		  BEFORE,
		  AFTER
	}
	
	
	private List<Component> leftListComponent = new ArrayList<>();
	private List<Component> rightListComponent = new ArrayList<>();
	
	private SearchTrigger searchTrigger;
	private Component singleComponent;

	public SearchTrigger getSearchTrigger() {
		return searchTrigger;
	}

	public void addImageIcon(Image image, Position position, InsertPosition insertPosition) {
		addComponent(image, position, insertPosition);
	}
	
	public void addComponent(Component component, Position position, InsertPosition insertPosition) {
		if (position == Position.LEFT) {
			if (insertPosition == InsertPosition.BEFORE) {
				leftListComponent = insertBefore(component, leftListComponent);
			}
			else if (insertPosition == null || insertPosition == InsertPosition.AFTER) {
				leftListComponent.add(component);
			}
		}
		else if (position == Position.RIGHT) {
			if (insertPosition == InsertPosition.BEFORE) {
				rightListComponent = insertBefore(component, rightListComponent);
			}
			else if (insertPosition == null || insertPosition == InsertPosition.AFTER) {
				rightListComponent.add(component);
			}
		}
	}
	
	/**
	 * Recopy the list
	 * @param component
	 * @param listComonent 
	 * @return
	 */
	private List<Component> insertBefore(Component component, List<Component> listComponent) {
		List<Component> tempList = new ArrayList<>();
		tempList.add(component);
		for (Component compnent : listComponent) {
			tempList.add(compnent);
		}
		return tempList;
	}
	
	/**
	 * Add Button at last place in the list
	 * @param buttonWithCheck
	 */
	public void addButtonWithCheck(ButtonWithCheck buttonWithCheck, Position position, InsertPosition insertPosition) {
		addComponent(buttonWithCheck, position, insertPosition);
	}
	

	public void addSearch(SearchTrigger searchTrigger, Position position, InsertPosition insertPosition) {
		this.searchTrigger = searchTrigger;
	}

	public List<Component> getLeftListComponent() {
		return leftListComponent;
	}

	public List<Component> getRightListComponent() {
		return rightListComponent;
	}

	public List<ButtonWithCheck> getListButtonWithCheck() {
		List<ButtonWithCheck> ret = new ArrayList<>();
		for (Component component : leftListComponent) {
			if (ButtonWithCheck.class.isAssignableFrom(component.getClass())) {
				ret.add((ButtonWithCheck) component);
			}
		}
		for (Component component : rightListComponent) {
			if (ButtonWithCheck.class.isAssignableFrom(component.getClass())) {
				ret.add((ButtonWithCheck) component);
			}
		}
		return ret;
	}

	public Component getSingleComponent() {
		return this.singleComponent;
	}

	public void setSingleComponent(Component singleComponent) {
		this.singleComponent = singleComponent;
	}
	
	
}
