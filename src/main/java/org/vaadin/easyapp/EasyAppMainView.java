package org.vaadin.easyapp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.vaadin.easyapp.event.LoginTrigger;
import org.vaadin.easyapp.event.LogoutTrigger;
import org.vaadin.easyapp.event.NavigationTrigger;
import org.vaadin.easyapp.event.SearchTrigger;
import org.vaadin.easyapp.ui.ToolBar;
import org.vaadin.easyapp.util.AnnotationScanner;
import org.vaadin.easyapp.util.TreeWithIcon;
import org.vaadin.easyapp.util.UserPasswordPopupView;
import org.vaadin.easyapp.util.VisitableView;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;


/**
 * Main class to embed navigation
 * @author igolus
 *
 */
public class EasyAppMainView extends VerticalSplitPanel  {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EasyAppMainView.class);

	private Navigator navigator;

	private Accordion accordion;

	private VerticalLayout navigationLayout;

	private HorizontalSplitPanel downSplitPanel;

	private Component topBar;
	
	private ArrayList<NavigationTrigger> navigationTriggers = new ArrayList<>();
	
	public ArrayList<NavigationTrigger> getNavigationTriggers() {
		return navigationTriggers;
	}

	public synchronized void addNavigationTrigger (NavigationTrigger navigationTrigger) {
		navigationTriggers.add(navigationTrigger);
	}
	

	/**
	 * return top bar as a component
	 * @return
	 */
	public Component getTopBar() {
		return topBar;
	}

	public ComponentContainer getMainArea() {
		return mainArea;
	}

	public ComponentContainer getNavigationPanel() {
		return navigationPanel;
	}

	private ComponentContainer mainArea;

	private ComponentContainer navigationPanel;

	private AnnotationScanner scanner;

	public AnnotationScanner getScanner() {
		return scanner;
	}

	private List<String> packagesToScan;

	private Image topBarIcon;

	private boolean logingCapabilities;

	private String loggingTextButton = "login";

	private boolean searchCapabilities;

	private boolean breadcrumb;

	private String TopBarStyleName;

	private String navigatorStyleName;

	private String mainViewStyle;

	private SearchTrigger onSearchListener;

	private Resource iconSearch;

	private String loginText;

	private String loggingUserPrompt;
	
	private String loginErrorText = "Login error";
	
	private String styleErrorText;
	
	public void setLoginErrorText(String loginErrorText) {
		this.loginErrorText = loginErrorText;
	}

	public void setStyleErrorText(String styleErrorText) {
		this.styleErrorText = styleErrorText;
	}

	void setLoggingText(String loggingText) {
		this.loggingTextButton = loggingText;
	}

	void setLoggingUserPrompt(String loggingUserPrompt) {
		this.loggingUserPrompt = loggingUserPrompt;
	}

	void setLoggingUserText(String loggingUserText) {
		this.loggingUserText = loggingUserText;
	}

	void setLoggingPassWordText(String loggingPassWordText) {
		this.loggingPassWordText = loggingPassWordText;
	}

	void setLoginCaption(String loginCaption) {
		this.loginCaption = loginCaption;
	}

	private String popupLoginStyle;

	private String loggingUserText = "login:";

	private String loggingPassWordText = "password:";

	private UserPasswordPopupView loginPopup;

	private String loginCaption = "Please login to access the application. (test@test.com/passw0rd)";

	private String logOutTextButton = "logout";

	private LoginTrigger loginTrigger;

	private LogoutTrigger logoutTrigger;

	private String userLabelStyle;
	
	private Label userLabel;

	private Button logoutButton;

	private Button toolsLoginButton;

	private Label breadcrumbLabel;

	private String breadcrumbLabelStyle;

	private HorizontalLayout breadCrumbBar;

	private String buttonLinkStyle;

	private HorizontalLayout toolsLayout;

	private boolean toolBarb;

	private ToolBar toolBar;

	private GridLayout gridBar;

	public GridLayout getGridBar() {
		return gridBar;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	public void setButtonLinkStyle(String buttonLinkStyle) {
		this.buttonLinkStyle = buttonLinkStyle;
	}

	public void setBreadcrumbLabelStyle(String breadcrumbLabelStyle) {
		this.breadcrumbLabelStyle = breadcrumbLabelStyle;
	}

	void setUserLabelStyle(String userLabelStyle) {
		this.userLabelStyle = userLabelStyle;
	}

	String getLoginText() {
		return loginText;
	}

	void setLoginText(String loginText) {
		this.loginText = loginText;
	}

	public void setIconSearch(Resource iconSearch2) {
		this.iconSearch = iconSearch2;
	}

	void setOnSearchListener(SearchTrigger searchTrigger) {
		this.onSearchListener = searchTrigger;
	}

	String getTopBarStyleName() {
		return TopBarStyleName;
	}

	void setTopBarStyleName(String topBarStyleName) {
		TopBarStyleName = topBarStyleName;
	}

	String getNavigatorStyleName() {
		return navigatorStyleName;
	}

	void setNavigatorStyleName(String navigatorStyleName) {
		this.navigatorStyleName = navigatorStyleName;
	}

	String getMainViewStyle() {
		return mainViewStyle;
	}

	void setMainViewStyle(String mainViewStyle) {
		this.mainViewStyle = mainViewStyle;
	}

	boolean isSearchCapabilities() {
		return searchCapabilities;
	}

	void setSearchCapabilities(boolean searchCapabilities) {
		this.searchCapabilities = searchCapabilities;
	}

	boolean isBreadcrumb() {
		return breadcrumb;
	}

	void setBreadcrumb(boolean breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	boolean isLogingCapabilities() {
		return logingCapabilities;
	}

	void setLogingCapabilities(boolean logingCapabilities) {
		this.logingCapabilities = logingCapabilities;
	}

	Image getTopBarIcon() {
		return topBarIcon;
	}

	void setTopBarIcon(Image topBarIcon) {
		this.topBarIcon = topBarIcon;
	}

	public Navigator getNavigator() {
		return navigator;
	}


	/**
	 * Constructor to use
	 * @param packageToScan the package where your view are stored
	 */
	EasyAppMainView(List<String> packages) {
		super();
		BasicConfigurator.configure();
		this.packagesToScan = packages;
	}

	/**
	 * Buikd the UI
	 */
	void build() {
		downSplitPanel = new HorizontalSplitPanel();

		mainArea = buildMainArea();
		if (getMainViewStyle() != null) {
			mainArea.setStyleName(getMainViewStyle());
		}

		topBar = buildTopBar();
		if (getTopBarStyleName() != null) {
			topBar.setStyleName(getTopBarStyleName());
		}

		navigationPanel = buildNavigation(mainArea);
		if (getNavigatorStyleName() != null) {
			navigationPanel.setStyleName(getNavigatorStyleName());
		}
		
		

		downSplitPanel.setSecondComponent(mainArea);
		downSplitPanel.setFirstComponent(navigationPanel);
		downSplitPanel.setSplitPosition(300, Unit.PIXELS);
		downSplitPanel.setSizeFull();
		downSplitPanel.setLocked(true);

		setSecondComponent(downSplitPanel);
		setFirstComponent(topBar);
		setSplitPosition(10, Unit.PIXELS);

		setSizeFull();
		setLocked(true);
		
		addNavigationTrigger( (clazz) -> resfreshBreabCrumb());
		
		if (getToolBar() != null) {
			manageToolBar();
		}
			
		
	}

	private void manageToolBar() {
		
		getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				if (event.getNewView() instanceof VisitableView) {
					getToolBar().inspect((VisitableView) event.getNewView());
				}
			}});
	}


	private void resfreshBreabCrumb() {
		List<Component> breadCrumbLinkComponent = scanner.getBreadCrumbLinkList();
		breadCrumbBar.removeAllComponents();
		for (Iterator iterator = breadCrumbLinkComponent.iterator(); iterator.hasNext();) {
			Component component = (Component) iterator.next();
			if (breadcrumbLabelStyle != null) {
				component.setStyleName(breadcrumbLabelStyle);
			}
			breadCrumbBar.addComponent(component);
			breadCrumbBar.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
			if (iterator.hasNext()) {
				Label labelSep = new Label (" >");
				if (breadcrumbLabelStyle != null) {
					labelSep.setStyleName(breadcrumbLabelStyle);
				}
				breadCrumbBar.addComponent(labelSep);
				breadCrumbBar.setComponentAlignment(labelSep, Alignment.MIDDLE_CENTER);
			}
		}
	}

	/**
	 * Get the HorizontalSplitPanel usefull if you want to call setSplitPosition
	 * @return
	 */
	public HorizontalSplitPanel getDownSplitPanel() {
		return downSplitPanel;
	}

	private void scanPackage() {
		try {
			scanner = new AnnotationScanner(getNavigator(), packagesToScan, navigationTriggers, buttonLinkStyle);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Unable to create the AnnotationScanner");
		}
	}

	private Component buildTopBar() {
		
		HorizontalLayout horizontalLayoutTopBar = new HorizontalLayout();
		horizontalLayoutTopBar.setSizeFull();
		horizontalLayoutTopBar.setStyleName("topBannerBackGround");
		
		HorizontalLayout lefthHorizontalBar = new HorizontalLayout();
		//icon
		Image image = getTopBarIcon();
		if (image != null) {
			lefthHorizontalBar.addComponent(image);
			//horizontalLayoutTopBar.setComponentAlignment(image, Alignment.MIDDLE_LEFT);
		}
		breadCrumbBar = new HorizontalLayout();
		lefthHorizontalBar.addComponent(breadCrumbBar);
		lefthHorizontalBar.setComponentAlignment(breadCrumbBar, Alignment.MIDDLE_LEFT);
		
		horizontalLayoutTopBar.addComponent(lefthHorizontalBar);
		horizontalLayoutTopBar.setComponentAlignment(lefthHorizontalBar, Alignment.MIDDLE_LEFT);
		
		toolsLayout = new HorizontalLayout();
		if (searchCapabilities) {
			TextField search = new TextField();
			addComponent(search);
			toolsLayout.addComponent(search);
			Button searchButton = new Button();
			if (iconSearch == null) {
				searchButton.setCaption("Search");
			}
			else {
				searchButton.setIcon(iconSearch);
			}

			toolsLayout.addComponent(searchButton);
			searchButton.addClickListener((event) -> onSearchListener.searchTriggered(search.getValue()));
		}

		if (logingCapabilities) {
			if (loginText == null) {
				loginText = "login";
			}
			manageLoginButton();
		}

		horizontalLayoutTopBar.addComponent(toolsLayout);
		horizontalLayoutTopBar.setComponentAlignment(toolsLayout,  Alignment.MIDDLE_RIGHT);
		
		if (toolBarb) {
			VerticalLayout verticalLayout= new VerticalLayout();
			verticalLayout.addComponent(horizontalLayoutTopBar);
			
			toolBar = new ToolBar(gridBar);
			verticalLayout.addComponent(toolBar);
			verticalLayout.setExpandRatio(horizontalLayoutTopBar, 0.7f);
			verticalLayout.setExpandRatio(toolBar, 0.3f);
			return verticalLayout;
		}
		
		return horizontalLayoutTopBar;
	}

	private void manageLoginButton() {
		toolsLoginButton = new Button(loggingTextButton);
		Button innerLoginButton = buidInnerLoginButton(toolsLayout);
		loginPopup = buildLoginPopup(innerLoginButton);
		
		manageLogoutBehavior(logoutButton, toolsLayout);
		
		toolsLayout.addComponents(toolsLoginButton, loginPopup);
		toolsLoginButton.addClickListener(click -> loginPopup.setPopupVisible(true));
	}

	private void manageLogoutBehavior(Button logoutButton, HorizontalLayout toolsLayout) {
		logoutButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				toolsLayout.removeComponent(userLabel);
				toolsLayout.removeComponent(logoutButton);
				manageLoginButton();
				if (logoutTrigger != null) {
					logoutTrigger.logoutTriggered();
				}
			}
		});
		
	}

	private UserPasswordPopupView buildLoginPopup(Button innerLoginButton) {
		VerticalLayout popupContent = new VerticalLayout();

		TextField user = new TextField(loggingUserText);
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt(loggingUserPrompt);

		user.setInvalidAllowed(false);

		// Create the password input field
		PasswordField password = new PasswordField(loggingPassWordText);
		password.setWidth("300px");
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");


		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, innerLoginButton);
		fields.setCaption(loginCaption);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		// The view root layout
		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		if (popupLoginStyle != null) {
			viewLayout.setStyleName(popupLoginStyle);
		}
		popupContent.addComponent(viewLayout);

		UserPasswordPopupView popup = new UserPasswordPopupView(null, popupContent);
		popup.setViewLayout(viewLayout);
		
		popup.setPasswordField(password);
		popup.setUserTextField(user);
		return popup;
	}

	private Button buidInnerLoginButton(HorizontalLayout toolsLayout) {
		Button loginInnerButton = new Button(loggingTextButton);
		logoutButton = new Button(logOutTextButton);
		loginInnerButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (loginTrigger != null) {
					String userLogged = loginTrigger.loginTriggered(loginPopup.getUserTextField().getValue(), 
							loginPopup.getPasswordField().getValue());
					if (userLogged != null) {
						loginPopup.setVisible(false);
						toolsLayout.removeComponent(toolsLoginButton);

						userLabel = new Label(userLogged);
						if (userLabelStyle != null) {
							userLabel.setStyleName(userLabelStyle);
						}
						
						toolsLayout.addComponent(userLabel);
						toolsLayout.addComponent(logoutButton);
						toolsLayout.setComponentAlignment(userLabel, Alignment.MIDDLE_CENTER);
						
					}
					else {
						loginPopup.markAsError(loginErrorText, styleErrorText);
						loginPopup.setVisible(true);
					}
				}
			}
		});
		return loginInnerButton;
	}

	private ComponentContainer buildMainArea() {

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setStyleName("mainBackGround");
		return verticalLayout;
	}

	private ComponentContainer buildNavigation(ComponentContainer target)  {
		VerticalLayout parentLayout = new VerticalLayout();
		navigationLayout = new VerticalLayout();
		navigationLayout.setImmediate(true);
		setFirstComponent(navigationLayout);
		ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(target);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		parentLayout.setSizeFull();
		parentLayout.addComponent(navigationLayout);
		buildAccordion();
		return parentLayout;
	}

	public void buildAccordion() {
		scanPackage();
		buildBreadCrum();
		
		Map<String, TreeWithIcon> treeByAccordeonItem = scanner.getTreeMap();
		accordion = new Accordion();
		treeByAccordeonItem.forEach((name, treeWithIcon)-> {
			Resource icon = EasyAppMainView.getIcon(treeWithIcon.getIcon());
			accordion.addTab(treeWithIcon.getTree(), name, icon);
		});

		accordion.setSizeFull();
		navigationLayout.addComponent(accordion);
	}

	private void buildBreadCrum() {
//		if (breadcrumb) {
//			breadcrumbLabel = new Label("breadcrumb");
//			if (breadcrumbLabelStyle != null) {
//				breadcrumbLabel.setStyleName(breadcrumbLabelStyle);
//			}
//			scanner.getBreadCrumbLinksList(breadcrumbLabelStyle);
//		}
	}

	/**
	 * get the resource based on definitions
	 * @param iconName
	 * @param fontAwasomeField
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Resource getIcon(String iconName) {
		Resource icon = null;
		Field fontAwasomeField;
		try {
			fontAwasomeField = FontAwesome.class.getDeclaredField(iconName);
			if (fontAwasomeField != null && !iconName.equals(RootView.NOT_SET)) {
				icon = (Resource) fontAwasomeField.get(null);
			}

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			if (!iconName.equals(RootView.NOT_SET)){
				icon = new ThemeResource(iconName);
			}
			else {
				logger.error("Unable to find the icon in font awesome, Check the Fiels Name", e);
			}

		}

		return icon;
	}

	public void setLoginTrigger(LoginTrigger loginTrigger) {
		this.loginTrigger = loginTrigger;

	}

	public void setLogoutTrigger(LogoutTrigger logoutTrigger) {
		this.logoutTrigger = logoutTrigger;
	}

	public void setToolbar(boolean toolBar) {
		this.toolBarb = toolBar;
		
	}
}
