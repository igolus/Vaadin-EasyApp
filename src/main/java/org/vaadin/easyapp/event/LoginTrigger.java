package org.vaadin.easyapp.event;

/**
 * Used when search is triggered
 * @author igolus
 *
 */
public interface LoginTrigger {
	/**
	 * Return null is the login is failing
	 * @param user
	 * @param password
	 * @return String to display when the user is logged
	 */
	public String loginTriggered(String user, String password);
}
