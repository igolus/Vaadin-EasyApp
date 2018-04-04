package org.vaadin.easyapp;

import java.util.List;
import java.util.ResourceBundle;

import org.vaadin.easyapp.ui.ViewWithToolBar;
import org.vaadin.easyapp.util.ActionContainer;

import com.vaadin.ui.Image;
import com.vaadin.ui.UI;

public class EasyAppBuilder {
	
	public EasyAppBuilder(List<String> packages) {
		EasyAppMainView easyAppMainView = new EasyAppMainView(packages);
		this.mainView = easyAppMainView;
		this.viewWithToolBar = new ViewWithToolBar();
	}
	
	private ViewWithToolBar viewWithToolBar;
	private EasyAppMainView mainView;
	
	public ViewWithToolBar build(UI targetUI) {
		mainView.build(targetUI);
		viewWithToolBar.buildComponents(mainView);
		return viewWithToolBar;
	}
	
	public EasyAppBuilder withActionContainer(ActionContainer actionContainer) {
		mainView.setActionContainer(actionContainer);
		return this;
	}
	
	public EasyAppBuilder withToolBarStyle(String style) {
		viewWithToolBar.setToolBarStyle(style);
		return this;
	}
	
	public EasyAppBuilder withTopBarIcon(Image topBarIcon) {
		mainView.setTopBarIcon(topBarIcon);
		return this;
	}
	
	public EasyAppBuilder withNavigationIcon(Image topBarIcon) {
		mainView.setNavigationIcon(topBarIcon);
		return this;
	}
	
	public EasyAppBuilder withNavigatorStyle(String style) {
		mainView.setNavigatorStyleName(style);
		return this;
	}
	
	public void withNavigationStyle(String mainStyle, String selectedStyle) {
		mainView.setMainNavigationButtonStyle(mainStyle);
		EasyAppMainView.setSelectedNavigationButtonStyle(selectedStyle);
	}

	public void withRessourceBundle(ResourceBundle bundle) {
		EasyAppMainView.setResourceBundle(bundle);
		
	}

	public void withContentStyle(String contentStyle) {
		EasyAppMainView.setContentStyle(contentStyle);
		
	}

}
