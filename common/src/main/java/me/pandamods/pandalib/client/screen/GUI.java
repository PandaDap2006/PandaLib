package me.pandamods.pandalib.client.screen;

import com.mojang.blaze3d.platform.Window;
import me.pandamods.pandalib.client.screen.impl.ElementImpl;
import me.pandamods.pandalib.client.screen.impl.PanelImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI implements PanelImpl {
	private final GUIScreen screen;
	private final List<ElementImpl> elements = new ArrayList<>();

	public final Minecraft minecraft;
	public final Font font;
	public final Window window;

	public GUI() {
		this.screen = new GUIScreen(this);
		this.minecraft = Minecraft.getInstance();
		this.font = this.minecraft.font;
		this.window = this.minecraft.getWindow();
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

	public void renderBackground(GuiGraphics guiGraphics) {
		screen().renderBackground(guiGraphics);
	}

	public void renderDirtBackground(GuiGraphics guiGraphics) {
		screen().renderDirtBackground(guiGraphics);
	}
}
