package org.vaadin.easyapp.ui;

import java.util.List;

import org.vaadin.easyapp.util.ActionContainer;
import org.vaadin.easyapp.util.ButtonWithCheck;
import org.vaadin.easyapp.util.EasyAppLayout;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ViewWithToolBar extends VerticalLayout implements View {

	private EasyAppLayout innerComponent;

	public ViewWithToolBar(EasyAppLayout innerComponent)   {
		this.innerComponent = innerComponent;
		
		ActionContainer actionContainer = innerComponent.getActionContainer();
		List<ButtonWithCheck> listButtonWithCheck = actionContainer.getListButtonWithCheck();
		
		GridLayout gridLayout = null;
		gridLayout.setSizeFull();
		
		HorizontalLayout buttonLayout = null;
		if (listButtonWithCheck != null && listButtonWithCheck.size() > 0) {
			buttonLayout = new HorizontalLayout();
			for (ButtonWithCheck buttonWithCheck : listButtonWithCheck) {
				buttonLayout.addComponent(buttonWithCheck.getButton());
			}
		}
		HorizontalLayout searchLayout = null;
		if (actionContainer.getSearchTrigger() != null) {
			searchLayout = new HorizontalLayout();
			TextField search = new TextField();
			searchLayout.addComponent(search);
			Button searchButton = new Button();
			searchLayout.addComponent(searchButton);
			searchButton.setIcon(VaadinIcons.SEARCH);
			searchButton.addClickListener((event) -> actionContainer.getSearchTrigger().searchTriggered(search.getValue()));
		}
		
		
		new GridLayout(2, 1);
		
		gridLayout.addComponent(buttonLayout, 0, 0);
		gridLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
		gridLayout.addComponent(searchLayout, 1, 0);
		gridLayout.setComponentAlignment(searchLayout, Alignment.MIDDLE_RIGHT);
	}

	public EasyAppLayout getInnerComponent() {
		// TODO Auto-generated method stub
		return innerComponent;
	}
}