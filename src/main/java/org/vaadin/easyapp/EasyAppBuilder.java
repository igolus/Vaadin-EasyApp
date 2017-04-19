package org.vaadin.easyapp;

import java.util.List;

import org.vaadin.easyapp.event.LoginTrigger;
import org.vaadin.easyapp.event.LogoutTrigger;
import org.vaadin.easyapp.event.SearchTrigger;

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
	
	public EasyAppBuilder withLogingCapabilities(LoginTrigger loginTrigger, LogoutTrigger logoutTrigger) {
		mainView.setLogingCapabilities(true);
		mainView.setLoginTrigger(loginTrigger);
		mainView.setLogoutTrigger(logoutTrigger);
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

	public EasyAppBuilder withLogingUserText(String loggingUserText) {
		mainView.setLoggingUserText(loggingUserText);
		return this;
	}

	public EasyAppBuilder withLogingPassWordText(String loggingPassWordText) {
		mainView.setLoggingPassWordText(loggingPassWordText);
		return this;
	}

	public EasyAppBuilder withLoginCaption(String loginCaption) {
		mainView.setLoginCaption(loginCaption);
		return this;
	}

	public EasyAppBuilder withLoginTextStyle(String styleName) {
		mainView.setUserLabelStyle(styleName);
		return this;
	}

	public EasyAppBuilder withLoginErroText(String loginErrorText) {
		mainView.setLoginErrorText(loginErrorText);
		return this;
	}

	public EasyAppBuilder withLoginErrotLabelStyle(String styleErrorText) {
		mainView.setStyleErrorText(styleErrorText);
		return this;
	}

	public EasyAppBuilder withBreadcrumbStyle(String breadcrumbLabelStyle) {
		mainView.setBreadcrumbLabelStyle(breadcrumbLabelStyle);
		return this;
	}

	public EasyAppBuilder withButtonLinkStyleInBreadCrumb(String buttonStyle) {
		mainView.setBreadcrumbLabelStyle(buttonStyle);
		return this;
	}
	
	

}
