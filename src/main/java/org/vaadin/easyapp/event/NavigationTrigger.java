package org.vaadin.easyapp.event;

/**
 * Used when search is triggered
 * @author igolus
 *
 */
public interface NavigationTrigger {
	
	/**
	 * when navigation is trriffered
	 * @param clazz
	 */
	public void enter(Class<?> clazz);
}
