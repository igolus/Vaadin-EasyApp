# Vaadin-EasyApp
EasyApp is a tool for vaadin that will generate a UI based on the attributes defines in your components.

![enter image description here](https://github.com/igolus/Vaadin-EasyApp/blob/master/docimg/application.png?raw=true)

### Installation
Easy app requires maven to run.

```sh
$ cd Vaadin-EasyApp
$ mvn install
```
# Usage
 - Extends the vaadin UI as usual 
 - Create a EasyAppMainView component giving the packages where your views are defined ex : *org.vaadin.easyApp.demo.view*
 - Define the icon you want to display in the Top Bar (Optional)

```
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

/**
 * VAAIN Easy APP sample
 */
@Theme("mytheme")
public class AddonDemoApplication extends UI {

    private EasyAppMainView easyAppMainView;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
        
        Image image = new Image(null, new ThemeResource("favicon.ico"));
		image.setWidth(50, Unit.PIXELS);
		image.setHeight(50, Unit.PIXELS);
		
		easyAppMainView = new EasyAppBuilder(Collections.singletonList("org.vaadin.easyApp.demo.view"))
        	.withTopBarIcon(image)
        	.withTopBarStyle("topBannerBackGround")
        	.withSearchCapabilities( (searchValue) -> search(searchValue) , FontAwesome.SEARCH)
        	.withLogingCapabilities( 
        			(user, password) -> loginAttempt(user, user),
        			() -> logout()
			)
        	.withLogingUserText("login:")
        	.withLogingPassWordText("password:")
        	.withLoginCaption("Please login to access the application. (test@test.com/passw0rd)")
        	.withLoginTextStyle("userLogged")
        	.withLoginErroText("Bad credentials")
        	.withLoginErrotLabelStyle("error")
        	.withBreadcrumb()
        	.withBreadcrumbStyle("breadcrumbStyle")
        	.withButtonLinkStyleInBreadCrumb(BaseTheme.BUTTON_LINK)
        	//.withLoginPopupLoginStyle("propupStyle")
        	.build();
	
		
		layout.addComponents(easyAppMainView);
        
		easyAppMainView.getTopBar().setStyleName("topBannerBackGround");
        
        setContent(layout);
    }
    
    ....
```

 - Create your components.

![enter image description here](https://github.com/igolus/Vaadin-EasyApp/blob/master/docimg/sample1.png?raw=true)
 
 - Note that all your components should implements com.vaadin.navigator.View
```
    @ContentView(sortingOrder=1, viewName = "Panel", icon = "ANDROID", rootViewParent = HomeRoot.class, homeView = true)
@SuppressWarnings("serial")
public class ViewOne extends VerticalLayout implements View {

    public ViewOne() {
        setSpacing(true);
        
        Panel panel = new Panel("Astronomer Panel");
        panel.addStyleName("mypanelexample");
        panel.setSizeUndefined(); // Shrink to fit content
        AbstractOrderedLayout layout;
		

        // Create the content
        FormLayout content = new FormLayout();
        content.addStyleName("mypanelcontent");
        content.addComponent(new TextField("Participant"));
        content.addComponent(new TextField("Organization"));
        content.setSizeUndefined(); // Shrink to fit
        content.setMargin(true);
        panel.setContent(content);
        panel.setSizeFull();
        addComponent(panel);

    }

	public void enter(ViewChangeEvent event) {
		Notification.show("Entering view 1");
	}

}
```
 
## RootView Annotation
![enter image description here](https://github.com/igolus/Vaadin-EasyApp/blob/master/docimg/rootView.png?raw=true)

Create a class with @RootView tag to create a root container that will be displayed within an accordion

    package org.vaadin.easyApp.demo.view;
    
    import org.vaadin.easyapp.util.annotations.RootView;
    
    @RootView(sortingOrder=1, viewName = "Home", icon = "HOME")
    public class HomeRoot {
    	
    }

Icon could be link to a Themed resources or a FontAwesome icon.

## ContentView Annotation
![enter image description here](https://github.com/igolus/Vaadin-EasyApp/blob/master/docimg/contentView.png?raw=true)
Create a view that will be displayed in the navigator.

    @ContentView(sortingOrder=2, viewName = "Table", icon = "icons/Java-icon.png", rootViewParent = HomeRoot.class, componentParent = ViewOne.class)

 - **sortingOrder** the order where the item will be displayed.
 - **viewName** the display used in the navigator
 - **icon** Themed resource ot FontAwesome icon
 - **rootViewParent** a link to a RootView tagged class indicate in which accordion it should be displayed 
 - **componentParent**  define the parent component.

# License
Apache 2.0

