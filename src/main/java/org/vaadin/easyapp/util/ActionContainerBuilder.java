package org.vaadin.easyapp.util;

import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.event.SearchTrigger;
import org.vaadin.easyapp.util.ActionContainer.InsertPosition;
import org.vaadin.easyapp.util.ActionContainer.Position;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;

public class ActionContainerBuilder {
	
	private ActionContainer actionContainer = new ActionContainer();
	private String bundleName;
	
	public ActionContainerBuilder(String bundleName) {
		this.bundleName = bundleName;
	}
	
	public ActionContainerBuilder addButton(String bundleValue, Resource icon, String description,
			ButtonClickable clickable, ClickListener listener, Position position, InsertPosition insertPosition) {
		String buttonName = AnnotationScanner.getBundleValue(bundleName, bundleValue);

		
		Button button = new Button(buttonName);
		if (description != null) {
			button.setDescription(AnnotationScanner.getBundleValue(bundleName, description));
		} 
		
		button.setIcon(icon);
		if (clickable == null) {
			clickable = () -> {return true;};
		}
		actionContainer.addButtonWithCheck(new ButtonWithCheck(buttonName, clickable), position, insertPosition);
		button.addClickListener(listener);
		return this;
	}
	
	
	public ActionContainerBuilder addButton(String bundleValue, String iconName, String description,
			ButtonClickable clickable, ClickListener listener, Position position, InsertPosition insertPosition) {
		addButton (bundleValue,  EasyAppMainView.getIcon(iconName), description, clickable, listener, position, insertPosition);
		return this;
	}
	
//	public ActionContainerBuilder withStyleName(String styleName) {
//		actionContainer.setStyleName(styleName);
//		return this;
//	}
//	
	public ActionContainerBuilder addImageIcon(Image image, Position position, InsertPosition insertPosition) {
		actionContainer.addImageIcon(image, position, insertPosition);
		return this;
	}
	
	public ActionContainerBuilder addSearch(SearchTrigger searchTrigger, Position position, InsertPosition insertPosition) {
		
		HorizontalLayout searchLayout = new HorizontalLayout();
		//searchLayout.setStyleName(actionContainer.getStyleName());
		TextField search = new TextField();
		search.setHeight("100%");
		//search.setStyleName(actionContainer.getStyleName());
		searchLayout.addComponent(search);
		Button searchButton = new Button();
		//searchButton.setStyleName(actionContainer.getStyleName());
		searchButton.setHeight("100%");
		searchLayout.addComponent(searchButton);
		searchButton.setIcon(VaadinIcons.SEARCH);
		//searchButton.setStyleName(actionContainer.getStyleName());
		searchButton.addClickListener(event -> searchTrigger.searchTriggered(search.getValue()));
		
		actionContainer.addComponent(searchLayout, position, insertPosition);
		return this;
	}
	
	public ActionContainer build() {
		return actionContainer;
	}

	
}
