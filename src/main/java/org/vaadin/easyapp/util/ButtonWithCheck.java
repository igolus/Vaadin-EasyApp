package org.vaadin.easyapp.util;

import com.vaadin.ui.Button;

public class ButtonWithCheck extends Button {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5788189783928567987L;
	private ButtonClickable buttonClickable;
	
	public ButtonWithCheck(ButtonClickable buttonClickable, ClickListener listener) {
		this(null, buttonClickable, listener);
	}
	
	public ButtonWithCheck(String caption, ButtonClickable buttonClickable, ClickListener listener) {
		super(caption);
		this.buttonClickable = buttonClickable;
		if (listener != null) {
			addClickListener( (event) -> {listener.buttonClick(event);});
		}
	}

	public ButtonClickable getButtonClickable() {
		return buttonClickable;
	}
	
}
