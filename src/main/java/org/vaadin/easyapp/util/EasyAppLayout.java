package org.vaadin.easyapp.util;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class EasyAppLayout extends VerticalLayout {

	protected ActionContainer actionContainer = null;

	public void setActionContainer(ActionContainer actionContainer) {
		this.actionContainer = actionContainer;
	}

	public ActionContainer getActionContainer() {
		return actionContainer;
	}

	public void enter(ViewChangeEvent event) {
	}


	public ActionContainer buildActionContainer() {
		return null;
	}

	/**
	 * Check if the buttons are clickable
	 */
	public void refreshClickable() {
		if (actionContainer != null) {
			for (ButtonWithCheck buttonWithCheck : actionContainer.getListButtonWithCheck()) {
				if (buttonWithCheck.getButtonClickable() != null) {
					boolean buttonClickable = buttonWithCheck.getButtonClickable().isClickable();
					buttonWithCheck.getButton().setEnabled(buttonClickable);
				}
			}
		}
	}




}
