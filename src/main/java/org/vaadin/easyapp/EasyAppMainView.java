package org.vaadin.easyapp;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.vaadin.easyapp.ui.ViewWithToolBar;
import org.vaadin.easyapp.util.ActionContainer;
import org.vaadin.easyapp.util.AnnotationScanner;
import org.vaadin.easyapp.util.EasyAppLayout;
import org.vaadin.easyapp.util.NavButtonWithIcon;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


/**
 * Main class to embed navigation
 * @author guillaume Rousseau
 *
 */
public class EasyAppMainView extends EasyAppLayout  {

	private static final int DEFAULT_NAV_SPLIT_POSITION = 300;

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(EasyAppMainView.class);

	private Navigator navigator;

	private VerticalLayout navigationLayout;

	private HorizontalSplitPanel downSplitPanel;

	private Component topBar;


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

	private String navButtonStyle;

	private GridLayout gridBar;

	private Image navigationIcon;

	private static EasyAppMainView Instance;

	private static String selecteNavigationButtonStyle;

	private static String contentStyle;
	
	private static boolean menuCollapsable;

	private static ResourceBundle bundle;

	private static UI targetUI;
	
	private static int navigatorSplitPosition;
	
	public static int getNavigatorSplitPosition() {
		if (navigatorSplitPosition == 0) {
			return DEFAULT_NAV_SPLIT_POSITION;
		}
		return navigatorSplitPosition;
	}

	public static void setNavigatorSplitPosition(int navigatorSplitPosition) {
		EasyAppMainView.navigatorSplitPosition = navigatorSplitPosition;
	}
	
	private static boolean navigatorPanelCollapsed = false;

	private static String actionContainerStyle;

	private static String topBarStyle;

	private static String applicationTitle;

	private static String contextualTextLabelStyle;
	
	public static boolean isNavigatorPanelCollapsed() {
		return EasyAppMainView.navigatorPanelCollapsed;
	}


	private void collapseNavigatorPanel() {
		if (downSplitPanel != null) {
			downSplitPanel.setSplitPosition(0);
		}
		EasyAppMainView.navigatorPanelCollapsed = true;
	}
	
	private void restoreNavigatorPanel() {
		if (downSplitPanel != null) {
			downSplitPanel.setSplitPosition(getNavigatorSplitPosition());
		}
		EasyAppMainView.navigatorPanelCollapsed  = false;
	}
	
	public void switchNavigatorPanelDisplay() {
		if (EasyAppMainView.navigatorPanelCollapsed) {
			restoreNavigatorPanel();
		}
		else {
			collapseNavigatorPanel();
		}
	}

	public String getNavButtonStyle() {
		return navButtonStyle;
	}

	public void setNavButtonStyle(String navButtonStyle) {
		this.navButtonStyle = navButtonStyle;
	}


	public static UI getTargetUI() {
		return targetUI;
	}

	public GridLayout getGridBar() {
		return gridBar;
	}

	public String getNavigatorStyleName() {
		return navigatorStyleName;
	}

	public void setNavigatorStyleName(String navigatorStyleName) {
		this.navigatorStyleName = navigatorStyleName;
	}

	public Image getTopBarIcon() {
		return topBarIcon;
	}

