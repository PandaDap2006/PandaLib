package me.pandamods.pandalib.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.pandamods.pandalib.PandaLib;
import me.pandamods.pandalib.client.screen.GUIScreen;
import me.pandamods.test.client.screen.TestScreen;

public class PandaLibModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return screen -> new TestScreen().screen();
	}
}
