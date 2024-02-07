package me.pandamods.pandalib.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class GUIScreen extends Screen {
	private final GUI gui;

	protected GUIScreen(GUI gui) {
		super(gui.title());
		this.gui = gui;
	}

	@Override
	protected void init() {
		this.gui.init();
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		gui.render(guiGraphics, mouseX, mouseY, partialTick);
	}
}
