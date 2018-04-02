package org.vaadin.easyapp;

import java.util.List;

import org.vaadin.easyapp.event.LoginTrigger;
import org.vaadin.easyapp.event.LogoutTrigger;
import org.vaadin.easyapp.event.SearchTrigger;
import org.vaadin.easyapp.ui.ViewWithToolBar;
import org.vaadin.easyapp.util.ActionContainer;

import com.vaadin.server.Resource;
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

	public EasyAppBuilder withBreadcrumbStyle(String breadcrumbLabelStyle) {
		mainView.setBreadcrumbLabelStyle(breadcrumbLabelStyle);
		return this;
	}

	public EasyAppBuilder withButtonLinkStyleInBreadCrumb(String buttonStyle) {
		mainView.setBreadcrumbLabelStyle(buttonStyle);
		return this;
	}
	
	public EasyAppBuilder withToolBar() {
		mainView.setToolbar(true);
		return this;
	}

	public void withNavigationStlyle(String mainStyle, String selectedStyle) {
		mainView.setMainStyle(mainStyle);
		mainView.setSelectedStyle(selectedStyle);
	}

}
