package org.vaadin.easyapp.util;

import com.vaadin.ui.Button;

public class ButtonWithCheck extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5788189783928567987L;
	private Button button;
	private ButtonClickable buttonClickable;
	
	public ButtonWithCheck(ButtonClickable buttonClickable) {
		super();
		this.buttonClickable = buttonClickable;
	}
	
	public ButtonWithCheck(String caption, ButtonClickable buttonClickable) {
		super(caption);
		this.buttonClickable = buttonClickable;
	}

	public Button getButton() {
		return button;
	}

	public ButtonClickable getButtonClickable() {
		return buttonClickable;
	}
	
}
