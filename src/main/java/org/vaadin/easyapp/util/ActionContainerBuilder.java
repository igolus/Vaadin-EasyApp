package org.vaadin.easyapp.util;

import org.vaadin.easyapp.EasyAppMainView;
import org.vaadin.easyapp.event.SearchTrigger;

import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Image;

public class ActionContainerBuilder {
	
	private ActionContainer actionContainer = new ActionContainer();
	private String bundleName;
	
	public ActionContainerBuilder(String bundleName) {
		this.bundleName = bundleName;
	}
	
	public ActionContainerBuilder addButton(String bundleValue, Resource icon, String description,
			ButtonClickable clickable, ClickListener listener) {
		String buttonName = AnnotationScanner.getBundleValue(bundleName, bundleValue);
		Button button = new Button(buttonName);
		if (description != null) {
			button.setDescription(AnnotationScanner.getBundleValue(bundleName, description));
		} 
		
		button.setIcon(icon);
		if (clickable == null) {
			clickable = () -> {return true;};
		}
		actionContainer.addButtonWithCheck(new ButtonWithCheck(button, clickable));
		button.addClickListener(listener);
		return this;
	}
	
	
	public ActionContainerBuilder addButton(String bundleValue, String iconName, String description,
			ButtonClickable clickable, ClickListener listener) {
		addButton (bundleValue,  EasyAppMainView.getIcon(iconName), description, clickable, listener);
		return this;
	}
	
	public ActionContainerBuilder withStyleName(String styleName) {
		actionContainer.setStyleName(styleName);
		return this;
	}
	
	public ActionContainerBuilder addImageIcon(Image image) {
		actionContainer.addImageIcon(image);
		return this;
	}
	
	
	public ActionContainerBuilder setSearch(SearchTrigger searchTrigger) {
		actionContainer.setSearch(searchTrigger);
		return this;
	}
	
	public ActionContainer build() {
		return actionContainer;
	} 
	
}
