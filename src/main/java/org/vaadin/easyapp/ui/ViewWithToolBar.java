package org.vaadin.easyapp.ui;

import java.util.List;

import org.vaadin.easyapp.util.ActionContainer;
import org.vaadin.easyapp.util.ButtonWithCheck;
import org.vaadin.easyapp.util.EasyAppLayout;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ViewWithToolBar extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		innerComponent.enter(event);
	}

	private EasyAppLayout innerComponent;

	public ViewWithToolBar(EasyAppLayout innerComponent)   {
		this.innerComponent = innerComponent;
		
		ActionContainer actionContainer = innerComponent.buildActionContainer();
		innerComponent.setActionContainer(actionContainer);
		GridLayout gridLayout = null;
		List<ButtonWithCheck> listButtonWithCheck = null;
		if (actionContainer != null) {
			 listButtonWithCheck = actionContainer.getListButtonWithCheck();
		}
		HorizontalLayout buttonLayout = null;
		if (listButtonWithCheck != null && listButtonWithCheck.size() > 0) {
			if (gridLayout == null) {
				gridLayout = new GridLayout(2, 1);
			}
			buttonLayout = new HorizontalLayout();
			for (ButtonWithCheck buttonWithCheck : listButtonWithCheck) {
				buttonLayout.addComponent(buttonWithCheck.getButton());
			}
		}
		HorizontalLayout searchLayout = null;
		if (actionContainer != null && actionContainer.getSearchTrigger() != null) {
			if (gridLayout == null) {
				gridLayout = new GridLayout(2, 1);
			}
			searchLayout = new HorizontalLayout();
			TextField search = new TextField();
			searchLayout.addComponent(search);
			Button searchButton = new Button();
			searchLayout.addComponent(searchButton);
			searchButton.setIcon(VaadinIcons.SEARCH);
			searchButton.addClickListener((event) -> actionContainer.getSearchTrigger().searchTriggered(search.getValue()));
		}
		
		
		if (gridLayout != null) {
			gridLayout.setWidth(100,Unit.PERCENTAGE);
			gridLayout.setStyleName("smallMargin");
			gridLayout.addComponent(buttonLayout, 0, 0);
			gridLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
			gridLayout.addComponent(searchLayout, 1, 0);
			gridLayout.setComponentAlignment(searchLayout, Alignment.MIDDLE_RIGHT);
			addComponent(gridLayout);
			
		}
		innerComponent.setSizeFull();
		addComponent(innerComponent);
		//gridLayout.set
		setSizeFull();
		setExpandRatio(innerComponent, 1.0f);
		innerComponent.refreshClickable();
	}

	public EasyAppLayout getInnerComponent() {
		// TODO Auto-generated method stub
		return innerComponent;
	}
}