	public void setTopBarIcon(Image topBarIcon) {
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
	public void build(UI targetUI) {

		EasyAppMainView.targetUI = targetUI;
		initCss();
		downSplitPanel = new HorizontalSplitPanel();

		mainArea = buildMainArea();

		navigationPanel = buildNavigation(mainArea);
		if (getNavigatorStyleName() != null) {
			navigationPanel.setStyleName(getNavigatorStyleName());
		}

		downSplitPanel.setSecondComponent(mainArea);
		downSplitPanel.setFirstComponent(navigationPanel);
		downSplitPanel.setSplitPosition(getNavigatorSplitPosition(), Unit.PIXELS);
		downSplitPanel.setSizeFull();
		downSplitPanel.setLocked(true);

		addComponent(downSplitPanel);

		setSizeFull();
		Instance = this;
	}
	
	public static EasyAppMainView getInstance() {
		return Instance;
	}
	
	private Label contextualTextLabel = null;

	private View currentView;
	
	public View getCurrentView() {
		return currentView;
	}

	public void setContextualText (String text) {
		ViewWithToolBar viewWithToolBar = getViewWithToolBarSource();
		if (viewWithToolBar != null && viewWithToolBar.getLeftLayout() != null) {
			if (viewWithToolBar.getLeftLayout().getComponentIndex(contextualTextLabel) == -1) {
				if (contextualTextLabel == null) {
					contextualTextLabel = new Label();
					contextualTextLabel.setStyleName(getContextualTextLabelStyle());
				}
				viewWithToolBar.getLeftLayout().addComponent(contextualTextLabel);
			}
			contextualTextLabel.setValue(text);
		}
	}
	
	public void removeContextualText () {
		ViewWithToolBar viewWithToolBar = getViewWithToolBarSource();
		if (viewWithToolBar != null && viewWithToolBar.getLeftLayout() != null) {
			if (viewWithToolBar.getLeftLayout().getComponentIndex(contextualTextLabel) != -1) {
				viewWithToolBar.getLeftLayout().removeComponent(contextualTextLabel);
			}
		}
	}
	



	private void initCss() {
//		InputStream in = getClass().getClassLoader().getResourceAsStream("/css/styles.css");
//		if (in != null) {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//			try {
//				String css = IOUtils.toString(reader);
//				new CSSInject(getTargetUI()).setStyles(css);
//			} catch (IOException e) {
//				logger.error("Unable to load Css style.css", e);
//			}
//		}
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
			scanner = new AnnotationScanner(getNavigator(), packagesToScan , this);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Unable to create the AnnotationScanner", e);
		}
	}

	
	private ComponentContainer buildMainArea() {

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setStyleName("mainBackGround");
		return verticalLayout;
	}

	private ComponentContainer buildNavigation(ComponentContainer target)  {
		VerticalLayout parentLayout = new VerticalLayout();
		parentLayout.setMargin(false);
		parentLayout.setSpacing(false);
		if (navigationIcon != null) {
			parentLayout.addComponent(navigationIcon);
			parentLayout.setComponentAlignment(navigationIcon,  Alignment.TOP_CENTER);
		}
		navigationLayout = new VerticalLayout();
		navigationLayout.setSizeFull();
		ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(target);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		navigator.addViewChangeListener(this::viewChanged);
		parentLayout.setSizeFull();

		parentLayout.addComponent(navigationLayout);

		parentLayout.setExpandRatio(navigationLayout, 1.0f);

		buildAccordion();
		return parentLayout;
	}
	
	private boolean viewChanged(ViewChangeEvent event) {
		this.currentView = event.getNewView();
		return true;
	}
	
	

	public void buildAccordion() {
		scanPackage();
		Map<RootView, List<NavButtonWithIcon>> listButtonByRootView = scanner.getNavButtonMap();
		Accordion accordion = new Accordion();
		
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
		accordion.setSizeFull();
		accordion.setStyleName(getNavigatorStyleName());
		navigationLayout.addComponent(accordion);

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

	public static void setSelectedNavigationButtonStyle(String selectedStyle) {
		EasyAppMainView.selecteNavigationButtonStyle = selectedStyle;
	}

	public static String getSelectedNavigationButtonStyle() {
		return selecteNavigationButtonStyle;
	}

	public static void setResourceBundle(ResourceBundle bundle) {
		EasyAppMainView.bundle = bundle;
	}

	public static ResourceBundle getBundle() {
		return bundle;
	}

	public static void setContentStyle(String contentStyle) {
		EasyAppMainView.contentStyle = contentStyle;
	}

	public static String getContentStyle() {
		return EasyAppMainView.contentStyle;
	}

	public static void setMenuCollapsable(boolean value) {
		EasyAppMainView.menuCollapsable = value;
	}

	public static boolean isMenuCollapsable() {
		return menuCollapsable;
	}

	public static void setActionContainerStyle(String style) {
		EasyAppMainView.actionContainerStyle = style;
	}

	public static String getActionContainerStyle() {
		return actionContainerStyle;
	}

	public static String getTopBarStyle() {
		return topBarStyle;
	}

	public static void setTopBarStyle(String topBarStyle) {
		EasyAppMainView.topBarStyle = topBarStyle;
	}

	public static void setApplicationTitle(String title) {
		EasyAppMainView.applicationTitle = title;
	}

	public static String getApplicationTitle() {
		return applicationTitle;
	}
	
	private static String getContextualTextLabelStyle() {
		// TODO Auto-generated method stub
		return EasyAppMainView.contextualTextLabelStyle;
	}

	public static void setContextualTextLabelStyle(String contextualTextLabelStyle) {
		EasyAppMainView.contextualTextLabelStyle = contextualTextLabelStyle;
	}

	@Override
	public void enterInView(ViewChangeEvent event) {
	}
	
}
