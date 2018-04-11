package org.vaadin.easyapp.util;

import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.ui.ViewWithToolBar;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class EasyAppLayout extends VerticalLayout {

	protected ActionContainer actionContainer = null;
	private ViewWithToolBar viewWithToolBarSource;

	public ViewWithToolBar getViewWithToolBarSource() {
		return viewWithToolBarSource;
	}

	public void setViewWithToolBarSource(ViewWithToolBar viewWithToolBarSource) {
		this.viewWithToolBarSource = viewWithToolBarSource;
	}

	public EasyAppLayout() {
		super();
	}

	public void setActionContainer(ActionContainer actionContainer) {
		this.actionContainer = actionContainer;
	}

	public ActionContainer getActionContainer() {
		return actionContainer;
	}

	public final void enter(ViewChangeEvent event) {
		if (getTitle() != null) {
			EasyAppMainView.getInstance().setContextualText(getTitle());
		}
		else {
			EasyAppMainView.getInstance().removeContextualText();
		}
		enterInView(event);
	}
	
	public void enterInView(ViewChangeEvent event) {
		
	}


	public ActionContainer buildActionContainer() {
		return null;
	}
	
	public String getTitle() {
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
					buttonWithCheck.setEnabled(buttonClickable);
				}
			}
		}
	}
	
	



}
