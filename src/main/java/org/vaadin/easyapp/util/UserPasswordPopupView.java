package org.vaadin.easyapp.util;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class UserPasswordPopupView extends PopupView {
	private boolean onError = false;
	
	public UserPasswordPopupView(Content content) {
		super(content);
	}

	public UserPasswordPopupView(String small, Component large) {
		super(small, large);
	}
	
	private PasswordField passwordField;
	public PasswordField getPasswordField() {
		return passwordField;
	}

	public TextField getUserTextField() {
		return userTextField;
	}

	private TextField userTextField;
	private VerticalLayout viewLayout;

	public VerticalLayout getViewLayout() {
		return viewLayout;
	}

	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public void setUserTextField(TextField userTextField) {
		this.userTextField = userTextField;
	}
	
	
	public void markAsError(String label, String style) {
		if (!onError) {
			Label errorLabel = new Label(label);
			if (style != null) {
				errorLabel.setStyleName(style);
			}
			viewLayout.addComponent(errorLabel);
		}
		onError = true;
	}

	public void setViewLayout(VerticalLayout viewLayout) {
		this.viewLayout = viewLayout;
	}
	
	
	
	
}
