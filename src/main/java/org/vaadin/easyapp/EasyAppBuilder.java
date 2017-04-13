package org.vaadin.easyapp;

import java.util.List;

import org.vaadin.easyapp.util.SearchTrigger;

import com.vaadin.server.Resource;
import com.vaadin.ui.Image;

public class EasyAppBuilder {
	
	public EasyAppBuilder(List<String> packages) {
		mainView = new EasyAppMainView(packages);
	}
	
	private EasyAppMainView mainView;

	public EasyAppMainView build() {
		mainView.build();
		return mainView;
	}
	
	public EasyAppBuilder withTopBarIcon(Image topBarIcon) {
		mainView.setTopBarIcon(topBarIcon);
		return this;
	}
	
	public EasyAppBuilder withLogingCapabilities() {
		mainView.setLogingCapabilities(true);
		return this;
	}
	
	public EasyAppBuilder withSearchCapabilities(SearchTrigger searchTrigger, Resource iconSearch) {
		mainView.setSearchCapabilities(true);
		mainView.setOnSearchListener(searchTrigger);
		mainView.setIconSearch(iconSearch);
		return this;
	}
	
	public EasyAppBuilder withBreadcrumb() {
		mainView.setBreadcrumb(true);
		return this;
	}
	
	public EasyAppBuilder withTopBarStyle(String style) {
		mainView.setTopBarStyleName(style);
		return this;
	}
	
	public EasyAppBuilder withNavigatorStyle(String style) {
		mainView.setNavigatorStyleName(style);
		return this;
	}
	
	public EasyAppBuilder withMainViewStyle(String style) {
		mainView.setMainViewStyle(style);
		return this;
	}

}
