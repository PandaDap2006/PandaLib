package me.pandamods.pandalib.client.screen;

import me.pandamods.pandalib.client.screen.elements.ElementImpl;
import me.pandamods.pandalib.client.screen.elements.GUIImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI implements GUIImpl {
	private final GUIScreen screen;
	private final List<ElementImpl> elements = new ArrayList<>();

	public GUI() {
		this.screen = new GUIScreen(this);
	}

	@Override
	public List<ElementImpl> elements() {
		return elements;
	}

	public Component title() {
		return Component.empty();
	}

	public Screen screen() {
		return screen;
	}
}
