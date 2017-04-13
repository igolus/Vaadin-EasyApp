package org.vaadin.easyapp;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.vaadin.easyapp.util.AnnotationScanner;
import org.vaadin.easyapp.util.SearchTrigger;
import org.vaadin.easyapp.util.TreeWithIcon;
import org.vaadin.easyapp.util.annotations.RootView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


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

	private List<String> packagesToScan;

	private Image topBarIcon;

	private boolean logingCapabilities;
	
	private boolean searchCapabilities;
	
	private boolean breadcrumb;
	
	private String TopBarStyleName;
	
	private String navigatorStyleName;

	private String mainViewStyle;

	private SearchTrigger onSearchListener;

	private Resource iconSearch;

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
		setSplitPosition(55, Unit.PIXELS);
		
		setSizeFull();
		setLocked(true);
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
			scanner = new AnnotationScanner(getNavigator(), packagesToScan);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("Unable to create the AnnotationScanner");
		}
	}

	private Component buildTopBar() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		
		HorizontalLayout toolsLayout = new HorizontalLayout();
		
		
		
		Image image = getTopBarIcon();
		if (image != null) {
			horizontalLayout.addComponent(image);
			horizontalLayout.setComponentAlignment(image, Alignment.MIDDLE_LEFT);
		}
		
		
		horizontalLayout.setStyleName("topBannerBackGround");
		
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
		
		horizontalLayout.addComponent(toolsLayout);
		horizontalLayout.setComponentAlignment(toolsLayout,  Alignment.MIDDLE_RIGHT);
		return horizontalLayout;
		
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
		Map<String, TreeWithIcon> treeByAccordeonItem = scanner.getTreeMap();
		accordion = new Accordion();
		treeByAccordeonItem.forEach((name, treeWithIcon)-> {
			Resource icon = EasyAppMainView.getIcon(treeWithIcon.getIcon());
			accordion.addTab(treeWithIcon.getTree(), name, icon);
		});
		
        accordion.setSizeFull();
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



	
}
