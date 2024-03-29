package me.pandamods.pandalib.api.client.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ScreenHooks {
	public static List<GuiEventListener> getChildren(Screen screen) {
		return screen.children;
	}

	public static List<NarratableEntry> getNarratables(Screen screen) {
		return screen.narratables;
	}

	public static List<Renderable> getRenderables(Screen screen) {
		return screen.renderables;
	}
}
