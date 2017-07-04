package org.vaadin.easyapp.util;

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
	
}
