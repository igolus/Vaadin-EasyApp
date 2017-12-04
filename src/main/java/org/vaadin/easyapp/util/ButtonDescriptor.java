package org.vaadin.easyapp.util;

import com.vaadin.server.Resource;

public class ButtonDescriptor {
	
	private String name;
	private String tooltip;
	private Resource imageResource;
	private String key;
	
	public String getName() {
		return name;
	}
	public ButtonDescriptor(String name, String tooltip, Resource imageResource, String key) {
		super();
		this.name = name;
		this.tooltip = tooltip;
		this.imageResource = imageResource;
		this.key = key;
	}
	
	public String getTooltip() {
		return tooltip;
	}
	
	public Resource getImageResource() {
		return imageResource;
	}
	
	public String getKey() {
		return key;
	}
	
	
	
}
