# Vaadin-EasyApp

EasyApp is a tool for vaadin that will generate a UI based on the attributes defines in your components.

### Installation

Easy app requires maven to run.

```sh
$ cd Vaadin-EasyApp
$ mvn install
```
# Usage

 - Extends the vaadin UI as usual 
 - Create a EasyAppMainView component giving the package where your views are defined ex : *org.vaadin.easyApp.demo.view*
 - Define the icon you want to display in the Top Bar (Optional)

```
@Theme("mytheme")
public class AddonDemoApplication extends UI {

    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
        EasyAppMainView easyAppMainView = new EasyAppMainView("org.vaadin.easyApp.demo.view") {

			@Override
			protected Image getTopBarImage() {
				Image image = new Image(null, new ThemeResource("favicon.ico"));
				image.setWidth(50, Unit.PIXELS);
				image.setHeight(50, Unit.PIXELS);
				return image;
			}
        };
		layout.addComponents(easyAppMainView);
        
		easyAppMainView.getTopBar().setStyleName("topBannerBackGround");
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AddonDemoApplication.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
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
 
TO BE CONTINUED ...


# License
Apache 2.0

