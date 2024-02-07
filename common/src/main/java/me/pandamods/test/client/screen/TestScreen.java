package me.pandamods.test.client.screen;

import me.pandamods.pandalib.client.screen.GUI;
import me.pandamods.pandalib.client.screen.elements.buttons.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TestScreen extends GUI {
	@Override
	public void initElement() {
		register(new Button(null, 0, 0, 200, 20, Component.literal("test")));
	}

	@Override
	public void renderElement(GuiGraphics guiGraphics, double mouseX, double mouseY, float partialTick) {
		renderBackground(guiGraphics);
	}
}
