package org.vaadin.easyapp.util;

import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.ui.Button.ClickListener;

public interface VisitableView extends View {
	
	
	/**
	 * define the action when buuton is clicked
	 * return null to disable 
	 * @param key
	 * @return
	 */
	ClickListener getClickListener(String key);
	
	/**
	 * List of buttons to display in the toolbar
	 * @return
	 */
	List<ButtonDescriptor> getButtons();
	
	/**
	 * Check if the button is cickable or not
	 * @param key
	 * @return
	 */
	boolean isClickable(String key);
	
}
