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
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ViewWithToolBar extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		innerComponent.enter(event);
	}

	private EasyAppLayout innerComponent;
	private HorizontalLayout leftLayout;
	private HorizontalLayout searchLayout;

	public ViewWithToolBar() {
		super();
	}

	public ViewWithToolBar(EasyAppLayout innerComponent)   {
		buildComponents(innerComponent);
	}

	public void buildComponents(EasyAppLayout innerComponent) {
		this.innerComponent = innerComponent;
		
		ActionContainer actionContainer = innerComponent.buildActionContainer();
		innerComponent.setActionContainer(actionContainer);
		GridLayout gridLayout = new GridLayout(2, 1);
		List<ButtonWithCheck> listButtonWithCheck = null;
		List<Image> listImages= null;
		if (actionContainer != null) {
			listImages = actionContainer.getListImages();
			listButtonWithCheck = actionContainer.getListButtonWithCheck();
		}
		leftLayout = new HorizontalLayout();
		
		if (listImages != null && listImages.size() > 0) {
			for (Image image : listImages) {
				leftLayout.addComponent(image);
			}
		}
		
		if (listButtonWithCheck != null && listButtonWithCheck.size() > 0) {
			for (ButtonWithCheck buttonWithCheck : listButtonWithCheck) {
				buttonWithCheck.getButton().setStyleName(actionContainer.getStyleName());
				leftLayout.addComponent(buttonWithCheck.getButton());
			}
		}
		
		
		if (actionContainer != null && actionContainer.getSearchTrigger() != null) {
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
			if (leftLayout != null) {
				gridLayout.addComponent(leftLayout, 0, 0);
				gridLayout.setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
			}
			if (searchLayout != null) {
				gridLayout.addComponent(searchLayout, 1, 0);
				gridLayout.setComponentAlignment(searchLayout, Alignment.MIDDLE_RIGHT);
			}
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

	public void setToolBarStyle(String style) {
		leftLayout.setStyleName(style);
		searchLayout.setStyleName(style);
	}
}