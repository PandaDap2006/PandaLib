package me.pandamods.pandalib.client.screen.elements;

import me.pandamods.pandalib.client.screen.GUI;

public interface UIElementProvider {
	UIElement create(GUI gui, UIElement parent);
}