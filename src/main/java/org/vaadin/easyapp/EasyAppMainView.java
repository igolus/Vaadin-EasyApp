package org.vaadin.easyapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.vaadin.cssinject.CSSInject;
import org.vaadin.easyapp.event.LoginTrigger;
import org.vaadin.easyapp.event.LogoutTrigger;
import org.vaadin.easyapp.event.NavigationTrigger;
import org.vaadin.easyapp.event.SearchTrigger;
import org.vaadin.easyapp.ui.ToolBar;
import org.vaadin.easyapp.util.ActionContainer;
import org.vaadin.easyapp.util.AnnotationScanner;
import org.vaadin.easyapp.util.EasyAppLayout;
import org.vaadin.easyapp.util.NavButtonWithIcon;
import org.vaadin.easyapp.util.TreeWithIcon;
import org.vaadin.easyapp.util.UserPasswordPopupView;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
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
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


/**
 * Main class to embed navigation
 * @author igolus
 *
 */
public class EasyAppMainView extends EasyAppLayout  {



	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EasyAppMainView.class);

	private Navigator navigator;

	private Accordion accordion;

	private VerticalLayout navigationLayout;

	private HorizontalSplitPanel downSplitPanel;

	private Component topBar;

	private ArrayList<NavigationTrigger> navigationTriggers = new ArrayList<>();

	private Button collapseButton;

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

	private String navigatorStyleName;

	private String mainViewStyle;

	private String navButtonStyle;

	private String loginText;

	public String getNavButtonStyle() {
		return navButtonStyle;
	}

	public void setNavButtonStyle(String navButtonStyle) {
		this.navButtonStyle = navButtonStyle;
	}

//	void setLoggingUserText(String loggingUserText) {
//		this.loggingUserText = loggingUserText;
//	}
//
//	void setLoggingPassWordText(String loggingPassWordText) {
//		this.loggingPassWordText = loggingPassWordText;
//	}
//
//	void setLoginCaption(String loginCaption) {
//		this.loginCaption = loginCaption;
//	}

//	private String popupLoginStyle;
//
//	private String loggingUserText = "login:";
//
//	private String loggingPassWordText = "password:";
//
//	private UserPasswordPopupView loginPopup;
//
//	private String loginCaption = "Please login to access the application. (test@test.com/passw0rd)";
//
//	private String breadcrumbLabelStyle;
//
//	private HorizontalLayout breadCrumbBar;
//
//	private String buttonLinkStyle;
//
//	private HorizontalLayout toolsLayout;
//
//	private boolean toolBarb;

	private ToolBar toolBar;

	private GridLayout gridBar;

	private Image navigationIcon;

