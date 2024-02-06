package me.pandamods.pandalib.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GUIScreen extends Screen {
	protected GUIScreen(GUI gui) {
		super(gui.title());
	}
}
