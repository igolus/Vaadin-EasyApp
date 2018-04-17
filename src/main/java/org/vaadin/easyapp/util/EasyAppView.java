package org.vaadin.easyapp.util;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

/**
 * Interface to implement to display a component 
 * @author igolus
 *
 */
public interface EasyAppView {
	
	public default void enter(ViewChangeEvent event) {
    }
	
	public default ActionContainer buildActionContainer() {
		return null;
    }

}