//	private String toolBarStyle;

	private String selectedStyle;

	private static ResourceBundle bundle;

	private static UI targetUI;

	public static UI getTargetUI() {
		return targetUI;
	}

	public GridLayout getGridBar() {
		return gridBar;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

//	public void setButtonLinkStyle(String buttonLinkStyle) {
//		this.buttonLinkStyle = buttonLinkStyle;
//	}
//
//	public void setBreadcrumbLabelStyle(String breadcrumbLabelStyle) {
//		this.breadcrumbLabelStyle = breadcrumbLabelStyle;
//	}
	String getLoginText() {
		return loginText;
	}

	void setLoginText(String loginText) {
		this.loginText = loginText;
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
	void build(UI targetUI) {

		this.targetUI = targetUI;
		initCss();
		downSplitPanel = new HorizontalSplitPanel();

		mainArea = buildMainArea();
		if (getMainViewStyle() != null) {
			mainArea.setStyleName(getMainViewStyle());
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

		addComponent(downSplitPanel);

		setSizeFull();
	}

	private void initCss() {
		InputStream in = getClass().getClassLoader().getResourceAsStream("/css/styles.css");
		if (in != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			try {
				String css = IOUtils.toString(reader);
				new CSSInject(getTargetUI()).setStyles(css);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Unable to load Css style.css", e);
			}
		}
		

	}

	//	private void manageToolBar() {
	//		
	//		getNavigator().addViewChangeListener(new ViewChangeListener() {
	//
	//			@Override
	//			public boolean beforeViewChange(ViewChangeEvent event) {
	//				// TODO Auto-generated method stub
	//				return true;
	//			}
	//
	//			@Override
	//			public void afterViewChange(ViewChangeEvent event) {
	//				if (event.getNewView() instanceof VisitableView) {
	//					getToolBar().inspect((VisitableView) event.getNewView());
	//				}
	//			}});
	//	}

//
//	private void resfreshBreabCrumb() {
//		List<Component> breadCrumbLinkComponent = scanner.getBreadCrumbLinkList();
//		breadCrumbBar.removeAllComponents();
//		for (Iterator iterator = breadCrumbLinkComponent.iterator(); iterator.hasNext();) {
//			Component component = (Component) iterator.next();
//			if (breadcrumbLabelStyle != null) {
//				component.setStyleName(breadcrumbLabelStyle);
//			}
//			breadCrumbBar.addComponent(component);
//			breadCrumbBar.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
//			if (iterator.hasNext()) {
//				Label labelSep = new Label (" >");
//				if (breadcrumbLabelStyle != null) {
//					labelSep.setStyleName(breadcrumbLabelStyle);
//				}
//				breadCrumbBar.addComponent(labelSep);
//				breadCrumbBar.setComponentAlignment(labelSep, Alignment.MIDDLE_CENTER);
//			}
//		}
//	}

	/**
	 * Get the HorizontalSplitPanel usefull if you want to call setSplitPosition
	 * @return
	 */
	public HorizontalSplitPanel getDownSplitPanel() {
		return downSplitPanel;
	}

	private void scanPackage() {
		try {
			scanner = new AnnotationScanner(getNavigator(), packagesToScan, navigationTriggers, this);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Unable to create the AnnotationScanner", e);
		}
	}

//	private UserPasswordPopupView buildLoginPopup(Button innerLoginButton) {
//		VerticalLayout popupContent = new VerticalLayout();
//
//		TextField user = new TextField(loggingUserText);
//		user.setWidth("300px");
//		Binder binder = new Binder<>();
//		binder.forField(user).asRequired();
//
//		// Create the password input field
//		PasswordField password = new PasswordField(loggingPassWordText);
//		password.setWidth("300px");
//		binder.forField(password).asRequired();
//		password.setValue("");
//
//		// Add both to a panel
//		VerticalLayout fields = new VerticalLayout(user, password, innerLoginButton);
//		fields.setCaption(loginCaption);
//		fields.setSpacing(true);
//		fields.setMargin(new MarginInfo(true, true, true, false));
//		fields.setSizeUndefined();
//
//		// The view root layout
//		VerticalLayout viewLayout = new VerticalLayout(fields);
//		viewLayout.setSizeFull();
//		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
//		if (popupLoginStyle != null) {
//			viewLayout.setStyleName(popupLoginStyle);
//		}
//		popupContent.addComponent(viewLayout);
//
//		UserPasswordPopupView popup = new UserPasswordPopupView(null, popupContent);
//		popup.setViewLayout(viewLayout);
//
//		popup.setPasswordField(password);
//		popup.setUserTextField(user);
//		return popup;
//	}

	
	private ComponentContainer buildMainArea() {

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		//verticalLayout.setMargin(false);
		verticalLayout.setStyleName("mainBackGround");
		return verticalLayout;
	}

	private ComponentContainer buildNavigation(ComponentContainer target)  {
		VerticalLayout parentLayout = new VerticalLayout();
		parentLayout.setMargin(false);
		parentLayout.setSpacing(false);
		//GridLayout parentLayout = new GridLayout(1, 2);

		if (navigationIcon != null) {
			parentLayout.addComponent(navigationIcon);
			parentLayout.setComponentAlignment(navigationIcon,  Alignment.TOP_CENTER);
		}

		HorizontalLayout collapseLayout = new HorizontalLayout();
		collapseButton = new Button();
		//collapseButton.setSizeFull();
		collapseButton.setIcon(VaadinIcons.ANGLE_DOUBLE_LEFT);

		collapseLayout.addComponent(collapseButton);
		collapseLayout.setComponentAlignment(collapseButton, Alignment.MIDDLE_RIGHT);
		collapseLayout.setSizeFull();

		navigationLayout = new VerticalLayout();
		navigationLayout.setSizeFull();
		ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(target);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		parentLayout.setSizeFull();

		parentLayout.addComponent(navigationLayout);
		parentLayout.addComponent(collapseButton);
		collapseButton.setWidth("100%");
		parentLayout.setComponentAlignment(collapseButton, Alignment.MIDDLE_RIGHT);
		parentLayout.setExpandRatio(navigationLayout, 1.0f);



		buildAccordion();
		return parentLayout;
	}

	public void buildAccordion() {
		scanPackage();
		Map<RootView, List<NavButtonWithIcon>> listButtonByRootView = scanner.getNavButtonMap();
		accordion = new Accordion();
		
		for (Map.Entry<RootView, List<NavButtonWithIcon>> entry : listButtonByRootView.entrySet()) {
			RootView rootView = entry.getKey();
			List<NavButtonWithIcon> listNavButtons = entry.getValue();
			VerticalLayout vertLayout = new VerticalLayout();
			vertLayout.setMargin(false);
			vertLayout.setSpacing(false);
			for (NavButtonWithIcon navButtonWithIcon : listNavButtons) {
				navButtonWithIcon.setWidth("100%");
				vertLayout.addComponent(navButtonWithIcon);
			}
			Tab tab = accordion.addTab(vertLayout, EasyAppMainView.getBundleValue(rootView.viewName()));
			tab.setIcon(EasyAppMainView.getIcon(rootView.icon()));
		}
		
//		treeByAccordeonItem.forEach((name, treeWithIcon)-> {
//			Resource icon = EasyAppMainView.getIcon(treeWithIcon.getIcon());
//			accordion.addTab(treeWithIcon.getTree(), name, icon);
//		});
//		accordion.setSizeFull();
//		navigationLayout.addComponent(accordion);
//
//		CSSInject css = new CSSInject(getTargetUI());
//
//		//css.setStyles(".v-button { background: #0ea; }");
//
//		css.setStyles(".v-button-Cool .v-button-caption {\r\n" + 
//				"  color: #fff;\r\n" + 
//				"  background-color: #4455aa;\r\n" + 
//				"  border: 1px solid #4455aa;\r\n" + 
//				"  text-shadow:2px 2px 0px #474746;\r\n" + 
//				"  height: 50px;\r\n" + 
//				"  font-family:Trebuchet MS;\r\n" + 
//				"  font-weight: bold;\r\n" + 
//				"}");
//
//		css.setStyles(".v-button-Red .v-button-caption {" + 
//				"    font-weight: bold;" + 
//				"    color:red;" + 
//				"    background-color: #4455aa;" +
//				"}");
//
//		Button testButt1 = new Button("Test");
//		testButt1.addClickListener(new ClickListener() {
//			
//			@Override
//			public void buttonClick(ClickEvent event) {
//				testButt1.setStyleName("Selected");
//				
//			}
//		});
//		testButt1.setStyleName("Nav");
//		testButt1.setIcon(VaadinIcons.ABACUS);
//		testButt1.setWidth("100%");
//
//		Button testButt2 = new Button("Butt2");
//		testButt2.setIcon(VaadinIcons.ACADEMY_CAP);
//		testButt2.setStyleName("Nav");
//		testButt2.setWidth("100%");
//		
//		
//		Button testButt3 = new Button("Butt3");
//		testButt3.setIcon(VaadinIcons.ABSOLUTE_POSITION);
//		testButt3.setStyleName("Nav");
//		testButt3.setWidth("100%");


		//		css.setStyles(".v-button {\r\n" + 
		//				"  color: #fff;\r\n" + 
		//				"  background-color: #4455aa;\r\n" + 
		//				"  border: 1px solid #4455aa;\r\n" + 
		//				"  text-shadow:2px 2px 0px #474746;\r\n" + 
		//				"  height: 50px;\r\n" + 
		//				"  font-family:Trebuchet MS;\r\n" + 
		//				"  font-weight: bold;\r\n" + 
		//				"}");
		//		testButt1.setStyleName("custom-style");


//		VerticalLayout vertLayout = new VerticalLayout();
//		vertLayout.setMargin(false);
//		vertLayout.setSpacing(false);
//		vertLayout.addComponent(testButt1);
//		vertLayout.addComponent(testButt2);
//		vertLayout.addComponent(testButt3);

//		css = new CSSInject(getTargetUI());
//		css.setStyles(".v-label {color:red;}");

//		Label colorMeDynamically = new Label(
//				"Hello Vaadin, in all different colors!");
//		vertLayout.addComponent(colorMeDynamically);

//		Tab tab = accordion.addTab(vertLayout, "Test");
//		tab.setStyleName("Nav");
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
		Field vaadinIconField;
		try {
			vaadinIconField = VaadinIcons.class.getDeclaredField(iconName);
			if (vaadinIconField != null && !iconName.equals(RootView.NOT_SET)) {
				icon = (Resource) vaadinIconField.get(null);
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
	
	/**
	 * Try to get the bundle value 
	 * return value if not found
	 */
	public static String getBundleValue(String value) {
		if (getBundle() != null && getBundle().containsKey(value)) {
			return bundle.getString(value);
		}
		return value;
	}

//	public void setToolbar(boolean toolBar) {
//		this.toolBarb = toolBar;
//
//	}

	public void setNavigationIcon(Image navigationIcon) {
		this.navigationIcon = navigationIcon;
	}

	public static void executeInTargetUI(Runnable runnable) {
		if (targetUI != null) {
			targetUI.access(runnable);
		} 
	}
	
	@Override
	public ActionContainer buildActionContainer() {
		return actionContainer;
	}

	public void setMainStyle(String mainStyle) {
		this.mainViewStyle = mainStyle;
	}
	
	public void setSelectedStyle(String selectedStyle) {
		this.selectedStyle = selectedStyle;
	}

	public String getSelectedStyle() {
		return selectedStyle;
	}

	public void setResourceBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public static ResourceBundle getBundle() {
		return bundle;
	}
	
	
}
